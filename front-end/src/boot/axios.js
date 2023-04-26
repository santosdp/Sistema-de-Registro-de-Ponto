import { boot } from 'quasar/wrappers'
import axios from 'axios'

// Especifica��es sobre a rota para comunica��o com o Back-End que utiliza a porta 3000
const api = axios.create({
  baseURL: 'http://localhost:3000',
  headers: {
    'Content-type' : 'application/json'
  }
})

export default boot(({ app }) => {

  app.config.globalProperties.$axios = axios
  app.config.globalProperties.$api = api

})

export { api }
