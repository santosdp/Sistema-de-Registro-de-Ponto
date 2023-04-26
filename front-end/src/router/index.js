import { route } from 'quasar/wrappers'
import { createRouter, createMemoryHistory, createWebHistory, createWebHashHistory } from 'vue-router'
import LoginPage from 'src/pages/LoginPage.vue'
import { useAuth } from 'stores/auth.js'


//Criação e tratamento do comportamento das rotas das páginas do projeto
export default route(function (/* { store, ssrContext } */) {
  const createHistory = process.env.SERVER
    ? createMemoryHistory
    : (process.env.VUE_ROUTER_MODE === 'history' ? createWebHistory : createWebHashHistory)

  const Router = createRouter({
    scrollBehavior: () => ({ left: 0, top: 0 }),
    history: createHistory(process.env.VUE_ROUTER_BASE),
    routes: [
      {
        //Rota que define a página de login, além de definir como rota padrão/principal
        path: '/',
        name: 'login',
        component: LoginPage,
        meta: {
          authlogin: true
        }
      },
      {
        //Rota para a página de cadastro
        path: '/cadastro',
        name: 'cadastro',
        component: () => import('pages/CadastroPage.vue')
      },
      {
        //Rota para a redefinição de senha
        path: '/recuperar',
        name: 'recuperar',
        component: () => import('pages/RecuperarPage.vue')
      },
      {
        //Rota para a página Home, onde o usuário pode registrar o ponto e verificar historico
        path: '/home',
        name: 'home',
        component: () => import('pages/Home.vue'),
        children: [
          {path: '/historico', component: () => import('pages/Home.vue')},
          {path: '/login', component: () => import('pages/Home.vue')},
          {path: '/justificar', component: () => import('pages/Home.vue')}
        ],
        meta: {
          auth: true
        }
      },
      {
        //Rota em caso de ser ir para página inexistente
        path: '/:catchAll(.*)*',
        component: () => import('pages/ErrorNotFound.vue')
      }
    ]


  })

  //Esta função faz o controle de acesso de usuário, somente usuário que tiverem um token válido podem acessar a página Home
  Router.beforeEach( async (to, from, next) => {
    if(to.meta?.authlogin){
      const auth = useAuth()
      if(auth.user && auth.token){
        const isAuthenticated = await auth.checkToken()
        if(isAuthenticated){
          next({name: 'home'})
        }
        else{
          next()
        }
      }
      else{
        next()
      }
    }
    else{
      next()
    }
  })
  // Esta função é responsavel por manter o usuário na página Home, impedindo este de sair para a página de Login caso não faça o Logout
  Router.beforeEach( async (to, from, next) => {
    if(to.meta?.auth){
      const auth = useAuth()
      if(auth.user && auth.token){
        const isAuthenticated = await auth.checkToken()
        if(isAuthenticated){
          next()
        }
        else{
          next({name: 'login'})
        }
      }
      else{
        next({name: 'login'})
      }
    }
    else{
      next()
    }
  })

  return Router
})
