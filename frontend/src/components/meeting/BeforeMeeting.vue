<template>
  <section>
    BeforeMeeting
    <button @click="$emit('enter')"> 입장 </button>
    <!-- 목록에 있는 방을 드랍다운으로 바꿀 수 있도록 할 것 -->
    <!-- 입장 가능한 방만 보여줘야 한다 -->
    <button @click="$emit('switchMeeting', 1)">1번방으로 변경</button>
    <button @click="$emit('switchMeeting', 2)">2번방으로 변경</button>
    <button @click="$emit('switchMeeting', 3)">3번방으로 변경</button>
    <button @click="$emit('switchMeeting', 4)">4번방으로 변경</button>
    <button @click="$emit('switchMeeting', 5)">5번방으로 변경</button>
  </section>
</template>

<script>
export default {
  name: 'BeforeMeeting',
}
</script>

<style>

</style>