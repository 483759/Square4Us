<template>
  <header id='conf-header'>
      <ConferenceCategory/>
      <ConferenceSearchBar/>
      <ConferenceCreate/>
  </header>
</template>

<script>
import ConferenceCategory from '@/components/conference/ConferenceCategory.vue'
import ConferenceSearchBar from '@/components/conference/ConferenceSearchBar.vue'
import ConferenceCreate from '@/components/conference/ConferenceCreate.vue'
export default {
  name: 'ConferenceListHeader',
  components: {
    ConferenceCategory,
    ConferenceSearchBar,
    ConferenceCreate,
  }
}
</script>

<style>
  #conf-header{
    display: flex;
    justify-content: space-between;
    /* background-color: white; */
    margin: 30px auto;
    width: 100%;
    height: 100px;
    border: 1px solid black;
  }
</style>