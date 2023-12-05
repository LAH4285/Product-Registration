package com.example.demo.User;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collections;

public class UserRequest {

    @Getter
    @Setter
    public static class JoinDTO {

        // 데이터가 비어있을 수 없는 상태.
        @NotEmpty

        /* ^: 문자열의 시작을 나타냅니다.
         * [A-Za-z0-9+_.-]: 영문 대소문자, 숫자, 특수 문자(+, _, ., -) 중 하나와 일치합니다. [] 사이에 있는 문자들 중 하나라도 일치하면 됩니다.
         * +: 앞선 요소가 최소 한 번 이상 반복되어야 함을 나타냅니다.
         * @: '@' 기호와 정확히 일치해야 합니다.
         * [A-Za-z0-9.-]: 영문 대소문자와 숫자로 이루어진 도메인 이름과 최소한 하나의 점(.) 중 하나와 일치합니다.
         * 여기서 '-'는 맨 앞이나 맨 뒤에 오지 않고 연속해서 사용되지 않아야 합니다. (e.g., 'a-b-c' 불가능)
         * 마찬가지로 '.'도 맨 앞이나 맨 뒤에 오지 않고 연속해서 사용되지 않아야 합니다. (e.g., 'a..b' 불가능)
         * 그리고 마침표 ('.')는 도메인 이름에서 반드시 포함되어야 합니다. (e.g., 'example.com')
         *  */
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        private String email;



        @NotEmpty
        @Size(min = 8, max = 20, message = "8자 이상 20자 이내로 작성 가능합니다.")
        /*
         * (?=.*[a-zA-Z]): 최소한 하나의 영문 대소문자가 있어야 함
         * (?=.*\\d): 최소한 하나의 숫자가 있어야 함
         * (?=.*[@#$%^&+=]): 최소한 하나의 특수 문자(@#$%^&+= 등)가 있어야 함
         * (?!.*\\s): 공백이 없어야 함
         * .{8,20}: 비밀번호 길이는 8에서 20 사이여야 함
         *  */
        //@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?!.*\\s).{8,20}$", message = "비밀번호는 영문 대소문자, 숫자, 특수 문자를 포함하고 공백이 없어야 합니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다.")
        private String password;


        public User toEntity() {
            return User.builder()
                    .email(email)
                    .password(password)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }
    }
}


