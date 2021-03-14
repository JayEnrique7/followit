import Vue from 'vue'
import VueRouter from 'vue-router'

import App from './App.vue'
import login from './components/View/login'
import profile from './components/View/profile'
import follows from './components/View/follows'
import messages from './components/View/messages'

Vue.use(VueRouter)

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