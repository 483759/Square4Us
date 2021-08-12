<template>
<ul class="studyList">
  <li v-for='study in studies' :key='study.id' @click='joinStudy(study.id)'>
    <div class="studyBox">
      <div class="studyImageBox">
          {{study.files}}
          {{study.category}}
          {{study.name}} 
      </div>
     
      <!-- <div>
        <button class="studySignInButton" >가입</button>
      </div> -->
    </div>
    
  </li>
</ul>

</template>

<script>
import { useStore } from 'vuex';
export default {
  name: 'studyListItem',
  props: {
    studies: {
      type: Array,
      required: true
    },
  },
  setup() {
  const store = useStore(); 
  console.log(store);
  const joinStudy = function (studyId) {
    store.dispatch('joinStudy', studyId)
  }
  
    return {
      joinStudy
    }
  }
}
</script>

<style>
.studyList{
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}
.studyBox {
  display: flex;
  flex-direction: column;
  
  height: 200px;
  aspect-ratio: 1.4/1;
  border: 1px solid gray;
  margin: 20px 15px 0 15px;
  

}
.studyImageBox {
  height: inherit;
  width: inherit;
  border-bottom: gray 1px solid;
  color: black;
}
.studyImageBox:hover {
  display: flex;
  flex-direction: column-reverse;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  color: white;
  
 

}
.studySignInButton{
  height: 30px;
  width: 50px;
  box-sizing: border-box;
}



</style>