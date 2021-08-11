import router from "@/router";
import axios from "axios";
import { createStore } from "vuex";

export default createStore({
  state: {
    isLogin: false,
    user: {},
    myStudies: [],
    myMeetings: [],
    activeStudyNav : 0
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
    SET_MY_STUDIES: function (state, payload) {
      state.myStudies = payload
    },
    SET_MEETINGS: function (state, payload) {
      state.myMeetings = payload
    },
    SET_STUDY_ACTIVE : function (state, payload) {
      state.activeStudyNav = payload
    }
  },
  actions: {
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
      router.push({path: `/study/${studyId}`})
    },
    createMeeting: async function (context, data) {
      const response = await axios({
        method: 'POST',
        url: `/study/${data.studyId}/meeting/${data.maximum}`,
      }).catch((err)=>{
        console.log(err.response);
      })
      if (!response) {
        alert('생성 실패')
        console.log(response);
        return
      }
      console.log(response);
    },
    updateMemberInfo: async function (context, data) {
      const response = await axios({
        method: 'PATCH',
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
      console.log(response);
    }
    ,
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
      }).catch((err)=>{
        console.log(err.response);
      })
      if (!response) {
        alert("내 스터디 목록을 받아오지 못했습니다")
        return
      }
      alert('가입 신청 성공')
      console.log(response.data.data);
    }
  },
  modules: {},
});
