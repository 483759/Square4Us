<template>
  <nav id='nav'>
    <section id='logo'>
      <img id='logo-icon' src="/logo.png" alt="로고" @click="$router.push({name: 'Main'})">
      <div class="slogan-box">
        <router-link id="logo-text square" class='text-hover' :to="{ name: 'Main' }">Square 4 Us</router-link>
        <a class="slogan" @click="$router.push({name: 'Main'})">we link the world</a>
      </div> 
    </section>
    
    <section v-if='isLogin' id='nav-list'>
      <router-link :to="{ name: 'Tutorial' }">Tutorial</router-link> 
      <router-link :to="{ name: 'StudyList' }">Study</router-link> 
      <router-link :to="{path: `/study/${1}`}">내 스터디</router-link> 
      <router-link :to="{ name: 'User' }">User</router-link> 
      <!-- <router-link :to="{ path: `/study/${1}` }">1번 스터디메인</router-link> 
      <router-link :to="{ path: `/study/${2}` }">2번 스터디메인</router-link>  -->
      <!-- <router-link :to="{ name: 'StudyReport' }">StudyReport</router-link> -->
      <Logout/>
    </section>
    <section v-else id='nav-list'>
      <Login/>
      <SignUp/>
    </section>
  </nav>
  <router-view></router-view>
  <footer id="footer">
    <div>삼성청년 SW 아카테미</div> 
    <div>대전캠퍼스 5기 3반8팀</div>
    <div >2학기 공통 프로젝트</div> 
  </footer>


</template>

<script>
import SignUp from '@/components/home/SignUp'
import Login from '@/components/home/Login'
import Logout from '@/components/home/Logout'
import { computed } from '@vue/runtime-core'
import store from '@/store'
export default {
  name : 'Header',
  components: {
    Login,
    Logout,
    SignUp,
  },
  setup() {
    const isLogin = computed(()=> store.state.isLogin)
    return {
      isLogin
    }
  }
}
</script>

<style>
#nav{
  top: 0;
  position: sticky;
  background-color: var(--headerBackground);
  color: var(--textColor);
  display: flex;
  justify-content: space-between;
  padding: 25px;
  height: 100px;
  min-width: 900px;
  box-sizing: border-box;
  /* vertical-align: middle; */
  user-select:none;
}
#nav a {
  display: block;
  text-decoration: none;
  font-weight: bold;
  color: var(--textColor);
  padding: 0px;
  text-align: right;
}


#nav-list a {
  display: block;
  text-decoration: none;
  font-weight: bold;
  color: var(--textColor);
  padding: 20px;
  font-size: 16px;
}

/* 현재 페이지 강조하는 style */
/* #nav a.router-link-exact-active {
  color: #42b983;
} */


.btn-to-a {
  border: 0;
  font-size: 1rem;
  cursor: pointer;
  background-color: rgb(255, 255, 255, 0);
  color: var(--textColor);
  font-weight: bolder;
}

#logo, #nav-list {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

#logo{
  width: 400px;
  padding: 0;
}

#logo-icon{
  width: 70px;
  height: 70px;
  cursor: pointer;
  margin-right: 20px;
}

#logo-text{
  font-size: 30px;
  /* transition: 0.3s; */
}
#logo-text:hover, .btn-to-a:hover, #nav a:hover {
  color: #42b983;
  transition: 0.3s;
  
}
#footer {
  position: relative;
  bottom: 0;
  font: bolder;
  font-size: 10px;
  text-align: right;
  display: flex;
  flex-wrap: wrap;
  justify-content: right;
  background: #f2f2f2;
  height: 42px;
  flex-direction: column;
  margin-bottom:5px;
  padding-top: 5px;
  margin: white;
}

.slogan-box{
  flex-wrap: wrap;
  margin: 0 auto
}
.square{
  font: 40px;
  font-weight: bold;
}
.slogan{
  font-size: 1px;
  flex-direction: row;
  margin: 0px
} 
</style>