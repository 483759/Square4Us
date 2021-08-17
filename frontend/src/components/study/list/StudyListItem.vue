<template>
<ul class="studyList">
  <!-- @click='joinStudy(study.id)' -->
  <li v-for='study in studies' :key='study.id' @click="openModal(study)">
    <div class="studyBox" >
      <div class="studyImageBox">
          <img class=studyImage v-if="study.profile==null" src="/main1.jpg" alt="스터디이미지">
          <img class=studyImage v-else :src='getFilePath(study.profile.filePath, study.profile.fileName)' alt="스터디이미지">
          <div class="studyNameBox">
            {{study.name}} 
            <br>
            <div class="studyCategory">
              [{{study.category}}]
            </div>
          </div>
      </div>
    </div>
  </li>
</ul>
<Modal :isShow='isShow' @switchModal='closeModal'>
  <template v-slot:header >
    <div class="studyModalHead">
      스터디 가입하기
    </div>
    
  </template>

  <!-- 모달바디 -->
  <template v-slot:default>
    <section>
      
      
      <img class='study-signin-img' v-if="state.currentStudy.profile==null" src="/main1.jpg" alt="스터디이미지">
      <img class='study-signin-img' v-else :src='getFilePath(state.currentStudy.profile.filePath, state.currentStudy.profile.fileName)' alt="스터디이미지">
      <div class="studyNameBox">
      {{state.currentStudy.name}} 
      <div class="studyCategory">
        study for {{state.currentStudy.category}}  
      </div>
        <br>
        
      </div>
    </section>
  </template>

  <!-- 모달푸터 -->
  <template v-slot:footer>
    <button class="green-button studySignButton" @click='joinStudy'>가입하기</button>
  </template>

</Modal>
</template>

<script>
import { useStore } from 'vuex';
import Modal from '@/components/home/Modal.vue'
import { reactive, ref } from '@vue/reactivity';
export default {
  name: 'studyListItem',
  props: {
    studies: {
      type: Array,
      required: true
    },
  },
  components : {
    Modal
  },
  setup() {
    // state
    const state = reactive({
      currentStudy: {}
    })
    // 모달 관련
    const isShow = ref(false)
    const openModal = (study)=>{
      state.currentStudy = study
      isShow.value = true
    }
    const closeModal = ()=>{
      isShow.value = false
    }
    // 스토어
    const store = useStore(); 

    const joinStudy = async function () {``
      await store.dispatch('joinStudy', state.currentStudy.id)
      closeModal()
    }
    
    const getFilePath = function(path, name) {
      return path + '/' + name
    }
  
    return {
      isShow,
      state,
      openModal,
      closeModal,
      getFilePath,
      joinStudy
    }
  }
}
</script>

<style>
.studyList{
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly;
}
.studyBox {
  display: flex;
  flex-direction: column;
  
  height: 220px;
  width: 330px;
  margin: 25px 20px 25px 20px;
  transform: scale(1);
  transition: all 0.1s ease-out;

}

.studyImage {
  height:220px;
  width:330px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  transform: scale(1);
}
.studyImage:hover {
  transform: scale(1.02);
  display: flex;
  flex-direction: column;
  justify-content: center;
  overflow: hidden;
  transition: all 0.1s ease-out;
  cursor: pointer;
}
.studyNameBox{
  height: 50px;
  width: inherit;
  display: flex;
  flex-direction: column;
  justify-content: center;
  font-size: 18px;
  font-weight: bold;
  color: #195C77;
}

.studyCategory {
  font-size: 15px;
  font-weight: lighter;
}

.study-signin-img {
  width: 350px;
}
.studyModalHead {
  height: 20px;
}
.studySignButton {
  width: 350px !important;
  height: 50px !important;
  margin:0 auto;

}
</style>