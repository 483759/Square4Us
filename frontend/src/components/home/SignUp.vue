<template>
<button class='btn-to-a' @click="modalPopup">
  Signup
</button>

<div v-show="modal == true">
  <div class="modal__background">
    <div class="modal__box">
      <form action="">
        <h2 class="modal_body">SignUp</h2>
        <hr>
        <p>email <input type="text" v-model="email">
        </p>
        <p>password <input type="text" v-model="password">
        </p>
        <p>
          <label for="password_confirmation"></label>
          password_confirmation <input type="text" name="password_confirmation" v-model="password_confirmation">
        </p>
        <button @click="modalPopup">close</button>
        <button >Signup</button>
      </form>
    </div>
  </div>
</div>



  

</template>

<script>
// import axios from 'axios'
import {reactive, ref} from '@vue/reactivity'
export default {
    name: 'SignUp',
    setup() {
      let state = reactive({
        email: '',
        password: '',
        password_confirmation: '',
      })
      console.log(state)
      // function signup() {
      //   axios({
      //     method: 'post',
      //   })
      // }
      const modal = ref(false)
      const modalPopup = () => {
        console.log(modal)
        console.log(modal.value)
        modal.value = !modal.value

      }
      return {
        state,
        modalPopup,
        modal
      }
    }


}
</script>

<style>
/* 모달창의 배경을 만든다 */
.modal__background{
  position: fixed;
  top:0; left: 0; bottom: 0; right: 0;
  background: rgba(0, 0, 0, 0.8);
  z-index: 9997;
}
 
/* 모달창 영역을 꾸민다 */
.modal__box{
  z-index: 9998;
  position: absolute;
  top: calc(50vh - 100px); left: calc(50vw - 200px);
  background-color: var(--background);
  color: var(--textColor);
  display: flex; justify-content: center;
  align-items: center;
  border-radius: 10px;
  width: 400px;
  height: 400px;
}
body {
  margin : 0;
}
div {
  box-sizing: border-box;
}
.black-bg {
   z-index: 9998;
  width: 100%; height:100%;
  background: rgba(0,0,0,0.5);
  position: fixed; padding: 20px;
}
.white-bg {
   z-index: 9997;
  width: 100%; background: white;
  border-radius: 8px;
  padding: 20px;
}
.modal_body {
  position: absolute;
  top: 10%;
  left: 35%;
} 
</style>

