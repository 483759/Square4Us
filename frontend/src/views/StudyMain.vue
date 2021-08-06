<template>
  <AsideFrame>

    <template v-slot:aside-header>
        <img id='aside-header-thumbnail' src="/meeting-thumbnail.jpg" alt="썸네일">
    </template>

    <template v-slot:aside-body>
      <StudyMainAside :menus='menus' :activeIndex='activeIndex' @onClickMenu='(idx)=>{activeIndex = idx}'/>
    </template>

    <template v-slot:section>
      <header id='section-title'>{{menus[activeIndex]}} (스터디_ID  {{ studyId }})</header>
      <!-- 컴포넌트 파서 알맞은 위치의 div를 컴포넌트로 대체하면 됨 -->
      <div class='study-main' v-if="activeIndex === 0"> <!-- 스터디 메인 --> </div>
      <StudyMainMeeting v-else-if="activeIndex === 1" :studyId='studyId' />
      <div v-else-if="activeIndex === 2"> 게시글 </div>
      <div v-else-if="activeIndex === 3"> 스터디 학습 자료 </div>
      <div v-else-if="activeIndex === 4"> 통계 </div>
      <div v-else-if="activeIndex === 5"> 스터디 설정 </div>
      <div v-else> 아무것에도 포함 안됨 </div>
    </template>
    
  </AsideFrame>
</template>

<script>
import AsideFrame from '@/components/AsideFrame.vue'
import StudyMainAside from '@/components/study/main/StudyMainAside.vue'
import StudyMainMeeting from '@/components/study/main/StudyMainMeeting.vue'
import { ref } from '@vue/reactivity'
export default {
  name: 'StudyMain',
  props: {
    studyId: {
      type: String,
      required : true
    }
  },
  components :{
    AsideFrame,
    StudyMainAside,
    StudyMainMeeting
  },
  setup() {
    const menus = [
      '스터디 메인', 
      '미팅', 
      '게시글', 
      '스터디 학습 자료', 
      '통계', 
      '스터디 설정'
    ]
    const activeIndex = ref(0)
    return {
      menus,
      activeIndex,
    }
  }
}
</script>

<style>
#aside-header-thumbnail{
  width: 230px;
  border-radius: 5px;
  box-shadow: -1px -1px 1px 1px rgb(231, 231, 231);
}

#section-title{
  box-sizing: border-box;
  border-bottom: 1px solid #195C77;
  color: #195C77;
  height: 60px;
  align-items: center;
  font-weight: bold;
  font-size: 1.1rem;
  display: flex;
  padding-left: 25px;
}

.study-main {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
}
</style>