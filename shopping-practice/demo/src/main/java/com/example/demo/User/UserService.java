package com.example.demo.User;

import com.example.demo.core.error.exception.Exception400;
import com.example.demo.core.error.exception.Exception401;
import com.example.demo.core.error.exception.Exception500;
import com.example.demo.core.security.CustomUserDetails;
import com.example.demo.core.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void join(UserRequest.JoinDTO requestDTO) throws Exception500 {
        checkEmail(requestDTO.getEmail());


        String encodedPassword = passwordEncoder.encode(requestDTO.getPassword());
        requestDTO.setPassword(encodedPassword);

        userRepository.save(requestDTO.toEntity());
    }

    public String login(UserRequest.JoinDTO requestDTO) {
        // ** 인증 작업.
        try{
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword());

            // ** anonymousUser = 비인증
            Authentication authentication =  authenticationManager.authenticate(
                    usernamePasswordAuthenticationToken
            );

            // ** 인증 완료 값을 받아온다.
            CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal();



            // ** 토큰 발급.
            return JwtTokenProvider.create(customUserDetails.getUser());

        }catch (Exception e){
            // 401 반환.
            String encodedPassword = passwordEncoder.encode("qwer1234!");
            System.out.println(encodedPassword);
            log.info("인증번호" + encodedPassword);
            throw new Exception401("인증되지 않음.");
        }
    }

    public void findAll() {
        List<User> all = userRepository.findAll();
        for(User user : all){
            user.output();
        }
    }

    public void checkEmail(String email){
        // 동일한 이메일이 있는지 확인.
        Optional<User> users = userRepository.findByEmail(email);
        if(users.isPresent()) {
            throw new Exception400("이미 존재하는 이메일 입니다. : " + email);
        }
    }
}