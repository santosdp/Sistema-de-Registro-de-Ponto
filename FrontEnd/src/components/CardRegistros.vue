<template>
  <div class="row justify-center">
    <template v-for="(cardItem, index) in cardsRegistro" :key="index">
      <q-card
        class="card-info q-mx-md q-my-xs"
        :class="registros[index] ? cardItem.color : 'bg-grey-4'"
      >
        <q-card-section>
          <p
            class="text-grey-9 text-h6 text-weight-medium text-center q-my-none"
          >
            {{ cardItem.title }}
          </p>
        </q-card-section>

        <q-card-section>
          <p
            class="text-h4 text-weight-bold text-center"
            :class="registros[index] ? cardItem.textColor : 'text-grey-6'"
          >
            {{ registros[index] != '' ? registros[index] : '' }}
          </p>
        </q-card-section>
      </q-card>
    </template>
  </div>
</template>

<style scoped>
.card-info {
  width: 175px;
  height: 175px;
  border: 1px solid #798aa3;
  border-radius: 10px;
}
@media screen and (max-width: 600px) {
  .card-info {
    width: 130px;
    height: 130px;
  }
  .q-card-section {
    margin: 0px 10px;
  }
}
</style>
<script lang="ts">
import { defineComponent, PropType } from 'vue';

export default defineComponent({
  name: 'CardRegistros',
  props: {
    isDense: Boolean,
    registros: {
      type: Array as PropType<string[]>,
      required: true,
    },
  },
  setup() {
    return {
      horariosReferencia: ['08:00', '12:00', '13:00', '17:00'],
      cardsRegistro: [
        {
          title: 'Entrada',
          color: 'bg-light-blue-4',
          textColor: 'text-blue-10',
        },
        {
          title: 'Intervalo',
          color: 'bg-amber-4',
          textColor: 'text-deep-orange-10',
        },
        {
          title: 'Retorno',
          color: 'bg-indigo-4',
          textColor: 'text-deep-purple-10',
        },
        {
          title: 'Saída',
          color: 'bg-light-green-4',
          textColor: 'text-teal-10',
        },
      ],
    };
  },
});
</script>
