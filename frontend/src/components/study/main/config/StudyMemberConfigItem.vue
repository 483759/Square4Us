<template>
  <li>
    <span>{{ member }}</span> <button @click="approve">가입 승인</button> <button @click='reject'>삭제</button>
  </li>
</template>

<script>
import axios from 'axios'
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
    const reject = ()=>{

    }
    return {
      approve,
      reject
    }
  }
}
</script>

<style>

</style>