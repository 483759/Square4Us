<template>
  <div class="backgroundImage">
    <p class="mainMent" @click="goSignup">Square4us에서 새로운 스터디를 경험하세요</p>
    <button class="mainButton" @click="goSignup">Signup</button>
  </div>
</template>

<script>
import { useRouter } from 'vue-router'
export default {
  name : 'Main',
  setup() {
    const router = useRouter()
    const goSignup = () => {
      router.push('SignUp')
    }

    return {
      router,
      goSignup
    }
  }
}
</script>
<style>
.backgroundImage{
background-image: url(/main1.jpg) !important;
height: 90%;
min-height: 90%;
background-position: center;
background-size: cover;
display: flex;
flex-direction: column;
justify-content: center;
align-items: center;
}
.mainMent{
  color: white;
  font-size: 3rem;
  font-weight: bold;
}

.mainButton{
  background-color: #509186;
  width: 300px;
  height: 60px;
  font: 1.5rem sans-serif;
  margin-top: 2rem;
  cursor: pointer;
  border: none;
  color: white;
  border-radius: 3px;
  border: 2px solid #345e57;
  
  z-index: 0 !important;
}
/* .mainButton:hover {
  transform: scale(1.05);
  transition: all 0.1s ease-out;
  overflow: hidden;
  font: 1.7rem sans-serif;
} */
</style>