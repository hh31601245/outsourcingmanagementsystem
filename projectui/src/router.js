import Vue from 'vue'
import Router from 'vue-router'
//import Home from './views/Home.vue'

Vue.use(Router)

export default new Router({
  routes: [
    // {
    //   path: '/home',
    //   name: 'home',
    //   component: Home
    // },
    // {
    //   path: '/about',
    //   name: 'about',
    //   // route level code-splitting
    //   // this generates a separate chunk (about.[hash].js) for this route
    //   // which is lazy-loaded when the route is visited.
    //   component: () => import(/* webpackChunkName: "about" */ './views/About.vue')
    // },
    {
      path: '/',
      name: 'login',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/Login.vue')
    },


    {
      path:'/regist',
      name:'regist',
      component: () => import('./views/Regist.vue')
    },


    {
      path:'/index',
      name:'index',
      component:()=>import('./views/Index.vue'),
       children:[
            {
              path:'/list',
              name:'list',
              component:()=>import('./views/List.vue'),
            },
            {
              path:'/add',
              name:'add',
              component:()=>import('./views/Add.vue'),
            },
            {
              path:'/adddeveloper',
              name:'adddeveloper',
              component:()=>import('./views/AddDeveloper.vue'),
            },
            {
              path:'/tasklist',
              name:'tasklist',
              component:()=>import('./views/Tasklist.vue'),
            },
            {
                path: '/about',
                name: 'about',
                // route level code-splitting
                // this generates a separate chunk (about.[hash].js) for this route
                // which is lazy-loaded when the route is visited.
                component: () => import(/* webpackChunkName: "about" */ './views/About.vue')
              },
              {
                path:'/developerlist',
                name:'developerlist',
                component: () => import('./views/DeveloperList.vue')
              },
              {
                path:'/addtask',
                name:'addtask',
                component:()=>import('./views/AddTask.vue')
              },
              {
                path: '/about1',
                name: 'about1',
                // route level code-splitting
                // this generates a separate chunk (about.[hash].js) for this route
                // which is lazy-loaded when the route is visited.
                component: () => import(/* webpackChunkName: "about" */ './views/About1.vue')
              },
       ]
    },

    


  ]
})
