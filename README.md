## Overview

> 효율적인 운영을 필요로 하는 사장님들을 위한 서비스입니다.

### Tech Stack

- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Security
- MySQL
- Docker
- JUnit5

---

### API Methods

- **인증**:
    - POST `/api/auth/register` 소유자 등록을 위함.
    - POST `/api/auth/login` 로그인, JWT 반환.
    - POST `/api/auth/logout` 로그아웃.


- **제품 관리**(유효한 JWT로만 접근 가능):
    - POST `/api/products` 새 제품 등록.
    - PUT `/api/products/{id}` 제품 수정.
    - DELETE `/api/products/{id}` 제품 삭제.
    - GET `/api/products` 페이징을 통한 제품 목록 조회.
    - GET `/api/products/{id}` 제품 상세 정보 조회.
    - GET `/api/products/search` 이름으로 제품 검색.

----

### 실행 방법

1. **도커 실행**:

- 도커가 설치되어 있지 않다면, [도커 설치](https://docs.docker.com/get-docker/)를 참조하여 설치하세요.
- 도커가 설치되어 있다면, 도커를 실행하세요.

2. **application build**:

```bash
./gradlew clean build
```

3. **Start to docker compose**:

```bash
docker-compose up
```
