<template>
  <select name="study-list" id="study-select" v-model='selectedStudy' @change="selectStudy">
    <option value="default" disabled key='0'>MyStudy</option>
    <option v-for='study in myStudies' :value="study.id" :key='study.id' >{{study.name}}</option>
  </select>
</template>

<script>
import { ref } from '@vue/reactivity';
import router from '../../../router';
import { useStore } from 'vuex';
export default {
  name: 'MyStudy',
  props: {
    myStudies : {
      type: Array
    }
  },
  setup(props) {
    const store = useStore()
    console.log(props.myStudies);
    const selectedStudy = ref('default')
    const selectStudy = async()=>{
      await store.dispatch('getStudyByNumber', selectedStudy.value);
      store.commit('SET_STUDY_ACTIVE', 0)
      router.push({path: `/study/${selectedStudy.value}`})
      selectedStudy.value = 'default'
    }
    return {
      selectedStudy,
      selectStudy
    }
  }
}
</script>

<style>

</style>
