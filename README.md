## 배달 시스템

### Main Dependency
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-security

### 프로젝트 구조
- Hexagonal Architecture

### Authorization
- Bearer JWT

### 라우트
- 회원가입: `POST /member/sign-up`
- 로그인: `POST /member/sign-in`
- 주문: `POST /checkout`
- 배송지 변경: `PATCH /checkout/{orderItemId}/address`
- 배달 생성: `POST /delivery/{orderItemId}`
- 배달 조회: `GET /delivery`

### API Doc
- http://localhost:9000/swagger-ui/index.html

### Test
```
$ gradlew test
```

