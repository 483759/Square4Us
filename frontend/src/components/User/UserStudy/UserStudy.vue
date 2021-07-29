<template>
  <h1>스터디관리</h1>
  <button @click="getStudy">a</button>
  <StudyList/>
</template>

<script>

// import axios from 'axios'
import StudyList from '@/components/User/UserStudy/StudyList.vue'
export default {
// studymanage에서 내가 가입한 스터디 목록을 요청하고 배열에 받는다.
// 받은 배열을 studylist로 보내고 v-for로 studycard에 담아 보여준다
name: 'studymanage',
components: {
  StudyList,
},
setup() {
  // const getStudy = () => {
  //   const API_URL = 'http://localhost:8080/api/member/me'

  //   axios({
  //     method: 'GET',
  //     url: API_URL,
  //   }).then(response => {
  //     console.log(response)
      
  //   })
  // }
  return {
    // getStudy,
    // StudyList
  }
}
}
</script>

<style>

</style>