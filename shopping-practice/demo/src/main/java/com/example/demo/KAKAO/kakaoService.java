package com.example.demo.KAKAO;

import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import com.example.demo.User.UserRequest;
import com.example.demo.core.error.exception.Exception400;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
public class kakaoService {

    @Autowired
    private KakaoUri kakaoUri;
    @Autowired
    private UserRepository userRepository;

    public String getAuthorizationUrl() {
        String authorizationUrl = "https://kauth.kakao.com/oauth/authorize?client_id=" + kakaoUri.CLIENT_ID
                + "&redirect_uri=" + kakaoUri.REDIRECT_URI
                + "&response_type=code";

        return authorizationUrl;
    }

    public KakaoTokenResponse getKakaoAccessToken(String code){

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", kakaoUri.CLIENT_ID);
        parameters.add("redirect_uri", kakaoUri.REDIRECT_URI);
        parameters.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<KakaoTokenResponse> responseEntity = restTemplate.exchange(kakaoUri.KAKAO_TOKEN_API_URL, HttpMethod.POST, requestEntity, KakaoTokenResponse.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            KakaoTokenResponse tokenResponse = responseEntity.getBody();
            if (tokenResponse != null) {
                String accessToken = tokenResponse.getAccess_token();
                String refreshToken = tokenResponse.getRefresh_token();
                int expiresIn = tokenResponse.getExpires_in();
                int refreshTokenExpiresIn = tokenResponse.getRefresh_token_expires_in();
                String scope = tokenResponse.getScope();
                String tokenType = tokenResponse.getToken_type();


                log.info("Access Token: " + accessToken);
                log.info("Refresh Token: " + refreshToken);
                log.info("Expires In: " + expiresIn);
                log.info("Refresh Token Expires In: " + refreshTokenExpiresIn);
                log.info("Scope: " + scope);
                log.info("Token Type: " + tokenType);
            }
        }
        return responseEntity.getBody();
    }

    public JsonNode userInfo(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json;charset=utf-8");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(kakaoUri.KAKAO_USER_ME_API_URL, HttpMethod.GET, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getBody());

        return jsonNode;
    }
    public String kakaoJoin(String code) throws JsonProcessingException {
        KakaoTokenResponse tokenResponse = getKakaoAccessToken(code);
        String accessToken = tokenResponse.getAccess_token();
        JsonNode userinfo = userInfo(accessToken);
        UserRequest.JoinDTO joinDTO = new UserRequest.JoinDTO();
        JsonNode properties = userinfo.path("properties");
        JsonNode kakao_account = userinfo.path("kakao_account");

        joinDTO.setEmail(kakao_account.path("email").asText());
        joinDTO.setUserName(properties.path("nickname").asText());

        try {
            userRepository.save(joinDTO.toEntity());
            log.info("저장정보 : " + userRepository.save(joinDTO.toEntity()));
        }catch (Exception e){
            throw new Exception400(e.getMessage());
        }
        return "/kakaologin.html";
    }

    public String login(String code, HttpSession session) {
        KakaoTokenResponse tokenResponse = getKakaoAccessToken(code);

        if (tokenResponse != null) {
            String accessToken = tokenResponse.getAccess_token();
            try {
                JsonNode userInfo = userInfo(accessToken);
                session.setAttribute("access_token", tokenResponse.getAccess_token());
                System.out.println("\nkakao code:" + code);
                JsonNode properties = userInfo.path("properties");
                JsonNode kakao_account = userInfo.path("kakao_account");
                System.out.println(userInfo.toPrettyString());

                String email = kakao_account.path("email").asText();
                String Nickname = properties.path("nickname").asText();

                boolean isEmailExist = checkEmail(email);

                if (isEmailExist) {
                    log.info("User already exists. Logging in...");
                    return "/loginG.html";
                } else {
                    User newUser = User.builder()
                            .email(email)
                            .password(accessToken)
                            .userName(Nickname)
                            .roles(Collections.singletonList("KAKAO"))
                            .build();

                    userRepository.save(newUser);

                    log.info("카카오 로그인으로 회원가입이 완료되었습니다");
                    return "/kakaologin.html";
                }
            } catch (JsonProcessingException e) {
                log.error("Failed to parse JSON response: " + e.getMessage());
            } catch (Exception e) {
                log.error("An error occurred while fetching user info: " + e.getMessage());
            }
        }
        return null;
    }

    public boolean checkEmail(String email){
        Optional<User> users = userRepository.findByEmail(email);
        if(users.isPresent()) {
            throw new Exception400("이미 존재하는 이메일 입니다. : " + email);
        }
        return users.isPresent();
    }



    public KakaoTokenResponse RefreshToken(String code){
        KakaoTokenResponse tokenResponse = getKakaoAccessToken(code);
        String RefreshToken = tokenResponse.getRefresh_token();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "refresh_Token");
        parameters.add("client_id", kakaoUri.CLIENT_ID);
        parameters.add("refresh_token", RefreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<KakaoTokenResponse> responseEntity = restTemplate.exchange(kakaoUri.KAKAO_TOKEN_API_URL, HttpMethod.POST, requestEntity, KakaoTokenResponse.class);

        return responseEntity.getBody();
    }

    public String logout(String accessToken) throws JsonProcessingException {

        JsonNode userinfo = userInfo(accessToken);
        String userId = userinfo.path("id").asText();


        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(null, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange("https://kapi.kakao.com/v1/user/logout", HttpMethod.POST, requestEntity, String.class);

        if (responseEntity.getBody().contains(userId)) {
            System.out.println("로그아웃 되셨습니다. Redirecting to index.html");
        } else {
            System.out.println("로그아웃에 실패하셨습니다. Redirecting to index.html");
        }
        return responseEntity.getBody();
    }


    public String getlogout() {

        String logoutUrl = "https://kauth.kakao.com/oauth/logout?client_id=" + kakaoUri.CLIENT_ID
                + "logout_redirect_uri=" + kakaoUri.REDIRECT_URI;

        return logoutUrl;
    }


}


