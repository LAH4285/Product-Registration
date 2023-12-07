package com.example.demo.core.security;

import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import com.example.demo.core.error.exception.Exception401;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    // ** CustomUserDetailsService 의 loadUserByUsername 메서드는 사용에 의해 자동으로자가 로그인을 시도할 때 Spring Security 실행.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // ** CustomUserDetailsService 클래스는 Spring Security의 UserDetailsService 인터페이스를 구현하여, 사용자 이름으로 사용자 정보를 로드하는 로직을 제공합니다.
        // ** 인증 과정에서 사용자 이름을 통해 사용자 정보를 찾을 때 실행.
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new Exception401("인증되지 않았습니다.")
        );

        return new CustomUserDetails(user);
    }
}















