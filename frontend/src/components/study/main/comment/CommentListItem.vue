<template>
  <div class="commentBox">
    <div class="commentProfile">
      <img style="height:50px; width:50px" :src="profile" alt="">
    </div>
    <div class="commentContent">
      <div class="commentNickname">
        {{comment.member.nickname}}
      </div>
    <template  v-if="state.modifyMode">
      <div class="commentInput">
        <div>
          <input type="text" v-model="state.content"  />
        </div>
        <div class="commentButtonBox">
          <button class="green-button commentButton" style="margin-bottom:5px" @click="modifyContent">수정</button>
          <button class="green-button commentButton" @click="modifyCommentMode">취소</button>
        </div>
        
      </div>
    </template>
    <template  v-else>
      <div class="commentInput">
        <div>
          {{comment.content}}
        </div>
        <div class="commentButtonBox">
          <button class="green-button commentButton" style="margin-bottom:5px" @click="modifyCommentMode">수정</button>
          <button class="green-button commentButton" @click="deleteComment">삭제</button>
        </div>
      </div>
      
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
  setup(props, {emit}) {
    const state = reactive({
      modifyMode : false,
      content : props.comment.content
    }) 

    const modifyCommentMode = async () => {
      state.modifyMode = !state.modifyMode
    }

    const modifyContent = () => {
      axios({
        url: `/comment/${props.article.id}/${props.comment.id}`,
        method: 'PATCH',
        data: {content: state.content}
      }).then(()=>{
        state.modifyMode = false
        emit('getComments')
      }).catch((err)=>{
        console.log(err)
      })
    }

    const deleteComment = () =>{
      axios({
        url: `/comment/${props.article.id}/${props.comment.id}`,
        method: 'DELETE'
      }).then(()=>{
        console.log('삭제 성공')
        emit('getComments')
      }).catch((err)=>{
        console.log(err)
      })
    }


    const data = reactive({
      imgUrl: ""
      
    })
    const profile = computed(() => props.comment.member.profile.filePath + '/' + props.comment.member.profile.fileName)
    
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
  border: 1px solid #195c77;
  
  border-left: 10px solid #195C77;
  margin: 0 10px 5px 10px;
}
.commentProfile {
  margin: 10px 40px 10px 20px;
}
.commentContent {
  display: flex-start;
  flex-direction: column;
  margin: 10px ;
  flex-basis: 1000px;
  min-width: 400px;
  justify-items: start;
  /* justify-content: start; */
  text-align: start;
}
.commentNickname {
  display: block;
  font-size: 13px;
  font-weight: bold;
  margin-bottom: 3px;
}
.commentInput {
  display: flex;
  justify-content: space-between;
}
.commentButton {
  height: 30px !important;
  width: 70px !important;
  gap: 5px;
}
.commentButtonBox{
  display: flex;
  flex-direction: row;
  gap: 5px;
}
</style>