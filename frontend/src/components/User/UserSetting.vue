<template>
  <h1>마이세팅</h1>
  <button @click="withdraw()">회원 탈퇴</button>
</template>

<script>
import { useStore } from 'vuex'
export default {
  name: 'UserSetting',
  setup() {
    const store = useStore()
    const withdraw = () =>{
      store.dispatch('withdrawMembership');
    }

    return {
      withdraw
    }
  }
}
</script>

<style>

</style>