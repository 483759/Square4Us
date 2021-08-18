import router from "@/router";
import axios from "axios";
import {createStore} from "vuex";

export default createStore({
  state: {
    isLogin: false,
    user: {},
    studies: [], // 전체스터디 목록
    curStudy: {},
    myStudies: [], // 내 스터디 목록
    myMeetings: [],
    studyArticles: [],

    activeStudyNav: 0
  },
  mutations: {
    LOGIN : function (state) {
      state.isLogin = true
    },
    LOGOUT : function (state) {
      state.isLogin = false
      state.user = {}
      state.myStudies = []
    },
    SET_USER: function (state, payload) {
      state.user = payload
    },
    SET_STUDIES: function (state, payload) { // 전체목록
      state.studies = payload
    },
    SET_CURRENT_STUDY: function (state, payload) {
      state.curStudy = payload
    },
    SET_MY_STUDIES: function (state, payload) { // 내 스터디목록
      state.myStudies = payload
    },
    SET_MEETINGS: function (state, payload) {
      state.myMeetings = payload
    },
    SET_STUDY_ARTICLES: function (state, payload) {
      state.studyArticles = payload
    },
    SET_STUDY_ACTIVE: function (state, payload) {
      state.activeStudyNav = payload
    },
    SET_ARTICLES : function (state, payload) {
      state.studyArticles = payload
    }
  },
  actions: {
    // 유저
    login: async function(context, credentials) {
      const response = await axios({
        method: "POST",
        url: "/member/login",
        data: credentials
      }).catch((err)=>{
        console.log(err.response);
        localStorage.removeItem('JWT');
      })
      if (!response) {
        console.log("로그인 실패");
        console.log(response);
        return
      }
      localStorage.setItem('JWT', response.data.data.accessToken)
      axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.data.accessToken}`
      console.log("로그인 성공", localStorage);
      context.commit('LOGIN')
      router.push({name: 'StudyList'})
    },
    signup: async function (context, credentials) {
      const response = await axios({
        method: "POST",
        url: "/member/join",
        data: credentials        
      }).catch((err)=>{
        console.log(err.response);
      })

      if (!response) return
      const loginCredentials = {
        email: credentials.email,
        password: credentials.password
      }
      context.dispatch('login', loginCredentials)
    },
    getUser : async function (context) {
      const response = await axios({
        method: "GET",
        url: "/member/me",
      }).catch((err)=>{
        console.log(err.response);
        localStorage.removeItem('JWT');
      })
      if (!response) return false
      await context.commit('SET_USER', response.data.data)
      context.commit('LOGIN')
      return true
    },
    updateMemberInfo: async function (context, data) {
      const response = await axios({
        method: 'POST',
        url: '/member/me',
        data: data
      }).catch((err)=>{
        console.log(err.response)
      })
      if(!response) {
        alert('수정 실패')
        console.log(response)
        return
      }
      await context.commit('SET_USER', response.data.data);
    },
    // 미팅
    getMeetings: async function (context, studyId) {
      const response = await axios({
        method: 'GET',
        url: `/study/${studyId}/meeting`,
      }).catch((err)=>{
        console.log(err.response);
      })
      if (!response) {
        alert('목록 조회 실패')
        console.log(response);
        return
      }
      context.commit('SET_MEETINGS', response.data.data.meetings)
    },
    createMeeting: async function (context, data) {
      const response = await axios({
        method: 'POST',
        url: `/study/${data.studyId}/meeting/${data.name}`,
        data: data.thumbnail,
        cache: false,
        contentType: false,
        processType: false,
      }).catch((err)=>{
        console.log(err.response);
      })
      if (!response) {
        alert('생성 실패')
        console.log(response);
        return
      }
      console.dir(response.data);
      context.commit('SET_MEETINGS', response.data.data.meetings);
    },
    // 스터디
    createStudy : async function(context, data) {
      console.log(data)
      const response = await axios({
        method: 'POST',
        url: '/study',
        data: data,
      }).catch((err)=>{
        console.log(err.response);
      })
      if (!response) {
        alert('생성 실패')
        console.log(response);
        return
      }
      const studyId = response.data.data.id
      context.dispatch('getMyStudies')
      router.push({path: `/study/${studyId}`})
    },
    getMyStudies: async function (context) {
      const response = await axios({
        method: "GET",
        url: "/study/me/list",
      }).catch((err)=>{
        console.log(err.response);
      })
      if (!response) {
        alert("내 스터디 목록을 받아오지 못했습니다")
        return
      }
      context.commit('SET_MY_STUDIES', response.data.data.studyList)
    },
    joinStudy: async function (context, studyId) {
      ///api/study/{studyId}
      const response = await axios({
        method: "POST",
        url: `/study/${studyId}`,
      }).catch((err) => {
        console.log(err.response);
      })
      if (!response) {
        alert("이미 가입되었거나 승인 대기중입니다 (또는 서버터짐)")
        return
      }
      alert('가입 신청 성공')
      console.log(response.data.data);
    },
    getStudyByNumber: async function (context, studyId) {
      const response = await axios({
        method: "GET",
        url: `/study/${studyId}`,
      }).catch((err) => {
        console.log(err.response);
      })
      if (response.data.statusCode === 204) {
        alert('존재하지 않는 스터디입니다');
        return;
      }
      context.commit('SET_CURRENT_STUDY', response.data.data);
    },
    getStudies: async function (context) { // 전체 스터디 목록
      const response = await axios({
        method: "GET",
        url: `/study?page=0&size=20&sorted=true&unsorted=true&empty=true`,
      }).catch((err) => {
        console.log(err.response);
      })
      if (!response) {
        alert("스터디 목록을 받아오지 못했습니다")
        return
      }
      context.commit('SET_STUDIES', response.data.data.studyList.content)
    },
    getStudiesWithSearch: async function (context, param) {
      const response = await axios({
        method: "GET",
        url: `/study/search?key=${param.key}&word=${param.word}`,
      }).catch(err => {
        console.log(err.response);
      })
      if (!response) {
        alert("스터디 목록을 받아오지 못했습니다")
        return
      }
      context.commit('SET_STUDIES', response.data.data.studyList.content);
    },

    // 아티클
    getArticles: async function(context, data) {
      const {studyId, page } = data
      const response = await axios({
        method: 'GET',
        url: `/study/${studyId}/article?page=${page}&size=6&sorted=true&unsorted=true&empty=true`,
      }).catch((err)=>{
        console.log(err.response)
      })
      if (!response) {
        alert('게시글 조회 실패')
        console.log(response);
        return
      }
      console.log(response.data);
      context.commit('SET_STUDY_ARTICLES', response.data.data.articleList)
    },

    getArticlesWithSearch: async function (context, param) {
      const response = await axios({
        method: 'GET',
        url: `/study/${param.studyId}/article/search?key=${param.key}&word=${param.word}`,
      }).catch((err)=>{
        console.log(err.response)
      })
      if (!response) {
        alert('게시글 조회 실패')
        console.log(response);
        return
      }
      context.commit('SET_STUDY_ARTICLES', response.data.data.articleList)
    },
    createArticle: async function(context, data){
      const response = await axios({
        method: 'POST',
        url: `/study/${data.studyId}/article`,
        data: data.article
      }).catch((err)=>err.response)

      console.log(response);
      if (response.status !==200) return false
      
      if (data.files.length) {
        context.dispatch('addFilesToArticle', { studyId : data.studyId , articleId : response.data.data.id, files: data.files})
      }
      return true
    },
    addFilesToArticle: async function (context, data) {
      const { studyId, articleId, files } = data
      let formData = new FormData();
      files.forEach((f) => {
        formData.append('files', f);
      });
      const response = await axios({
        method: 'POST',
        url: `/study/${studyId}/article/${articleId}/files`,
        data: formData
      }).catch((err)=>{
        console.log(err.response)
      })
      if (!response) {
        alert('파일 업로드 실패')
        console.log(response);
        return
      }
      console.log(response);
    },
    withdrawMembership : async function(context) {
      const response = await axios({
        method: "DELETE",
        url: `/member/me`,
      }).catch((err)=>{
        if(err.response.status===409){
          alert('팀장인 스터디가 있으면 탈퇴할 수 없습니다');
        }        
        console.log(err.response);
      })
      if (!response) {
        alert("회원 탈퇴에 실패했습니다");
        return
      }
      if(response.status===200){
        localStorage.removeItem('JWT');
        context.commit('LOGOUT');
        router.push({name: 'Main'})
      }
    },
    removeStudy : async function(context, studyId) {
      const response = await axios({
        method: "DELETE",
        url: `/study/${studyId}`,
      }).catch((err)=>{
        if(err.response.status===409){
          alert('삭제할 수 없습니다');
        }        
        console.log(err.response);
      })
      console.log(response)
      if(response.status===200){
        this.state.curStudy=[]
        router.push({name: 'Main'})
      }
    }
  },
  getters: {
    isLeader : function (state) {
      // console.log(state.user, state.curStudy);
        if (state.user && state.curStudy) {
          if (state.user.id===state.curStudy.leaderId) {
            return true
          }
        }
        return false
    }
  },
  modules: {},
});
