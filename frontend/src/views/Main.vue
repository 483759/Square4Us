<template>
  <img src="/main1.jpg" alt="main" style="width:100%">
    <div class="maintext"> 
    <div></div>
    <div>Square 4 us에서 새로운 스터디를 경험하세요</div>
    <div></div>
    </div>
    <button class="signbtn" style="color: #f2f2f2"><SignUp/></button>
    
</template>

<script>
import SignUp from '@/components/home/SignUp'
export default {
  name : 'MainImage',
  components: {
    SignUp,
  }
}
</script>

<style>
.maintext {
  position: fixed;
  
  margin: 0 auto;
  top: 55%;
  left: 0;
  right: 0;
  margin: auto;
  font-size: 50px;
  color: #f2f2f2;
  font-family: sans-serif;
  font-weight: bold;
  
}
.signbtn {
  position: fixed;
  top: 70%;
  left: 43%;
  height: 60px;
  width: 200px;
  font: 15px sans-serif;
  color: #f2f2f2;
  background-color: #509186;
  border: transparent;
  font-family: sans-serif;
  /* font-weight: bold; */
}


</style>