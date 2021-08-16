<template>
  <div >
    
    <CommentListItem  v-for='comment in state.commentList' :key='comment.id' :comment='comment' :article='article'/>
  
  <div  class="commentCreatBox">
    <input class="commentInput" type="text" name="content" v-model="state.writeContent">
    <button class='green-button' @click="writeComment">댓글 작성</button>
  </div>
    

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
        url: `/comment/${props.article.id}?page=0&size=30&sorted=true&unsorted=true&empty=true`,
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
<style>
.commentInput {
  border-radius: 5px;
  margin-right: 5px;
}
.commentCreatBox{
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin-top: 30px;
}
  
</style>