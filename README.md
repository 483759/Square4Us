# 📑Square 4 Us

<br>

## 📎 목차

> 전부 작성 후 추가 예정

<br>

## 👩‍💻 프로젝트 소개

Square 4 Us는 스터디를 찾고, 운영하고, 활동하길 원하는 많은 사람들에게 Ontact 스터디 관리 서비스를 제공하는 **온라인 스터디 플랫폼**입니다.

<br>

## 📚 프로젝트 명세

- __URL__ : https://i5b308.p.ssafy.io/
- __배포 여부__ : O
- __접속 가능__ : O
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

### 개발 환경

|이름|역할|세부|
|----|----|----|
|김준영|팀원|프론트엔드 개발, CI/CD 담당자|
|윤이진|팀장|백엔드 개발, QA(Jira 관리)|
|천수승|팀원|프론트엔드 개발, UCC 담당자|
|최경운|팀원|백엔드 개발, AWS 담당자|

<br>

#### Front-end

- __Framework__ : Vue.js
- __지원 환경__ : Web
- __담당자__ : 김준영, 천수승
  <br>

#### Back-end

- __Framework__ : Spring Boot, JPA,  AWS S3,  Openvidu Media Server
- __File Server__ : AWS S3
- __Database__ : MySQL
- __담당자__ : 윤이진, 최경운, 김준영
  <br>

#### Design

- __Framework 사용__ : X
- __Design Tool 사용__ : X
- __담당자__ : 천수승, 김준영
  <br>

#### Commit Convention

> #지라이슈번호 [타입]: 커밋메시지

<br>

#### 실행 방법

##### 포트 번호

    FrontEnd: 80(443)
    BackEnd: 8080
    Openvidu: 4443
    Kurento-Media-Server: 8888
    Database: 3306

<br>

##### 실행 순서

    1. 
    2. 

<br>

#### 산출물 포팅 메뉴얼

    example

<br>

### ✅ 핵심 라이브러리

- __Openvidu Media Server__
    - __링크__ : https://openvidu.io/
    - __소개__ : WebRTC 스택의 기능적 구현을 제공하는 미디어 서버
    - __사용 기능__ : 그룹 화상 통화 기능
    - __담당자__ : 최경운
