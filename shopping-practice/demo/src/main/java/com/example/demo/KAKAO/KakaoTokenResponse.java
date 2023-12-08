package com.example.demo.KAKAO;
import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class KakaoTokenResponse {

    @Column(length = 256, nullable = false)
    private String token_type;
    @Column(length = 256, nullable = false)
    private String access_token;
    @Column(length = 256)
    private String id_token;
    @Column(length = 256, nullable = false)
    private int expires_in;
    @Column(length = 256, nullable = false)
    private String refresh_token;
    @Column(length = 256, nullable = false)
    private int refresh_token_expires_in;
    @Column(length = 256)
    private String scope;
}


