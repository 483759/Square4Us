<template>
<div class="articleInfo" @click="getArticle">
  <h2>{{props.article.title}}</h2>
  <h3>작성자: {{props.article.member.nickname}}</h3>
  <h4>작성일자: {{dateFormatter(props.article.createdDate)}}</h4>
  <h5>조회수: {{props.article.hit}}</h5>
  <div>
    {{props.article.content}}
  </div>
  <button @click="like">좋아요</button>
  <button @click="like">싫어요</button>
</div>
</template>

<script>
//import { useStore } from 'vuex'
import { onMounted, reactive } from '@vue/runtime-core'

export default {
  name: 'StudyArticleRead',
  props: {
    article: {
      type: Object,
      required: true
    }
  },
  setup(props) {
    // const store = useStore()
    const state = reactive({
      isViewMode: false,
      page: 1
    })

    const dateFormatter = (date)=>{
        var month=date.substring(5,7)
        var day=date.substring(8,10)
        var time=date.substring(11,16)
        return month+'월 '+day+'일 '+time
    }

    const getArticle = async () => {
    }

    onMounted(()=>{
        console.log("HI"+props.article)
    })

    return {
      props,
      state,
      dateFormatter,
      getArticle
    }
  }
}
</script>

<style>
</style>