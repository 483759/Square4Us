<template >
<h1>내 정보</h1>
<hr>
<section class="usersection myInfoBox" >
  <div style="height:100%; margin-bottom : 40px">
    <!-- 왼쪽블럭 -->
    <p>userInfo</p>
    <div>
      <label for="nickname">nickname</label>
      <input type="text" name="nickname" id="nickname" class="inputbox" placeholder="{{credentials.nickname}}" v-model="credentials.nickname">
    </div>
    <div>
      <label for="introduction" >introduction</label>
      <textarea type="textarea" name="introduction" class="inputbox" style="height:250px" id="" cols="30" rows="10" placeholder="{{credentials.introduction}}" v-model="credentials.introduction">
      </textarea>
    </div>
    <div>
      <label for="email">email</label>
      <input type="text" name="email" class="inputbox" placeholder="{{credentials.email}}" v-model="credentials.email">
    </div>
    <div>
      <label for="badge">badge</label>
      <div class="badgeBox"></div>
    </div>
    <button class="menuButton" @click="putUserInfo">프로필 저장</button>
  </div>
  <!-- 오른쪽 블럭 -->
  <div class="profileFrame">
    <div class="profile">
      <img style="height: 360px; width: 300px;" src= "http://img.marieclairekorea.com/2018/10/mck_5bd26c6899aa0-562x709.jpg" alt="없음">
    </div>
    <div style="margin-bottom : 40px" >
      <button class="menuButton" v-show="!data.imgChange" @click="imgchangebutton">프로필 사진 수정</button>
      <div v-if="data.imgChange" >
          <input type="text" v-model="imgUrl" style="height:40px">
          <div class="buttonsection" >
            <button class="cancelButton" @click="imgchangebutton">취소</button>
            <button class="cancelButton" @click="putimage">사진 저장</button>
          </div>
        </div>
    </div> 
  </div>
</section>

</template>

<script>
import axios from 'axios'
import { reactive } from '@vue/reactivity'
export default {
    name: "UserInfo",
    setup(){
        const credentials = reactive({
            nickname: 'your nickname',
            introduction: 'introduction',
            email: 'your email',
            

        })
        const data = reactive({
            imgUrl: "http://img.marieclairekorea.com/2018/10/mck_5bd26c6899aa0-562x709.jpg",
            imgChange: false
        })
        // 개인 프로필 불러오는 함수(computed 대용)
        const getUserInfo = () => {
            const API_URL = "#"

            axios({
                method: 'GET',
                url: API_URL,
                data: {
                    credentials
                }
            }).then(response => {
                this.credentials = response.data
            })
        }
        console.log(data.imgUrl)

        // 버튼 변경 함수
        const imgchangebutton = () => {
            data.imgChange = !data.imgChange
        }
        // 프로필 수정 함수
        const putUserInfo = () => {
            const API_URL = "#"

            axios({
                method: 'PUT',
                url: API_URL,
                data: {
                    credentials
                }
            }).then(response => {
                this.credentials = response.data
            })
        }

    return {
        getUserInfo,
        putUserInfo,
        credentials,
        data,
        imgchangebutton,
        }
    }


}
</script>

<style>
template {
    background-color: #f2f2f2;
}
.usersection{
    display: flex;
    
}
.myInfoBox {
    width: 800px;
    height: 100%;
}
.inputbox {
  height: 50px;
  width: 500px;
  color: #000;
  font: 20px sans-serif;
  margin-bottom: 20px;
  margin-left: 80px;
  margin-right: 80px;
}
.profileFrame {
    width: 600px;
    margin-top: 50px;
    margin-left: 50px;
    margin-right: 50px;
}
.profile {
    margin-bottom: 50px;
    margin-left: 50px;
    margin-right: 50px;
    height: 360px;
    width: 300px;
    border: 1px solid black;
}
.menuButton {
  /* margin-left: 100px; */
  height: 40px;
  width: 200px;
  background-color: #195C77;
  color: white;
  font: 20px sans-serif;
}
.cancelButton {
  /* margin-left: 100px; */
  height: 40px;
  width: 120px;
  background-color: #195C77;
  color: white;
  font: 20px sans-serif;
  margin-left: 10px;
}
.badgeBox {
  height: 200px;
  width: 500px;
  border: 1px solid black;
  margin-left: 80px;
  margin-bottom: 40px;
  margin-right: 80px;
}
.buttonsection{
    display: flex;
    justify-content: center ;
    }
</style>