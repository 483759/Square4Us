<template>
<article>
  <header id='section-title'>
    <div>미팅</div>
    <div>
      <select name="" id="" v-model='data.maximum'>
        <option v-for='option in options' :value="option" :key="option">{{ option }}</option>
      </select>
      <button @click='createMeeting'>미팅 생성</button>
    </div>
  </header>
  <ul class='meeting-item'>
    <!-- <h3>스터디 메인 미팅리스트 : 여기 들어오면 axios요청을 보내 목록을 갱신함</h3> -->
    <StudyMainMeetingItem 
      v-for='meeting in state.meetings' 
      :key='meeting.meeting_id' 
      :meeting='meeting'
      @onEnter='onEnter'
      />
  </ul>
</article>
</template>

<script>
import { reactive } from '@vue/reactivity'
import StudyMainMeetingItem from '@/components/study/main/StudyMainMeetingItem.vue'
import router from '@/router'
import { useStore } from 'vuex'
import { onMounted } from '@vue/runtime-core'
export default {
  name: 'StudyMainMeeting',
  props: {
    studyId: {
      type: String,
      required : true
    }
  },
  components : {
    StudyMainMeetingItem
  },
  setup(props) {
    const store = useStore()
    const options = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10] // 미팅 인원 배열
    const data = reactive({
      studyId: props.studyId,
      maximum : 5 
    })

    const createMeeting = ()=>{ // 미팅 인원 정해서 생성
      store.dispatch('createMeeting', data)
    }

    onMounted(()=>{
      store.dispatch('getMeetings',props.studyId)
    })

    const state = reactive({
      meetings : [
        {
        meeting_id: 1,
        thumnail_name: '썸네일이름',
        thumnail_path: '/meeting-thumbnail.jpg',
        run_flag: true,
        maximum: 6 
        },
        {
        meeting_id: 2,
        thumnail_name: '썸네일이름',
        thumnail_path: '/meeting-thumbnail.jpg',
        run_flag: false,
        maximum: 5 
        },
        {
        meeting_id: 3,
        thumnail_name: '썸네일이름',
        thumnail_path: '/meeting-thumbnail.jpg',
        run_flag: true,
        maximum: 4
        },
      ]

    })


    const onEnter = (meetingId)=>{ // 채팅방 입장
      console.log(`${props.studyId}번 스터디, ${meetingId}번 방 입장!`);
      router.push({path: `/study/${props.studyId}/meeting/${meetingId}`})
    }
    return {
      data,
      state,
      options,
      onEnter,
      // selectMax,
      createMeeting
    }
  }
}
</script>

<style>

.meeting-item {
  display: flex;
  width : 100%;
  flex-direction: column;
}
.meeting-item > li {
  display: flex;
  height: 80px;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #195C77;
  padding: 0 10px;
}
</style>