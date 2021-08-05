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
        console.log(err.response.data);
      })
      if (!response) return false
      localStorage.setItem('JWT', response.data.accessToken)
      context.commit('LOGIN')
      return true
    },
    getUser : async function (context) {
      
      const response = await axios({
        method: "GET",
        url: "/member/me",
      }).catch((err)=>{
        if (err.response.data.status === 401) {
          console.log('인증이 유효하지 않습니다');
        }
      })
      if (!response) return false
      context.commit('LOGIN')
      // context.commit('SET_USER', response.data)
      console.log(context);
      return true
    }
  },
  modules: {},
});
