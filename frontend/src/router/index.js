import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Tutorial from '@/views/Tutorial.vue'
import User from '@/views/User.vue'
import Study from '@/views/Study.vue'
import StudyList from '@/views/StudyList.vue'
import StudyMain from '@/views/StudyMain.vue'
import Meeting from '@/views/Meeting.vue'
import StudyReport from '@/views/StudyReport.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    children: [
      { path: '/tutorial', name: 'Tutorial', component: Tutorial },
      { path: '/user', name: 'User', component: User },
      { path: '/userinfo', name: 'UserInfo', component: UserInfo },
      { path: '/study', name: 'Study', component: Study },
      { path: '/study/list', name: 'StudyList', component: StudyList },
      { path: '/study/main', name: 'StudyMain', component: StudyMain },
      { path: '/study/report', name: 'StudyReport', component: StudyReport },
    ]
  },
  { path: '/study/main/room', name: 'Meeting', component: Meeting },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
