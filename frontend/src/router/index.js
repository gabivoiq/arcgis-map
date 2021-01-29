import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import TuristicMap from '@/components/TuristicMap'
import NavigationBar from '@/components/NavigationBar'
import Profile from '@/components/Profile'
import StoryMap from '@/components/StoryMap'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login',
      name: 'Login',
      alias: "/",
      component: Login
    },
    {
      path: '/map',
      name: 'Map',
      component: TuristicMap
    },
    {
      path: '/navigationbar',
      name: 'NavigationBar',
      component: NavigationBar
    },
    {
      path:'/profile',
      name: 'Profile',
      component: Profile
    },
    {
      path:'/storymap',
      name: 'StoryMap',
      component: StoryMap
    }
  ]
})