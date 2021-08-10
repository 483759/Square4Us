<template>
  <section >
    <Modal :isShow='isShow' @switchModal='switchModal'>
      <!-- 모달헤더 -->
      <template v-slot:header class="header_signup">
        <!-- <header> 헤더도 들어감 </header> -->
        <button class="button_close" @click.stop="switchModal">X</button>

      </template>

      <!-- 모달바디 -->
      <template v-slot:default>
        
        <section style="">
          <div class="sloganbox_signup">
            <img class="logo_signup" src="/square4us.png" alt="">
            <div>
              <p class="welcome_signup">Welcome to </p>
              <span class="slogan_signup">Square4us</span>
            </div>
          
          </div>
          <form id='signup-form' method="POST" @submit.prevent="signup">
            <p><input class="input_signup" type="email" id="email" name="email" placeholder="이메일 입력" v-model="credentials.email"></p>
            <p><input class="input_signup" type="nickname" id="nickname" name="nickname" placeholder="닉네임" v-model="credentials.nickname"></p>
            <p><input class="input_signup" type="password"  id="password" name="password" placeholder="비밀번호" v-model="credentials.password"></p>
            <p><input class="input_signup" type="password_confirmation"  id="password_confirmation" name="password_confirmation" placeholder="비밀번호확인" v-model="credentials.password_confirmation"></p>
            <button class="button_signup">signup</button>
          </form>
        </section>
      </template>

      <!-- 모달푸터 -->
      <template v-slot:footer>
        <!-- <footer>푸터도 들어감</footer> -->
        <p></p>
      </template>

      <!-- 모달여닫는 버튼 (외부노츨) -->
      <template v-slot:button>
          <button class='btn-to-a' @click="switchModal">SignUp</button>
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
      nickname : "",
      password : "",
      password_confirmation : "",
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

  #signup-form {
    margin: 0px 0px auto;
    margin-top: 0;
  }
  .header_signup{
    height: 30px;
    display: flex;
    flex-direction: row-reverse;  
    justify-content: flex-start;
  }
  .sloganbox_signup {
    margin: 30px 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    
  }
  .logo_signup {
    height: 60px;
    width: 60px;
    margin-right: 25px;
  }
  .welcome_signup {
    height: 25px;
    font: 25px sans-serif;
    text-align: flex-start;
  }
  .slogan_signup{
    height: 40px;
    font: 40px sans-serif;
    font-weight: bold;
    margin-top: 0;
  }
  .input_signup {
    border: 1px solid black;
    margin-top: 1px;
    height: 35px;
    margin-bottom: 10px;
    width: 300px;
    flex-basis: 150px;
    box-sizing: border-box;
    border-radius: 5px;
    border: lightgray;
  }
  p {
    margin: 0;
  }
  .button_signup {
    height: 50px;
    width: 300px;
    flex-basis: 150px;
    background: #509186;
    margin: 20px 0 30px;
    box-sizing: border-box;
    border-radius: 5px;
    border: transparent;
  }

  .button_close {
    display: block;
    /* block으로 해야 x가 쉽게 가운데로 옴 */
    height: 30px;
    width: 30px;
    border: gray;
    border-radius: 5px;
    margin: 0 1px 10px 0;

    box-shadow: 2px 1px 3px;
    box-sizing: border-box;
    
  }
  .signup_p {
    font-size: 10px;
  }
</style>