<template>
  <nav id='nav'>
    <section id='logo'>
      <img id='logo-icon' src="logo.png" alt="로고">
      <router-link id="logo-text" class='text-hover' :to="{ name: 'Home' }">Sqeare 4 Us</router-link>
    </section>
    <section id='nav-list'>
      <router-link :to="{ name: 'Tutorial' }">Tuto</router-link> 
      <router-link :to="{ name: 'UserInfo' }">User</router-link> 
      <router-link :to="{ name: 'ConferenceBase' }">ConfBase</router-link> 
      <router-link :to="{ name: 'ConferenceRoom' }">ConfRoom</router-link> 
      <router-link :to="{ name: 'ConferenceReport' }">ConfReport</router-link>
      <Login/>
      <SignUp/>
    </section>
  </nav>
  <router-view></router-view>
</template>

<script>
import SignUp from '@/components/home/SignUp'
import Login from '@/components/home/Login'
export default {
  name : 'Header',
  components: {
    Login,
    SignUp,
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@500&display=swap');
#nav {
  background-color: var(--headerBackground);
  color: var(--textColor);
  display: flex;
  justify-content: space-between;
  padding: 25px;
  height: 100px;
  vertical-align: middle;
  user-select:none;
}

#nav a {
  display: block;
  text-decoration: none;
  font-weight: bold;
  color: var(--textColor);
  padding: 7px;
}

/* 현재 페이지 강조하는 style */
/* #nav a.router-link-exact-active {
  color: #42b983;
} */


.btn-to-a {
  border: 0;
  background-color: rgb(255, 255, 255, 0);
  color: var(--textColor);
  font-weight: bold;
}

#logo, #nav-list {
  display: flex;
  justify-content: space-between;
  align-items: center;
  
}
#logo{
  width: 200px;
}


#logo-icon{
  width: 50px;
  height: 50px;
  cursor: pointer;
}

#logo-text{
  font-family: 'Inter', sans-serif;
  font-size: 20px;
  text-decoration: none;
  /* transition: 0.3s; */
}
#logo-text:hover, .btn-to-a:hover, #nav a:hover {
  color: #42b983;
  transition: 0.3s;
  
}
</style>