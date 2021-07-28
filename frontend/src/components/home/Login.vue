<template>
  <!-- section은 백그라운드가 될 것 -->
  <section :class="['background', isLogin && 'show']" @click.stop="changeModal"> 
    <!-- article은 window 창이 될 것 : 내부에 팝업이 뜨므로? 나중에 확인 -->
    <article class='window'>
      <div class='popup' @click.stop="">
        <form id='login-form' method="POST" @submit.prevent="login">
        <p>
        <label for="email">Email :</label>
        <input type="email" id="email" name="email" v-model="credentials.email">
        </p>
        <p>
        <label for="password">Password :</label>
        <input type="password"  id="password" name="password" v-model="credentials.password">
        </p>
        <button >로그인</button>
      </form>
      </div>
    </article>
  </section>

  <button class='btn-to-a' @click="changeModal">로그인</button>
</template>

<script>
import axios from 'axios'
import { reactive, ref } from '@vue/reactivity'
export default {
  name : "Login",
  setup(){
    // state : credentials
    const credentials = reactive({
      email : "",
      password : "",
    })
    // state : openModel
    const isLogin = ref(false)
    const popup = ref('popup hide')
    // 로그인 함수
    const login = ()=>{
      console.log("로그인!");
      console.log(credentials);
      axios({
      method : "POST",
      url : "#",
      data : {
        credentials
      }
      })
    }
    // 팝업 함수
    const changeModal = ()=>{
      isLogin.value = !isLogin.value
    }
    // setup의 리턴
    return {
      credentials,
      changeModal,
      isLogin,
      login,
      popup,
    }
  }
}
</script>

<style>
  .background {
    visibility: hidden;
    position : fixed;
    top : 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.3);
    z-index: 100;
  }

  .window {
    position: relative;
    width: 100%;
    height: 100%;
  }

  .popup {
    position: absolute;
    top: 50%;
    left: 50%;
    background-color: #ffffff;
    /* 박스 그림자 */
    box-shadow: 0 2px 7px rgba(0, 0, 0, 0.3);
    
    /* 임시 지정 */
    width: 400px;
    height: 500px;
    /* fade in-out */
    opacity: 0;
    /* 위치 조절 */
    /* transform: translate(-50%, -50%); */
    transform: translate(-50%, -46%);
    /* 위치 변화 css 내용 전부 0.5초 주겠다 */
    transition: all 0.5s;
  }
  /* 백그라운드에 들어감 */
  .show { 
    /* 보이게 하겠다. background에 들어감 */
    visibility: visible;
    /* 위치 변화 css 내용 전부 0.5초 주겠다 */
    transition: all 0.5s;
  }
  /* .show와 .popup을 같이 적어야 하는 이유를 모르겠음 */
  .show .popup {
    /* fade in-out */
    opacity: 1;
    /* 위치 조절 */
    transform: translate(-50%, -50%);
    /* 위치 변화 css 내용 전부 0.5초 주겠다 */
    transition: all 0.5s;
  }

  #login-form {
    margin: 30% auto;
  }
  input {
    border: 1px solid black;
  }
</style>