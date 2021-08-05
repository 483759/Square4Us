<template>
  <section>
    <Modal :isShow='isShow' @switchModal='switchModal' width='500px' height='400px'>
      <!-- 모달헤더 -->
      <template v-slot:header>
        <header id='login-title'>Login</header>
      </template>

      <!-- 모달바디 -->
      <template v-slot:default>
        <section id='login-form'>
          <form  method="POST" @submit.prevent="login" @keyup.enter="login">
            <p>
              <label for="email">Email :</label>
              <input type="email" id="email" name="email" v-model="credentials.email">
            </p>
            <p>
              <label for="password">Password :</label>
              <input type="password"  id="password" name="password" v-model="credentials.password">
            </p>
          </form>
        </section>
      </template>

      <!-- 모달푸터 -->
      <template v-slot:footer>
        <footer id='login-footer'>
          <button @click='login' id='login-button'>Login</button>
        </footer>
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
import router from '@/router'
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
    const login = async ()=>{
      const response = await store.dispatch('login', credentials)
      if (response) {
        switchModal()
        router.push({name: 'StudyList'})
      }
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
#login-title {
  font-size: 1.7rem;
  font-weight: 600;
  flex-basis:80px;
  display: flex;
  justify-content: center;
  align-items: flex-end;
}

#login-form {
  flex-grow:1;
  display: flex;
  justify-content: center;
  align-items: center;
}

#login-footer {
  flex-basis:120px;
}

#login-button {
  font-size: 0.9rem;
  color: #195C77;
  font-weight: 600;
  /* background-color: #FFFFFF; */
  /* border-radius : 4px; */
  width:80px;
  height: 40px; 
}
</style>