# server를 위한 dockerfile

FROM openjdk:17-alpine AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJAR

FROM openjdk:17-alpine
COPY --from=builder build/libs/*.jar app.jar
# 이미지 빌드시에 사용되는 Build-time variable을 정의: docker build에서 --build-arg로 변수를 전달
ARG PROFILE
# 이미지 실행시에 사용되는 환경변수를 정의: 컨테이너가 실행될 때 사용되는 환경변수
ENV PROFILE=${PROFILE}
ENTRYPOINT ["java","-jar", "-Duser.timezone=Asia/Seoul", "-Dspring.profiles.active=${PROFILE}", "/app.jar"]