<template >
<h1>내 정보</h1>
<hr>
<section class="usersection myInfoBox" >
    <div>
        <!-- 왼쪽블럭 -->
        <p>userInfo</p>
        <div>
            <label for="nickname">nickname</label>
            <input type="text" name="nickname" id="nickname" class="inputbox">
        </div>
        <div>
            <label for="introduction" >introduction</label>
            <input type="textarea" name="introduction" class="inputbox" style="height:250px">
        </div>
        <div>
            <label for="email">email</label>
            <input type="text" name="email" class="inputbox">
        </div>
        <div>
            <label for="badge">badge</label>
            <div class="badgeBox"></div>
        </div>
    </div>
    <!-- 오른쪽 블럭 -->
    <div class="profileFrame">
        <div class="profile" >
            <img style="height: 360px; width: 300px;" src= "http://img.marieclairekorea.com/2018/10/mck_5bd26c6899aa0-562x709.jpg" alt="없음">
        </div>
        <div style="margin-bottom : 40px" >
            <button class="menuButton" v-show="!data.imgChange" @click="changeImg">프로필 수정</button>
            <div v-if="data.imgChange">
                <input type="text" v-model="imgUrl" style="height:40px">
                <button class="cancelButton" @click="changeImg">취소</button>
            </div>
        </div>
        <button class="menuButton" >저장</button>
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
            nickname: '',
            email: '',
            

        })
        const data = reactive({
            imgUrl: "http://img.marieclairekorea.com/2018/10/mck_5bd26c6899aa0-562x709.jpg",
            imgChange: false
        })
        
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

        const changeImg = () => {
            data.imgChange = !data.imgChange
        }


    return {
        getUserInfo,
        credentials,
        data,
        changeImg
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
  width: 150px;
  background-color: #195C77;
  color: white;
  font: 20px sans-serif;
}
.cancelButton {
  /* margin-left: 100px; */
  height: 40px;
  width: 100px;
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
  margin-bottom: 80px;
  margin-right: 80px;
}
</style>