<template>
  <div class="commentBox">
    <div class="commentProfile">
      <img style="height:50px; width:50px" :src="profile" alt="">
    </div>
    <div class="commentContent">
      <div class="commentNickname">
        {{comment.member.nickname}}
      </div>
    <template v-if="state.modifyMode">
      <div>
        <input type="text" v-model="state.content"  />
        <button @click="modifyContent">수정</button>
        <button @click="modifyCommentMode">취소</button>
      </div>
    </template>
    <template v-else>
      {{comment.content}}
      <button @click="modifyCommentMode">수정</button>
      <button @click="deleteComment">삭제</button>
    </template>
    </div>
    

  </div>
 
</template>
<script>
import axios from "axios";
import { computed, reactive } from '@vue/runtime-core'
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


    const data = reactive({
      imgUrl: ""
      
    })
    const profile = computed(() => {
      
      return props.comment.member.profile.filePath + '/' + props.comment.member.profile.fileName
      
    })

    console.log(props.comment)
    console.log('filepath', props.comment.member.profile.filePath)
    console.log('filename', props.comment.member.profile.fileName)
   
    

    return {
      data,
    profile, 
      state,
      props,
      modifyCommentMode,
      modifyContent,
      deleteComment}
  
}}
</script>
<style>
.commentBox {
  display: flex;
  flex-direction: row;
  border: 1px solid #195c77;
  
  border-left: 10px solid #195C77;
  margin: 0 10px 10px 10px;
}
.commentProfile {
 margin: 10px 40px 10px 20px;
}
.commentContent {
  display: flex-start;
  flex-direction: column;
  margin: 10px ;
  flex-basis: 600px;
  min-width: 400px;
  justify-items: start;
  /* justify-content: start; */
  text-align: start;
}
.commentNickname {
  font-size: 13px;
  font-weight: bold;
  margin-bottom: 3px;
}
</style>