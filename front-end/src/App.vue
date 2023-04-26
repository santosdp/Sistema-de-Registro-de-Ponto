<template>
  <!-- Aqui é feito o roteamento da página, para que sejam executadas as páginas do sistema-->
  <router-view />
</template>

<script>
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'App'
})
</script>
