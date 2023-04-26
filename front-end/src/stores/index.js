import { store } from 'quasar/wrappers'
import { createPinia } from 'pinia'

//Inicialização do Pinia, necessário no tratamento do token de usuário
export default store((/* { ssrContext } */) => {
  const pinia = createPinia()

  return pinia
})
