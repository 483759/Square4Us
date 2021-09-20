## 📑 SSAFY 5기 공통 프로젝트 - Square 4 Us 

**Web RTC 기반 화상 스터디 플랫폼**

![image](https://user-images.githubusercontent.com/30489264/134001898-7beb52a4-cc42-495c-bc28-cdb45a9a3418.png)

<br>

### 👩‍💻 프로젝트 소개

Square 4 Us는 스터디를 찾고, 운영하고, 활동하길 원하는 많은 사람들에게 Ontact 서비스를 제공하는 WebRTC 기반 **온라인 화상 스터디 플랫폼**입니다.
<br>

## 📎 목차

<details>

<summary> 목차 보기</summary>

- [📑 SSAFY 5기 공통 프로젝트 - Square 4 Us](#-ssafy-5기-공통-프로젝트---square-4-us)
  - [👩‍💻 프로젝트 소개](#-프로젝트-소개)
- [📎 목차](#-목차)
- [📚 프로젝트 명세](#-프로젝트-명세)
- [🎈개발 환경](#개발-환경)
  - [Front-end](#front-end)
  - [Back-end](#back-end)
  - [DevOps](#devops)
  - [Commit Convention](#commit-convention)
- [🎡 프로젝트 구조](#-프로젝트-구조)
- [📚 S4U's Wiki](#-s4us-wiki)
- [✅ 핵심 라이브러리](#-핵심-라이브러리)

</details>

<br>


<br>

## 📚 프로젝트 명세

- __URL__ : https://i5b308.p.ssafy.io/
- __배포 여부__ : O
- __접속 가능__ : X
- __HTTPS 적용__ : O
- __PORT__ : 443
  <br>

<!-- </details> -->

<details>
<summary>E-R Diagram</summary>

![image](https://user-images.githubusercontent.com/30489264/130028179-c342e616-5275-4951-97f0-2c90124baac4.png)

</details>


<details>
<summary>와이어 프레임</summary>

https://www.figma.com/file/Cq8wRgZiDYmEuX8snuic1S/Untitled?node-id=0%3A1
</details>

<details>
<summary>Swagger API Document</summary>
![image](https://user-images.githubusercontent.com/30489264/130029531-57ea9aed-d65e-4f7c-bd2b-3f228f3c13dc.png)
</details>

<br>

## 🎈개발 환경

|이름|역할|세부|
|----|----|----|
|김준영|팀원|프론트엔드 개발, CI/CD(배포) 담당자|
|윤이진|팀장|백엔드 개발, QA(Jira 관리), Git 관리|
|천수승|팀원|프론트엔드 개발, UCC 담당자|
|최경운|팀원|백엔드 개발, AWS 담당자|

<br>

### Front-end

- Vue.js 3
- Openvidu Media Server
  
  <br>

### Back-end

- Java 8
- Spring Boot 2.4.5, Spring Data JPA, Query DSL 4.4.0, Swagger(OpenAPI 3.0), JWT
- MySQL 8

<br>

### DevOps

- Jenkins
- Docker
- AWS S3
- AWS EC2
- nginx
  
  <br>

### Commit Convention

> #Jira Issue Number [type]: commit message

  <br>

## 🎡 프로젝트 구조

![image](https://user-images.githubusercontent.com/30489264/130086804-fb109252-ae8e-4987-a6c4-b21475127bbf.png)

<br>

## 📚 S4U's Wiki

- [Wiki Main](https://github.com/483759/Square4Us/wiki)
- [프로젝트 배포 과정](https://github.com/483759/Square4Us/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%B0%B0%ED%8F%AC-%EB%B0%A9%EB%B2%95)
- [Openvidu 환경 구축 과정](https://github.com/483759/Square4Us/wiki/Openvidu-%EA%B5%AC%EC%B6%95-%EA%B3%BC%EC%A0%95)


<br>

## ✅ 핵심 라이브러리

- __Openvidu Media Server__
    - __링크__ : https://openvidu.io/
    - __소개__ : WebRTC 스택의 기능적 구현을 제공하는 미디어 서버
    - __사용 기능__ : 그룹 화상 통화 기능
    - __담당자__ : 최경운

