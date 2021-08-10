<template>

<section class="userbody">
 
  <div name="menu" class="menubar  display=flex ml-0">
    <div>
    <button @click="infopage1" :class="{ 'clickedbutton': data.click1, 'menubutton': !data.click1 }">UserInfo</button>
    </div>
    <div>
    <button  @click="infopage2" :class="{ 'clickedbutton': data.click2, 'menubutton': !data.click2 }">profileManage</button>
    </div>
    <div> 
    <button @click="infopage3" :class="{ 'clickedbutton': data.click3, 'menubutton': !data.click3 }">UserSetting</button>
    </div>
    <div>
    <button @click="infopage4" :class="{ 'clickedbutton': data.click4, 'menubutton': !data.click4 }">UserStudy</button>
    </div>
    <div>
    <button @click="infopage5" :class="{ 'clickedbutton': data.click5, 'menubutton': !data.click5 }">UserNote</button>
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
      <UserStudy/>
    </div>
    <div v-if="data.infopage === 5">
      <UserNote/>
    </div>
    
  </div>
  
</section>

</template>

<script>
import UserInfo from '@/components/User/UserInfo'
import UserSetting from '@/components/User/UserSetting'
import UserStudy from '@/components/User/UserStudy/UserStudy'
import UserNote from '@/components/User/UserNote'
import ProfileManage from '@/components/User/ProfileManage.vue'
import { reactive } from '@vue/reactivity'
export default {
  name : "User",
  components: {
    UserInfo,
    ProfileManage,
    UserStudy,
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
    
    return {
      data,
      infopage1,
      infopage2,
      infopage3,
      infopage4,
      infopage5
      
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
.menubar{
  display: flex;
  flex-direction: column;

  padding: 0;
  margin: 100px 50px 0 50px;
  width: ;
  /* background-color: white; */
  color: #195C77;
  height: 50%;
  border: 0.5px transparent;
  border-bottom: 0.5px black ;
  
}
.menubutton {
  /* margin-left: 100px; */
  height: 3.5rem;
  width: 12rem;
  background-color: #195C77;
  color: white;
  font: 15px sans-serif;
  padding-bottom: 5px;
  border: 1px transparent;
  border-bottom: 1px black ;
  /* box-shadow: 1px 1px 10px 5px #13475c inset; */
}
.clickedbutton {
  height: 3.5rem;
  width: 12rem;
  background-color: white;
  color: #195C77;
  font: 15px sans-serif;
  box-shadow: 2px 5px 5px 3px #103b4d ;
}
/* .xbutton {
  height: 300px;
  width: 150px;
  background-color: #195C77;
  color: white;
  font: 20px sans-serif;
} */


.page {
  display: flex;
  width: 50rem;
  /* height: 100%; */
  right: 10%;
  margin: 100px 100px 0 50px;
  /* margin-bottom: 1000px; */
  background-color: white;
  float: right;
  margin: 100px 0 100px 0;

}
</style>

