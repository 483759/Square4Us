<template>
  <header id='section-title'>
    <div>게시글</div>
    <div>
      <button>글쓰기</button>
      <button @click="getArticles">게시글불러오기</button>


    </div>
  </header>
  <ul>
    <StudyArticleItem 
     v-for='article in studyArticles'
     :key='article.id'
     :article='article'/>
  </ul>
    
</template>

<script>
import { reactive } from '@vue/reactivity'
import StudyArticleItem from '@/components/study/main/StudyArticleItem.vue'
import { useStore } from 'vuex'
import { computed } from '@vue/runtime-core'
// import axios from 'axios'
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
    console.log("스터디아이디", props.studyId)
    const store = useStore()
    const data = reactive({
      studyId: props.studyId,
      page: '',
      size: '',
      sort: '',
      articleList: [],
          })
    const getArticles = async() => {
      await store.dispatch('getArticles', props.studyId)  
    }
    
    const studyArticles = computed(() => {
      return store.state.studyArticles
    })
    console.log('studyArticles', studyArticles)
    
    

    return {
        store,
        data,
        getArticles,
        studyArticles
    }
  }
}    

</script>

<style>

</style>