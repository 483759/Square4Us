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
      console.log(state.content)
      await axios({
        url: `/comment/${props.article.id}/${props.comment.id}`,
        method: 'PATCH',
        data: {content: state.content}
      }).then((response)=>{
        console.log(response)
        //props.comment.content = response.data.data.
        state.modifyMode = false
      }).catch((err)=>{
        console.log(err)
      })
    }

    return {
      state,
      props,
      modifyCommentMode,
      modifyContent
    }
  }
}
</script>
<style lang="">
  
</style>