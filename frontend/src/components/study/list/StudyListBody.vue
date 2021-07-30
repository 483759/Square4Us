<template>
  <article id='conf-body'>
    <ul id='study-list'>
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

  #conf-body {
    width: 100%;
    height: auto;
    background-color: var(--headerBackground);
    box-sizing: border-box;
    padding: 20px 20px;
    
  }

  #study-list {
    height: 90%;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-evenly;
  }

  #study-list > li {
    border: 1px solid var(--textColor);
    flex: 1 1 20%;
    min-height: 200px;
    
  }
  @media (min-width: 1241px) {
    #conf-body {
      border-radius: 5px;
    }
  }
  @media (min-width:721px) and (max-width:1024px) {
    #study-list > li {
      border: 1px solid var(--textColor);
      flex: 1 1 40%;
      min-height: 300px;
    }
  }

  @media (max-width:720px) {
    #conf-body{
      border-radius: 0;
    }
    #study-list > li {
      border: 1px solid var(--textColor);
      flex: 1 1 100%;
      min-height: 350px;
    }
  }
</style>