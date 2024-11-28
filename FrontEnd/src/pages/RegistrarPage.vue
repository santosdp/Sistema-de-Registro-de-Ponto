<template>
  <q-page class="column items-center justify-start">
    <div class="q-pt-md q-px-lg row justify-between full-width">
      <p class="text-weight-medium text-h6 text-secondary">Registrar</p>
      <p class="text-weight-light text-subtitle1 text-secondary">
        {{ data }}
      </p>
    </div>

    <div class="full-width">
      <p
        class="text-weight-medium text-body1 text-secondary q-mb-none text-center q-pb-sm"
      >
        Marcações de Hoje
      </p>
      <card-registros
        :registros="[registro1, registro2, registro3, registro4]"
        :is-dense="isDense"
      ></card-registros>
    </div>

    <q-form @submit.prevent="onSubmit" class="q-pa-lg q-my-md register-form">
      <p class="text-weight-bold text-h6 text-secondary text-center">
        Registrar Ponto
      </p>

      <p class="text-subtitle text-dark">
        Confirme sua matricula para realizar a marcação de ponto.
      </p>

      <generic-input
        label="Matricula"
        :model-value="matriculaForm"
        input-type="number"
        icon-prepend="badge"
        :is-dense="isDense"
        :rules="[requiredField, matriculaValid]"
      ></generic-input>

      <q-btn
        label="Enviar"
        type="submit"
        class="full-width text-weight-bold"
        color="secondary"
      ></q-btn>
    </q-form>
  </q-page>
</template>
<style>
.register-form {
  width: 75%;
  max-width: 400px;
  border: 1px solid black;
  border-radius: 8px;
}
</style>
<script lang="ts">
import CardRegistros from 'src/components/CardRegistros.vue';
import GenericInput from 'src/components/GenericInput.vue';
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'RegistrarPage',
  props: {
    isDense: Boolean,
    data: String,
  },
  components: {
    GenericInput,
    CardRegistros,
  },
  setup() {
    return {
      matriculaForm: ref<string>(''),
      horariosReferencia: ['08:00', '12:00', '13:00', '17:00'],
      registro1: ref<string>('08h00'),
      registro2: ref<string>('12h00'),
      registro3: ref<string>('13h00'),
      registro4: ref<string>('17h00'),
      registros: ['08h00', '12h00', '13h00', '17h00'],
    };
  },
  methods: {
    onSubmit() {},
    requiredField(val: string) {
      return !!val || 'Campo obrigatório';
    },
    matriculaValid(val: string) {
      return val.length === 12 || 'A matrícula deve possuir 12 caracteres';
    },
  },
});
</script>
