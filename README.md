Springboot Framework를 활용한 쇼핑몰 구현V5.0.0
===
### 개요
-SpringFramework를 이용한 쇼핑몰 기능 구현
### 일정  
 -23.12.04 ~

 -개인 프로젝트  
- - - - - - - - -
### 사용 기술 및 개발 환경
-O/S : window11 Home 22H2
-DB : MySQL WORKBENCH 8.0.35 CE   
-Framework/Flatform : Spring Data JPA, Thymeleaf  
-Spring Boot 2.6.7  
-JDK 11, Gradle, yml   
-Language : JAVA, HTML5   
-IDE: IntelliJ IDEA Community
- - - - - - - - - - - - - -
### 구현 기능
1. 상품등록(/save)
2. 상품확인(/product/{id})
3. 상품수정(/update/{id})
4. 상품삭제(/delete/{id})



###  향후 업데이트 예정 기능

1. 무궁무진합니다 기대해주세요

 - - - - - - - - - - - - - -


#### v1.0.0 (2023.12.04)
1. 상품등록 기능 구현(save)  
  -상품이름, 상품내용, 가격 등
2. 전체상품 확인 기능 구현(/products)

3. 개별상품 확인 기능 구현(/products/{id})

4. 상품삭제 기능 구현(/delete/{id})

5. (Issue) 상품 업데이트 기능 구현 실패

#### v1.0.1 (2023.12.04)
1. 상품수정 기능 구현(/update/{id})

#### v2.0.0 (2023.12.04)
1. 옵션등록 기능 구현(/option/save)  
  -옵션이름, 가격, 수량
2. 옵션 개별상품 검색 기능 구현(/products/{id}/options)

3. 옵션 전체상품 검색 기능 구현(/options)

4. 옵션 업데이트 기능 구현(/option/update/{id})

5. 옵션 삭제 기능 구현(/option/delete/{id})

#### v3.0.0 (2023.12.04)
1. 유저 회원가입 기능 구현

2. 유저 로그인 기능 구현

3. 유저정보 확인

### v4.0.0 (2023.12.04)
1. 예외처리 클래스 구현
  -Exception400, 401, 404, 405, 500
2. 시큐리티 클래스 구현
  1. JWT 토큰
  2. 인증권한
  3. SecurityConfig(스프링 시큐리티(Security)의 설정을 담당)

3. ApiUtils   
  -API 결과를 처리하기 위한 유틸리티


### v5.0.0 (2023.12.04)
