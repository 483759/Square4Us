<template>
<section id='study-list'>
  <StudyListHeader/>
  <StudyListBody/>
</section>
</template>

<script>
import StudyListHeader from '@/components/study/list/StudyListHeader.vue'
import StudyListBody from '@/components/study/list/StudyListBody.vue'
export default {
  name: 'StudyList',
  components: {
    StudyListHeader,
    StudyListBody
  }
}
</script>

<style>
  #study-list{
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    /* height: 100%; */
  }

  @media (max-width: 1240px) {
    #study-list{
      padding: 0 0;
    }
  }

  @media (min-width:1241px) {
    #study-list{
      padding: 0 5%;
    }
  }
  
</style>