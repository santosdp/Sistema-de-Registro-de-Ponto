<template>
  <q-layout view="lHh lpr lFf">
    <q-header class="q-my-auto shadow-3">
      <q-toolbar class="bg-grey-7">
        <q-avatar size="64px">
          <q-icon name="img:src/assets/tjam-brasao-grande-1.png" size="64px">
          </q-icon>
        </q-avatar>
        <q-toolbar-title
          ><strong>TJAM</strong> | DIVISÃO DE TECNOLOGIA E
          COMUNICAÇÃO</q-toolbar-title
        >
        <div class="row justify-evenly header-icons">
          <a href="http://www.facebook.com/TribunaldeJusticadoAmazonas"
            ><q-icon name="ti-facebook" size="16px" color="white"
          /></a>
          <a href="https://www.instagram.com/tjamazonas/"
            ><q-icon name="ti-instagram" size="16px" color="white"
          /></a>
          <a href="http://www.youtube.com/user/tjamazonas1?feature=watch"
            ><q-icon name="ti-youtube" size="16px" color="white"
          /></a>
        </div>
      </q-toolbar>
    </q-header>
    <q-page-container id="flex-container">
      <div id="background-image" class="background-image shadow-3"></div>

      <div id="card-container" class="card-container">
        <p class="text-weight-bolder text-h4">
          Bem-vindo ao
          <span style="color: #367aff">Sistema de Ponto Eletrônico TJAM</span>
        </p>

        <q-btn-toggle
          v-model="toggleButton"
          size="lg"
          toggle-color="primary"
          text-color="grey"
          :dense="isDense"
          flat
          :options="[
            { label: 'Entrar', value: 1 },
            { label: 'Cadastrar', value: 2 },
          ]"
        />
        <card-login
          v-if="toggleButton === 1"
          :buttonSize="buttonSize"
          :isDense="isDense"
          :method="changeView"
        ></card-login>
        <card-register
          v-if="toggleButton === 2"
          :buttonSize="buttonSize"
          :isDense="isDense"
          :method="changeView"
        ></card-register>
      </div>
    </q-page-container>
  </q-layout>
</template>

<style>
.q-header {
  .header-icons {
    width: 10vw;
    max-width: 200px;
  }
}

.q-page-container {
  display: flex;
  justify-content: space-around;
  flex-direction: row;
  background-color: #ffffff;
}

.background-image {
  background-image: url(../assets/01_Fachada_TJAM.jpg);
  background-size: cover;
  background-position: center;
  width: 65vw;
  height: auto;
  border-radius: 15px;
  margin: 10px;
  position: relative;
}

.card-container {
  background-color: #ffffff;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: start;
  height: auto;
  width: 35vw;
  padding-top: 20px;
  padding-left: 20px;
  position: relative;
}
</style>

<script lang="ts">
import CardLogin from 'src/components/CardLogin.vue';
import CardRegister from 'src/components/CardRegister.vue';
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'LoginLayout',

  components: {
    CardLogin,
    CardRegister,
  },

  data() {
    return {
      windowWidth: window.innerWidth,
      viewController: 1,
    };
  },

  setup() {
    return {
      emailInput: ref(''),
      passwordInput: ref(''),
      rememberCheckbox: ref(false),
      passwordVisible: ref(false),
      buttonSize: ref('md'),
      isDense: ref(false),
      toggleButton: ref<number>(1),
    };
  },

  methods: {
    onSubmit(): void {
      this.$router.push('login');
    },

    handleWindowsSizeChange(): void {
      this.windowWidth = window.innerWidth;
    },

    changeView(): void {
      if (this.toggleButton === 2) {
        this.toggleButton = 1;
      } else {
        this.toggleButton = 2;
      }
    },
    invertOrder(): void {
      const flexContainer = document.getElementById('flex-container');

      if (flexContainer) {
        const child1 = flexContainer.childNodes[0] as HTMLElement;
        const child2 = flexContainer.childNodes[1] as HTMLElement;

        if (child1 && child2) {
          this.toggleButton === 1
            ? (child1.style.opacity = '0')
            : (child2.style.opacity = '0');
          flexContainer.removeChild(child2);
          flexContainer.insertBefore(child2, child1);

          setTimeout(() => {
            this.toggleButton === 1
              ? (child1.style.opacity = '1')
              : (child2.style.opacity = '1');
          }, 250);
        }
      }
    },
  },

  watch: {
    windowWidth(newValue) {
      if (newValue < 925) {
        this.isDense = true;
        this.buttonSize = 'sm';
      } else {
        this.isDense = false;
        this.buttonSize = 'md';
      }
    },
    toggleButton(oldValue, newValue) {
      if (
        (oldValue === 1 && newValue === 2) ||
        (oldValue === 2 && newValue === 1)
      ) {
        this.invertOrder();
      }
    },
  },

  mounted() {
    window.addEventListener('resize', this.handleWindowsSizeChange);
  },

  deactivated() {
    window.removeEventListener('resize', this.handleWindowsSizeChange);
  },
});
</script>
