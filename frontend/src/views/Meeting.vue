<template>
  <h1>Meeting</h1>  
  <BeforeMeeting v-if='!state.isReady' @enter='enter' @switchMeeting='switchMeeting'/>
  <div v-else>준비됨 입장합니다.</div>
  
</template>

<script>
import BeforeMeeting from '@/components/meeting/BeforeMeeting.vue'
import { onMounted, reactive } from '@vue/runtime-core'
import router from '@/router'
export default {
  name : "Meeting",
  props : {
    studyId: {
      type: String,
      required: true
    },
    meetingId: {
      type: String,
      required: true
    }
  },
  components : {
    BeforeMeeting
  },
  setup(props) {
    const state = reactive({
      isReady : false
    })
    onMounted(()=>{
      console.log(`${props.studyId}번 스터디, ${props.meetingId}번 방 입장 완료`);
      // 이제 여기서 연결 준비를 하고 세팅을 하고, 들어가면 신호를 주고받고 해서 화상을 시작하면 된다.
    })

    const enter = ()=>{
      console.log('입장!');
      state.isReady = !state.isReady
    }
    const switchMeeting = (newId)=>{
      router.push({path: `/study/${props.studyId}/meeting/${newId}`})
    }



    return {
      state,
      enter,
      switchMeeting
    }
  }
}
</script>

<style>

</style>