import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import MyHttpServer from '@/plugins/http.js'
import qs from '../node_modules/qs'
Vue.config.productionTip = false
Vue.use(MyHttpServer)
 Vue.use(qs)
 Vue.prototype.qs = qs
 Vue.prototype.thisisstatus=""
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')


// Vue.http.interceptors.push((request, next)  =>{
//   if(localStorage.getItem('token')){
//     request.headers.set('TOKEN',localStorage.getItem('token'));
//     console.log(request.headers)
//   }
//   next((response) => {

//     return response;
//   });
// });
