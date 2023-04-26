import { ref } from 'vue'
import { defineStore } from 'pinia'
import { api } from 'boot/axios'

//Funções para tratamento e verificação de token de usuário
export const useAuth = defineStore('auth', () => {
  const token = ref(localStorage.getItem('token'))
  const user = ref(JSON.parse(localStorage.getItem('user')))
  
  function setToken(tokenValue){
    localStorage.setItem('token', tokenValue)
    token.value = tokenValue
  }
  function setUser(userValue){
    localStorage.setItem('user', JSON.stringify(userValue))
    user.value = userValue
  }
  //Função essencial, esta faz a validação do token do usuário
  async function checkToken(){
    try {
      const tokenAuth = 'Bearer ' + token.value
      const { data } = await api.get('/auth/verify', {
        headers: {
          Authorization: tokenAuth
        }
      })
      return data
    } catch (ex) {
      console.log("Erro: " + ex)
    }
  }
  //Função de remover token
  function clear(){
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    token.value = ''
    token.value = ''
  }

  return {token, user, setToken, setUser, checkToken, clear}
})
