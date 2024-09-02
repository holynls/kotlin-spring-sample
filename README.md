## Project Structure

이 프로젝트는 [Gradle Multi-Project Builds](https://docs.gradle.org/current/userguide/multi_project_builds.html) 과 Java 21을 사용하며,
아래와 같은 subprojects 및 디렉토리로 구성되어있습니다.

Hexagonal Architecture 를 적용하여 아래와 같은 subprojects 및 디렉토리로 구성되어있습니다.

```
.
├── domain            #(1)
├── application       #(2)
└── port              #(3)
```
---

#### domain
> 모든 비즈니스 로직과 도메인 모델들이 들어있는 모듈입니다.
>
> 비즈니스 코드의 결합을 방지하기 위해 다른 어떠한 모듈도 참조하지 않습니다.

#### application
> 어플리케이션의 진입점이 되는 모듈입니다. `domain`의 코드를 참조하여 비즈니스 로직을 조합하는 역할을 담당하고 있습니다.

#### port
> Hexagonal Architecture 에서의 Port 역할을 하는 모듈입니다. `domain`과 `application` 모듈을 참조하며, 외부와의 통신을 위한 인터페이스를 제공합니다.
>
> 외부에서 들어오는 요청을 처리하기 위한 `input` 포트와 외부로 나가는 응답을 처리하기 위한 `output` 포트를 제공합니다.
>
> 현재 가벼운 구현을 위해 mysql이 아닌 H2 데이터베이스를 사용하고 있습니다.

---
### 구조와 구현에 대한 설명

데이터라이즈 밋업에서 발표한 헥사고날 아키텍쳐를 저도 최근에 사용하고 있고, 고민한 부분도 있어 해당 방식으로 구현해보았습니다.  
port -> application -> domain 순으로 의존성이 존재합니다.  
gradle 멀티모듈을 이용하여 역방향 참조는 아예 불가능하도록 구현되어있습니다.  
헥사고날 아키텍쳐를 통해 도메인간 결합을 방지하고, 테스트 가능한 코드를 작성할 수 있었습니다.  
또한, 헥사고날 아키텍쳐를 통해 포트, 어플리케이션, 도메인 레이어간 데이터를 별도로 구현함으로써, 비즈니스 요구사항 변화와 도메인 요구사항 변화가 발생했을 때, 사이드 이펙트를 방지할 수 있습니다.  
개인적으로는 inbound port와 outbound port도 분리하여 서로를 참조할 수 없도록 하는 방향을 선호합니다.  
본래 유닛 테스트를 더 선호하나, 도메인 모듈 코드가 테스트할 정도로 방대하지 않아 통합 테스트로 대체하였습니다.


---
### 실행

JAVA 17이 설치되어있어야 합니다.

맥 환경에서 sdkman을 통해 JAVA 17을 설치할 수 있도록 만들어둔 install_java_17_on_mac.sh를 실행하시면 자바 17이 설치됩니다.

#### 이미지 빌드

`./gradlew build port:jibDockerBuild` 명령어를 통해 schedule 이라는 이름의 도커 이미지를 빌드할 수 있습니다.

#### 컨테이너 실행

`docker-compose up -d` 명령어를 통해 컨테이너를 실행할 수 있습니다.

---
### Test

JAVA 17이 설치된 환경에서 `./gradlew test` 명령어를 통해 테스트를 실행할 수 있습니다.

---
### API Docs

어플리케이션을 실행한 후 [Swagger Docs](http://localhost:8080/docs) 에서 API 문서를 확인할 수 있습니다.
