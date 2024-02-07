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

### How to Run

- **docker 설치 환경이 필요합니다.**

1. docker-compose.yml 파일을 이용하여 MySQL 컨테이너를 실행합니다.

```bash
docker-compose up
```

