<template>
<article>
  <header id='section-title'>
    <div>미팅</div>
    <div>
      <meeting-create-button :studyId="data.studyId" />
      <!-- <button class="white-button" @click='createMeeting'>미팅 생성</button> -->
    </div>
  </header>
  <ul class='meeting-item'>
    <!-- <h3>스터디 메인 미팅리스트 : 여기 들어오면 axios요청을 보내 목록을 갱신함</h3> -->
    <StudyMainMeetingItem 
      v-for='meeting in meetings' 
      :key='meeting.id' 
      :meeting='meeting'
      :studyId='data.studyId'
      :isLeader='data.isLeader'
      @onEnter='onEnter'
      />
  </ul>
</article>
</template>

<script>
import { reactive } from '@vue/reactivity'
import StudyMainMeetingItem from '@/components/study/main/meeting/StudyMainMeetingItem.vue'
import router from '@/router'
import { useStore } from 'vuex'
import { computed, onMounted, onUnmounted } from '@vue/runtime-core'
import MeetingCreateButton from '@/components/meeting/MeetingCreateButton.vue'
import axios from 'axios'
export default {
  name: 'StudyMainMeeting',
  props: {
    studyId: {
      type: String,
      required : true
    }
  },
  components : {
    StudyMainMeetingItem,
    MeetingCreateButton
  },
  setup(props) {
    const store = useStore()
    // const options = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10] // 미팅 인원 배열
    const data = reactive({
      studyId: props.studyId,
      maximum : 5,
      isLeader: false
    })

    const createMeeting = async()=>{ // 미팅 인원 정해서 생성
      await store.dispatch('createMeeting', data)
      store.dispatch('getMeetings',props.studyId)
    }

    onMounted(()=>{
      axios({
        method: 'GET',
        url: `study/isLeader/${props.studyId}`
      }).then(response => {
        data.isLeader = response.data.data;
      }).catch(error => {
        console.dir(error);
      });
      store.dispatch('getMeetings',props.studyId)
    })

    onUnmounted(()=>{
      store.commit('SET_MEETINGS', [])
    })
    const meetings  = computed(()=>{
        return store.state.myMeetings
      })
    // const state = reactive({
    //   meetings : computed(()=>{
    //     return store.state.myMeetings
    //   })
    // })


    const onEnter = (meetingName)=>{ // 채팅방 입장
      console.log(`${props.studyId}번 스터디, ${meetingName}번 방 입장!`);
      router.push({path: `/study/${props.studyId}/meeting/${meetingName}`})
    }

    onMounted(()=>{
      store.dispatch('getMeetings', props.studyId)
    })

    onUnmounted(()=>{
      store.commit('SET_MEETINGS', [])
    })


    return {
      data,
      // state,
      meetings,
      // options,
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