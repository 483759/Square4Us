<template>
<Header/>
<SignUp/>
<Login/>
</template>

<script>
import Header from '@/components/Header'
import SignUp from '@/components/home/SignUp'
import Login from '@/components/home/Login'
// import { reactive } from '@vue/reactivity'
export default {
  name: 'Home',
  components: {
    SignUp,
    Login,
    Header
  }
}

</script>
<style>
.modal-dialog.modal-bigsize { width:100%; height: 30%; margin: 0; padding: 0; }
</style>