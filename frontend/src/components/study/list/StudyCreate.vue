<template>
  <article id='study-create'>
    StudyCreate
    <Modal :isShow='isShow' @switchModal='switchModal'>
      <template v-slot:header>
        <div>헤더</div> 
      </template>
      <template v-slot:default>
        <div>바디</div> 
      </template>
      <template v-slot:footer>
        <div>푸터</div> 
      </template>
      <template v-slot:button>
        <button @click="switchModal">스터디생성</button>
      </template>
    </Modal>
  </article>
</template>

<script>
import Modal from '@/components/home/Modal.vue'
import { ref } from '@vue/reactivity'
export default {
  name: 'StudyCreate',
  components: {
    Modal
  },
  setup() {
        // 모달 여닫기 관련
    const isShow = ref(false)
    const switchModal = ()=>{
      isShow.value = !isShow.value
    }
    return {
      isShow,
      switchModal
    }
  }
}
</script>

<style>
  #study-create{
    width: 15%;
    border: 1px solid black;
    overflow: hidden;
  }
</style>