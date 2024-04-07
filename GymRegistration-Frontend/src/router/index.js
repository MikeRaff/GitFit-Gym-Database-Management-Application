import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import GymRegistration from '@/components/GymRegistration'
import Login from '@/components/Login'
import CreateSession from '@/components/CreateSession'
import SessionsStaffView from '@/components/SessionsStaffView'
import SessionsClientView from '@/components/SessionsClientView'
import Accounts from '@/components/Accounts'


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
      path: '/class-types',
      name: 'GymRegistration',
      component: GymRegistration
    },
    {
      path: '/login',
      name: 'LogIn',
      component: Login
    },
    {
      path: '/create-session', 
      name: 'CreateSession',
      component: CreateSession
    },
    {
      path: '/view-session-staff', 
      name: 'SessionsStaffview',
      component: SessionsStaffView
    },
    {
      path: '/view-session-client', 
      name: 'SessionsClientview',
      component: SessionsClientView
    },
    {
      path: '/accounts', 
      name: 'Accounts',
      component: Accounts
    }
  ]
})
