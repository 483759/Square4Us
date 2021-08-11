<template>
  <select name="study-list" id="study-select" v-model='selectedStudy' @change="selectStudy">
    <option value="default" disabled key='0'>MyStudy</option>
    <option v-for='study in myStudies' :value="study.id" :key='study.id' >{{study.name}}</option>
  </select>
</template>

<script>
import { ref } from '@vue/reactivity';
import router from '../../../router';
export default {
  name: 'MyStudy',
  props: {
    myStudies : {
      type: Array
    }
  },
  setup() {
    const selectedStudy = ref('default')
    const selectStudy = ()=>{
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