<template>
  <StudyListFrame>
    <template v-slot:header>
      <div id='study-category'>카테고리</div>
      <div id='study-search'>
        <select v-model="search.key" @change="resetWord">
          <option disabled value="">Please select one</option>
          <option value="category">카테고리</option>
          <option value="name">이름</option>
        </select>
        <select v-if="search.key == 'category'" v-model="search.word">
          <option disabled value="">Please select one</option>
          <option value="coding">코딩</option>
          <option value="cert">자격증</option>
          <option value="official">공시</option>
          <option value="just">모각코</option>
        </select>
        <input v-if="search.key == 'name'" type="text" v-model="search.word" @keyup.enter="getStudiesWithSearch"/>
        <button type="button" v-if="search.key == 'name' || (search.key == 'category' && search.word != '')" @click="getStudiesWithSearch">검색</button>
      </div>
      <div id='study-create'>
        <StudyCreateButton/>
      </div>
    </template>
    <template v-slot:section>
      <StudyListItem v-if='studies.length' :studies='studies'/>
    </template>
  </StudyListFrame>
</template>

<script>
import StudyListFrame from '@/components/StudyListFrame.vue'
import StudyListItem from '@/components/study/list/StudyListItem.vue'
import StudyCreateButton from '@/components/study/list/StudyCreateButton.vue'
import { useStore } from 'vuex'
import { computed, onMounted, reactive } from '@vue/runtime-core'
export default {
  name: 'StudyList',
  components: {
    StudyListFrame,
    StudyListItem,
    StudyCreateButton
  },
  setup(){
    const search = reactive({
      key: "",
      word: ""
    });
    const store = useStore()
    const studies = computed(()=>store.state.studies)

    onMounted( ()=>{
      store.dispatch('getMyStudies')
      store.dispatch('getStudies')
      if (Object.keys(store.state.user).length == 0) {
        store.dispatch('getUser')
      }
      // 여기서 스터디 목록을 가져온다
    })

    const resetWord = () => search.word = "";

    const getStudiesWithSearch = () => {
      store.dispatch('getStudiesWithSearch', { key: search.key, word: search.word });
    };

    return {
      search,
      studies,
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
}

#study-category {
  flex-basis: 660px;
  flex-grow: 1;
  }

#study-search {
  flex-basis: 210px;
}
#study-create {
  flex-basis: 160px;
}

</style>