<template>
  <Modal :isShow='isShow' @switchModal='switchModal' width='350px' height='450px' top='400px' left='78vw'>
    <template v-slot:header>
      <header id='header'>스터디 생성</header>
    </template>
    <template v-slot:default>
      <section id='section'>
        <p>스터디이름</p>
          <input class="study_create_input" type="text" name="" id="study_name" v-model="data.name">
        <p>카테고리</p>
          <select  class="study_create_input" name="" id="" v-model="data.category">
            <option value="coding">코딩</option>
            <option value="cert">자격증</option>
            <option value="official">공시</option>
            <option value="just">모각코</option>
          </select>
        <!-- <p>스터디인원</p>
          <select  class="study_create_input" name="" id="" >
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
          </select>
        <p>스터디소개</p>
          <input class="study_create_input" type="text" name="" id="study_"> -->
      </section>
    </template>
    <template v-slot:footer>
      <button @click="createStudy">생성</button>
    </template>
    <template v-slot:button>
      <button class='white-button' @click='switchModal'>
        <div id='button-text'>스터디생성</div> 
        <div id='button-icon'>v</div> 
      </button>
    </template>
  </Modal>
</template>

<script>
import Modal from '@/components/home/Modal.vue'
import { ref, reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
export default {
  name: 'StudyCreateButton',
  components: {
    Modal
  },
  setup(){
    // 모달 여닫기 관련
    const isShow = ref(false)
    const switchModal = ()=>{
      isShow.value = !isShow.value
    }
    // 스터디 데이터
    const data = reactive({
      name: "",
      category: "",
    })
    const store = useStore();
    const createStudy = async ()=> {
      store.dispatch('createStudy', data)
      
      store.commit('SET_STUDY_ACTIVE', 0)
    }

    return {
      isShow,
      switchModal,
      data,
      createStudy,
    }
  }
}
</script>

<style scoped>
#header {
  height: 50px;
}

#section {
  flex-grow: 1;
}

.white-button {
  background-color: white;
  border: 0;
  padding: 0;
  box-shadow: 0 0 0 1px #195C77 inset;
  color: #195C77;
  font-size: 0.8rem;
  font-weight: 600;
  border-radius: 5px;
  width: 150px;
  height: 35px;

  display: flex;
  justify-content: space-around;
  align-items: center;
  cursor: pointer;
}

.white-button:hover {
  color: #42b983;
  box-shadow: 0 0 0 1px #42b983 inset;
  border: 0px;
  transition: 0.3s;
}

#button-text {
  box-sizing: border-box;
  height: 100%;
  border-right: 1px solid #195C77;
  flex-grow: 1;
  padding: 8px 0;
}

#button-text:hover {
  border-right: 1px solid #42b983;
  color: #42b983;
  transition: 0.3s !important;
}


#button-icon {
  justify-content: center;
  width: 25px;
  padding-right: 1.5px;
}

.study_create_input {
  width: 200px;
  height: 30px;
  box-sizing: border-box;
}
</style>