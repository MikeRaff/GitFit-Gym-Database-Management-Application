import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import GymRegistration from '@/components/GymRegistration'
import Login from '@/components/Login'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/app',
      name: 'GymRegistration',
      component: GymRegistration
    },
    {
      path: '/login',
      name: 'LogIn',
      component: Login
    }
  ]
})
