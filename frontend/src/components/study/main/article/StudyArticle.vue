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
  <StudyArticleCreate v-if='state.isCreateMode' @saveArticle='saveArticle'/>
  <StudyArticleRead v-if='state.isReadMode' :article='state.article' @refreshArticle="refreshArticle" />
  <ul class='article-item' v-else>
    <li v-for='article in articles.content' :key='article.id' >
    <!-- <h3>스터디 메인 미팅리스트 : 여기 들어오면 axios요청을 보내 목록을 갱신함</h3> -->
    <StudyArticleItem :article='article' @click="readArticle(article.id)"/>
    </li>
  </ul>
  <div id="article-search">
    <select v-model="search.key" @change="resetWord">
      <option disabled value="">Please select one</option>
      <option value="category">카테고리</option>
      <option value="title">제목</option>
      <option value="content">내용</option>
    </select>
    <select v-if="search.key == 'category'" v-model="search.word">
      <option disabled value="">Please select one</option>
      <option value=""></option>
    </select>
    <input v-if="search.key == 'title' || search.key == 'content'" v-model="search.word" @keyup.enter="getArticlesWithSearch">
    <button type="button" v-if="search.key == 'title' || search.key == 'content' || (search.key == 'category' && search.word != '')" @click="getArticlesWithSearch">검색</button>
  </div>
  <Pagination v-model="state.page" :records="20" :per-page="1" @paginate="paginate" :options='{texts: {count:""}}'/>
</article>
</template>

<script>
//import { reactive } from '@vue/reactivity'
import StudyArticleItem from '@/components/study/main/article/StudyArticleItem.vue'
import StudyArticleRead from '@/components/study/main/article/StudyArticleRead.vue'
import StudyArticleCreate from '@/components/study/main/article/StudyArticleCreate.vue'
import Pagination from 'v-pagination-3';
// import router from '@/router'
import axios from 'axios'
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
    Pagination,
    StudyArticleItem,
    StudyArticleRead,
    StudyArticleCreate
  },
  setup(props) {
    const store = useStore()
    const articles = computed(()=>{
      return store.state.studyArticles
    })
    const search = reactive({
      key: "",
      word: "",
    });
    const state = reactive({
      isCreateMode: false,
      isReadMode: false,
      article: null,
      page: 1
    })

    //페이지네이션
    const paginate = (pageNum)=>{
      state.page = pageNum
      console.log(pageNum); // 클릭한 페이지가 넘어옴
    }

    // 게시글 작성 토글
    const createArticle = ()=>{
      state.isCreateMode = !state.isCreateMode
    }

    const readArticle = async (articleId) =>{
      const response = await axios({
        url: `/study/${props.studyId}/article/${articleId}`,
        method: 'GET'
      }).catch((err)=>{
        console.log(err.response)
      })
      if(response.status===200){
        state.article = response.data.data
        state.isReadMode = !state.isReadMode
      }
    }

    const refreshArticle = (article) => {
      state.article = article;
    }

    // 게시글 작성  
    const saveArticle = async (data)=>{
      const  { article, files } = data
      const newData = {
        studyId : props.studyId, 
        article,
        files
      }
      const result = await store.dispatch('createArticle', newData)
      if (result) {
        console.log('게시물 생성 성공');
        state.isCreateMode = !state.isCreateMode
        getArticles()
        return
      }

      console.log('게시물 생성 실패');
    }
    const getArticles = ()=>{
      store.dispatch('getArticles', props.studyId)
    }
    const getArticlesWithSearch = () => {
      store.dispatch('getArticlesWithSearch',
      {
        studyId: props.studyId,
        key: search.key,
        word: search.word
      });
    }
    const resetWord = () => { search.word = ""; }

    onMounted(()=>{
      getArticles()
    })
    onUnmounted(()=>{
      store.commit('SET_STUDY_ARTICLES', [])
    })

    return {
      props,
      search,
      state,
      articles,
      paginate,
      readArticle,
      refreshArticle,
      getArticles,
      saveArticle,
      createArticle,
      getArticlesWithSearch,
      resetWord
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

/* 버튼 배치 */
.pagination  {
  display: flex;
  justify-content: center;
}
/* 버튼 스타일링 */
/* .page-link { 
  background-color:white ;
  border: none;
  cursor: pointer;
} */
</style>