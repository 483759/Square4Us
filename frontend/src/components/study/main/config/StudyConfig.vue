<template>
  <header id='section-title'>
    <div>스터디 설정</div>
  </header>

  <button v-if='state.isLeader===state.userId' @click="withdraw()">스터디 폐쇄</button>
</template>

<script>
import { useStore } from 'vuex'
import { reactive } from '@vue/runtime-core'
export default {
  name: 'StudyConfig',
  props: {
    studyId: {
      type: String,
      required : true
    }
  },
  setup(props) {
    const store = useStore()
    const state = reactive({
       userId : store.state.user.id,
       isLeader : store.state.curStudy.leaderId,
    })

    const withdraw = () =>{
      store.dispatch('removeStudy', props.studyId)
    }

    return {
      props,
      state,
      withdraw
    }
  }
}
</script>

<style>

</style>