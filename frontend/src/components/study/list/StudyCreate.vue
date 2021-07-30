<template>
  <article id='study-create'>
    StudyCreate
    <Modal width='600px' height='800px' top='500px' left ='500px'>
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
        
      </template>
    </Modal>
  </article>
</template>

<script>
import Modal from '@/components/home/Modal.vue'
export default {
  name: 'StudyCreate',
  components: {
    Modal
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