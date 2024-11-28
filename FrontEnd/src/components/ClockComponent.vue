<template>
  <div class="clock-container column items-center">
    <div class="clock bg-white">
      <div class="hour" :style="hourStyle"></div>
      <div class="minute" :style="minuteStyle"></div>
      <div class="second" :style="secondStyle"></div>
    </div>
    <div class="time-text text-weight-bold text-h6 text-secondary q-mt-md">
      {{ formattedTime }}
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, computed, onMounted, ref } from 'vue';

export default defineComponent({
  name: 'ClockComponent',
  setup() {
    const time = ref(new Date());

    onMounted(() => {
      setInterval(() => {
        time.value = new Date();
      }, 1000);
    });

    const hourStyle = computed(() => {
      const hours = time.value.getHours() % 12;
      const minutes = time.value.getMinutes();
      const degrees = hours * 30 + minutes * 0.5;
      return { transform: `rotate(${degrees}deg)` };
    });

    const minuteStyle = computed(() => {
      const minutes = time.value.getMinutes();
      const seconds = time.value.getSeconds();
      const degrees = minutes * 6 + seconds * 0.1;
      return { transform: `rotate(${degrees}deg)` };
    });

    const secondStyle = computed(() => {
      const seconds = time.value.getSeconds();
      const degrees = seconds * 6;
      return { transform: `rotate(${degrees}deg)` };
    });

    const formattedTime = computed(() => {
      const hours = time.value.toLocaleTimeString('pt-br', { hour: 'numeric' });
      const minutes = time.value.toLocaleTimeString('pt-br', {
        minute: 'numeric',
      });
      return hours + 'h' + minutes;
    });

    return { hourStyle, minuteStyle, secondStyle, formattedTime };
  },
});
</script>

<style scoped>
.clock {
  width: 200px;
  height: 200px;
  border: 10px solid #e0e4f1;
  border-radius: 50%;
  position: relative;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transform: rotate(-90deg);
}

.clock::after {
  content: '';
  width: 10px;
  height: 10px;
  background-color: #000;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border-radius: 50%;
}

.hour,
.minute,
.second {
  position: absolute;
  background-color: #000;
  height: 2px;
  top: 50%;
  left: 50%;
  transform-origin: 0% 50%;
  transform: rotate(0deg);
  transition: transform 0.05s linear;
}

.hour {
  width: 35%;
  background-color: #122539;
}

.minute {
  width: 45%;
  background-color: #83acd8;
}

.second {
  width: 50%;
  background-color: #dc2d7c;
}
</style>
