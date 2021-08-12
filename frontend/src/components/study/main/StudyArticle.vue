<template>
<article>
  <header id='section-title'>
    <div>게시글</div>
    <div>
      <!-- <select name="" id="" v-model='data.maximum'>
        <option v-for='option in options' :value="option" :key="option">{{ option }}</option>
      </select> -->
      <button @click='createArticle'>게시글 작성</button>
    </div>
  </header>
  <ul class='article-item'>
    <!-- <h3>스터디 메인 미팅리스트 : 여기 들어오면 axios요청을 보내 목록을 갱신함</h3> -->
    <StudyArticleItem 
      v-for='article in articles.content' 
      :key='article.id' 
      :article='article'
      @onEnter='onEnter'
      />
  </ul>
</article>
</template>

<script>
//import { reactive } from '@vue/reactivity'
import StudyArticleItem from '@/components/study/article/StudyArticleItem.vue'
// import router from '@/router'
 import { useStore } from 'vuex'
 import { computed, onMounted, onUnmounted } from '@vue/runtime-core'

export default {
  name: 'StudyArticle',
  props: {
    studyId: {
      type: String,
      required : true
    }
  },
  components : {
    StudyArticleItem
  },
  setup(props) {
    const store = useStore()
    const articles = computed(()=>{
      return store.state.studyArticles
    })

    onMounted(()=>{
      store.dispatch('getArticles', props.studyId)
    })
    onUnmounted(()=>{
      store.commit('SET_STUDY_ARTICLES', [])
    })

    return {
      articles
    }
  }
}
</script>

<style>

.article-item {
  display: flex;
  width : 100%;
  flex-direction: column;
}
.article-item > li {
  display: flex;
  height: 80px;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #195C77;
  padding: 0 10px;
}
</style>