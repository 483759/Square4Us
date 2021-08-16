<template>
  <ul id='aside-list'>
    <li v-for='(value, index) in menus' :key='index' :class="{ 'active' : activeIndex===index ? true : false }" @click="$emit('onClickMenu', index)">
      {{ value }}
    </li>
  </ul>
</template>

<script>
import { useStore } from 'vuex'
import { onMounted, onUnmounted } from '@vue/runtime-core'
export default {
  name: 'StudyMainAside',
  props: {
    menus :{
      type: Array,
      required: true
    },
    activeIndex: {
      type: Number,
      required: true
    },
    studyId: {
      type: String,
      required : true
    }
  },
  setup(props) {
    const store = useStore()
    onMounted(()=>{
      store.dispatch('getStudyByNumber', props.studyId);
    })
    onUnmounted(()=>{
      store.commit('SET_CURRENT_STUDY', [])
    })

    return {
      props
    }
  }
}
</script>

<style>
#aside-list {
  margin: 0;
  margin-top:20px;
  cursor: pointer;
  box-sizing: border-box;
  /* border-top: 1px solid #195C77; */
}

#aside-list:nth-child(1){
  box-shadow: 0 0 0 1px #195C77 inset;
}

#aside-list > li{
  display: flex;
  box-sizing: border-box;
  border-bottom: 1px solid #195C77;
  height: 50px;
  padding-left: 25px;
  align-items: center;
  font-weight: bold;
  font-size: 0.85rem;
  color: #195C77;
}

#aside-list > li:hover{
  color:white !important;
  background-color: #195C77 !important;
  transition: 0.2s;
}

.active {
  color:white !important;
  background-color: #195C77 !important;
}
</style>