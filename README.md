# 🪙 GoldMarket

## 🔍 목차
1. [서비스 소개](#-서비스-소개)
2. [Quick Start](#-quick-start)
3. [프로젝트 환경](#%EF%B8%8F-프로젝트-환경)
4. [API 명세서 및 ERD](#-api-명세서-및-erd)
5. [트러블 슈팅](#-트러블-슈팅)
6. [고민한 흔적](#-고민한-흔적)
7. [디렉토리 구조](#%EF%B8%8F-디렉토리-구조)

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

## 💥 트러블 슈팅
<details>
  <summary>gRPC 설정 과정에서 발생한 오류</summary>
<h3>⚠️ 문제</h3>

- gRPC 설정 과정에서 <b>gRPC 관련 클래스들이 생성되지 않고</b> 오류가 발생했다.
- <details>
  <summary>오류 내용</summary><br>
    <img src="https://github.com/user-attachments/assets/49aabd77-9680-4d98-a72b-b6ccefca9ba7">

    CxxxgrpcTestServiceGrpc.java10 error cannot find symbol<br>
    @javax.annotation.Generated(<br>
                     ^<br>
      symbol   class Generated<br>
      location package javax.annotation<br>

  </details>

<br>

<h3>❓ 원인</h3>

- 해당 오류는 `javax.annotation.Generated` 클래스를 찾을 수 없다는 의미다.<br>
이 클래스는 <b>Java 9부터 Java 표준 라이브러리에서 제외</b>되었기 때문에, 현재 사용중인 Java 17버전에서 충돌이 발생했다.<br>
`javax.annotation.Generated`는 `javax.annotation` 패키지에 속해 있기 때문에, 이 패키지를 별도로 추가하면 문제를 해결할 수 있다.

<br>

<h3>💡 해결</h3>

- `build.gradle`에 아래 <b>의존성을 추가</b>한 후, Gradle을 clean하고 build를 수행했다.<br>
`implementation 'javax.annotation:javax.annotation-api:1.3.2'`<br>
- 수정 후, `build > generated` 하위에 gRPC 관련 클래스들이 정상적으로 생성되었다.
- <details>
  <summary>성공 결과</summary><br>
    <img src="https://github.com/user-attachments/assets/08d8db2f-9e9f-405f-8336-3360c4017566">
  </details>

<br>

<h3>⚠️ + 2차 문제</h3>

- <b>문제</b> : 생성된 gRPC 관련 클래스들이 정상적으로 <b>import되지 않는</b> 또 다른 문제가 발생했다.<br>
- <b>과정</b> : 여러 참고 자료를 찾아 수정해봤지만, 문제는 해결되지 않았다. gRPC 관련 자료가 많지 않아 해결에 어려움이 있었다.<br>
- <b>해결</b> : 원인이 gRPC <b>버전 호환성</b> 문제임을 파악하고, 기존 의존성을 전부 제거한 후 <b>최신 버전</b>으로 교체하니 정상적으로 작동했다.<br>
새로운 라이브러리를 추가할 때는 버전 호환성을 잘 확인해야 한다는 점을 다시 한번 깨달았다.
<br>

</details>

<details>
  <summary>자원 서버의 gRPC 포트가 9090으로 실행되는 문제</summary>
<h3>⚠️ 문제</h3>

- 자원 서버의 `application.yml`에 gprc client port를 50052로 설정했음에도 불구하고, 서버를 실행할 때마다 <b>gRPC 포트가 `9090`으로 실행</b>되었다.
이로 인해 <b>인증 서버와 자원 서버 간의 통신이 실패</b>했다.
- <details>
  <summary>오류 내용</summary><br>
    <ul>
      <li>
        <div>
          <p>자원 서버</p>
          <img src="https://github.com/user-attachments/assets/f801f82f-01f4-49e2-9450-e77acaa240e2" width="80%">
        </div>
      </li><br>
      <li>
        <div>
          <p>인증 서버 비교) 인증서버는 설정한 대로 <b>port: 50051</b>이 정상적으로 표시되었다.</p>
          <img src="https://github.com/user-attachments/assets/107e7d60-c163-4e93-a5c9-6c8ceeae899b" width="80%">
        </div>
      </li>
    </ul>

  </details>

<br>

<h3>❓ 원인</h3>

- **의존성 문제**

  - 인증 서버에는 `implementation 'net.devh:grpc-spring-boot-starter:2.15.0.RELEASE'`,<br>
자원 서버에는 `implementation 'net.devh:grpc-client-spring-boot-starter:3.1.0.RELEASE'`<br>
의존성이 필요하다. 하지만 나는 자원서버에 `client` 의존성이 누락된 상태였다.<br>

- **포트 설정 문제**

  - 인증 서버의 `server: port` 번호와 자원 서버의 `client: server: address` 번호가 일치해야 하지만, 내가 설정한 포트 번호는 서로 달랐다.

<br>

<h3>💡 해결</h3>

- 자원 서버에 **gRPC 클라이언트 의존성을 추가**하고, `application.yml`에서 인증 서버와 자원 서버의 gPRC 포트 번호를 동일하게 `50051`로 수정했다.
수정 후, 자원 서버는 더 이상 9090 포트로 실행되지 않고, 인증 서버와 자원 서버 간의 통신이 정상적으로 성공했다.

<br>

</details>

<details>
  <summary>gRPC 의존성 추가 과정에서 테스트 전용 프로파일로 인해 발생한 오류</summary>
<h3>⚠️ 문제</h3>

- 인증 서버에 gRPC 의존성을 추가한 후 서버 실행 시 여러 오류가 발생했고, **실제로는 정상적으로 통과되는 테스트도 실패**했다는 메시지가 출력되었다.
- <details>
  <summary>오류 내용</summary><br>
    <ul>
      <li>
        <div>
          <p>오류 내용</p>
          <img src="https://github.com/user-attachments/assets/f8582a1a-fe9b-492c-85d1-3fcfc1ae4c2d">
        </div>
      </li><br>
      <li>
        <div>
          <p>테스트 실패 내용</p>
          <img src="https://github.com/user-attachments/assets/58a28135-9326-4cdd-bf7f-42511081d782" width="70%">
        </div>
      </li><br>
      <li>
        <div>
          <p>하지만 실패한다는 테스트는 <b>실제로 실행해보면 성공하는 테스트</b>였다.</p>
          <img src="https://github.com/user-attachments/assets/92ecd2cd-fae7-455d-aa9a-d3d7dceffd56" width="90%">
        </div>
      </li>
    </ul>

  </details>

<br>

<h3>❓ 원인</h3>

- `application.yml`의 `grpc: server: port:` 설정 위치가 문제였다.
- 로컬 환경과 테스트 실행 환경에서 **동일한 gRPC 포트를 사용하게 되어 포트 충돌**이 발생했다. gRPC 포트 설정이 기본 프로파일(로컬과 테스트 환경에서 공통으로 사용되는 프로파일)에 있었기 때문이다.

<br>

<h3>💡 해결</h3>

- **gPRC 포트 설정을 로컬 프로파일로 이동함**으로써 충돌을 방지하고, 문제를 해결했다.

<br>

</details>

<details>
  <summary>JWT_SECRET_KEY 환경변수 설정 문제</summary>
<h3>⚠️ 문제</h3>

- JWT 기반 로그인을 구현하는 과정에서 `@Value("${JWT_SECRET_KEY}")`를 통해 환경변수에 설정된 **`JWT_SECRET_KEY` 값을 읽어오지 못하는 문제**가 발생했다.
`application.yml`과 `.env` 파일에 각각 같은 환경변수를 설정했음에도 충돌이 발생했다.

<br>

<h3>❓ 원인</h3>

- `JWT_SECRET_KEY` 값이 `application.yml`과 `.env` 파일 중 한 곳에만 설정되어야 하는데 **두 파일에 중복 정의**된 것이 문제였다. 이로 인해 변수 충돌이 발생해 올바르게 값을 읽지 못했다.

<br>

<h3>💡 해결</h3>

- **`application.yml` 파일에서 `jwt:secretkey` 항목을 삭제**하고, `.env` 파일에만 `JWT_SECRET_KEY` 환경변수를 유지했다.
수정 후, `@Value("${JWT_SECRET_KEY}")`를 통해 환경변수가 정상적으로 읽혔다.
- <details>
  <summary>수정한 내용</summary><br>
    <ul>
      <li>
        <div>
          <p>application.yml 파일에서 <b>jwt:secretkey</b> 제거</p>
        </div>
      </li>
      <li>
        <div>
          <p>.env 파일에 <b>JWT_SECRET_KEY</b> 유지</p>
          <img src="https://github.com/user-attachments/assets/36972473-9e0b-4fc1-880b-9144d148ae83">
        </div>
      </li>
    </ul>

  </details>

<br>

</details>

<details>
  <summary>POST 요청 성공 시 응답코드로 설정한 201 CREATED가 아닌 200 OK로 반환되는 문제</summary>
<h3>⚠️ 문제</h3>

- `@PostMapping` 요청 성공 시 반환값으로 `SuccessResponse.created()`를 사용했는데,
실제 응답은 **`201 CREATED`가 아닌 `200 OK`가 반환**되었다.
- <details>
  <summary>작성한 SuccessResponse 클래스와 API 코드</summary><br>
    <img src="https://github.com/user-attachments/assets/f8a68142-16a6-4c3d-b4dd-e0bd12b612ec" width="80%">
    <img src="https://github.com/user-attachments/assets/43f2c0dc-c9e0-4279-b45d-da883c417d95" width="80%">
  </details>

<br>

<h3>❓ 원인</h3>

- `SuccessResponse.created` 메서드가 `HttpStatus.CREATED`를 반환하도록 설정하더라도,
Spring은 기본적으로 **`ResponseEntity`를 사용하지 않으면 HTTP 상태 코드를 자동으로 설정하지 않는다.**
- Spring MVC는 컨트롤러 메서드에서 반환된 객체를 HTTP 응답 본문으로 직렬화하지만,
상태 코드를 명시적으로 설정하지 않으면 기본값인 `HttpStatus.OK(200)`가 반환된다.
- 즉, `SuccessResponse.created()`가 내부적으로 `HttpStatus.CREATED`를 설정하고 있어도, 이 정보는 **응답의 실제 HTTP 상태 코드에 반영되지 않는다.**

<br>

<h3>💡 해결</h3>

- 반환값을 **`ResponseEntity<>`로 감싸서 상태 코드를 명시적으로 설정**했다.
수정 후, 실제로 `201 CREATED` 상태 코드가 반환되었다.
- <details>
  <summary>수정한 내용</summary><br>
  <img src="https://github.com/user-attachments/assets/32922fe5-7b01-4bbf-9e80-bf8a9d30d6dd" width="80%">
  </details>

<br>

</details>

<br>

## 🤔 고민한 흔적
<details>
  <summary>JPA Auditing을 활용한 엔티티 생성 및 수정 시간 관리를 위한 Base 클래스 선택 과정</summary>
<h3>🖼️ 고민한 내용</h3>

- 각 도메인 엔티티 클래스를 생성하는 과정에서 생성 및 수정 시간을 자동으로 관리해주는 `BaseEntity`, `BaseTimeEntity`, `Timestamped` 중 어떤 클래스를 사용할지 고민했다.

<br>

<h3>🔍 분석</h3>

- **BaseEntity**
    - 시간 정보 뿐만 아니라, 엔티티에서 공통적으로 사용될 필드를 포함하는 부모 클래스<br><br>
- **BaseTimeEntity**
    - 시간 관련 로직에 특화되어 있으며, 엔티티의 시간 정보만 관리할 때 유용하다.<br><br>
- **Timestamped**
    - 단순한 시간 관리만 필요할 때 사용한다.
    - `BaseTimeEntity`와 필드 이름이나 기능이 조금 다를 수 있지만, 시간 필드를 자동으로 처리하는 기능적 측면에서 유사하다.

<br>

<h3>💡 결론</h3>

- 이 프로젝트에서는 모든 엔티티에 `cretaed_at`, `updated_at` 뿐만 아니라<br>
`deleted_at`, `deleted_yn` 필드를 공통으로 추가하도록 설계했기 때문에,
`BaseEntity`가 적절하다고 판단하여 **`BaseEntity`를 사용**했다.

<br>

</details>

<details>
  <summary>테스트용 데이터베이스 분리</summary>
<h3>🖼️ 고민한 내용</h3>

- 로컬 환경과 테스트 환경을 분리하면서, **테스트 시 로컬에서 사용하는 실제 DB와 동일한 데이터베이스를 사용할 것인지**에 대해 고민했다.

<br>

<h3>❓ 고민한 이유</h3>

- 로컬 DB와 테스트 DB를 동일하게 유지하면 편리할 수 있지만, **테스트 중 데이터가 손상되거나 의도하지 않은 영향**을 미칠까 우려되었다.

<br>

<h3>💡 결론</h3>

- **테스트 환경과 실제 로컬 개발 환경을 완전히 분리**하기로 결정했다.
이를 위해 Docker를 활용해 **테스트 전용 MariaDB를 설정**하여 테스트 데이터와 로컬 데이터를 독립적으로 관리할 수 있게 했다.<br>
- 이로 인해 테스트 중 발생하는 데이터 변화가 로컬 DB에 영향을 주지 않고, 테스트 환경을 필요에 따라 쉽게 초기화하거나 재구성할 수 있어 더 **안전하고 효율적인 테스트 환경**을 구성할 수 있게 되었다.

<br>

</details>

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
