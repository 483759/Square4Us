<template>
<li>
  <img class='meeting-thumbnail' :src="meeting.thumnail_path" :alt="meeting.thumnail_name">
  <div class='meeting-title' style="width=400px">미팅{{ meeting.meeting_id }} </div> 
  <button class='meeting-button' :disabled='meeting.run_flag!=="T"' @click="$emit('onEnter', meeting.id)">입장</button>
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
  },
}
</script>

<style>
.meeting-thumbnail {
  flex-shrink: 0;
  width: 100px;
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