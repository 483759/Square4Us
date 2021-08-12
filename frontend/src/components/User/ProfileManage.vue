<template>

<div class="profileFrame">
  <h1>프로필관리</h1>
  <div>
    <div class="profile">
      <img style="height: 360px; width: 300px;" src= "" alt="없음">
    </div>
      <button class="menuButton" v-show="!data.imgChange" @click="imgchangebutton">사진 수정</button>
      <div class="buttonsection" v-if="data.imgChange">
          <!-- <input type="text" placeholder="{{data.profile_path}}" v-model="data.profile_path" class="profilePath" > -->
          <!-- <div> -->
            <!-- <button class="cancelButton" @click="imgchangebutton">취소</button> -->
            <!-- 사진저장 버튼을 누를 때 따로 보내야할지 모르겠음 -->
            <!-- <button class="cancelButton" @click="putimage">사진 저장</button> -->
          <!-- </div> -->
          <form id="profileForm" role="form" method="post" enctype="multupart/form-data" action="/api/member/me/profile">
            <input type="file" name="profile">
            <button type="button" class="profile_gitmenuButton" @click="updateProfilePhoto">사진 변경</button>
            <button type="button" class="profile_gitmenuButton" @click="deleteProfilePhoto">사진 삭제</button>
            <button type="button" class="cancelButton" @click="imgchangebutton">취소</button>
          </form>
        </div>
        <div>
          <button class="menuButton" @click="putUserInfo">프로필 저장</button>
        </div>
    </div>
</div>
</template>

<script>
import axios from 'axios'
import { useStore } from 'vuex'
import { reactive } from '@vue/reactivity'
import { computed} from '@vue/runtime-core'
export default {
  name: "ProfileManage",
  setup() {
    const store = useStore()
    const user = computed(()=>store.state.user)
    const credentials = reactive({
      nickname: user.nickname,
      email: user.email,
        introduction: 'introduction',
        profile_path: 'profile path'
    })

    const data = reactive({
            imgUrl: "http://img.marieclairekorea.com/2018/10/mck_5bd26c6899aa0-562x709.jpg",
            imgChange: false,
            profile_path: ""
            })
     // 버튼 변경 함수
    const imgchangebutton = () => {
        data.imgChange = !data.imgChange
    }
    // 프로필 수정 함수
    const putUserInfo = () => {
            store.dispatch('updateMemberInfo', {nickname: credentials.nickname});
        }

        const updateProfilePhoto = () => {
          axios({
            method: 'POST',
            url: 'member/me/profile',
            data: new FormData(document.getElementById("profileForm")),
            cache: false,
            contentType: false,
            processType: false,
          }).then(response => {
            console.log(response.data.data.profile);
            credentials.profile_path = response.data.data.profile.filePath + '\\' + response.data.data.profile.fileName;
            console.log(credentials.profile_path);
          })
        }

        const deleteProfilePhoto = () => {
          axios({
            method: 'DELETE',
            url: 'member/me/profile'
          })
        }

    return {
      imgchangebutton,
      putUserInfo,
      updateProfilePhoto,
      deleteProfilePhoto,
      data,
      credentials,
      user
    }
  }
}
</script>

<style>
.myInfoBox {
    width: 800px;
    height: 100%;
}
.profileFrame {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;

  width: 20rem;
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
.profile_menuButton {
  /* margin-left: 100px; */
  height: 40px;
  width: 12rem;
  background-color: #195C77;
  color: white;
  font: 16px sans-serif;
  border-radius: 3px;
  margin-bottom: 4rem;
}
.cancelButton {
  /* margin-left: 100px; */
  height: 40px;
  width: 7rem;
  background-color: #195C77;
  color: white;
  font: 20px sans-serif;
  margin: 20px 10px 0 0 ;
  border-radius: 3px;
}
.buttonsection{
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    }
</style>