<template>
<section id='conf-list'>
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
  #conf-list{
    display: flex;
    flex-direction: column;
    height: 100%;
  }

  @media (max-width: 1240px) {
    #conf-list{
      padding: 0 0;
    }
  }

  @media (min-width:1241px) {
    #conf-list{
      padding: 0 5%;
    }
  }
  
</style>