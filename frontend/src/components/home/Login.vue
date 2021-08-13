<template>
  <section class="modal_z_index">
    <Modal  :isShow='isShow' @switchModal='switchModal'>
      <!-- 모달헤더 -->
      <template v-slot:header >
        <!-- <header> 헤더도 들어감 </header> -->
        <div class="header_login">
          <button class="button_close" @click.stop="switchModal">X</button>
        </div>
      </template>

      <!-- 모달바디 -->
      <template v-slot:default>
        <section>
          <div class="sloganbox_login">
          <img class="logo_login" src="/square4us.png" alt="">
          <span class="slogan_login">Square4us</span>
        </div>
           <form id='login-form' method="POST" @submit.prevent="login">
          <!-- <p><label for="email">Email</label></p> -->
          <p><input class="input_login" type="email" id="login_email" name="email" placeholder="이메일 입력" v-model="credentials.email"></p>
          <!-- <p><label for="password">Password</label></p> -->
          <p><input class="input_login" type="password"  id="login_password" name="password" placeholder="비밀번호" v-model="credentials.password"></p>
          
          <button class="button_login">Login</button>
          
        </form>
        </section>
      </template>

      <!-- 모달푸터 -->
      <template v-slot:footer>
        <!-- <footer>푸터도 들어감</footer> -->
        <a class="login_p">아직 회원이 아니신가요? </a>
      </template>

      <!-- 모달여닫는 버튼 (외부노츨) -->
      <template v-slot:button>
          <button class='btn-to-a' @click="switchModal">Login</button>
      </template>
    </Modal>
  </section>
</template>

<script>
import Modal from '@/components/home/Modal.vue'
import { reactive, ref } from '@vue/reactivity'
import { useStore } from 'vuex'
export default {
  name: 'Login',
  components : {
    Modal
  },
  setup() {
    // 모달 여닫기 관련
    const isShow = ref(false)
    const switchModal = ()=>{
      isShow.value = !isShow.value
    }
    //로그인 객체
    const credentials = reactive({
      email : "",
      password : "",
    })
    // 로그인 함수
    const store = useStore();
    const login = ()=>{
      store.dispatch('login', credentials)
    }
    return {
      isShow,
      switchModal,
      credentials,
      login,
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
    background-color: var(--background);
    /* 박스 그림자 */
    box-shadow: 0 2px 7px rgba(0, 0, 0, 0.3);
    
    /* 임시 지정 */
    width: 400px;
    height: 400px;
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
  .modal_z_index {
    z-index: 3;
  }

  #login-form {
    margin: 0px 0px auto;
    margin-top: 0;
  }
  .header_login{
    display: flex;
    flex-direction: row;  
    justify-content: end;
    height: 35px;
  }
  .footer_login{
    display: flex;
    justify-content: center;
  }
  .sloganbox_login {
    margin: 60px 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    
  }
  .logo_login {
    height: 65px;
    width: 65px;
    margin-right: 25px;
  }
  .slogan_login{
    height: 40px;
    font: 40px sans-serif;
    font-weight: bold;
  }
  .input_login {
    border: 1px solid gray;
    margin-top: 1px;
    height: 50px;
    margin-bottom: 10px;
    width: 300px;
    flex-basis: 150px;
    box-sizing: border-box;
    border-radius: 5px;
  }
  p {
    margin: 0;
  }
  .button_login {
    height: 50px;
    width: 300px;
    flex-basis: 150px;
    background: #509186;
    margin: 20px 0 30px ;
    box-sizing: border-box;
    border-radius: 5px;
    border: transparent;
  }
  
  .button_close {
    display: block;
    /* block으로 해야 x가 쉽게 가운데로 옴 */
    height: 30px;
    width: 30px;
    border: black 1px solid;
    border-radius: 5px;
    margin: 0;
    padding: auto;
    background-color: lightgray;
    box-shadow: -2px 1px 3px;
    box-sizing: border-box;
    
  }
  .login_p {
    font-size: 10px;
  }
  
</style>