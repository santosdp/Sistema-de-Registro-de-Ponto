<template>
  <div class="card-content column justify-evenly">
    <div class="card-login-item">
      <p class="text-body2">
        Bem-vindo ao sistema de acesso do TJAM, para entrar na Intranet primeira
        você deve se identificar como funcionário.
      </p>
    </div>

    <div class="card-login-item">
      <q-form @submit.prevent="onSubmit">
        <q-input
          outlined
          :dense="isDense"
          label="E-mail"
          type="email"
          v-model="emailInput"
          color="primary"
          :rules="[requiredField, emailValid]"
        >
          <template v-slot:prepend>
            <q-icon name="email" color="dark"></q-icon>
          </template>
        </q-input>

        <q-input
          outlined
          :dense="isDense"
          label="Senha"
          :type="passwordVisible ? 'text' : 'password'"
          v-model="passwordInput"
          color="primary"
          :rules="[requiredField]"
        >
          <template v-slot:prepend>
            <q-icon
              name="key"
              style="transform: rotate(135deg)"
              color="dark"
            ></q-icon>
          </template>
          <template v-slot:append>
            <q-icon
              :name="passwordVisible ? 'visibility' : 'visibility_off'"
              class="cursor-pointer"
              @click="passwordVisible = !passwordVisible"
              color="dark"
            ></q-icon>
          </template>
        </q-input>

        <div class="row items-center justify-between">
          <q-checkbox
            v-model="rememberCheckbox"
            label="Lembrar-se"
            size="xs"
          ></q-checkbox>

          <p class="q-pt-md"><a href="">Esqueceu sua senha?</a></p>
        </div>

        <q-btn
          label="Login"
          type="submit"
          class="full-width text-weight-bold"
          :size="buttonSize"
          color="primary"
        ></q-btn>
      </q-form>
    </div>
  </div>
</template>

<style scoped>
.card-content {
  padding: 20px;
}
.card-login-item {
  max-width: 400px;
}
.q-input {
  border-radius: 8px;
}

.q-btn {
  margin-top: 10px;
  margin-bottom: 10px;
  height: 40px;
  color: #f1f1f1;
  border-radius: 8px;
}
</style>

<script lang="ts">
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'CardLogin',

  props: {
    buttonSize: String,
    isDense: Boolean,
  },

  data() {
    return {
      windowWidth: window.innerWidth,
    };
  },

  setup() {
    const emailInput = ref<string>('');
    const passwordInput = ref<string>('');
    const rememberCheckbox = ref<boolean>(false);
    const passwordVisible = ref<boolean>(false);

    return {
      emailInput,
      passwordInput,
      rememberCheckbox,
      passwordVisible,
    };
  },

  methods: {
    onSubmit(): void {
      this.$router.push('home');
    },

    requiredField(val: string) {
      return !!val || 'Campo obrigatório';
    },

    emailValid(val: string) {
      const regex: RegExp = /^[a-zA-Z0-9._%+-]+@tjam\.jus\.br$/;
      return regex.test(val) || 'E-mail deve ser um endereço @tjam.jus.br';
    },
  },
});
</script>
