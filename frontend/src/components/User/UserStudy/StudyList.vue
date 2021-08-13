<template>
  <section id='user-info'>
    <option v-for='study in myStudies' :value="study.id" :key='study.id' @click="selectStudy(study.id)" >{{study}}</option>
</section>
</template>

<script>
import { ref } from '@vue/reactivity';
import router from '../../../router';
import { useStore } from 'vuex';
import { computed} from '@vue/runtime-core'
export default {
  name: 'studyManage',
  setup() {
    const store = useStore()
    const myStudies = computed(()=>store.state.myStudies)

    const selectedStudy = ref('default')

    const selectStudy = (studyId)=>{
      router.push({path: `/study/${studyId}`})
      selectedStudy.value = 'default'
    }
    return {
      myStudies,
      selectedStudy,
      selectStudy
    }
  }
}
</script>

<style>
#user-info {
  flex-grow: 1;
}

</style>