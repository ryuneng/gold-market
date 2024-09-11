# 🪙 GoldMarket

> ## 📝 목차
> 1. [서비스 소개](#-서비스-소개)
> 2. [주요 기능](#주요-기능)
> 3. [프로젝트 일정](#프로젝트-일정)
> 4. [프로젝트 환경](#%EF%B8%8F-프로젝트-환경)
> 6. [API 명세서](#-api-명세서)
> 7. [ERD](#%EF%B8%8F-erd)
> 8. [디렉토리 구조](#%EF%B8%8F-디렉토리-구조)

<br>

## 🪙 서비스 소개
- **금 거래 활성화 서비스** : 금의 판매와 구매를 지원하는 서비스를 제공하여 금 거래의 활성화를 촉진합니다.

<br>

### 주요 기능
> 1. 유저는 회원가입하고, 로그인할 수 있습니다.
> 2. 상품 목록을 조회합니다.
> 3. 주문을 생성합니다.
> 4. 주문 목록을 조회합니다.
> 5. 주문 상세를 조회합니다.
> 6. 권한을 가진 사용자가 주문 상태를 변경합니다.

<br>

## Quick Start
### 1. 사전 준비 사항
- Docker 및 Docker Composer가 설치되어 있어야 합니다. (버전 20.10 이상 권장)

### 2. 데이터베이스 실행
- 애플리케이션을 시작하기 전에 데이터베이스를 Docker Compose를 사용하여 설정해야 합니다.
다음 명령어를 사용하여 각 서버의 데이터베이스를 실행합니다.
```
docker-compose -f ./auth-server/docker-compose.auth.yml up -d
docker-compose -f ./resource-server/docker-compose.resource.yml up -d
```
위 명령어는 백그라운드에서 데이터베이스 컨테이너를 실행합니다.
실행 중인 상태를 확인하려면 docker ps 명령어를 사용하세요.

### 3. Redis 패스워드 설정
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
<br>

---
### 프로젝트 일정
- 2024.09.04 - 2024.09.11

<br>

### 🛠️ 프로젝트 환경
| Stack                                                                                                        | Version           |
|:------------------------------------------------------------------------------------------------------------:|:-----------------:|
| ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) | Spring Boot 3.3.3 |
| ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)    | Gradle 7.6       |
| ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)    | JDK 17           |
| ![MariaDB](https://img.shields.io/badge/mariadb-%2300A3E0.svg?style=for-the-badge&logo=mariadb&logoColor=white) | MariaDB 10.5.20 |
| ![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)    | Redis 7.0.11        |

<br>


<details>
  <summary><b>🧾 API 명세서</b></summary><br>
  🔗<a href="https://documenter.getpostman.com/view/33600354/2sAXqmB5nZ"> POSTMAN API 명세 링크 클릭</a>
  <br>
  <img src="https://github.com/user-attachments/assets/5fc22d1e-c91b-473e-8850-c24c4f5892c6" width="80%">
</details>

<details>
  <summary><b>⛓️ ERD</b></summary><br>
  <a href="https://dbdiagram.io/d/gold-market-66d7b203eef7e08f0e99f31f">🔗 dbdiagram 링크 클릭</a>
  <h4>인증서버</h4>
  <img src="https://github.com/user-attachments/assets/f7d7bfcf-d942-4ca3-b86a-e13e847690c3" width="40%">
  <h4>자원서버</h4>
  <img src="https://github.com/user-attachments/assets/4163cf35-35c6-4a72-abd0-d63e6826b92e" width="70%">
</details>


<details>
  <summary><b>🗂️ 디렉토리 구조</b></summary><br>
  - 직관적인 구조 파악과 관리를 위해 <b>도메인형 구조</b>를 채택하였습니다.

</details>
