<template>
  <header id='section-title'>
    멤버 관리
  </header>
  <!--  -->
  <h2> 스터디 멤버 </h2>
  <ul class='member-item'>
    <StudyMemberListItem v-for='member in state.memberList' :key='member.id' :member='member' :studyId='studyId'/>
  </ul>
  <div v-if='state.isLeader && state.waitList.length!=0'>
    <h2> 가입 신청한 멤버 </h2>
    <ul class='member-item'>
      <StudyMemberConfigItem v-for='member in state.waitList' :key='member.id' :member='member' :studyId='studyId' @refreshWaitList='refreshWaitList'/>
    </ul>
  </div>
</template>

<script>
import axios from 'axios'
import { useStore } from 'vuex';
import { onMounted, reactive,} from '@vue/runtime-core'
import StudyMemberListItem from '@/components/study/main/config/StudyMemberListItem'
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
    StudyMemberConfigItem,
    StudyMemberListItem
  },
  setup(props){
    const store = useStore()
    const state = reactive({
      memberList : [],
      isLeader : store.getters.isLeader,
      waitList : []
    })

    const joinedListup = async()=>{
      const response = await axios({
        url: `/member/study/${props.studyId}`,
        method: 'GET'
      }).catch((err)=>{
        console.log(err.response);
      })
      if(response.status===200){
        state.memberList = [...response.data.data.memberList]
      }
    }
    const getSignup = async()=>{
      const response = await axios({
        url: `/member/study/${props.studyId}/wait`,
        method: 'GET'
      }).catch((err)=>{
        console.log(err.response);
      })
      if (response) {
        state.waitList = [...response.data.data.memberList]
      }
    }
    // 가입 대기목록 갱신
    const refreshWaitList = ()=>{
      getSignup()
      joinedListup()
    }

    onMounted(()=>{
      refreshWaitList()
    })
    return {
      state,
      refreshWaitList
    }
  }
}
</script>

<style>
.member-item {
  display: flex;
  width : 100%;
  flex-direction: column;
}
.member-item > li {
  display: flex;
  height: 80px;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #195C77;
  padding: 0 10px;
}
</style>