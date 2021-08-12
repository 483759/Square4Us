<template>
<ul>
  <li v-for='study in studies' :key='study.id'>
      {{study}} <button @click='joinStudy(study.id)'>가입</button>
  </li>
</ul>

</template>

<script>
import { useStore } from 'vuex';
export default {
  name: 'studyListItem',
  props: {
    studies: {
      type: Array,
      required: true
    },
  },
  setup() {
  const store = useStore(); 
  console.log(store);
  const joinStudy = function (studyId) {
    store.dispatch('joinStudy', studyId)
  }
  
    return {
      joinStudy
    }
  }
}
</script>

<style>

</style>