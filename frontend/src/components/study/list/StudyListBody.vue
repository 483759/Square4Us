<template>
  <article id='study-body'>
    <ul id='study-ul'>
      <StudyListItem v-for='item in state.studyList' :key='item'/>
    </ul>
  </article>

</template>

<script>
import StudyListItem from '@/components/study/list/StudyListItem.vue'
import { reactive } from '@vue/reactivity'
export default {
  name: 'StudyListBody',
  components: {
    StudyListItem
  },
  setup() {
    const state = reactive({
      studyList: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
    })
    return {
      state
    }
  }
}
</script>

<style>

  #study-body {
    width: 100%;
    height: auto;
    background-color: var(--headerBackground);
    box-sizing: border-box;
    padding: 20px 20px;
    
  }

  #study-ul {
    height: 90%;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-evenly;
  }

  #study-ul > li {
    border: 1px solid var(--textColor);
    flex: 1 1 20%;
    min-height: 200px;
    
  }
  
</style>