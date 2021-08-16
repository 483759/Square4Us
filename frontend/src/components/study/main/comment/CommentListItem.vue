<template lang="">
  <template v-if="state.modifyMode">
    <div>
      <input type="text" v-model="state.content"  />
      <button @click="modifyContent">수정</button>
      <button @click="modifyCommentMode">취소</button>
    </div>
  </template>
  <template v-else>
  <div>
    <p>{{comment.content}}</p>
    <button @click="modifyCommentMode">수정</button>
    <button @click="deleteComment">삭제</button>
  </div>
  </template>
</template>
<script>
import axios from "axios";
import { reactive } from '@vue/reactivity';
export default {
  name: 'CommentListItem',
  props: {
    comment: {
      type: Object,
      required: true
    },
    article: {
      type: Object,
      required: true
    }
  },
  setup(props) {
    const state = reactive({
      modifyMode : false,
      content : props.comment.content
    }) 

    const modifyCommentMode = async () => {
      state.modifyMode = !state.modifyMode
    }

    const modifyContent = async () => {
      await axios({
        url: `/comment/${props.article.id}/${props.comment.id}`,
        method: 'PATCH',
        data: {content: state.content}
      }).then(()=>{
        state.modifyMode = false
      }).catch((err)=>{
        console.log(err)
      })
    }

    const deleteComment = async () =>{
      await axios({
        url: `/comment/${props.article.id}/${props.comment.id}`,
        method: 'DELETE'
      }).then(()=>{
        console.log('삭제 성공')
      }).catch((err)=>{
        console.log(err)
      })
    }

    return {
      state,
      props,
      modifyCommentMode,
      modifyContent,
      deleteComment
    }
  }
}
</script>
<style lang="">
  
</style>