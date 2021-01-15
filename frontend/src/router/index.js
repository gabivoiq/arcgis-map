import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import TuristicMap from '@/components/TuristicMap'

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
    }
  ]
})