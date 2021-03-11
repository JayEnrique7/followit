import Vue from 'vue'
import VueRouter from 'vue-router'

import App from './App.vue'
import login from './components/View/Login'
import profile from './components/View/Profile'

Vue.use(VueRouter)

let routes = [
  {path: '/' , component: login},
  {path: '/profile' , component: profile}
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