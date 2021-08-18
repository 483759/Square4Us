<template>
  <StudyListFrame>
    <template v-slot:header>
      <div id='study-category'>카테고리</div>
      <div id='study-search'>
        <select v-model="search.key" @change="resetWord" class="selectOption" >
          <option id="option" disabled value="">Please select one</option>
          <option id="option" value="category">카테고리로 검색</option>
          <option id="option" value="name">이름으로 검색</option>
        </select>
        <select v-show="search.key == 'category'" v-model="search.word" class="selectCat" >
          <option id="option" disabled value="">Please select one</option>
          <option id="option" value="coding">코딩</option>
          <option id="option" value="cert">자격증</option>
          <option id="option" value="official">공시</option>
          <option id="option" value="just">모각코</option>
        </select>
        <input v-show="search.key == 'name'" type="text" v-model="search.word" @keyup.enter="getStudiesWithSearch" class="searchName" style="height:30px"/>
        <button class="white-button searchButtonSize" type="button" v-if="search.key == 'name' || (search.key == 'category' && search.word != '')" @click="getStudiesWithSearch">검색</button>
      </div>
      <div id='study-create'>
        <StudyCreateButton/>
      </div>
    </template>
    <template v-slot:section>
      <StudyListItem v-if='studies.length' :studies='studies'/>
      <section class='pagination-wrapper'>
      <Pagination  v-model="state.page" :records="state.totalElements" :per-page="8" @paginate="paginate" :options='{texts: {count:""}}'/>
      </section>
    </template>
  </StudyListFrame>
</template>

<script>
import Pagination from 'v-pagination-3';

import StudyListFrame from '@/components/StudyListFrame.vue'
import StudyListItem from '@/components/study/list/StudyListItem.vue'
import StudyCreateButton from '@/components/study/list/StudyCreateButton.vue'
import { useStore } from 'vuex'
import { computed, onMounted, reactive } from '@vue/runtime-core'
export default {
  name: 'StudyList',
  components: {
    Pagination,
    StudyListFrame,
    StudyListItem,
    StudyCreateButton
  },
  setup(){
    //페이지네이션
    const state = reactive({
      page : 1,
      totalElements : 2
    })
    
    const paginate = async(pageNum)=>{
      state.page = pageNum
      state.totalElements = await store.dispatch('getStudies', pageNum-1)
    }

    const search = reactive({
      key: "",
      word: ""
    });
    const store = useStore()
    const studies = computed(()=>store.state.studies)



    const resetWord = () => search.word = "";

    const getStudiesWithSearch = async() => {
      state.totalElements = await store.dispatch('getStudiesWithSearch', { key: search.key, word: search.word });
    };

    onMounted(async()=>{
      if (Object.keys(store.state.user).length == 0) {
        store.dispatch('getUser')
      }
      store.dispatch('getMyStudies')
      state.totalElements = await store.dispatch('getStudies', 0)
    })

    return {
      state,
      search,
      studies,
      paginate,
      resetWord,
      getStudiesWithSearch
    }
  }
}
</script>

<style scoped>
/* 합쳐서 900나오면 됨 */
#study-category, #study-search, #study-create {
  box-shadow: 0 0 0 1px #195C77 inset;
  display: flex;
  box-sizing: border-box;
  justify-content: center;
  align-items: center;
  font-size: 0.8rem;
  background-color: #195C77;
  
  font-weight: bold;
  font-size: 30px;
  height: 70px;
}

#study-category {
  flex-basis: 660px;
  flex-grow: 1;
  color: white;
  }

#study-search {
  display: flex-start;
  flex-direction: row;
  /* justify-content: start; */
  flex-basis: 210px;
  padding-right: 20px;
  gap: 10px;
  text-align: center;
  
}
#study-create {
  flex-basis: 160px;
  padding-right: 20px;
}
.pagination-wrapper{
  height: auto;
  display: flex;
  box-sizing: border-box;
  justify-content: center;
  align-items: flex-end;
  padding-bottom: 30px;
}
.pagination {
  display: flex !important;

}
.searchButtonSize {
  width: 100px !important;
}
.selectOption {
  height: 33px;
}
.selectCat {
  width: 270px;
  height: 33px;
  margin-left: 3px;
}
.selectName{
  width: 200px !important;
  /* height: 33px !important; */
}

</style>