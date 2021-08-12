<template>
  <header id='section-title'>
    멤버 관리
  </header>
  <!--  -->
  <ul>
    <StudyMemberConfigItem v-for='member in state.memberList' :key='member.id' :member='member' :studyId='studyId'/>
  </ul>
</template>

<script>
import axios from 'axios'
import { onMounted, reactive,} from '@vue/runtime-core'
import StudyMemberConfigItem from '@/components/study/main/config/StudyMemberConfigItem'
export default {
  name: 'StudyMemberConfig',
  props: {
    studyId :{
      type: String,
      required: true
    }
  },
  components: {
    StudyMemberConfigItem
  },
  setup(props){
    /// api/member/study/{studyId}/wait
    const state = reactive({
      memberList : []
    })
    const getSignup = async()=>{
      const response = await axios({
        url: `/member/study/${props.studyId}/wait`,
        method: 'GET'
      }).catch((err)=>{
        console.log(err.response);
      })
      if (response) {
        console.log(response.data.data.memberList);
        state.memberList = response.data.data.memberList
      }
    }
    onMounted(()=>{
      getSignup()
    })
    return {
      state,
    }
  }
}
</script>

<style>

</style>