<template>

<section class="userbody">
 
  <div name="menu" class="user_side_bar">
    <div class="user_name_card">
      <p>안녕하세요,</p>
      <p>{{user.nickname}}님!</p>
    </div>
    <div>
    <button @click="infoPage(0)" :class="{ 'clickedbutton': data.click[0], 'unclickedbutton': !data.click[0] }">UserInfo</button>
    </div>
    <div>
    <button  @click="infoPage(1)" :class="{ 'clickedbutton': data.click[1], 'unclickedbutton': !data.click[1] }">profileManage</button>
    </div>
    <div> 
    <button @click="infoPage(2)" :class="{ 'clickedbutton': data.click[2], 'unclickedbutton': !data.click[2] }">UserSetting</button>
    </div>
    <div>
    <button @click="infoPage(3)" :class="{ 'clickedbutton': data.click[3], 'unclickedbutton': !data.click[3] }">UserNote</button>
    </div>
    <div>
    
    </div>
    <div>
    <div class="xbutton "></div>
    </div>
  </div>
  
  <div name="page" class="page">
    <div v-if="data.infopage === 0">
      <UserInfo/>
    </div>
    <div v-if="data.infopage === 1">
      <ProfileManage/>
    </div>
    <div v-if="data.infopage === 2">
      <UserSetting/>
    </div>
    <div v-if="data.infopage === 3">
       <UserNote/>
    </div>
    <div v-if="data.infopage === 4">
     
    </div>
    
  </div>
  
</section>

</template>

<script>
import UserInfo from '@/components/User/UserInfo'
import UserSetting from '@/components/User/UserSetting'
// import UserStudy from '@/components/User/UserStudy/UserStudy'
import UserNote from '@/components/User/UserNote'
import ProfileManage from '@/components/User/ProfileManage.vue'
import { reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
import { computed } from '@vue/runtime-core'
export default {
  name : "User",
  components: {
    UserInfo,
    ProfileManage,
    // UserStudy,
    UserNote,
    UserSetting,
  },
  setup () {
    
    const store = useStore()
    const user = computed(()=>store.state.user)

    const data = reactive ({
      infopage : 0, 
      click : new Array(5).fill(false),
      user: {}
    })
    
    const infoPage = (page) => {
      data.infopage = page;
      for (let i = 0; i< 5; i++) {
        data.click[i]=(i==page)?true:false;
      }
    }
    // console.log(user)
    
    
    return {
      data,
      infoPage,
      store,
      user
      
    }
  },

  
  
}
</script>

<style>
/* .userheader{
  margin-top: 100px;
  margin-bottom: 100px;
  background-color: white;
  font-size: 50px;
  background-color: #F2F2F2;
  color: #195C77;
} */
.userbody{
  display: flex;
  justify-content: center;
  background-color: #F2F2F2; 
  margin: 0 0 0 0;

}
/* .container {
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
} */
.user_side_bar{
  display: flex;
  flex-direction: column;
  /* align-content: center; */

  padding: 0;
  margin: 100px 20px 0 50px;
  width: 300px;
  /* background-color: white; */
  color: #195C77;
  height: 50%;
  border: 0.5px transparent;
  border-bottom: 0.5px rgb(39, 39, 39) ;
  
}
.user_name_card {
  display: flex;
  justify-content: flex-start;
  padding-left: 7%;
  flex-wrap: wrap;
  font-size: 18px;
  font-weight: bold;
  margin: 3px 0 0 10px;
  animation: move 1s ease-in 0 1 none forwards;
  animation: name duration timing-function delay iteration-count direction fill-mode;
}
.clickedbutton {
  display: flex;
  align-content: center;
  justify-items: center;
  background: #f2f2f2;
  height: 50px;
  width: 250px;
  border: none;
  border-bottom: gray 1px solid;
  padding: 30px;
  font-weight: bolder;
  color: rgb(83, 83, 83);
  font-size: 20px;
  align-items: baseline;
  justify-content: flex-start;
}
.unclickedbutton {
  display: flex;
  align-content: center;
  justify-items: center;

  background: #f2f2f2;
  height: 50px;
  width: 250px;
  border: none;
  border-bottom: gray 1px solid;
  padding: 30px;
  font-weight: normal;
  color: gray;
  font-size: 20px;
  align-items: baseline;
  justify-content: flex-start;
}


.page {
  display: flex;
  justify-content: center;
  width: 40rem;
  min-height: 600px;
  min-width: 900px;
  /* height: 100%; */
  right: 10%;
  margin: 100px 100px 0 0px;
  /* margin-bottom: 1000px; */
  background-color: white;
  /* float: right; */
  flex-direction: row;
  flex-basis: 90%;

}

@keyframes move {
  0% {

  }

  30%{

  }
  60%{

  }
  100%{

  }
}


</style>

