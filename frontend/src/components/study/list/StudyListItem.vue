<template>
<ul class="studyList">
  <li v-for='study in studies' :key='study.id' @click='joinStudy(study.id)'>
    <div class="studyBox">
      <div class="studyImageBox">
          <img class=studyImage v-if="study.profile==null" src="/main1.jpg" alt="스터디이미지">
          <img class=studyImage v-else :src='getFilePath(study.profile.filePath, study.profile.fileName)' alt="스터디이미지">
          <div class="studyNameBox">
             {{study.name}} 
             <br>
             <div class="studyCategory">
                [{{study.category}}]
             </div>
          </div>
      </div>
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
  
  const joinStudy = function (studyId) {
    store.dispatch('joinStudy', studyId)
  }
  
  const getFilePath = function(path, name) {
    return path + '/' + name
  }
  
    return {
      getFilePath,
      joinStudy
    }
  }
}
</script>

<style>
.studyList{
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly;
}
.studyBox {
  display: flex;
  flex-direction: column;
  
  height: 220px;
  width: 330px;
  margin: 25px 20px 25px 20px;
  transform: scale(1);
  transition: all 0.2s ease-out;

}

.studyImage {
  height:220px;
  width:330px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  transform: scale(1);
}
.studyImage:hover {
  transform: scale(1.05);
  display: flex;
  flex-direction: column;
  justify-content: center;
  overflow: hidden;
  transition: all 0.2s ease-out;
}
.studyNameBox{
  height: 50px;
  width: inherit;
  display: flex;
  flex-direction: column;
  justify-content: center;
  font-size: 18px;
  font-weight: bold;
  color: #195C77;
}
.studySignInButton{
  height: 30px;
  width: 50px;
  box-sizing: border-box;
  
}
.studyCategory {
  font-size: 15px;
  font-weight: lighter;
}



</style>