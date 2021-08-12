<template>
<article class='study-article'>
  <header id='section-title'>
    <div>게시글</div>
    <div>
      <!-- <select name="" id="" v-model='data.maximum'>
        <option v-for='option in options' :value="option" :key="option">{{ option }}</option>
      </select> -->
      <button @click='createArticle'>{{ state.isCreateMode ? '게시글 목록' : '게시글 작성' }}</button>
    </div>
  </header>
  <!-- 여기에 v-if로 게시글 작성, 조회가 들어가게 됨 -->
  <StudyArticleCreate v-if='state.isCreateMode'/>
  <ul class='article-item' v-else>
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
import StudyArticleItem from '@/components/study/main/article/StudyArticleItem.vue'
import StudyArticleCreate from '@/components/study/main/article/StudyArticleCreate.vue'
// import router from '@/router'
 import { useStore } from 'vuex'
 import { computed, onMounted, onUnmounted, reactive } from '@vue/runtime-core'

export default {
  name: 'StudyArticle',
  props: {
    studyId: {
      type: String,
      required : true
    }
  },
  components : {
    StudyArticleItem,
    StudyArticleCreate
  },
  setup(props) {
    const store = useStore()
    const articles = computed(()=>{
      return store.state.studyArticles
    })

    const state = reactive({
      isCreateMode: false
    })

    // 게시글 작성
    const createArticle = ()=>{
      state.isCreateMode = !state.isCreateMode
    }

    onMounted(()=>{
      store.dispatch('getArticles', props.studyId)
    })
    onUnmounted(()=>{
      store.commit('SET_STUDY_ARTICLES', [])
    })

    return {
      state,
      articles,
      createArticle
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