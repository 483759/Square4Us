<template>
  <header id='section-title'>
    <div>스터디 설정</div>
  </header>
  <article>
    <!-- 여기에 이제 스터디 프로필 변경을 넣기 -->
    <section>
      <div class='img-container' >
        <img src="" alt="" ref='imgContainer' id='study-img'>
      </div>
      <input type="file" @change="readImg" ><button @click="saveImg">스터디 이미지 저장</button>
    </section>
    <button v-if='isLeader' @click="withdraw()">스터디 폐쇄</button>
  </article>
</template>

<script>
import { computed, ref } from '@vue/runtime-core'
import { useStore } from 'vuex'
import axios from 'axios'
export default {
  name: 'StudyConfig',
  props: {
    studyId: {
      type: String,
      required : true
    }
  },
  setup(props) {
    const store = useStore()
    const imgContainer = ref(null)
    const isLeader = computed(()=> store.getters.isLeader)
    const imgFile = ref(null);

    const withdraw = () =>{
      store.dispatch('removeStudy', props.studyId)
    }
    const readImg = (e)=>{
      console.log(e.target.files[0]); 
      imgFile.value = e.target.files[0] // 파일 state에 저장
      const reader = new FileReader()
      reader.onload = (e)=>{
        imgContainer.value.setAttribute('src', e.target.result)
      }
      reader.readAsDataURL(e.target.files[0]) // 실제 읽는 함수
    }

    const saveImg = async()=>{
      // 그냥 바로 보내자
      const formData = new FormData();
      formData.append('profile', imgFile.value)
      const response = await axios({
        method: 'POST',
        url: `/study/${props.studyId}/profile`,
        data: formData
      }).catch((err)=>err.response)

      if (!response) {
        alert('스터디 이미지 등록에 실패했습니다')
        return 
      }
      console.log(response);
    }

    return {
      props,
      isLeader,
      withdraw,
      saveImg,
      readImg,
      imgContainer
    }
  }
}
</script>

<style>
#study-img {
  max-width: 300px;
  max-height: 200px;
}
</style>