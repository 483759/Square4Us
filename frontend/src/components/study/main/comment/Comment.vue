<template lang="">
  <div>
    <li v-for='comment in state.commentList' :key='comment.id'>
      <CommentListItem :comment='comment' :article='article' />
    </li>
    <input type="text" name="content" v-model="state.writeContent">
    <button @click="writeComment">댓글 작성</button>
  </div>
</template>
<script>
import { reactive } from '@vue/reactivity'
import CommentListItem from '@/components/study/main/comment/CommentListItem.vue'
import axios from 'axios'
import { onMounted } from '@vue/runtime-core'
export default {
  name: 'Comment',
  props: {
    article: {
      type: Object,
      required: true
    }
  },
  components: {
    CommentListItem
  },
  setup(props) {
    const state = reactive({
      writeContent: null,
      commentList: null
    })

    const writeComment = async ()=>{
      const response = await axios({
        url: `/comment/${props.article.id}`,
        method: 'POST',
        data: {content: state.writeContent}
      }).catch((err)=>{
        console.log(err.response)
      })
      if(response.status===200){
        console.log('')
      }
    }

    onMounted(async ()=>{
      const response = await axios({
        url: `/comment/${props.article.id}?page=0&size=100&sorted=true&unsorted=true&empty=true`,
        method: 'GET'
      }).catch((err)=>{
        console.log(err.response)
      })
      if(response.status===200){
        state.commentList = response.data.data.commentList.content
      }
    })

    return {
      props,
      state,
      writeComment
    }
  }
}
</script>
<style lang="">
  
</style>