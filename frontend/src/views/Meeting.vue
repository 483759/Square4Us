<template>
  <h1>Meeting</h1>
</template>

<script>
import { onMounted } from '@vue/runtime-core'
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
  setup(props) {
    onMounted(()=>{
      console.log(`${props.studyId}번 스터디, ${props.meetingId}번 방 입장 완료`);
      // 이제 여기서 연결 준비를 하고 세팅을 하고, 들어가면 신호를 주고받고 해서 화상을 시작하면 된다.
    })
  }
}
</script>

<style>

</style>