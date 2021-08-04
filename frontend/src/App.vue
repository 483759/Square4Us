<template>
  <router-view/>
  <!-- <button class='theme-button' @click='changeTheme'>dark</button> -->
</template>

<script>
// export default {
//   name: 'App',
//   setup() {
//     const changeTheme = (e)=>{
//       const htmlTag = document.getElementsByTagName('html')[0]
//       console.log(htmlTag);
//       const theme = htmlTag.getAttribute('color-theme')
//       if (theme ==='light') {
//         htmlTag.setAttribute('color-theme', 'dark')
//         e.target.textContent = 'light'
//       } else {
//         htmlTag.setAttribute('color-theme', 'light')
//         e.target.textContent = 'dark'
//       }
//     }

//     return {
//       changeTheme
//     }
//   }
// }
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
}
#app {
  display: flex;
  flex-direction: column;

}

:root[color-theme='light'] {
  --headerBackground : white;
  --background: rgb(197, 197, 197);
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
</style>
