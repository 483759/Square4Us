<template>
<div class="articleInfo" @click="getArticle">
  <h2>{{props.article.title}} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 좋아요: {{props.article.good}} 싫어요: {{props.article.dislike}} </h2>
  <h3>작성자: {{props.article.member.nickname}}</h3>
  <h4>작성일자: {{dateFormatter(props.article.createdDate)}}</h4>
  <h5>조회수: {{props.article.hit}}</h5>
  <div>
    {{props.article.content}}
  </div>
  <button @click="evalArticle('l')">좋아요</button>
  <button @click="evalArticle('d')">싫어요</button>
  <Comment :article="props.article" />
</div>
</template>

<script>
//import { useStore } from 'vuex'
import Comment from '@/components/study/main/comment/Comment.vue'
import { reactive } from '@vue/runtime-core'
import axios from 'axios'
import store from '@/store'

export default {
  name: 'StudyArticleRead',
  props: {
    article: {
      type: Object,
      required: true
    }
  },
  components: {
    Comment
  },
  setup(props, { emit }) {
    // const store = useStore()
    const state = reactive({
      isViewMode: false,
      page: 1,
    })

    const dateFormatter = (date)=>{
        var month=date.substring(5,7)
        var day=date.substring(8,10)
        var time=date.substring(11,16)
        return month+'월 '+day+'일 '+time
    }

    const evalArticle = (flag)=>{
      console.dir("실행 전 : " + props.article.good + " " + props.article.dislike);
      axios({
        method: "POST",
        url: `/study/${store.state.curStudy.id}/article/${props.article.id}/${flag}`,
      }).then((res) => {
        alert('좋아요/싫어요 성공!')
        console.log(res);
        emit('refreshArticle', res.data.data)
        console.dir("실행 후 : " + props.article.good + " " + props.article.dislike);
      }).catch((error) => {
        console.dir(error);
        alert('좋아요/싫어요 실패!')
      });
    }

    return {
      props,
      state,
      dateFormatter,
      evalArticle
    }
  }
}
</script>

<style>
</style>