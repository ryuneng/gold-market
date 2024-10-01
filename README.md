# 🪙 GoldMarket

## 🔍 목차
1. [서비스 소개](#-서비스-소개)
2. [Quick Start](#-quick-start)
3. [프로젝트 환경](#%EF%B8%8F-프로젝트-환경)
4. [API 명세서 및 ERD](#-api-명세서-및-erd)
5. [컨벤션 전략](#-컨벤션-전략)
6. [GitHub Issue, PR을 통한 트래킹 관리](#%EF%B8%8F-github-issue-pr을-통한-트래킹-관리)
7. [트러블 슈팅](#-트러블-슈팅)
8. [고민한 흔적](#-고민한-흔적)
9. [디렉토리 구조](#%EF%B8%8F-디렉토리-구조)

<br>

## 📋 서비스 소개
사용자가 진열된 금 상품을 판매하거나 구매할 수 있는 **금 거래소 웹 서비스**입니다.<br>
> 서비스 확장을 고려해 `MSA 아키텍처`를 적용하여 인증 서버와 자원 서버를 분리하였으며,<br>`gRPC 통신`을 사용해 자원 서버의 사용자 인증 API를 구현하였습니다.

<br>

### 주요 기능
1. 유저는 회원가입하고, 로그인할 수 있습니다.
2. 상품 목록을 조회합니다.
3. 주문을 생성합니다.
4. 주문 목록을 조회합니다.
5. 주문 상세를 조회합니다.
6. 권한을 가진 사용자가 주문 상태를 변경합니다.

<br>
<br>

## ✅ Quick Start
#### 1. 사전 준비 사항
- Docker 및 Docker Composer가 설치되어 있어야 합니다. (버전 20.10 이상 권장)

#### 2. 데이터베이스 실행
- 애플리케이션을 시작하기 전에 데이터베이스를 Docker Compose를 사용하여 설정해야 합니다.
다음 명령어를 사용하여 각 서버의 데이터베이스를 실행합니다.
```
docker-compose -f ./auth-server/docker-compose.auth.yml up -d
docker-compose -f ./resource-server/docker-compose.resource.yml up -d
```
위 명령어는 백그라운드에서 데이터베이스 컨테이너를 실행합니다.
실행 중인 상태를 확인하려면 docker ps 명령어를 사용하세요.

#### 3. Redis 패스워드 설정
```
1) docker exec -it [CONTAINER ID] redis-cli
  (위 명령어가 실행되지 않을 경우, 아래 명령어를 실행해주세요.)
  winpty docker exec -it [CONTAINER ID] redis-cli

2) config get requirepass
3) config set requirepass [PASSWORD]
4) config get requirepass
5) exit
6) winpty docker exec -it [CONTAINER ID] redis-cli
7) auth [PASSWORD]
```

<br>

## 🛠️ 프로젝트 환경
| Stack                                                                                                        | Version           |
|:------------------------------------------------------------------------------------------------------------:|:-----------------:|
| ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) | Spring Boot 3.3.3 |
| ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)    | Gradle 7.6       |
| ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)    | JDK 17           |
| ![MariaDB](https://img.shields.io/badge/mariadb-%2300A3E0.svg?style=for-the-badge&logo=mariadb&logoColor=white) | MariaDB 10.5.20 |
| ![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)    | Redis 7.0.11        |

<br>

## 📄 API 명세서 및 ERD
<details>
  <summary><b>🧾 API 명세서</b></summary><br>
  🔗<a href="https://documenter.getpostman.com/view/33600354/2sAXqmB5nZ"> POSTMAN API 명세 링크 클릭</a>
  <br>
  <h4>인증서버</h4>
  <img src="https://github.com/user-attachments/assets/ec11004c-4bd3-4c21-8956-e36c674ce983" width="80%">
  <h4>자원서버</h4>
  <img src="https://github.com/user-attachments/assets/17bf7813-fb3a-46d4-839f-ca1807809131" width="80%">
</details>

<details>
  <summary><b>⛓️ ERD</b></summary><br>
  <a href="https://dbdiagram.io/d/gold-market-66d7b203eef7e08f0e99f31f">🔗 dbdiagram 링크 클릭</a>
  <h4>인증서버</h4>
  <img src="https://github.com/user-attachments/assets/4bb00055-0980-4666-a027-b19d0c324183" width="70%">
  <h4>자원서버</h4>
  <img src="https://github.com/user-attachments/assets/120855c4-d430-41ee-a405-3cd3ca1cee88" width="70%">
</details>

<br>

## 🚩 컨벤션 전략
**1. 브랜치 전략**
| Commit Type | Description                      |
|:-----------:|:--------------------------------:|
| Feat        | 새로운 기능 추가                  |
| Fix         | 오류 및 문제 해결                 |
| Test        | 테스트 코드 추가                  |
| Refactor    | 코드 리팩토링                     |
| Style       | 코드 스타일 변경                  |
| Chore       | 빌드 업무 수정, 패키지 매니저 수정 |
| Docs        | 문서 수정, 주석 추가              |

**2. 코딩 컨벤션**
| Type            | Description                 |
|:---------------:|:---------------------------:|
| 변수명           | 카멜 케이스 (camelCase)     |
| 파일명 & 클래스명 | 파스칼 케이스 (PascalCase)  |
| DB 테이블명      | 스네이크 케이스 (snake_case) |
| API 응답 필드명  | 카멜 케이스 (camelCase)     |
| DTO 네이밍       | ~Request / ~Response       |

<br>

## 🏃🏻‍➡️ GitHub Issue, PR을 통한 트래킹 관리
<details>
  <summary><b>Issues</b></summary><br>
  <img src="https://github.com/user-attachments/assets/39c20507-b2c7-4c64-ac25-a10dfb2743a3" width="80%">
</details>
<details>
  <summary><b>Pull requests</b></summary><br>
  <img src="https://github.com/user-attachments/assets/9d1998fc-1403-42f7-b09d-3470056fe16c" width="80%">

</details>

<br>

## 💥 트러블 슈팅
- gRPC 설정 과정에서 발생한 오류 - <a href="https://github.com/ryuneng/gold-market/wiki/gRPC-%EC%84%A4%EC%A0%95-%EA%B3%BC%EC%A0%95%EC%97%90%EC%84%9C-%EB%B0%9C%EC%83%9D%ED%95%9C-%EC%98%A4%EB%A5%98"> WIKI 이동 </a>
- 자원 서버의 gRPC 포트가 9090으로 실행되는 문제 - <a href="https://github.com/ryuneng/gold-market/wiki/%EC%9E%90%EC%9B%90-%EC%84%9C%EB%B2%84%EC%9D%98-gRPC-%ED%8F%AC%ED%8A%B8%EA%B0%80-9090%EC%9C%BC%EB%A1%9C-%EC%8B%A4%ED%96%89%EB%90%98%EB%8A%94-%EB%AC%B8%EC%A0%9C"> WIKI 이동 </a>
- gRPC 의존성 추가 과정에서 테스트 전용 프로파일로 인해 발생한 오류 - <a href="https://github.com/ryuneng/gold-market/wiki/gRPC-%EC%9D%98%EC%A1%B4%EC%84%B1-%EC%B6%94%EA%B0%80-%EA%B3%BC%EC%A0%95%EC%97%90%EC%84%9C-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%A0%84%EC%9A%A9-%ED%94%84%EB%A1%9C%ED%8C%8C%EC%9D%BC%EB%A1%9C-%EC%9D%B8%ED%95%B4-%EB%B0%9C%EC%83%9D%ED%95%9C-%EC%98%A4%EB%A5%98"> WIKI 이동 </a>
- JWT_SECRET_KEY 환경변수 설정 문제 - <a href="https://github.com/ryuneng/gold-market/wiki/JWT_SECRET_KEY-%ED%99%98%EA%B2%BD%EB%B3%80%EC%88%98-%EC%84%A4%EC%A0%95-%EB%AC%B8%EC%A0%9C"> WIKI 이동 </a>
- POST 요청 성공 시 응답코드로 설정한 201 CREATED가 아닌 200 OK로 반환되는 문제 - <a href="https://github.com/ryuneng/gold-market/wiki/POST-%EC%9A%94%EC%B2%AD-%EC%84%B1%EA%B3%B5-%EC%8B%9C-%EC%9D%91%EB%8B%B5%EC%BD%94%EB%93%9C%EB%A1%9C-%EC%84%A4%EC%A0%95%ED%95%9C-201-CREATED%EA%B0%80-%EC%95%84%EB%8B%8C-200-OK%EB%A1%9C%C2%A0%EB%B0%98%ED%99%98%EB%90%98%EB%8A%94-%EB%AC%B8%EC%A0%9C"> WIKI 이동 </a>
</details>

<br>

## 🤔 고민한 흔적
- JPA Auditing을 활용한 엔티티 생성 및 수정 시간 관리를 위한 Base 클래스 선택 과정 - <a href="https://github.com/ryuneng/gold-market/wiki/JPA-Auditing%EC%9D%84-%ED%99%9C%EC%9A%A9%ED%95%9C-%EC%97%94%ED%8B%B0%ED%8B%B0-%EC%83%9D%EC%84%B1-%EB%B0%8F-%EC%88%98%EC%A0%95-%EC%8B%9C%EA%B0%84-%EA%B4%80%EB%A6%AC%EB%A5%BC-%EC%9C%84%ED%95%9C-Base-%ED%81%B4%EB%9E%98%EC%8A%A4-%EC%84%A0%ED%83%9D-%EA%B3%BC%EC%A0%95"> WIKI 이동 </a>
- 테스트용 데이터베이스 분리 - <a href="https://github.com/ryuneng/gold-market/wiki/%ED%85%8C%EC%8A%A4%ED%8A%B8%EC%9A%A9-%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-%EB%B6%84%EB%A6%AC"> WIKI 이동 </a>

<br>

## 🗂️ 디렉토리 구조
직관적인 구조 파악과 관리를 위해 <b>도메인형 구조</b>를 채택하였습니다.

<details>
  <summary><b>인증 서버 (auth-server)</b></summary>
  
```
├─main
│  ├─java
│  │  └─com
│  │      └─ryuneng
│  │          └─goldauth
│  │              ├─domain
│  │              │  ├─jwt
│  │              │  └─user
│  │              │      ├─controller
│  │              │      ├─dto
│  │              │      ├─entity
│  │              │      ├─repository
│  │              │      └─service
│  │              ├─global
│  │              │  ├─config
│  │              │  ├─entity
│  │              │  └─exception
│  │              │      ├─handler
│  │              │      └─response
│  │              └─grpc
│  ├─proto
│  └─resources
│      ├─static
│      └─templates
└─test
    └─java
        └─com
            └─ryuneng
                └─goldauth
                    └─domain
                        └─user
                            ├─controller
                            ├─repository
                            └─service
```

<br>
</details>
<details>
  <summary><b>자원 서버 (resource-server)</b></summary>

```
├─main
│  ├─java
│  │  └─com
│  │      └─ryuneng
│  │          └─goldresource
│  │              ├─domain
│  │              │  ├─auth
│  │              │  ├─order
│  │              │  │  ├─controller
│  │              │  │  ├─dto
│  │              │  │  ├─entity
│  │              │  │  ├─enums
│  │              │  │  ├─repository
│  │              │  │  └─service
│  │              │  └─product
│  │              │      ├─controller
│  │              │      ├─dto
│  │              │      ├─entity
│  │              │      ├─enums
│  │              │      ├─repository
│  │              │      └─service
│  │              ├─global
│  │              │  ├─config
│  │              │  ├─entity
│  │              │  └─exception
│  │              │      ├─handler
│  │              │      └─response
│  │              └─grpc
│  ├─proto
│  └─resources
│      ├─static
│      └─templates
└─test
    └─java
        └─com
            └─ryuneng
                └─goldresource
```
</details>
