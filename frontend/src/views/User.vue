<template>

<section class="userbody">
 
  <div name="menu" class="user_side_bar">
    <div class="user_name_card">
      <p>안녕하세요,</p>
      <p>{{user.nickname}}님!</p>
    </div>
    <div>
    <button @click="infopage1" :class="{ 'clickedbutton': data.click1, 'unclickedbutton': !data.click1 }">UserInfo</button>
    </div>
    <div>
    <button  @click="infopage2" :class="{ 'clickedbutton': data.click2, 'unclickedbutton': !data.click2 }">profileManage</button>
    </div>
    <div> 
    <button @click="infopage3" :class="{ 'clickedbutton': data.click3, 'unclickedbutton': !data.click3 }">UserSetting</button>
    </div>
    <div>
    <button @click="infopage4" :class="{ 'clickedbutton': data.click4, 'unclickedbutton': !data.click4 }">UserNote</button>
    </div>
    <div>
    
    </div>
    <div>
    <div class="xbutton "></div>
    </div>
  </div>
  
  <div name="page" class="page">
    <div v-if="data.infopage === 1">
      <UserInfo/>
    </div>
    <div v-if="data.infopage === 2">
      <ProfileManage/>
    </div>
    <div v-if="data.infopage === 3">
      <UserSetting/>
    </div>
    <div v-if="data.infopage === 4">
       <UserNote/>
    </div>
    <div v-if="data.infopage === 5">
     
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
    
    const data = reactive ({
      infopage : 1, 
      click1 : true,
      click2 : false,
      click3 : false,
      click4 : false,
      click5 : false,
      user: {}


       })
    
    const infopage1 = () => { 
    data.infopage = 1,
    data.click1 = true
    data.click2 = false
    data.click3 = false
    data.click4 = false
    data.click5 = false
    }
    const infopage2 = () => { 
      data.infopage = 2, 
      data.click1 = false
      data.click2 = true
      data.click3 = false
      data.click4 = false
      data.click5 = false
    }
    const infopage3 = () => { 
      data.infopage = 3, 
      data.click1 = false
      data.click2 = false
      data.click3 = true
      data.click4 = false
      data.click5 = false
    }
    const infopage4 = () => { 
      data.infopage = 4, 
      data.click1 = false
      data.click2 = false
      data.click3 = false
      data.click4 = true
      data.click5 = false
    }
    const infopage5 = () => { 
      data.infopage = 5, 
      data.click1 = false
      data.click2 = false
      data.click3 = false
      data.click4 = false
      data.click5 = true
    }
    const store = useStore()
    const user = computed(()=>store.state.user)
    // console.log(user)
    
    
    return {
      data,
      infopage1,
      infopage2,
      infopage3,
      infopage4,
      infopage5,
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

