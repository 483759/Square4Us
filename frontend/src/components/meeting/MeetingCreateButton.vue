<template>
  <Modal :isShow='isShow' @switchModal='switchModal' width='350px' height='450px' top='400px' left='78vw'>
    <template v-slot:header>
      <div id='header'>
        <p style="padding: 10px">스터디 생성하기</p>
      </div>
      <!-- <header id='header'>스터디 생성</header> -->
    </template>
    <template v-slot:default>
      <section id='section'>
        <div class="meeting_create_box">
            <div id="create_box">
            <p>스터디 이름</p>
            <input class="meeting_create_input" type="text" name="" id="meeting_name" v-model="data.name">
          </div>
          <form id="thumbnailForm" enctype="multupart/form-data">
            <input type="file" name="thumbnail" id="thumbnail">
          </form>
          <div id="create_box">
            <button class="meeting_create_button" @click="createMeeting">생성</button>
          </div>
        </div>
      </section>
    </template>
    <template v-slot:footer id='footer'>
    </template>
    <template v-slot:button>
      <button class='white-button' @click='switchModal'>스터디생성</button>
    </template>
  </Modal>
</template>

<script>
import Modal from '@/components/home/Modal.vue'
import { ref, reactive } from '@vue/reactivity'
import { useStore } from 'vuex'
export default {
  name: 'MeetingCreateButton',
  components: {
    Modal
  },
  props: {
    studyId: {
      type: String,
      required: true,
    }
  },
  setup(props){
    // 모달 여닫기 관련
    const isShow = ref(false)
    const switchModal = ()=>{
      isShow.value = !isShow.value
    }
    // 스터디 데이터
    const data = reactive({
      name: "",
    })
    const store = useStore();
    const createMeeting = async ()=> {
      if(!data.name || data.name.length == 0) {
        alert("미팅 이름을 입력해주세요!");
        return;
      }
      console.dir(document.getElementById("thumbnailForm"));
      store.dispatch('createMeeting', {name: data.name, studyId: props.studyId, thumbnail: new FormData(document.getElementById("thumbnailForm"))})
      data.name = "";
      document.getElementById("thumbnail").value = "";
      switchModal();
    }

    return {
      isShow,
      switchModal,
      data,
      createMeeting,
    }
  }
}
</script>

<style scoped>
#header {
  height: 50px;
  font-size: 25px;
  font-weight: bold;
  padding-top: 10px;
}

#section {
  flex-grow: 1;
  margin-top: 30px;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}
#footer {
  
}


#create_box{
  display: inline-block;
  flex-direction: column;
  justify-content: center;
  align-content: center;
  height: 90px;
  
}




.meeting_create_box {
  padding-top: 30px;
  width: 300px;
  height: 300px;
  background-color: #fff;
  /* border-top: px solid #195C77; */
  border-bottom: 5px solid #195C77;
}
.meeting_create_box p {
  font-size: 16px;
  font-weight: bold;
  margin-top: 10px 0 10px;
}

.meeting_create_input {
  width: 200px;
  height: 45px;
  font-size: 20px;
  font-family: 'Inter', sans-serif;
  box-sizing: border-box;
  border-radius: 5px;
  margin-top: 10px;
  border: 2px solid #195C77;
}
.meeting_create_button {
  width: 200px;
  height: 45px;
  box-sizing: border-box;
  background-color: #195C77;
  color: white;
  font-size: 18px;
  border-radius: 5px;
  margin-top: 55px;
  
}
</style>