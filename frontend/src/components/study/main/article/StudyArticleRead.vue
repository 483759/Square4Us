<template>
<div class="articleInfo" @click="getArticle">
  <div class="articleTab">
    <div class="articleReadTitle">
      {{props.article.title}}
    </div>
    <div class="article_box">
      <div class="articleAuthor">
        작성자: {{props.article.member.nickname}}
        작성일자: {{dateFormatter(props.article.createdDate)}}
      </div>  
      <div class="articleView">
        조회수: {{props.article.hit}}
        좋아요: {{props.article.good}}
        싫어요: {{props.article.dislike}}
      </div>
    </div>
  </div>
  
  
  
  <div class="articleReadContent">
    {{props.article.content}}
  </div>
  <div class="articleReadButtonBox">
    <button class="green-button" @click="like">좋아요</button>
    <button class="green-button" @click="like">싫어요</button>
  </div>
  
  <Comment :article="props.article" />
</div>
</template>

<script>
//import { useStore } from 'vuex'
import Comment from '@/components/study/main/comment/Comment.vue'
import { reactive } from '@vue/runtime-core'

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
.articleReadTitle {
  display: block;
  height: 40px;
  margin: 0 10px 0 10px;
  text-align: start;
  font-size: 20px;
  font-weight: bold;
  margin: 10px;

}
.articleTab{
  border: 2px solid #195c77;
  margin: 10px 20px 0 20px;
}
.articleBox {
  display: flex;
  flex-wrap: nowrap;
  flex-direction: row;
  justify-content: center;
  margin: 10px;
  
}
.articleAuthor {
  /* flex-wrap: wrap; */
  display: block;
  
  text-align: start;
  margin:0 10px 0 10px;

}

.articleView {
  display: block;
  
  text-align: end;
  margin:0 10px 20px 10px;
}
.articleReadContent {
  height: 100px;
  margin: 20px 30px 0 30px;
  text-align: start;
}
.articleReadButtonBox{
  display: flex;
  flex-direction: row;
  justify-content: center;
  gap: 10px;
  margin-bottom: 10px;
}

</style>