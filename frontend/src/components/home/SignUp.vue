<template>
  <div>
    <div style="height: 100px">
      <h3>email</h3>
      <input style="height: 40px " type="text" v-model="state.email"> 
    </div>
  
  <div style="height: 100px">
    <h3>password</h3>
    <input style="height: 40px " type="text" v-model="state.password">
  </div>

  <div style="height: 100px">
    <h3>password confirmation</h3>
    <input style="height: 40px " type="text" v-model="state.password_confirmation">
  </div>
  
   <!-- @click="signup" -->
  </div>  
  




  

</template>

<script>
// import axios from 'axios'
import {reactive} from 'vue'
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

      return {
        state
      }
    }


}
</script>

<style>

</style>