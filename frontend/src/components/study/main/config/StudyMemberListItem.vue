<template>
  <li>
    <div class="member-profile" v-if="state.profile">
      <img class="profileImgFrame" :src="state.profile" alt="">
    </div>
    <div class="member-profile" v-else>
      <img class="profileImgFrame" src="/logo.png" alt="">
    </div>
    <div class='member-email' style="width=300px">{{ member.email }} </div> 
    <div class='member-nickname' style="width=200px">{{ member.nickname }} </div> 
    <div class='member-introduction' style="width=500px">{{ member.introduction }} </div> 

    <template v-if='state.userId===member.id'>
      <button class="green-button withdrawButtonSize" @click="withdraw">스터디 탈퇴</button>
    </template>
    <template  v-else>
      <button class="green-button withdrawButtonSize" v-if='state.isLeader' @click="delegate">스터디장 변경</button>
      <button v-else class="nullButton"></button>
    </template>
    
  </li>
</template>

<script>
import axios from 'axios'
import router from '../../../../router';
import { useStore } from 'vuex';
import { onMounted, reactive } from '@vue/runtime-core'
// import { computed } from '@vue/runtime-core'
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
      profile : ''
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
    // 프로필 사진 불러오는 함수
    onMounted(() => {
      getProfile()
    })
    const getProfile = () => {
      if(props.member.profile !== null){
        return state.profile = props.member.profile.filePath + '/' + props.member.profile.fileName
      }else{ return state.profile = 0} 
    }
    return {
      props,
      state,
      router,
      delegate,
      withdraw,
      getProfile
      
    }
  }
}
</script>

<style>
.member-profile {

}
.member-email, .member-nickname, .member-introduction {
  flex-grow: 1;
  font-size: 0.95rem;
  font-weight: 600;
  flex-basis: 200px;
  text-align: start;
}
.member-readbutton{
  flex-basis: 100px;
  min-width: 100px;
  box-sizing: border-box;
}
.read-button {
  width: 100px;
  height: 30px;
  box-sizing: border-box;
}
.nullButton{
  width: 100px;
  height: 30px;
  border: transparent;
  background-color: transparent;
  font: 1px transparent;
  box-sizing: border-box;
}
.profileImgFrame{
  width: 50px;
  height: 50px;
  margin: 0 20px;
}
.withdrawButtonSize{
  width: 100px !important;
  margin: 0 auto;
}
</style>