import { RouteRecordRaw } from 'vue-router';
//import LoginPage from 'src/pages/LoginPage.vue';
import ErrorNotFound from 'src/pages/ErrorNotFound.vue';
import HomeLayout from 'src/layouts/HomeLayout.vue';
import RegistrarPage from 'src/pages/RegistrarPage.vue';
import DashboardPage from 'src/pages/DashboardPage.vue';
import ConsultaPage from 'src/pages/ConsultaPage.vue';
import PerfilPage from 'src/pages/PerfilPage.vue';
import LoginLayout from 'src/layouts/LoginLayout.vue';

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'login',
    component: LoginLayout,
  },
  {
    path: '/home',
    name: 'home',
    component: HomeLayout,
    children: [
      {
        path: 'registrar',
        component: RegistrarPage,
      },
      {
        path: '',
        component: DashboardPage,
      },
      {
        path: 'consultar',
        component: ConsultaPage,
      },
      {
        path: 'perfil',
        component: PerfilPage,
      },
    ],
  },
  {
    path: '/:catchAll(.*)*',
    component: ErrorNotFound,
  },
];

export default routes;
