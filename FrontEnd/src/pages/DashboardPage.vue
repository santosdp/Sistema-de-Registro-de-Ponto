<template>
  <q-page class="column items-center justify-around">
    <div class="q-pt-md q-px-lg row justify-between full-width">
      <p class="text-weight-medium text-h6 text-secondary">Dashboard</p>
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

    <div class="q-pa-md">
      <q-date
        v-model="dataAtual"
        color="white"
        text-color="black"
        subtitle=""
        readonly
        :title="mesCalendario + ' ' + anoCalendario"
        :events="[dataAtual]"
        event-color="secondary"
      />
    </div>

    <div class="clock-card row items-center justify-around q-pa-lg bg-white">
      <ClockComponent class="q-mr-md"></ClockComponent>

      <div class="column items-center">
        <div
          class="clock-card-content q-px-xl q-py-sm q-my-sm column items-center"
        >
          <p class="text-body1 text-secondary text-weight-light q-my-xs">
            Tempo trabalhando
          </p>
          <p class="text-h6 text-secondary text-weight-bold q-my-xs">
            {{ tempoTrabalhado }}
          </p>
        </div>

        <div
          class="clock-card-content q-px-xl q-py-sm q-my-sm column items-center"
        >
          <p class="text-body1 text-secondary text-weight-light q-my-xs">
            Tempo em intervalo
          </p>
          <p class="text-h6 text-secondary text-weight-bold q-my-xs">
            {{ tempoIntervalo }}
          </p>
        </div>
      </div>
    </div>
  </q-page>
</template>
<style>
.clock-card {
  border: 1px solid #d6e3f2;
  border-radius: 10px;
}

.clock-card-content {
  border: 1px solid #d6e3f2;
  border-radius: 10px;
}
</style>
<script lang="ts">
import CardRegistros from 'src/components/CardRegistros.vue';
import ClockComponent from 'src/components/ClockComponent.vue';
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'DashboardPage',
  props: {
    isDense: Boolean,
    data: String,
  },
  components: {
    CardRegistros,
    ClockComponent,
  },
  setup() {
    return {
      matriculaForm: ref<string>(''),
      registro1: ref<string>('08h00'),
      registro2: ref<string>('12h00'),
      registro3: ref<string>('13h00'),
      registro4: ref<string>('17h00'),
      tempoTrabalhado: ref<string>(''),
      tempoIntervalo: ref<string>(''),
      dataAtual: ref(new Date()),
      mesCalendario: ref<string>(''),
      anoCalendario: ref<string>(''),
    };
  },

  methods: {
    calculaDiferenca(start: string, end: string): number {
      const startHora = Number(start.split('h')[0]);
      const startMinuto = Number(start.split('h')[1]);
      const endHora = Number(end.split('h')[0]);
      const endMinuto = Number(end.split('h')[1]);

      const startDate = new Date(0, 0, 0, startHora, startMinuto);
      const endDate = new Date(0, 0, 0, endHora, endMinuto);

      return (endDate.getTime() - startDate.getTime()) / (1000 * 60);
    },
    updateTime() {
      const trabalhoManha = this.calculaDiferenca(
        this.registro1,
        this.registro2
      );
      const trabalhoTarde = this.calculaDiferenca(
        this.registro3,
        this.registro4
      );
      const intervalo = this.calculaDiferenca(this.registro2, this.registro3);

      this.tempoTrabalhado = `${Math.floor(
        (trabalhoManha + trabalhoTarde) / 60
      )}h ${(trabalhoManha + trabalhoTarde) % 60}min`;
      this.tempoIntervalo = `${Math.floor(intervalo / 60)}h ${
        intervalo % 60
      }min`;
    },
    updateDate() {
      const data = new Date();
      const mesFormat = data.toLocaleString('pt-BR', {
        timeZone: 'America/Manaus',
        month: 'long',
      });
      this.anoCalendario = data.toLocaleString('pt-BR', {
        timeZone: 'America/Manaus',
        year: 'numeric',
      });
      this.mesCalendario =
        mesFormat.charAt(0).toUpperCase() + mesFormat.slice(1);
    },
  },
  mounted() {
    this.updateTime();
    this.updateDate();
  },
});
</script>
