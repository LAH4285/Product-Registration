package com.example.demo.KAKAO;


import com.example.demo.core.error.exception.Exception400;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class KakaoController {
    private final kakaoService kakaoService;

    @GetMapping("/kakao-login")
    public String redirectToKakaoLogin(HttpServletResponse response) {

        String authorizationUrl = kakaoService.getAuthorizationUrl();
        log.info("이게 처음인가" + authorizationUrl);
        return "redirect:" + authorizationUrl;
    }

    @GetMapping("/info")
    public String getUserInfo(@RequestParam("accessToken") String accessToken, Model model) {
        try {
            JsonNode userInfo = kakaoService.userInfo(accessToken);
            log.info("User info retrieved successfully.");

            // 모델에 사용자 정보 추가
            model.addAttribute("userInfo", userInfo);

            // Thymeleaf 템플릿 경로 반환
            return "userInfo";
        } catch (JsonProcessingException e) {
            log.error("Failed to process JSON response: " + e.getMessage());
            return "error";
        }
    }


    @GetMapping(value = "/oauth/kakao", produces = "application/json")
    public String kakaoJoin(@RequestParam("code") String code, HttpSession session, HttpServletResponse response) {
        System.out.println("\nkakao code:" + code);

        String link = kakaoService.login(code,session);
        session.setAttribute("access_token", link);

        return "redirect:" + link;

    }

    @PostMapping("/refresh-token")
    public ResponseEntity<KakaoTokenResponse> refreshToken(@RequestParam("code") String code) {
        KakaoTokenResponse refreshedToken = kakaoService.RefreshToken(code);

        if (refreshedToken != null) {
            return new ResponseEntity<>(refreshedToken, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value="/kakao/logout")
    public RedirectView logout(HttpSession session) throws JsonProcessingException {
        String accessToken = (String) session.getAttribute("access_token");
        kakaoService.logout(accessToken);
        session.invalidate();

        return new RedirectView("http://localhost:8080/");
    }

}
