<template>
    <!-- section은 백그라운드가 될 것 -->
  <article :class="['background']" @click.stop="changeModal"> 
    <div class='popup' @click.stop="">
      <slot></slot> 
      <!-- 부모의 태그 내부에 작성된 내용이 여기 나옴 -->
    </div>
  </article>

  <button class='btn-to-a' @click="changeModal">프롭스가 들어갈 것</button>
</template>
<script>
import { reactive, ref } from '@vue/reactivity'
export default {
  name: 'GeneralModal',
  setup() {
    // content : 내부에 들어갈 값 모음
    const content = reactive({
      email : "",
      password : "",
    })
    // 모달 보여줄지 여부
    const isShow = ref(false)
    const changeModal = ()=>{
      isShow.value = !isShow.value
    }

    return {
      content,
      isShow,
      changeModal,
    }
  }
}
</script>

<style>
  .background {
    visibility: hidden;
    position : fixed;
    top : 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.3);
    z-index: 100;
  }

  .window {
    position: relative;
    width: 100%;
    height: 100%;
  }

  .popup {
    position: absolute;
    top: 50%;
    left: 50%;
    background-color: var(--background);
    /* 박스 그림자 */
    box-shadow: 0 2px 7px rgba(0, 0, 0, 0.3);
    
    /* 임시 지정 */
    width: 400px;
    height: 500px;
    /* fade in-out */
    opacity: 0;
    /* 위치 조절 */
    /* transform: translate(-50%, -50%); */
    transform: translate(-50%, -46%);
    /* 위치 변화 css 내용 전부 0.5초 주겠다 */
    transition: all 0.5s;
  }
  /* 백그라운드에 들어감 */
  .show { 
    /* 보이게 하겠다. background에 들어감 */
    visibility: visible;
    /* 위치 변화 css 내용 전부 0.5초 주겠다 */
    transition: all 0.5s;
  }
  /* .show와 .popup을 같이 적어야 하는 이유를 모르겠음 */
  .show .popup {
    /* fade in-out */
    opacity: 1;
    /* 위치 조절 */
    transform: translate(-50%, -50%);
    /* 위치 변화 css 내용 전부 0.5초 주겠다 */
    transition: all 0.5s;
  }

  #login-form {
    margin: 30% auto;
  }
  input {
    border: 1px solid black;
  }
</style>