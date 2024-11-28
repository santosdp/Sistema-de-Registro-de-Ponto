<template>
  <q-layout view="hHh Lpr lff">
    <q-drawer
      v-model="drawer"
      show-if-above
      :width="250"
      :breakpoint="600"
      class="bg-white text-white"
    >
      <q-scroll-area class="scroll-area">
        <q-list padding>
          <template v-for="(menuItem, index) in menuList" :key="index">
            <q-item
              v-if="
                menuItem.label != 'Funcionários' ||
                (menuItem.label === 'Funcionários' &&
                  cargoUsuario === 'Administrador')
              "
              clickable
              @click="
                menuAtual = menuItem.label;
                navigateTo(menuItem.path);
              "
              :active="menuItem.label === menuAtual"
              v-ripple
              class="q-pa-lg"
              :class="menuItem.label === 'Sair' ? 'absolute-bottom' : ''"
            >
              <q-item-section avatar>
                <q-icon
                  :name="menuItem.icon"
                  :color="menuItem.label === menuAtual ? 'secondary' : 'dark'"
                />
              </q-item-section>
              <q-item-section
                class="text-weight-medium"
                :class="
                  menuItem.label === menuAtual ? 'text-white-page' : 'text-dark'
                "
              >
                {{ menuItem.label }}
              </q-item-section>
            </q-item>
          </template>
        </q-list>
      </q-scroll-area>

      <div class="absolute-top logo-area column items-center q-pt-md">
        <q-img
          src="../assets/tjam-brasao-grande-1.png"
          alt="Logo TJAM"
          width="80px"
          class="col"
        ></q-img>
        <p class="col text-weight-bold text-secondary text-h6 text-center">
          Sistema de Ponto Eletrônico
        </p>
      </div>
    </q-drawer>

    <q-toolbar class="user-toolbar bg-white q-py-sm q-px-lg">
      <q-btn
        v-if="isDense"
        flat
        @click="drawer = !drawer"
        round
        icon="menu"
        class="absolute-left q-mx-md"
        color="secondary"
      ></q-btn>
      <q-btn flat round>
        <q-icon name="sym_o_notifications" color="dark" />
        <q-badge
          v-if="notificacoes.length != 0"
          floating
          rounded
          color="dark"
        />
      </q-btn>

      <q-avatar size="45px" class="q-mx-sm">
        <q-img
          src="https://cdn.quasar.dev/img/boy-avatar.png"
          alt="Avatar de perfil"
        />
      </q-avatar>

      <div class="user-toolbar-text column items-end justify-center">
        <p class="col text-weight-medium text-secondary text-body2 q-my-sm">
          {{ nomeUsuario }}
        </p>

        <p class="col text-weight-light text-dark text-caption">
          {{ cargoUsuario }}
        </p>
      </div>
    </q-toolbar>
    <q-page-container class="bg-grey-1">
      <router-view
        :isDense="isDense"
        class="window-width"
        :data="dataLocal"
      ></router-view>
    </q-page-container>
  </q-layout>
</template>
<style>
.q-drawer {
  border-right: 0.5px solid #798aa35b;
}
.scroll-area {
  height: calc(100vh - 150px);
  margin-top: 150px;
}
.logo-area {
  height: 150px;
}

.user-toolbar {
  display: flex;
  flex-direction: row;
  justify-content: end;
  align-items: center;
  border-bottom: 0.5px solid #798aa35b;
}
</style>
<script lang="ts">
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'HomeLayout',

  data() {
    return {
      windowWidth: window.innerWidth,
    };
  },

  setup() {
    return {
      isDense: ref(false),
      menuAtual: ref<string>('Dashboard'),
      drawer: ref<boolean>(true),
      nomeUsuario: ref<string>('Daniel Pereira'),
      cargoUsuario: ref<string>('Funcionário'),
      dataLocal: ref<string>(''),
      notificacoes: [],
      menuList: [
        {
          icon: 'dashboard',
          label: 'Dashboard',
          path: '/home/',
        },
        {
          icon: 'event_available',
          label: 'Registrar',
          path: '/home/registrar',
        },
        {
          icon: 'calendar_month',
          label: 'Consultar',
          path: '/home/consultar',
        },
        {
          icon: 'account_circle',
          label: 'Perfil',
          path: '/home/perfil',
        },
        {
          icon: 'group',
          label: 'Funcionários',
          path: '/home/funcionarios',
        },
        {
          icon: 'logout',
          label: 'Sair',
          path: '/home/',
        },
      ],
    };
  },

  methods: {
    navigateTo(path: string) {
      this.$router.push(path);
    },

    verifyMenu(): void {
      const atualPath = this.$route.path;
      const atualItem = this.menuList.find((item) => item.path === atualPath);
      if (atualItem) {
        this.menuAtual = atualItem.label;
      }
    },

    verifyScreen(): void {
      this.isDense = this.windowWidth < 600;
    },

    setData(): void {
      const datatime = new Date();
      const diaSemana = datatime.toLocaleDateString('pt-br', {
        timeZone: 'America/Manaus',
        weekday: 'long',
      });
      const mes = datatime.toLocaleDateString('pt-br', {
        timeZone: 'America/Manaus',
        month: 'long',
      });
      const dia = datatime.toLocaleDateString('pt-br', {
        timeZone: 'America/Manaus',
        day: 'numeric',
      });
      const ano = datatime.toLocaleDateString('pt-br', {
        timeZone: 'America/Manaus',
        year: 'numeric',
      });
      const diaSemanaFormat =
        diaSemana.charAt(0).toUpperCase() + diaSemana.slice(1);
      const mesFormat = mes.charAt(0).toUpperCase() + mes.slice(1);
      this.dataLocal =
        diaSemanaFormat +
        ', ' +
        dia +
        ' de ' +
        mesFormat.replace('.', '') +
        ', ' +
        ano;
    },
  },

  mounted() {
    window.addEventListener('resize', this.verifyScreen);
    this.verifyScreen();
    this.setData();
    this.verifyMenu();
  },

  deactivated() {
    window.removeEventListener('resize', this.verifyScreen);
  },
});
</script>
