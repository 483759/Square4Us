<template>
<li>
  <img v-if="meeting.thumbnail" class='meeting-thumbnail' :src="meeting.thumbnail.filePath + '/' + meeting.thumbnail.fileName" alt="미팅룸 이미지">
  <img v-else class='meeting-thumbnail' src="https://daejeon-b308-cku-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail/basic.png" alt="미팅룸 이미지">
  <div class='meeting-title' style="width=400px">{{meeting.name}}</div> 
  <button class='green-button meeting-button' @click="$emit('onEnter', meeting.id)">입장</button>
</li>
</template>

<script>
export default {
  name: 'StudyMainMeetingItem',
  props: {
    meeting: {
      type: Object,
      required: true
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
</style>