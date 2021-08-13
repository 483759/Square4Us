<template>
<div>
  <h1>회원탈퇴</h1>
  <button @click="switchModal" class="withdrawlButton">회원탈퇴!</button>
  <section class="modal_z_index">
    <Modal :isShow='isShow' @switchModal='switchModal'>
      <template v-slot:header class="header_withdrawl">
        <div style="display: flex">
          <button class="button_close" @click.stop="switchModal">X</button>
        </div>
        
      </template>
      <template v-slot:default>
        <div class="withdrawlBox">
          <div class="sloganbox_login">
            <img class="logo_login" src="/square4us.png" alt="">
            <span class="slogan_login">Square4us</span>
        </div>
          <div class="withdrawlMessage">
            <p>저장된 모든 정보가 삭제됩니다.</p><br>
            <p>정말 탈퇴하시겠습니까?</p>
          </div>
          <div >
            <button @click="withdraw()" class="withdrawlButton">회원 탈퇴</button>
          </div>
        </div>
        
      </template>

      <!-- 모달푸터 -->
      <template v-slot:footer>
      </template>
    </Modal>
  </section>
</div>
  
  
</template>

<script>
import { ref } from '@vue/reactivity'
import { useStore } from 'vuex'
import Modal from '@/components/home/Modal.vue'
export default {
  name: 'UserSetting',
  components : {
    Modal
  },
  setup() {
    const isShow = ref(false)
    const switchModal = ()=>{
      isShow.value = !isShow.value
      console.log(isShow.value)
    }
    const store = useStore()
    const withdraw = () =>{
      store.dispatch('withdrawMembership');
    }

    return {
      isShow,
      withdraw,
      switchModal,
      Modal
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
  .header_withdrawal{
    display: flex;
    flex-direction: row; 
    justify-content: flex-end; 
    height: 35px;
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
  .sloganbox_login {
    margin: 60px 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    
  }
  .slogan_login{
    height: 40px;
    font: 40px sans-serif;
    font-weight: bold;
  }
  .withdrawlBox{
    height: 420px;
    width: 350px;
    margin: auto;
    background-color: #f2f2f2;
  }
  .withdrawlMessage{
    display: inline-block;
    flex-wrap: nowrap;
    font-size: 20px;
    font-weight: bold;
    color: gray;
    margin: 0 0 50px 0 ;
  }
  .withdrawlButton {
    border: none;
    border-radius: 5px;
    background-color: #da3939;
    color: white;
    height: 50px;
    width: 150px;
    font-size: 20px;
  }
</style>