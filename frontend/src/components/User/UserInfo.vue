<template>
  <div>
    <h1>내 정보</h1>
    <hr />
    <section class="usersection myInfoBox">
      <div class="usersection_left">
        <!-- 왼쪽블럭 -->
        <div>
          <input
            type="text"
            name="nickname"
            id="nickname"
            class="inputbox"
            :placeholder="user.nickname"
            v-model="credentials.nickname"
          />
        </div>
        <div>
          <textarea
            type="textarea"
            name="introduction"
            class="inputbox"
            style="height:250px"
            id=""
            cols="30"
            rows="10"
            placeholder="{{credentials.introduction}}"
            v-model="credentials.introduction"
          >
          </textarea>
        </div>
        <div>
          <input
            type="text"
            name="email"
            class="inputbox"
            :placeholder="user.email"
            v-model="credentials.email"
          />
        </div>
        <div>
          <div class="badgeBox"></div>
        </div>
      </div>
      <!-- 오른쪽 블럭 -->
      <div class="profileFrame">
        <div class="profile">
          <img style="height: 360px; width: 300px;" :src="credentials.profile_path" alt="없음" />
        </div>
        <button class="menuButton" v-show="!data.imgChange" @click="imgchangebutton">
          사진 수정
        </button>
        <div class="buttonsection" v-if="data.imgChange">
          <!-- <input type="text" placeholder="{{credentials.profile_path}}" v-model="credentials.profile_path" class="profilePath" /> -->
          <!-- <div> -->
          <!-- <button class="cancelButton" @click="imgchangebutton">취소</button> -->
          <!-- 사진저장 버튼을 누를 때 따로 보내야할지 모르겠음 -->
          <!-- <button class="cancelButton" @click="putimage">사진 저장</button> -->
          <!-- </div> -->
          <form
            id="profileForm"
            role="form"
            method="post"
            enctype="multupart/form-data"
            action="/api/member/me/profile"
          >
            <input type="file" name="profile" />
            <button type="button" class="menuButton" @click="updateProfilePhoto">사진 변경</button>
            <button type="button" class="menuButton" @click="deleteProfilePhoto">사진 삭제</button>
            <button type="button" class="menuButton" @click="imgchangebutton">취소</button>
          </form>
        </div>
      </div>
    </section>
    <div>
      <button class="menuButton" @click="putUserInfo">프로필 저장</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { useStore } from "vuex";
import { reactive } from "@vue/reactivity";
import { computed } from "@vue/runtime-core";
export default {
  name: "UserInfo",
  setup() {
    const store = useStore();
    const user = computed(() => store.state.user);
    const credentials = reactive({
      nickname: user.nickname,
      email: user.email,
      introduction: "introduction",
      profile_path: "profile path",
    });
    const data = reactive({
      imgUrl: "http://img.marieclairekorea.com/2018/10/mck_5bd26c6899aa0-562x709.jpg",
      imgChange: false,
    });
    // 개인 프로필 불러오는 함수(computed 대용)
    const getUserInfo = () => {
      const API_URL = "#";

      axios({
        method: "GET",
        url: API_URL,
        data: {
          credentials,
        },
      }).then((response) => {
        this.credentials = response.data;
      });
    };

    // 버튼 변경 함수
    const imgchangebutton = () => {
      data.imgChange = !data.imgChange;
    };
    // 프로필 수정 함수
    const putUserInfo = () => {
      store.dispatch("updateMemberInfo", { nickname: credentials.nickname });
    };

    const updateProfilePhoto = () => {
      axios({
        method: "POST",
        url: "member/me/profile",
        data: new FormData(document.getElementById("profileForm")),
        cache: false,
        contentType: false,
        processType: false,
      }).then((response) => {
        console.log(response.data.data.profile);
        credentials.profile_path =
          response.data.data.profile.filePath + "\\" + response.data.data.profile.fileName;
        console.log(credentials.profile_path);
      });
    };

    const deleteProfilePhoto = () => {
      axios({
        method: "DELETE",
        url: "member/me/profile",
      });
    };

    return {
      getUserInfo,
      putUserInfo,
      credentials,
      data,
      imgchangebutton,
      updateProfilePhoto,
      deleteProfilePhoto,
      user,
    };
  },
};
</script>

<style>
template {
  background-color: #f2f2f2;
}
.usersection {
  display: flex;
  justify-content: space-evenly;
}
.myInfoBox {
  display: flex;
  /* justify-content: start; */
  width: 20rem;
}
.usersection_left {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.inputbox {
  height: 1.5rem;
  width: 18rem;
  color: #000;
  font: 20px sans-serif;
  margin-bottom: 20px;
  margin-left: 80px;
  margin-right: 40px;
  border-radius: 3px;
  border: gray 1px solid;
}
.profileFrame {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;

  width: 20rem;
  margin-top: 50px;
  margin-right: 50px;
}
.profile {
  height: 14rem;
  width: 12rem;
  border: 1px solid black;
  margin: 0 0 2rem 0;
}
.profilePath {
  width: 12rem;
}
.menuButton {
  /* margin-left: 100px; */
  height: 40px;
  width: 12rem;
  background-color: #195c77;
  color: white;
  font: 16px sans-serif;
  border-radius: 3px;
  margin-bottom: 4rem;
}
.cancelButton {
  /* margin-left: 100px; */
  height: 40px;
  width: 7rem;
  background-color: #195c77;
  color: white;
  font: 20px sans-serif;
  margin: 20px 10px 0 0;
  border-radius: 3px;
}
.badgeBox {
  height: 200px;
  width: 18rem;
  border: 1px solid black;
  margin-left: 80px;
  margin-bottom: 40px;
  margin-right: 40px;
  border-radius: 3px;
  border: gray 1px solid;
}
.buttonsection {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}
</style>
