import axios from "axios";
import { createStore } from "vuex";

export default createStore({
  state: {},
  mutations: {},
  actions: {
    login: function(context, credentials) {
      console.log(credentials);
      axios({
        method: "POST",
        url: "/member/login",
        data: {
          ...credentials,
        },
      }).then((res)=>{
        console.log(res.data);
      }).catch((err)=>{
        console.log(err.response);
      })
    },
  },
  modules: {},
});
