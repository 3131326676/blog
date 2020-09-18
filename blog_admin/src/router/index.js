import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },

  {
    path: '/article',
    component: Layout,
    redirect: '/article/blog',
    name: 'Article',
    meta: { title: '帖子管理', icon: 'example' },
    children: [
      {
        path: 'type',
        name: 'Type',
        component: () => import('@/views/blog/type/type-list'),
        meta: { title: '分类管理', icon: 'table' }
      },
      {
        path: 'blog',
        name: 'Blog',
        component: () => import('@/views/blog/blog/blog-list'),
        meta: { title: '博客管理', icon: 'table' }
      },
      {
        path: 'about',
        name: 'About',
        component: () => import('@/views/blog/about/about-list'),
        meta: { title: '关于我的', icon: 'table' }
      }
    ]
  },

  {
    path: '/user',
    component: Layout,
    redirect: '/user/admin',
    name: 'UserManager',
    meta: { title: '用户管理', icon: 'example' },
    children: [
      {
        path: 'admin',
        name: 'Admin',
        component: () => import('@/views/user/admin/admin-update'),
        meta: { title: '管理员', icon: 'table' }
      },
      {
        path: 'user',
        name: 'User',
        component: () => import('@/views/user/user/user-list'),
        meta: { title: '用户管理', icon: 'table' }
      }
    ]
  },

  {
    path: '/comment',
    component: Layout,
    redirect: '/comment/comment',
    name: 'CommentManager',
    meta: { title: '评论管理', icon: 'example' },
    children: [
      {
        path: 'comment',
        name: 'Comment',
        component: () => import('@/views/comment/comment-list'),
        meta: { title: '评论管理', icon: 'table' }
      }
    ]
  },

  {
    path: '/sys',
    component: Layout,
    redirect: '/sys/log',
    name: 'System',
    meta: { title: '系统管理', icon: 'example' },
    children: [
      {
        path: 'log',
        name: 'Log',
        component: () => import('@/views/sys/log/log-list'),
        meta: { title: '系统日志', icon: 'table' }
      },
      {
        path: 'music',
        name: 'Music',
        component: () => import('@/views/sys/music/music-list'),
        meta: { title: '音乐欣赏', icon: 'table' }
      },
      {
        path: 'link',
        name: 'Link',
        component: () => import('@/views/sys/link/link-list'),
        meta: { title: '友情链接', icon: 'table' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
