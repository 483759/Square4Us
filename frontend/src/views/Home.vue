<template>
<h1>Home</h1>
<SignUp/>






</template>

<script>
import SignUp from '@/components/home/SignUp'
// import { reactive } from '@vue/reactivity'
export default {
  name: 'Home',
  components: {
    SignUp,
  }
    
  }

</script>
<style>
.modal-dialog.modal-bigsize { width:100%; height: 30%; margin: 0; padding: 0; }
</style>