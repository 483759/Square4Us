<template>
  <li>
    <div class='article-title' style="width=400px">{{ member.email }} </div> 
    <div class='article-author' style="width=400px">{{ member.nickname }} </div> 

    <button v-if='state.userId===state.isLeader' @click="delegate">스터디장 변경</button>
    
  </li>
</template>

<script>
import axios from 'axios'
import { useStore } from 'vuex';
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
    const store = useStore()
    const state = reactive({
       userId : store.state.user.id,
       isLeader : store.state.curStudy.leaderId,
    })
    const delegate = async ()=>{
      // /api/study/{studyId}/accept/{memberId}
      const response = await axios({
        url: `/study/${props.studyId}/delegate/${props.member.id}`,
        method: 'PATCH'
      }).catch((err)=>{
        console.log(err.response);
      })
      if (response) {
        store.dispatch('getStudyByNumber', props.studyId);
      }
    }
    return {
      props,
      state,
      delegate
    }
  }
}
</script>

<style>
.article-title, .article-author, .article-date {
  flex-grow: 1;
  font-size: 0.95rem;
  font-weight: 600;
}
.read-button {
  width: 100px;
  height: 30px;
}
</style>