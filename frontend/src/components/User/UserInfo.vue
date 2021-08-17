<template >

<div>
  <h1>내 정보</h1>

<section class="usersection myInfoBox" >
  <div class="usersection_left">
    <div class="infoItem">
      <h3>닉네임</h3>
      <input type="text" name="nickname" id="nickname" class="inputbox" :placeholder="user.nickname" v-model="credentials.nickname">
      <h3>이메일</h3>
      <input type="text" name="email" id="email" class="inputbox" v-model="user.email" disabled>
</div>
    <div class="infoItem">
      <h3>내 소개</h3>
      <textarea type="textarea" name="introduction" class="inputbox" style="height:250px" id="" cols="30" rows="10" :placeholder="user.introduction" v-model="credentials.introduction">
      </textarea>
    </div>
    <div class="infoItem">
    </div>
    <!-- <div class="infoItem">
      <div class="badgeBox"></div>
    </div> -->
    
    
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
import { computed, onMounted } from "@vue/runtime-core";
export default {
  name: "UserInfo",
  setup() {
    const store = useStore();
    const user = computed(() => store.state.user);
    const credentials = reactive({
      nickname: store.state.user.nickname,
      email: store.state.user.email,
      introduction: store.state.user.introduction,
      profile_path: "profile path",
    });
    const data = reactive({
      imgUrl: "http://img.marieclairekorea.com/2018/10/mck_5bd26c6899aa0-562x709.jpg",
      imgChange: false,
    });

    onMounted({

    })

    // 개인 프로필 불러오는 함수(computed 대용)
    const getUserInfo = () => {
      // const API_URL = "#";

      // axios({
      //   method: "GET",
      //   url: API_URL,
      //   data: {
      //     credentials,
      //   },
      // }).then((response) => {
      //   this.credentials = response.data;
      // });
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
        credentials.profile_path =
          response.data.data.profile.filePath + "/" + response.data.data.profile.fileName;
      }).catch((err)=>{
        console.log(err);
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

.usersection_left{
  display: flex;
  justify-content: center;
  margin: 20px;
  text-align: left;
}

.infoItem {
  margin: 20px;
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
  transition: .3s;
  outline: none;
}

.inputbox:focus{
  border-color: #509186;
  box-shadow: 0 0 8px 0 #509186;
}


#nickname{
  height: 100px;
  font-weight: bold;
  font-size: 24px;
  /* border: none;
  border-bottom: gray 1px solid; */
}

#email {
  height: 65px;
  /* border: none;
  border-bottom: gray 1px solid; */
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
