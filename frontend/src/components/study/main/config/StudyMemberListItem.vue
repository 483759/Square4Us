<template>
  <li>
    <div>
      <img v-if="props.member.profile==null" src="/main1.jpg" alt="스터디이미지" width="100">
      <img v-else :src='getFilePath(props.member.profile.filePath, props.member.profile.fileName)' alt="스터디이미지" width="100">
    </div>
    
    <div class='member-email' style="width=300px">{{ member.email }} </div> 
    <div class='member-nickname' style="width=200px">{{ member.nickname }} </div> 
    <div class='member-introduction' style="width=500px">{{ member.introduction }} </div> 

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
    const getFilePath = function(path, name) {
      return path + '/' + name
    }
    return {
      props,
      state,
      router,
      delegate,
      withdraw,
      getFilePath
    }
  }
}
</script>

<style>
.member-email, .member-nickname, .member-introduction {
  flex-grow: 1;
  font-size: 0.95rem;
  font-weight: 600;
}
.read-button {
  width: 100px;
  height: 30px;
}
</style>