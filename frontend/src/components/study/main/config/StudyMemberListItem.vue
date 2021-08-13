<template>
  <li>
    <div class='article-title' style="width=400px">{{ member.email }} </div> 
    <div class='article-author' style="width=400px">{{ member.nickname }} </div> 

    <template v-if='state.userId===member.id'>
      <button @click="withdraw">스터디 탈퇴</button>
    </template>
    <template v-else>
      <button v-if='state.isLeader' @click="delegate">스터디장 변경</button>
    </template>
    
  </li>
</template>

<script>
import axios from 'axios'
import router from '../../../../router';
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
       isLeader : store.getters.isLeader,
       userId : store.state.user.id,
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
    const withdraw = async () =>{
      const response = await axios({
        url: `/study/${props.studyId}/withdraw`,
        method: 'POST'
      }).catch((err)=>{
        console.log(err.response);
      })
      if (response) {
        store.dispatch('getMyStudies');
        store.commit('SET_CURRENT_STUDY', []);
        router.push({name: 'Main'})
      }
    }
    return {
      props,
      state,
      router,
      delegate,
      withdraw
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