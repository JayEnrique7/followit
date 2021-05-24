import Vue from 'vue'
import VueRouter from 'vue-router'
import axios from 'axios'

import App from './App.vue'
import login from './components/View/Login'
import profile from './components/View/profile'
import follows from './components/View/follows'
import messages from './components/View/messages'

Vue.use(VueRouter)

Vue.prototype.$http = axios;
const token = localStorage.getItem('token')

if (token) {
  Vue.prototype.$http.defaults.headers.common['Authorization'] = 'Bearer ' + token
}

let routes = [
  { path: '/', name: 'login', component: login },
  { path: '/profile' , name: 'profile', component: profile,
      children: [
                  { path: '/follows', name:'follows' , component: follows },
                  { path: '/messages', name:'messages', component: messages }
                ]
  }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router
}).$mount('#app')