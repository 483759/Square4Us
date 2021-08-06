<template>
  <button class='btn-to-a' @click="logout">Logout</button>
</template>

<script>
import store from '@/store'
import router from '@/router'
export default {
  name: 'Logout',
  setup() {
    const logout = ()=>{
      store.commit('LOGOUT')
      localStorage.removeItem('JWT')
      router.push({name: 'Main'})
    }
    return {
      logout
    }
  }
}
</script>

<style>

</style>