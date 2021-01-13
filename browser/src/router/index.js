import Vue from 'vue'
import Router from 'vue-router'
const _import = require('./_import_' + process.env.NODE_ENV)
// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/** note: submenu only apppear when children.length>=1
*   detail see  https://panjiachen.github.io/vue-element-admin-site/#/router-and-nav?id=sidebar
**/

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    roles: ['admin','editor']     will control the page roles (you can set multiple roles)
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
    noCache: true                if true ,the page will no be cached(default is false)
  }
**/
export const constantRouterMap = [
  { path: '/login', component: _import('login/index'), hidden: true },
  { path: '/authredirect', component: _import('login/authredirect'), hidden: true },
  { path: '/404', component: _import('errorPage/404'), hidden: true },
  { path: '/401', component: _import('errorPage/401'), hidden: true },
  {
    path: '',
    component: Layout,
    redirect: 'dashboard',
    children: [{
      path: 'dashboard',
      component: _import('dashboard/index'),
      name: 'dashboard',
      meta: { title: 'Home', icon: 'dashboard', noCache: true }
    }]
  },
]

export default new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [

  {
    path: '/system',
    component: Layout,
    meta: { perm:'m:sys', title: 'System Management', icon: 'component' },
    children: [
      {
        path: 'user_manage',
        name: 'user_manage',
        component: _import('_system/user/index'),
        meta: { perm: 'm:sys:user', title: 'User Management', icon: 'user', noCache: true }
      },
      {
        path: 'role_manage',
        name: 'role_manage',
        component: _import('_system/role/index'),
        meta: { perm: 'm:sys:role', title: 'Role Management', icon: 'user', noCache: true },
      },
      {
        hidden: true,
        path: 'role_manage/:roleId/assign_perm',
        name: 'role_manage_assign_perm',
        component: _import('_system/role/assign_perm'),
        meta: { hiddenTag: true , title: '角色授权'},
      },
      {
        path: 'perm_manage',
        name: 'perm_manage',
        component: _import('_system/perm/index'),
        meta: { perm: 'm:sys:perm', title: 'Role Authorization', icon: 'chart', noCache: true }

      },
    ]
  },
  {
    path: '/menu1',
    component: Layout,
    children: [{
      path: 'index',
      name: 'menu1',
      component: _import('menu/menu1'),
      meta: { perm:'m:menu1', title: 'Start Online Course', icon: 'peoples' }
    }]
  },


  {
    path: '/menu2',
    component: Layout,
    children: [{
      path: 'index',
      name: 'menu2',
      component: _import('menu/menu2'),
      meta: { perm:'m:menu2', title: 'Personal Attention Analysis', icon: 'chart' }
    }]
  },

  {
    path: '/menu3',
    component: Layout,
    meta: {
      perm:'m:menu3',
      title: 'Attention Analysis',
      icon: 'chart'
    },
    children: [
      { path: 'menu3_1', component: _import('menu/menu3_1'), name: 'menu3_1', meta: { perm:'m:menu3:1', title: 'Student Attention Analysis', icon: 'chart', noCache: true }},
      { path: 'menu3_2', component: _import('menu/menu3_2'), name: 'menu3_2', meta: { perm:'m:menu3:2', title: 'Class Attention Analysis', icon: 'chart', noCache: true }},
      { path: 'menu3_3', component: _import('menu/menu3_3'), name: 'menu3_3', meta: { perm:'m:menu3:3', title: 'Class Emotion', icon: 'chart', noCache: true }}
    ]
  },


  {
    path: '/menu4',
    component: Layout,
    children: [{
      path: 'index',
      name: 'menu4',
      component: _import('menu/menu4'),
      meta: { perm:'m:menu4', title: 'Data Export', icon: 'clipboard' }
    }]
  },

  {
    path: '/menu5',
    component: Layout,
    meta: {
      perm:'m:menu5',
      title: 'Data Export',
      icon: 'clipboard'
    },
    children: [
      { path: 'menu5_1', component: _import('menu/menu5_1'), name: 'menu5_1', meta: { perm:'m:menu5:1', title: 'Student Attention Analysis Export', icon: 'clipboard', noCache: true }},
      { path: 'menu5_2', component: _import('menu/menu5_2'), name: 'menu5_2', meta: { perm:'m:menu5:2', title: 'Class Attention Analysis Export', icon: 'clipboard', noCache: true }},
    ]
  },

  { path: '*', redirect: '/404', hidden: true }
]
