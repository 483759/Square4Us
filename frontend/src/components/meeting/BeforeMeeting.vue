<template>
  <section>
    BeforeMeeting
    <button @click="$emit('enter')">입장</button>
    <!-- 목록에 있는 방을 드랍다운으로 바꿀 수 있도록 할 것 -->
    <!-- 입장 가능한 방만 보여줘야 한다 -->
    <button @click="$emit('switchSession', 1)">1번 세부 세션</button>
    <button @click="$emit('switchSession', 2)">2번 세부 세션</button>
    <button @click="$emit('switchSession', 3)">3번 세부 세션</button>
    <button @click="$emit('switchSession', 4)">4번 세부 세션</button>
    <button @click="$emit('switchSession', 5)">5번 세부 세션</button>
    <button @click="$emit('exit')">돌아가기</button>
  </section>
</template>

<script>
export default {
  name: "BeforeMeeting",
};
</script>

<style></style>
