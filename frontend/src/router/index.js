import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Tutorial from '@/views/Tutorial.vue'
import UserInfo from '@/views/UserInfo.vue'
import ConferenceBase from '@/views/ConferenceBase.vue'
import ConferenceRoom from '@/views/ConferenceRoom.vue'
import ConferenceReport from '@/views/ConferenceReport.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/tutorial',
    name: 'Tutorial',
    component: Tutorial
  },
  {
    path: '/userinfo',
    name: 'UserInfo',
    component: UserInfo
  },
  {
    path: '/conference-base',
    name: 'ConferenceBase',
    component: ConferenceBase
  },
  {
    path: '/conference-room',
    name: 'ConferenceRoom',
    component: ConferenceRoom
  },
  {
    path: '/conference-report',
    name: 'ConferenceReport',
    component: ConferenceReport
  },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
