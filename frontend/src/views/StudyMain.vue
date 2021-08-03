<template>
  <AsideFrame>
    <template v-slot:aside-header>
        <img id='aside-header-thumbnail' src="/meeting-thumbnail.jpg" alt="썸네일">
    </template>
    <template v-slot:aside-body>
      <div id='empty-block'>
      </div>
      <ul id='aside-list'>
      <li>
        스터디 메인
      </li> 
      <li class='active'>
        미팅
      </li> 
      <li>
        게시글
      </li> 
      <li>
        스터디 학습 자료
      </li>
      <li>
        통계
      </li>
      <li>
        스터디 설정
      </li>
      </ul>
    </template>
    <template v-slot:section>
      <div id='section-title'>스터디 메인</div> 
    </template>
  </AsideFrame>
</template>

<script>
import AsideFrame from '@/components/AsideFrame.vue'
export default {
  name: 'StudyMain',
  components :{
    AsideFrame
  }
}
</script>

<style>
#aside-header-thumbnail{
  width: 230px;
  border-radius: 5px;
  box-shadow: -1px -1px 1px 1px rgb(231, 231, 231);
}

#empty-block {
  border-bottom: 1px solid #195C77;
  height: 20px;
}

#aside-list {
  margin: 0;
}


#aside-list > li{
  display: flex;
  box-sizing: border-box;
  border-bottom: 1px solid #195C77;
  height: 50px;
  padding-left: 25px;
  align-items: center;
  font-weight: bold;
  font-size: 0.9rem;
  color: #195C77;
  
}

.active {
  color:white !important;
  background-color: #195C77 !important;
  /* background-clip: border-box; */
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
</style>