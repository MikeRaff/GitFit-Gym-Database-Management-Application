import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import GymRegistration from '@/components/GymRegistration'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/app',
      name: 'GymRegistration',
      component: GymRegistration
    }
  ]
})
