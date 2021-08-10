import axios from "axios";
import { createStore } from "vuex";

export default createStore({
  state: {
    isLogin: true,
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
        data: {
          ...credentials,
        },
      }).catch((err)=>{
        console.log(err.response);
        localStorage.removeItem('JWT');
      })
      if (!response) return false
      localStorage.setItem('JWT', response.data.data.accessToken)
      console.log("로그인 성공", localStorage);
      context.commit('LOGIN')
      return true
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
