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
      beforeEnter() {
        // Put the full page URL including the protocol http(s) below
        window.open("https://learngis2.maps.arcgis.com/apps/MapTour/index.html?appid=2948ee98fa784d26bb54882cf3c316df")
      },
      path:'/storymap',
      name: 'StoryMap',
      component: StoryMap
    }
  ]
})