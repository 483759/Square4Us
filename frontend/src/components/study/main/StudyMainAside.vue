<template>
  <ul id='aside-list'>
    <li v-for='(value, index) in menus' :key='index' :class="{ 'active' : activeIndex===index ? true : false }" @click="$emit('onClickMenu', index)">
      {{ value }}
    </li>
  </ul>
</template>

<script>
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
    }
  },
  setup() {
  }
}
</script>

<style>
#aside-list {
  margin: 0;
  margin-top:20px;
  cursor: pointer;
  box-sizing: border-box;
  border-top: 1px solid #195C77;
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