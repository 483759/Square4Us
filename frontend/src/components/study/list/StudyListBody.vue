<template>
  <article id='conf-body'>
    StudyBody
    <ul>
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
    padding: 0 20px;
    
  }

  ul {
    height: 90%;
    padding: 0;
    list-style: none;
    display: flex;
    flex-wrap: wrap;
  }

  ul > li {
    border: 1px solid var(--textColor);
    flex: 1 1 25%;
    min-height: 200px;
  }
  @media (min-width: 1241px) {
    #conf-body {
      border-radius: 5px;
    }
  }
  @media (min-width:721px) and (max-width:1024px) {
    ul > li {
      border: 1px solid var(--textColor);
      flex: 1 1 50%;
      min-height: 300px;
    }
  }

  @media (max-width:720px) {
    #conf-body{
      border-radius: 0;
    }
    ul > li {
      border: 1px solid var(--textColor);
      flex: 1 1 100%;
      min-height: 350px;
    }
  }
</style>