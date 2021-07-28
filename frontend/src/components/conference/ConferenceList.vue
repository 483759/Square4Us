<template>
<section id='conf-list'>
  <ConferenceListHeader/>
  <ConferenceListBody/>
</section>
</template>

<script>
import ConferenceListHeader from '@/components/conference/ConferenceListHeader.vue'
import ConferenceListBody from '@/components/conference/ConferenceListBody.vue'
export default {
  name: 'ConferenceList',
  components: {
    ConferenceListHeader,
    ConferenceListBody
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