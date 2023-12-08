package com.example.demo.KAKAO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class kakaouser {
    private String id;
    private KakaoAccount kakao_account;
    private String connected_at;



    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    @NoArgsConstructor
    public static class KakaoAccount {
        private String email;
        private String birthday;
        private String name;
        private String birthyear;
        private String gender;
        private String phone_number;
        private boolean profile_nickname_needs_agreement;
        private boolean profile_image_needs_agreement;
        private String age_range;
        private boolean birthday_needs_agreement;
        private String birthday_type;
        private String ci;
        private String ci_authenticated_at;
        private String legal_name;
        private String nationality;
        private Profile profile;

        @Getter
        @Setter
        @AllArgsConstructor
        @ToString
        @NoArgsConstructor
        public static class Profile {
            private String nickname;
            private String thumbnail_image_url;
            private String profile_image_url;
            private boolean profile_needs_agreement;
        }
    }
}