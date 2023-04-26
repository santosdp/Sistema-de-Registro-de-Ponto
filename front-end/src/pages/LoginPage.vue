<!-- Layout da Pagina de Login -->
<template>
  <div>
    <!-- Definição do tipo de pagina -->
    <q-layout view="lHh lpr lFf" container style="height: 100vh" class="shadow-2 no-border">
      <!-- Propriamente o conteudo da página -->
      <q-page-container>
        <q-page class="bg-grey-4">
          <!-- Cabeçalho da página -->
          <q-header>
            <q-toolbar class="bg-grey-8 q-px-lg text-white">
              <q-toolbar-title><strong>TJAM</strong> | DIVISÃO DE TECNOLOGIA E COMUNICAÇÃO</q-toolbar-title>
              <div>
                <a href="http://www.facebook.com/TribunaldeJusticadoAmazonas" style="color: white; text-decoration: none;"><q-icon name = "ti-facebook" size="16px" class="q-pr-sm"/></a>
                <a href="http://www.twitter.com/tjamazonas" style="color: white; text-decoration: none;"><q-icon name = "ti-twitter-alt" size="16px" class="q-pr-sm" /></a>
                <a href="http://www.youtube.com/user/tjamazonas1?feature=watch" style="color: white; text-decoration: none;"><q-icon name = "ti-youtube" size="16px" class="q-pr-sm" /></a>
                <a href="http://www.flickr.com/photos/tribunaldejusticadoamazonas" style="color: white; text-decoration: none;"><q-icon name = "ti-flickr-alt" size="16px" /></a>
              </div>
            </q-toolbar>
          </q-header>
          <!-- Sessão de pagina com Logo do TJAM-->
          <q-toolbar style="height:auto;">
            <q-img src="../assets/tjam.png" alt="Erro ao carregar" class="q-gutter-md" style="height: 120px; width: 120px;"></q-img>
            <p class="text-h4 text-weight-light q-mt-md">INTRANET</p>
          </q-toolbar>
          <!--Definição de imagem de fundo -->
          <div align="center" class="q-py-md" style="height: auto; width: auto; background-image: url(https://images.unsplash.com/photo-1584556326561-c8746083993b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80);">
            <!-- Sessão que está presente o formulário de login de funcionário -->
            <div class="column items-center text-center shadow-8 rounded-border bg-white q-pa-lg" style="width: 90vw; min-width: 300px; max-width: 400px; height: auto; border-radius: 20px;">
              <div>
                <p class="text-body1 text-weight-bold col">INTRANET - Tribunal de Justiça do Estado do Amazonas</p>
                <p class="text-body2 col">Bem-vindo ao sistema de acesso do TJAM, para entrar na Intranet primeira você deve ser identificar como funcionário.</p>
                <p class="text-h4 col">Acesso Restrito</p>
                <p class="text-body2 col">Utilize seu nome de usuário e senha do TJAM</p>
              </div>
              <div>
                <q-form @submit.prevent="onSubmit" class="text-center col">
                  <!-- Entrada de Login, onde possui restrições de obrigatoriedade e tamanho máximo -->
                  <q-input
                    outlined
                    v-model = "form.login"
                    color="grey-7"
                    label="Login"
                    style="width: 25vw; min-width: 250px;"
                    lazy-rules
                    :rules="[
                      val => val && val.length > 0 || 'Campo obrigatório',
                      val => val.length <= 16 || 'O login possui no máximo 16 caracteres'
                    ]"
                  >
                    <template v-slot:prepend>
                      <q-icon name="person" />
                    </template>
                  </q-input>
                  <!-- Entrada de Senha, onde possui restrições de obrigatoriedade e tamanho máximo -->
                  <q-input
                    outlined
                    v-model = "form.senha"
                    color="grey-7"
                    label="Senha"
                    style="width: 25vw; min-width: 250px;"
                    filled :type="isPwd ? 'password' : 'text'"
                    lazy-rules
                    :rules="[
                      val => val && val.length > 0 || 'Campo obrigatório',
                      val => val.length <= 16 && val.length >=8 || 'A senha possuir entre 8 à 16 caracteres'
                    ]"
                  >
                    <template v-slot:append>
                      <q-icon
                        :name="isPwd ? 'visibility_off' : 'visibility'"
                        class="cursor-pointer"
                        @click="isPwd = !isPwd"
                      />
                    </template>
                  </q-input>
                  <!-- Definição de opção para marcar se quer continuar logado ou não -->
                  <div align="left">
                    <q-checkbox v-model="lembrar" label="Manter login"/>
                  </div>
                  <!--Definição do botão de Login para entrar no sistema-->
                  <q-btn label="Entrar" type="submit" color="primary" align="center"/>
                </q-form>
              </div>
              <!-- Espaço para colocar o link para o usuário ir para a página de recuperar senha, porém ainda não possui neste projeto -->
              <p class="text-body2 col text-primary q-mt-sm"><a href="/recuperar">Esqueceu sua senha?</a></p>
              <!-- Criação de link para o usuário ir para a página de Cadastro -->
              <p class="text-body2 col text-primary"><a href="/cadastro">Criar conta de funcionário</a></p>
            </div>
          </div>
          <!-- Rodapé da página -->
          <q-footer>
            <q-toolbar class="bg-grey-8">
              <q-toolbar-title class="text-body2 text-center q-py-lg text-white">Tribunal de Justiça do Estado do Amazonas - Divisão de Tecnologia da informação e Comunicação (DVTIC) - Setor de Desenvolvimento de Sistemas (SCS) - Todos os Direitos Reservados</q-toolbar-title>
            </q-toolbar>
          </q-footer>
        </q-page>
      </q-page-container>
    </q-layout>
  </div>
</template>

<script>
  import { ref } from 'vue'
  import { api } from 'boot/axios'
  import { useAuth } from 'stores/auth.js'

  export default {
    name: 'LoginPage',
    data () {
      return {
        //Variaveis do formulário
        form: {
          login: '',
          senha: ''
        }
      }
    },
    setup () {
      return {
        //Variavel que faz o controle da visibilidade da senha para o usuário
        isPwd: ref(true),
        //Variavel que faz o controle da opção de manter o usuário logado
        lembrar: ref(false)
      }
    },
    methods: {
      //Aqui nesta função é feito o envio dos dados no usuário para o servidor validar o login do usuário
      //Caso o login esteja cadastrado no servidor e a senha seja igual à cadastrada no servidor, caso batam esta resposta vai acionar uma notificação de que o login foi realizado e redirecionar para a página Home
      //Caso contrário vai ser acionado uma notificação informando que as credenciais são inválidas
      //Caso ocorra algum erro na comunição entre o servidor, será acionada uma notificação informando e para tentar novamente
      onSubmit(){
        const auth = useAuth()
        api.post('/auth', { login: this.form.login, senha: this.form.senha, lembrar: this.lembrar})
        .then( response => {
          if(response.data.msg){
            console.log(response.data)
            auth.setToken(response.data.token)
            auth.setUser(response.data.user)
            this.$q.notify({
              message: 'Login realizado!',
              color: 'positive',
              icon: 'check_circle_outline'
            })
            this.$router.push('/home')
          }
          else{
            this.$q.notify({
              message: 'Credenciais inválidas',
              color: 'negative',
              icon: 'ti-close'
            })
          }
        })
        .catch((ex) => {
          console.log(ex)
            this.$q.notify({
              message: 'Erro no sistema, tente novamente depois!',
              color: 'warning',
              icon: 'ti-info-alt'
            })
        })
      }
    }
  }
</script>
<style scoped>
  a{
    text-decoration: none;
    color: #427bd2;
  }
</style>
