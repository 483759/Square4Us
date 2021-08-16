<template>
  <div class="commentBox">
    <div class="commentProfile">
      <img style="height:50px; width:50px" :src="profile" alt="">
    </div>
    <div class="commentContent">
      <div class="commentNickname">
        {{comment.member.nickname}}
      </div>
      
      {{comment.content}}
    </div>
    
    
  </div>
</template>
<script>
import { computed, onMounted, reactive } from '@vue/runtime-core'
export default {
  name: 'CommentListItem',
  props: {
    comment: {
      type: Object,
      required: true
    }
  },
  setup(props) {
    const data = reactive({
      imgUrl: ""
      
    })
    const profile = computed(() => {
      
      return props.comment.member.profile.filePath + '/' + props.comment.member.profile.fileName
      
    })

    console.log(props.comment)
    console.log('filepath', props.comment.member.profile.filePath)
    console.log('filename', props.comment.member.profile.fileName)
   
    onMounted((props) => {
      this.data.imgUrl = props.comment.member.profile.filePath + '/' +props.comment.member.profile.filePath
      console.log(data.imgUrl)
    })

    return {
      props,
      data,
      profile 
      
    }
  }
}
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