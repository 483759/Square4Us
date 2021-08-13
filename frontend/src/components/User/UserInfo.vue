<template >

<div>
  <h1>내 정보</h1>

<section class="usersection myInfoBox" >
  <div class="usersection_left">
    <!-- 왼쪽블럭 -->
    <div>
      <input type="text" name="nickname" id="nickname" class="inputbox" :placeholder="user.nickname" v-model="credentials.nickname">
    </div>
    <div>
      <textarea type="textarea" name="introduction" class="inputbox" style="height:250px" id="" cols="30" rows="10" :placeholder="user.introduction" v-model="credentials.introduction">
      </textarea>
    </div>
    <div>
      <input type="text" name="email" class="inputbox" :placeholder="user.email" v-model="credentials.email">
    </div>
    <div>
      <div class="badgeBox"></div>
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
      introduction: user.introduction,
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
      store.dispatch("updateMemberInfo", { nickname: credentials.nickname, introduction: credentials.introduction });
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

.myInfoBox {
    width: 40rem;
    display: flex;
    justify-content: center;
}

.inputbox {
  height: 1.5rem;
  width: 18rem;
  color: #000;
  font: 20px sans-serif;
  border-radius: 3px;
  border: gray 1px solid;
  margin: 0 0 20px 0;
}


#nickname{
  border: none;
  border-bottom: gray 1px solid;
}
#user_email {
  border: none;
  border-bottom: gray 1px solid;
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
  border-radius: 3px;
  border: gray 1px solid;
  margin: 0 0 20px 0;
}
.buttonsection {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}
</style>
