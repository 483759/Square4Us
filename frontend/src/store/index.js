import router from "@/router";
import axios from "axios";
import { createStore } from "vuex";

export default createStore({
  state: {
    isLogin: false,
    user: {}
  },
  mutations: {
    LOGIN : function (state) {
      state.isLogin = true
    },
    LOGOUT : function (state) {
      state.isLogin = false
      state.user = {}
    },
    SET_USER: function (state, payload) {
      state.user = payload
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
    getUser : async function (context) {
      const response = await axios({
        method: "GET",
        url: "/member/me",
      }).catch((err)=>{
        console.log(err.response);
        localStorage.removeItem('JWT');
      })
      if (!response) return false
      context.commit('LOGIN')
      // context.commit('SET_USER', response.data)
      console.log("유저 정보 받아옴", response);
      return true
    }
  },
  modules: {},
});
