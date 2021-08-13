<template>
  <header id='section-title'>
    <div>스터디 설정</div>
  </header>
  <article>
     <!-- 여기에 이제 스터디 프로필 변경을 넣기 -->
    <section>
      d
    </section>
    <button v-if='isLeader' @click="withdraw()">스터디 폐쇄</button>
  </article>
</template>

<script>
import { computed } from '@vue/runtime-core'
import { useStore } from 'vuex'
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
    const isLeader = computed(()=> store.getters.isLeader)
    const withdraw = () =>{
      store.dispatch('removeStudy', props.studyId)
    }
    return {
      props,
      isLeader,
      withdraw
    }
  }
}
</script>

<style>

</style>