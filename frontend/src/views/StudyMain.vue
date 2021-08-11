<template>
  <AsideFrame>

    <template v-slot:aside-header>
        <img id='aside-header-thumbnail' src="/meeting-thumbnail.jpg" alt="썸네일">
    </template>

    <template v-slot:aside-body>
      <StudyMainAside :menus='menus' :activeIndex='activeStudyNav' @onClickMenu='(idx)=>{$store.commit("SET_STUDY_ACTIVE", idx)}'/>
    </template>

    <template v-slot:section>
      <!-- <header id='section-title'>{{menus[activeIndex]}}</header> -->
      <!-- 컴포넌트 파서 알맞은 위치의 div를 컴포넌트로 대체하면 됨 -->
      <div class='study-main' v-if="activeStudyNav === 0"> <!-- 스터디 메인 --> </div>
      <StudyMainMeeting v-else-if="activeStudyNav === 1" :studyId='studyId' />
      <div v-else-if="activeStudyNav === 2"> 게시글 </div>
      <div v-else-if="activeStudyNav === 3"> 스터디 학습 자료 </div>
      <div v-else-if="activeStudyNav === 4"> 통계 </div>
      <div v-else-if="activeStudyNav === 5"> 스터디 설정 </div>
      <div v-else> 아무것에도 포함 안됨 </div>
    </template>
    
  </AsideFrame>
</template>

<script>
import AsideFrame from '@/components/AsideFrame.vue'
import StudyMainAside from '@/components/study/main/StudyMainAside.vue'
import StudyMainMeeting from '@/components/study/main/StudyMainMeeting.vue'
import { computed } from '@vue/runtime-core'
import { useStore } from 'vuex'
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
    const store = useStore()
    const activeStudyNav = computed(()=>{
      return store.state.activeStudyNav
    })
    return {
      menus,
      activeStudyNav
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
  font-size: 1rem;
  display: flex;
  justify-content: space-between;
  padding: 0 25px;
}

.study-main {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
}
</style>