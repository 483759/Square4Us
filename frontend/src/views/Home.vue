<template>
<h1>Home</h1>
<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal"  @click="signup">
  sign up
</button>

<!-- Modal -->
<div class="modal fade modal-dialog modal-dialog-centered  modal-dialog-scrollable" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" >
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">SignUp</h5>
        
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <SignUp />
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="signup">Close</button>
        <button type="button" class="btn btn-primary" >Sign Up</button>
      </div>
    </div>
  </div>
</div>






</template>

<script>
import SignUp from '@/components/home/SignUp'
import { reactive } from '@vue/reactivity'
export default {
  name: 'Home',
  components: {
    SignUp,
  },
  setup() {
    let state = reactive({
      signupmodal: false,
    })

    function signup() {
      state.signupmodal = !state.signupmodal
      console.log(state.signupmodal)
       }
    return {
      state,
      signup,
      
    }
    
  }
}
</script>
<style>
.modal-dialog.modal-bigsize { width:100%; height: 30%; margin: 0; padding: 0; }
</style>