<template>
<li>
  <img v-if="meeting.thumbnail" class='meeting-thumbnail' :src="meeting.thumbnail.filePath + '/' + meeting.thumbnail.fileName" alt="미팅룸 이미지">
  <img v-else class='meeting-thumbnail' src="https://daejeon-b308-cku-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail/basic.png" alt="미팅룸 이미지">
  <div class='meeting-title' style="width=400px">{{meeting.name}}</div>
  <div class="button-box">
    <button v-if="isLeader" class="green-button remove-button" @click="deleteMeeting(meeting.id)">미팅 삭제</button>
    <button class='green-button meeting-button' @click="$emit('onEnter', meeting.name)">입장</button>
  </div>
</li>
</template>

<script>

import { useStore } from 'vuex';
import axios from 'axios';

export default {
  name: 'StudyMainMeetingItem',
  props: {
    meeting: {
      type: Object,
      required: true
    },
    studyId: {
      type: String,
      required: true
    },
    isLeader: {
      type: Boolean,
      required: true
    }
  },
  setup(props) {
    const store = useStore();
    const deleteMeeting = async (id) => {
      const response = await axios({
        method: 'DELETE',
        url: `study/${props.studyId}/meeting/${id}`
      }).catch((err) => {
        console.log(err.response);
      })
      if (!response) {
        alert("삭제 실패!")
        console.log(response);
        return
      }
      store.dispatch('getMeetings', props.studyId);
    }

    return {
      deleteMeeting
    }
  }
}
</script>

<style>
.meeting-thumbnail {
  padding: 10px;
  flex-shrink: 0;
  width: 100px;
  height: 50px;
}
.meeting-title {
  flex-grow: 1;
  font-size: 0.95rem;
  font-weight: 600;
}
.meeting-button {
  width: 100px;
  height: 30px;
}
.remove-button {
  width: 100px;
  height: 30px;
}
.button-box {
  display: flex;
  flex-direction: row;
  justify-content: center;
  gap: 10px;
  margin-bottom: 10px;
}
</style>