# 📑Square 4 Us

## 📎 목차

- [프로젝트 소개](#프로젝트-소개)   
- [프로젝트 명세](#프로젝트-명세)
  - [배포 환경](#배포-환경)
  - [개발 환경](#개발-환경)
  - [핵심 라이브러리](#핵심-라이브러리)
<br>

## 👩‍💻 프로젝트 소개
Square 4 Us는 스터디를 찾고, 운영하고, 활동하길 원하는 많은 사람들에게 Ontact 스터디 관리 서비스를 제공하는 **온라인 스터디 플랫폼**입니다.

<br>

## 📚 프로젝트 명세
<details> 
<summary>배포 환경(미적용)</summary>

- __URL__ : 
- __배포 여부__ : X
- __접속 가능__ : 수정 중
- __HTTPS 적용__ : O
- __PORT__ : 
<br>
</details>

### 개발 환경

|이름|역할|세부|
|----|----|----|
|김준영|팀원|풀스택 개발, CI 담당자|
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
- __Framework__ : Spring Boot, Kurento Media Server
- __Database__ : MySQL
- __담당자__ : 윤이진, 최경운, 김준영
<br>

#### Design
- __Framework 사용__ : X
- __Design Tool 사용__ : X
- __담당자__ : 천수승, 김준영
<br>

#### 실행 방법

##### 포트 번호
    FrontEnd: 80
    BackEnd: 8080
    Database: 3306

##### 실행 순서
    1. 백엔드 서버 실행
    2. 프론트엔드 실행
<br>

### ✅ 핵심 라이브러리
Kurento Media Server

- __Kurento Media Server__
  - __링크__ : https://www.kurento.org/
  - __소개__ : WebRTC 스택의 기능적 구현을 제공하는 미디어 서버
  - __사용 기능__ : 그룹 화상 통화 기능
  - __담당자__ : 최경운