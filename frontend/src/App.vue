<template>
  <router-view/>
  <!-- <button class='theme-button' @click='changeTheme'>dark</button> -->
</template>

<script>
import axios from 'axios'
import { onMounted } from '@vue/runtime-core'
import router from './router'
import store from './store'
export default {
  name: 'App',
  setup() {
    onMounted(async()=>{
      axios.defaults.baseURL = process.env.VUE_APP_API_URL
      console.log("환경변수 정보 ", process.env);
      
      const jwt = localStorage.getItem('JWT')
      if (jwt) {
        axios.defaults.headers.common['Authorization'] = `Bearer ${jwt}`
        const response = await store.dispatch('getUser')
        if (response) {
          router.push({name: 'StudyList'})
        }else{
          router.push({name: 'Main'})
        }
      }else{
        router.push({name: 'Main'})
      }
    })
    // const changeTheme = (e)=>{
    //   const htmlTag = document.getElementsByTagName('html')[0]
    //   console.log(htmlTag);
    //   const theme = htmlTag.getAttribute('color-theme')
    //   if (theme ==='light') {
    //     htmlTag.setAttribute('color-theme', 'dark')
    //     e.target.textContent = 'light'
    //   } else {
    //     htmlTag.setAttribute('color-theme', 'light')
    //     e.target.textContent = 'dark'
    //   }
    // }

    // return {
    //   changeTheme
    // }
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@500&display=swap');
html, body, #app {
  font-family: 'Inter', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: var(--textColor);
  height: 100%;
  width: 100%;
  background-color: var(--background);
  margin: 0;
}
#app {
  display: flex;
  flex-direction: column;

}
ul {
  margin : 0;
}

:root[color-theme='light'] {
  --headerBackground : white;
  --background: rgb(230, 230, 230);
  --boxColor: black;
  --textColor: #2c3e50;

  --strongBackground: #ffffff;
  --weakBackground: #B5B3AF;
  --strongTextColor: #3E2E29;
  --weakTextColor: #D2D1CD;
}
:root[color-theme='dark'] {
  --headerBackground : black;
  --background:#313131; 
  --boxColor: rgb(197, 197, 197);
  --textColor: rgb(197, 197, 197);

  --strongBackground: #2F3437;
  --weakBackground: #373C3F;
  --strongTextColor: #9C9EA0;
  --weakTextColor: #8F9295;
}

.theme-button {
  background-color: var(--boxColor);
  color: var(--headerBackground);
  position: fixed;
  bottom: 20px;
  right: 20px;
  border-radius: 100%;
  width : 50px;
  height: 50px;
  font-size: 0.8rem;
  user-select: none;
  border: 1px solid var(--headerBackground);
}

ul {
  padding: 0;
  list-style: none;
}


/* 공통 사용 버튼 */
.white-button {
  background-color: white;
  border: 0;
  padding: 0;
  box-shadow: 0 0 0 1px #195C77 inset;
  color: #195C77;
  font-size: 0.8rem;
  font-weight: 600;
  border-radius: 5px;
  width: 150px;
  height: 35px;

  display: flex;
  justify-content: space-around;
  align-items: center;
  cursor: pointer;
}

.white-button:hover {
  color: #42b983;
  box-shadow: 0 0 0 1px #42b983 inset;
  border: 0px;
  transition: 0.3s;
}


.green-button {
  background-color: #195C77;
  border: 0;
  padding: 0;
  color: white;
  font-size: 0.8rem;
  font-weight: 600;
  border-radius: 5px;
  width: 150px;
  height: 35px;

  display: flex;
  justify-content: space-around;
  align-items: center;
  cursor: pointer;
}

.green-button:hover {
  color: #195C77;
  box-shadow: 0 0 0 1px #195C77 inset;
  background-color: white;
  border: 0px;
  transition: 0.3s;
}
</style>
