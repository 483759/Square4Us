import {createRouter, createWebHistory} from 'vue-router'
import Home from '../views/Home.vue'
import Tutorial from '@/views/Tutorial.vue'
import User from '@/views/User.vue'
import Study from '@/views/Study.vue'
import StudyList from '@/views/StudyList.vue'
import StudyMain from '@/views/StudyMain.vue'
import Meeting from '@/views/Meeting.vue'
import StudyReport from '@/views/StudyReport.vue'
import Main from '@/views/Main.vue'
import SignUp from '@/views/SignUp.vue'
import Openvidu from '@/views/Openvidu.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    children: [
      { path: '', name: 'Main', component: Main },
      { path: '/tutorial', name: 'Tutorial', component: Tutorial },
      { path: '/user', name: 'User', component: User },
      // { path: '/userinfo', name: 'UserInfo', component: UserInfo },
      { path: '/study', name: 'Study', component: Study }, /* 실제 화상회의 페이지 */
      { path: '/study/list', name: 'StudyList', component: StudyList },
      { path: '/study/:studyId', name: 'StudyMain', component: StudyMain, props : true },
      { path: '/study/report', name: 'StudyReport', component: StudyReport },
      { path: '/signup', name: 'SignUp', component: SignUp },
      { path: '/openvidu', name: 'Openvidu', component: Openvidu }
    ]
  },
  { path: '/study/:studyId/meeting/:meetingId', name: 'Meeting', component: Meeting, props : true },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
