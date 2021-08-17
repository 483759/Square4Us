<template>

<div class="profileFrame">
  <h1>프로필관리</h1>
  <div class="profileBox">
    <div class="imageSection">
      <img id="profileImage" :src="credentials.profile_path" alt="없음">
    </div>
    <div class="setting">
      <button class="menuButton" v-show="!data.imgChange" @click="imgchangebutton">사진 수정</button>
      <div class="buttonsection" v-if="data.imgChange">
          <form id="profileForm" role="form" method="post" enctype="multupart/form-data" action="/api/member/me/profile">
            <div class="uploadSection">
              <input type="text" class="inputbox" name="upload_name" id="upload_name" disabled="disabled" v-model="credentials.profile_name">
              <input type="file" name="profile" id="profile" @change="changeFileName">
              <label  class="profile_menuButton" id="upload_label" for="profile">업로드</label>
            </div>
            <!-- <button type="button" class="profile_menuButton" @click="updateProfilePhoto">사진 변경</button> -->
            <button type="button" class="profile_menuButton" @click="deleteProfilePhoto">사진 삭제</button>
            <button type="button" class="cancelButton" @click="imgchangebutton">돌아가기</button>
          </form>
          
          <!-- <div class="filebox"> 
            <input class="upload-name" value="파일선택" disabled="disabled"> 
            <label for="ex_filename">업로드</label> 
            <input type="file" id="ex_filename" class="upload-hidden">
             </div> -->
      </div>
      
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios'
import { useStore } from 'vuex'
import { reactive } from '@vue/reactivity'
import { computed, onMounted} from '@vue/runtime-core'
export default {
  name: "ProfileManage",
  setup() {
    const store = useStore()
    const user = computed(()=>store.state.user)
    const credentials = reactive({
      nickname: user.nickname,
      email: user.email,
        introduction: 'introduction',
        profile_path: '',
        profile_name: '파일 선택'
    })

    

    const data = reactive({
            imgUrl: credentials.profile_path,
            imgChange: false,
            profile_path: ""
    })

    onMounted(()=> {
      axios({
        method: 'get',
        url: 'member/me',
      }).then(response => {
        credentials.profile_path = response.data.data.profile.filePath + '/' + response.data.data.profile.fileName
      })
    })
   

     // 버튼 변경 함수
    const imgchangebutton = () => {
        data.imgChange = !data.imgChange
    }
    // 프로필 수정 함수
    const putUserInfo = () => {
            store.dispatch('updateMemberInfo', {nickname: credentials.nickname});
    }

    const changeFileName = () => {
      credentials.profile_name = document.getElementById("profile").files[0].name
      axios({
        method: 'POST',
        url: 'member/me/profile',
        data: new FormData(document.getElementById("profileForm")),
        cache: false,
        contentType: false,
        processType: false,
      }).then(response => {
        credentials.profile_path = response.data.data.profile.filePath + '/' + response.data.data.profile.fileName;
      })
    }

    const updateProfilePhoto = () => {
      // console.log(document.getElementById("profile").files[0].name)
      // // console.log(document.getElementById("profileForm")[1].file[0].name)
      // axios({
      //   method: 'POST',
      //   url: 'member/me/profile',
      //   data: new FormData(document.getElementById("profileForm")),
      //   cache: false,
      //   contentType: false,
      //   processType: false,
      // }).then(response => {
      //   credentials.profile_path = response.data.data.profile.filePath + '/' + response.data.data.profile.fileName;
      // })
    }

    const deleteProfilePhoto = () => {
      axios({
        method: 'DELETE',
        url: 'member/me/profile'
      })
    }

    return {
      imgchangebutton,
      changeFileName,
      putUserInfo,
      updateProfilePhoto,
      deleteProfilePhoto,
      data,
      credentials,
      user,
    
    }
  },watch: {

  }
}
</script>

<style>
.myInfoBox {
    width: 800px;
    height: 100%;
}
.profileFrame {
  align-items: center;
  width: 20rem;
}
.profileBox .uploadSection{
  display: flex;
  justify-content: center;
  /* width: 90%; */
}
.profile {
  position: inherit;
  
    height: 14rem;
    width: 12rem;
    border: 1px solid black;
    margin: 0 0 2rem 0;
}
#profileImage {
  height: 14rem;
  width: 12rem;
}
.profilePath {
  width: 12rem;
}
.profile_menuButton {
  /* margin-left: 100px; */
  height: 40px;
  width: 7rem;
  background-color: #195C77;
  color: white;
  font: 16px sans-serif;
  border-radius: 3px;
  margin-bottom: 4rem;
  margin: 5px;
}
.cancelButton {
  /* margin-left: 100px; */
  height: 40px;
  width: 7rem;
  background-color: #195C77;
  color: white;
  font: 16px sans-serif;
  margin: 20px 10px 0 0 ;
  border-radius: 3px;
}
.buttonsection{
    display: flex;
    justify-content: center;
    flex-wrap: wrap;
    }

#profile{
  position: absolute; 
  width: 1px; height: 1px; 
  padding: 0; margin: -1px; 
  overflow: hidden; 
  clip:rect(0,0,0,0); border: 0; 
}

#upload_label {
  padding-top:10px;
  padding-bottom: -5px;
}
</style>