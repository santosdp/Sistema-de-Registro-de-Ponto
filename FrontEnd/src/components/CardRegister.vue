<template>
  <div class="card-content column justify-evenly">
    <div class="card-login-item">
      <p class="text-body2">
        Inscreva-se utilizando seus dados pessoais e de funcionário do TJAM.
      </p>
    </div>

    <div class="card-login-item">
      <q-stepper
        v-model="registerStepperStep"
        ref="stepper"
        contracted
        color="primary"
        animated
      >
        <q-step
          :name="1"
          title="Dados pessoais"
          icon="person"
          :done="registerStepperStep > 1"
          :error="registerStepperStateForm1"
        >
          <q-form ref="registerForm1" class="q-gutter-md">
            <generic-input
              label="Nome"
              :model-value="registerFormName"
              input-type="text"
              icon-prepend="person"
              :is-dense="isDense"
              :rules="[requiredField, NomeValid]"
            ></generic-input>

            <generic-input
              label="Data de Nascimento"
              :model-value="registerFormNascimento"
              input-type="date"
              hint="DD/MM/YYYY"
              :is-dense="isDense"
              :rules="[requiredField, dataValid, maioridadeValid]"
            ></generic-input>
            <q-input
              outlined
              :dense="isDense"
              label="Matricula"
              type="number"
              v-model="registerFormMatricula"
              color="primary"
              :rules="[requiredField, matriculaValid]"
            ></q-input>

            <q-input
              outlined
              :dense="isDense"
              label="E-mail"
              type="email"
              v-model="registerFormEmail"
              color="primary"
              :rules="[requiredField, emailValid]"
            ></q-input>
          </q-form>
        </q-step>

        <q-step
          :name="2"
          title="Contato"
          icon="smartphone"
          :done="registerStepperStep > 2"
          :error="registerStepperStateForm2"
        >
          <q-form ref="registerForm2"> </q-form>
        </q-step>

        <q-step
          :name="3"
          title="Contato"
          icon="security"
          :done="registerStepperStep > 3"
          :error="registerStepperStateForm3"
        >
        </q-step>

        <template v-slot:navigation>
          <q-stepper-navigation class="flex-center">
            <q-btn
              v-if="registerStepperStep > 1"
              @click="backStepper()"
              color="primary"
              flat
              icon="arrow_back"
              class="roundButton"
            ></q-btn>
            <q-btn
              @click="nextStepper()"
              color="primary"
              :icon="registerStepperStep === 3 ? 'check' : 'arrow_forward'"
              class="roundButton"
            ></q-btn>
          </q-stepper-navigation>
        </template>
      </q-stepper>
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
.roundButton {
  border-radius: 50%;
  width: 40px;
  height: 40px;
}
</style>
<script lang="ts">
import { QStepper } from 'quasar';
import { defineComponent, PropType, ref } from 'vue';
import GenericInput from './GenericInput.vue';

export default defineComponent({
  name: 'CardRegister',
  props: {
    buttonSize: String,
    isDense: Boolean,
    method: {
      type: Function as PropType<() => void>,
      required: true,
    },
  },

  components: {
    GenericInput,
  },

  setup() {
    return {
      registerStepperStep: ref<number>(1),
      registerStepperDone1: ref<boolean>(false),
      registerStepperDone2: ref<boolean>(false),
      registerStepperDone3: ref<boolean>(false),
      registerStepperStateForm1: ref<boolean>(false),
      registerStepperStateForm2: ref<boolean>(false),
      registerStepperStateForm3: ref<boolean>(false),

      nameError: ref<boolean>(),
      nascimentoError: ref<boolean>(),
      emailError: ref<boolean>(),
      telefoneError: ref<boolean>(),
      passwordError: ref<boolean>(),

      registerFormName: ref<string>(''),
      registerFormNascimento: ref<string>(''),
      registerFormEmail: ref<number>(),
      registerFormMatricula: ref<string>(),
      registerFormPassword: ref<string>(),
      registerFormTelefone: ref<string>(),
    };
  },
  methods: {
    requiredField(val: string) {
      return !!val || 'Campo obrigatório';
    },
    NomeValid(val: string) {
      return val.length >= 12 || 'O nome deve possuir ao menos 12 caracteres';
    },
    matriculaValid(val: string) {
      return val.length === 12 || 'A matrícula deve possuir 12 caracteres';
    },
    emailValid(val: string) {
      const regex: RegExp = /^[a-zA-Z0-9._%+-]+@tjam\.jus\.br$/;
      return regex.test(val) || 'E-mail deve ser um endereço @tjam.jus.br';
    },
    dataValid(val: string) {
      return (
        this.validaNascimento(val) || 'Data inválida. Use o formato dd/mm/yyyy.'
      );
    },
    maioridadeValid(val: string) {
      return this.validaMaioridade(val) || 'Você deve ter pelo menos 18 anos.';
    },

    validaNascimento(dateString: string): boolean {
      const [year, month, day] = dateString.split('-').map(Number);
      const date = new Date(year, month - 1, day);
      return (
        date.getFullYear() === year &&
        date.getMonth() === month - 1 &&
        date.getDate() === day
      );
    },

    validaMaioridade(dateString: string): boolean {
      if (!this.validaNascimento(dateString)) return false;

      const [year, month, day] = dateString.split('-').map(Number);
      const birthDate = new Date(year, month - 1, day);

      const today = new Date();
      const age = today.getFullYear() - birthDate.getFullYear();

      // Ajusta a idade se a data de aniversário ainda não ocorreu este ano
      const hasHadBirthdayThisYear =
        today.getMonth() > birthDate.getMonth() ||
        (today.getMonth() === birthDate.getMonth() &&
          today.getDate() >= birthDate.getDate());

      return age > 18 || (age === 18 && hasHadBirthdayThisYear);
    },

    checkForm() {
      const registerForms = [
        {
          form: this.$refs.registerForm1 as unknown as {
            validate: () => Promise<boolean>;
          },
          state: 'stateForm1',
        },
        {
          form: this.$refs.registerForm2 as unknown as {
            validate: () => Promise<boolean>;
          },
          state: 'stateForm2',
        },
        {
          form: this.$refs.registerForm3 as unknown as {
            validate: () => Promise<boolean>;
          },
          state: 'stateForm3',
        },
      ];
      const currentStep = this.registerStepperStep;
      const currentForm = registerForms[currentStep - 1];

      if (currentForm && currentForm.form) {
        currentForm.form.validate().then((success: boolean) => {
          if (currentForm.state == 'stateForm1') {
            this.registerStepperStateForm1 = !success;
          } else if (currentForm.state == 'stateForm2') {
            this.registerStepperStateForm2 = !success;
          } else {
            this.registerStepperStateForm3 = !success;
          }
        });
      }
    },
    nextStepper() {
      this.checkForm();
      if (this.registerStepperStep === 1) {
        this.registerStepperDone1 = true;
      } else if (this.registerStepperStep === 2) {
        this.registerStepperDone2 = true;
      } else {
        this.registerStepperDone3 = true;
        this.$props.method();
      }
      (this.$refs.stepper as QStepper).next();
    },
    backStepper() {
      if (this.registerStepperStep === 2) {
        this.registerStepperDone1 = false;
      } else if (this.registerStepperStep === 3) {
        this.registerStepperDone2 = false;
      }
      (this.$refs.stepper as QStepper).previous();
    },
  },
});
</script>
