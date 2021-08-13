<template>
  <li>
    <div class='article-title' style="width=400px">{{ member.email }} </div> 
    <div class='article-author' style="width=400px">{{ member.nickname }} </div> 

    <v-if>
      <button v-if='state.userId===state.isLeader' @click="approve">가입 승인</button> <button @click='reject'>가입 거절</button>
    </v-if>
  </li>
</template>

<script>
import axios from 'axios'
import { useStore } from 'vuex'
import { reactive } from '@vue/runtime-core'
export default {
  name: 'StudyMemberConfigItem',
  props: {
    member: {
      type: Object,
      required: true
    },
    studyId: {
      type: String,
      required: true
    }
  },
  setup(props){
    /// api/member/study/{studyId}/wait
    // 가입 승인
    const store = useStore()
    const state = reactive({
       userId : store.state.user.id,
       isLeader : store.state.curStudy.leaderId,
    })
    const approve = async ()=>{
      // /api/study/{studyId}/accept/{memberId}
      const response = await axios({
        url: `/study/${props.studyId}/accept/${props.member.id}`,
        method: 'POST'
      }).catch((err)=>{
        console.log(err.response);
      })
      if (response) {
        console.log(response.data.data);
      }
    }
    // 가입 거절 : 아직 없음
    const reject = async ()=>{
      const response = await axios({
        url: `/study/${props.studyId}/reject/${props.member.id}`,
        method: 'POST'
      }).catch((err)=>{
        console.log(err.response);
      })
      if (response) {
        console.log(response.data.data);
      }
    }
    return {
      state,
      approve,
      reject
    }
  }
}
</script>

<style>

</style>