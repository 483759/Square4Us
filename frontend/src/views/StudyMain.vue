<template>
<div>{{isLeader}}</div>
  <AsideFrame>

    <template v-slot:aside-header>
        <img id='aside-header-thumbnail' src="/meeting-thumbnail.jpg" alt="썸네일">
    </template>

    <template v-slot:aside-body>
      <StudyMainAside :menus='menus' :activeIndex='activeStudyNav' :studyId="studyId" @onClickMenu='(idx)=>{$store.commit("SET_STUDY_ACTIVE", idx)}'/>
    </template>

    <template v-slot:section>
      <!-- <header id='section-title'>{{menus[activeIndex]}}</header> -->
      <!-- 컴포넌트 파서 알맞은 위치의 div를 컴포넌트로 대체하면 됨 -->
      <StudyMainPage v-if="activeStudyNav === 0"/>
      <StudyMainMeeting v-else-if="activeStudyNav === 1" :studyId='studyId' />
      <StudyArticle v-else-if="activeStudyNav === 2" :studyId="studyId" /> 

      <StudyDataPage v-else-if="activeStudyNav === 3"/>
      <StudyStatistic v-else-if="activeStudyNav === 4"/> 
      <StudyConfig v-else-if="activeStudyNav === 5" :studyId="studyId" />
      <!-- 회원 관리 목록 만들기 -->
      <StudyMemberConfig v-else-if="activeStudyNav === 6" :studyId='studyId'/>
      <div v-else> 아무것에도 포함 안됨 </div>
    </template>
  
  </AsideFrame>
</template>

<script>
import AsideFrame from '@/components/AsideFrame.vue'
import StudyMainAside from '@/components/study/main/StudyMainAside.vue'
import StudyMainPage from '@/components/study/main/StudyMainPage.vue'
import StudyMainMeeting from '@/components/study/main/meeting/StudyMainMeeting.vue'
import StudyArticle from '@/components/study/main/article/StudyArticle.vue'
import StudyDataPage from '@/components/study/main/data/StudyDataPage.vue'
import StudyStatistic from '@/components/study/main/statistic/StudyStatistic.vue'
import StudyConfig from '@/components/study/main/config/StudyConfig.vue'
import StudyMemberConfig from '@/components/study/main/config/StudyMemberConfig.vue'
import { useStore } from 'vuex'
import { computed } from '@vue/runtime-core'
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
    StudyMainPage,
    StudyMainMeeting,
    StudyArticle,
    StudyDataPage,
    StudyStatistic,
    StudyConfig,
    StudyMemberConfig
  },
  setup() {
    const store = useStore()
    const activeStudyNav = computed(()=>store.state.activeStudyNav)
    const menus = computed(()=>{
      if (store.state.user && store.state.curStudy) {
        if (store.state.user.id===store.state.curStudy.leaderId) {
          return ['스터디 메인', '미팅', '게시글', '스터디 학습 자료', '통계', '스터디 설정','멤버 관리']
        }
      }
      return ['스터디 메인', '미팅', '게시글', '스터디 학습 자료', '통계']
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