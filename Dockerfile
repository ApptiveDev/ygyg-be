# 멀티 스테이지 빌드를 사용한 Spring Boot 애플리케이션 Dockerfile

# 1단계: 빌드 스테이지(FROM: jdk 이미지 레이어, COPY: 빌드 파일 및 소스 코드 복사, RUN: Gradle 빌드 실행)
## 'openjdk:17-alpine linux' 이미지를 빌드 스테이지의 베이스 이미지로 사용, 'builder'라는 별칭 부여
FROM openjdk:17-alpine AS builder
## Working Directory 설정
WORKDIR /app
## 소스 코드 복사 (의존성 캐싱을 위해 build.gradle, settings.gradle, gradle, gradlew 먼저 복사)
COPY build.gradle settings.gradle ./
COPY gradle gradle
COPY gradlew .
RUN ./gradlew dependencies
COPY src src
## Gradle Wrapper에 실행 권한 부여
RUN chmod +x ./gradlew
## Gradle 빌드
RUN ./gradlew bootJAR

# 2단계: 실행 스테이지
## 'openjdk:17-alpine linux' 이미지를 실행 스테이지의 베이스 이미지로 사용
FROM openjdk:17-alpine
## Working Directory 설정
WORKDIR /app
## 빌드 스테이지에서 생성된 JAR 파일을 복사
COPY --from=builder build/libs/*.jar app.jar
## 빌드 시 전달받은 프로필 값을 환경변수로 설정
ARG PROFILE
ENV PROFILE=${PROFILE}

# 3단계: 컨테이너 실행 명령어 설정
## JVM 타임존을 서울로 설정, Spring Profile 설정, JAR 파일 실행
ENTRYPOINT ["java","-jar", "-Duser.timezone=Asia/Seoul", "-Dspring.profiles.active=${PROFILE}", "/app.jar"]