<template>
  <header id='study-header'>
      <StudyCategory/>
      <StudySearch/>
      <StudyCreate/>
  </header>
</template>

<script>
import StudyCategory from '@/components/study/list/StudyCategory.vue'
import StudySearch from '@/components/study/list/StudySearch.vue'
import StudyCreate from '@/components/study/list/StudyCreate.vue'
export default {
  name: 'ConferenceListHeader',
  components: {
    StudyCategory,
    StudySearch,
    StudyCreate,
  }
}
</script>

<style>
  #study-header{
    display: flex;
    /* justify-content: space-between; */
    /* background-color: white; */
    margin: 30px auto;
    width: 100%;
    height: 100px;
    border: 1px solid black;
    box-sizing: border-box;
  }
</style>