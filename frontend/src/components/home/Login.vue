<template>
  <section>
    <Modal :isShow='isShow' @switchModal='switchModal'>
      <!-- 모달헤더 -->
      <template v-slot:header>
        <!-- <header> 헤더도 들어감 </header> -->
      </template>

      <!-- 모달바디 -->
      <template v-slot:default>
        <section>
          <form id='login-form' method="POST" @submit.prevent="login">
            <p>
              <label for="email">Email :</label>
              <input type="email" id="email" name="email" v-model="credentials.email">
            </p>
            <p>
              <label for="password">Password :</label>
              <input type="password"  id="password" name="password" v-model="credentials.password">
            </p>
            <button>Login</button>
          </form>
        </section>
      </template>

      <!-- 모달푸터 -->
      <template v-slot:footer>
        <!-- <footer>푸터도 들어감</footer> -->
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

</style>