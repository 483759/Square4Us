<template>
  <article :class="['background', isShow && 'show']" @click="$emit('switchModal')"> 
    <div class='popup' :id='popupId' @click.stop="">
        <slot name='header'></slot>
        <slot></slot>
        <slot name='footer'></slot>
    </div>
  </article>
  <slot name='button'>
    <!-- <button @click="$emit('switchModal')"> 열기 </button> -->
  </slot>
</template>

<script>
import _ from 'lodash'
import ModalApi from '@/api/ModalApi.js'
import { onMounted } from '@vue/runtime-core'
export default {
  name: 'Modal',
  emits: ['switchModal'], // 하나의 태그로 묶이지 않은 경우 명시적으로 emit을 선언해주어야 한다
  props: {
    isShow : {
      type: Boolean,
      required: true,
    },
    popupId : {
      default : ()=> `popup-${_.uniqueId()}`
    },
    width : {
      default : ()=>'400px'
    },
    height : {
      default : ()=>'500px'
    },
    top : {
      default : ()=>'50%'
    },
    left : {
      default : ()=>'50%'
    }
  },
  setup(props) {
    // 모달 사이즈 지정
    onMounted(()=>{
      ModalApi.setModalSize(props.popupId, props.width, props.height, props.top, props.left) // 너비, 높이
    })
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
    background-color: white;
    border-radius: 5px;
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

  .popup {
    display: flex;
    flex-direction: column;
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


  input {
    border: 1px solid black;
  }
</style>