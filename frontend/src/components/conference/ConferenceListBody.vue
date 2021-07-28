<template>
  <section id='conf-body'>
    ConferenceBody
    <ul>
      <ConferenceListItem v-for='item in state.studyList' :key='item'/>
    </ul>
  </section>

</template>

<script>
import ConferenceListItem from '@/components/conference/ConferenceListItem.vue'
import { reactive } from '@vue/reactivity'
export default {
  name: 'ConferenceListBody',
  components: {
    ConferenceListItem
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
    height: 100%;
    background-color: white;
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
    border: 1px solid black;
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
      border: 1px solid black;
      flex: 1 1 50%;
      min-height: 300px;
    }
  }

  @media (max-width:720px) {
    #conf-body{
      border-radius: 0;
    }
    ul > li {
      border: 1px solid black;
      flex: 1 1 100%;
      min-height: 250px;
    }
  }
</style>