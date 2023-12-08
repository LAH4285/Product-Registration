package com.example.demo.KAKAO;

import org.springframework.stereotype.Component;

@Component
public class KakaoUri {

    public String KAKAO_TOKEN_API_URL = "https://kauth.kakao.com/oauth/token";
    public String KAKAO_USER_ME_API_URL = "https://kapi.kakao.com/v2/user/me";
    public String KAKAO_ACCESS_TOKEN_URL = "https://kapi.kakao.com/v1/user/access_token_info";
    public String KAKAO_MESSAGE_URL = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
    public String CLIENT_ID = "a37ca1b91468aaf37e93f7d3f11b5860";
    public String REDIRECT_URI = "http://localhost:8080/oauth/kakao";
    public String KAKAO_LOGOUT_URL = "https://kauth.kakao.com/oauth/logout";


}
// https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=a37ca1b91468aaf37e93f7d3f11b5860&redirect_uri=http://localhost:8080/oauth/kakao
