<template>
  <Modal :isShow='isShow' @switchModal='switchModal' width='350px' height='450px' top='400px' left='78vw'>
    <template v-slot:header>
      <div id='header'>
        <p style="padding: 10px">스터디 생성하기</p>
      </div>
      <!-- <header id='header'>스터디 생성</header> -->
    </template>
    <template v-slot:default>
      <section id='section'>
        <div class="study_create_box">
            <div id="create_box">
            <p>스터디 이름</p>
            <input class="study_create_input" type="text" name="" id="study_name" v-model="data.name">
          </div>
          <div id="create_box">
            <p>카테고리</p>
            <select  class="study_create_input" name="" id="" v-model="data.category">
              <option value="coding">코딩</option>
              <option value="cert">자격증</option>
              <option value="official">공시</option>
              <option value="just">모각코</option>
            </select>
            <button class="study_create_button" @click="createStudy">생성</button>
          </div>
        </div>
        
        
        
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
    <template v-slot:footer id='footer'>
    </template>
    <template v-slot:button>
      <button class='white-button' @click='switchModal'>스터디생성</button>
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
  font-size: 25px;
  font-weight: bold;
  padding-top: 10px;
}

#section {
  flex-grow: 1;
  margin-top: 30px;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}
#footer {
  
}


#create_box{
  display: inline-block;
  flex-direction: column;
  justify-content: center;
  align-content: center;
  height: 90px;
  
}




.study_create_box {
  padding-top: 30px;
  width: 300px;
  height: 300px;
  background-color: #fff;
  /* border-top: px solid #195C77; */
  border-bottom: 5px solid #195C77;
}
.study_create_box p {
  font-size: 16px;
  font-weight: bold;
  margin-top: 10px 0 10px;
}

.study_create_input {
  width: 200px;
  height: 45px;
  font-size: 20px;
  font-family: 'Inter', sans-serif;
  box-sizing: border-box;
  border-radius: 5px;
  margin-top: 10px;
  border: 2px solid #195C77;
}
.study_create_button {
  width: 200px;
  height: 45px;
  box-sizing: border-box;
  background-color: #195C77;
  color: white;
  font-size: 18px;
  border-radius: 5px;
  margin-top: 55px;
  
}
</style>