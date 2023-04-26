<!-- Layout da Pagina de Cadastro-->
<template>
  <div>
    <!-- Definição do tipo de pagina (cabeçalho, conteudo e rodapé) -->
    <q-layout view="lHh lpr lFf" container style="height: 100vh;" class="shadow-2 no-border">
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
            </q-toolbar >
          </q-header>
          <!-- Sessão de pagina com Logo do TJAM-->
          <q-toolbar style="height:auto;">
            <q-img src="../assets/tjam.png" alt="Erro ao carregar" class="q-gutter-md" style="height: 120px; width: 120px;"></q-img>
            <p class="text-h4 text-weight-light q-mt-md">INTRANET</p>
          </q-toolbar>
          <div align="center" class="q-py-md" style="height: auto; background-image: url(https://images.unsplash.com/photo-1584556326561-c8746083993b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1332&q=80);">
            <!-- Sessão que está presente o formulário de cadastro de funcionário -->
            <div class="column items-center text-center shadow-8 rounded-border bg-white q-pa-lg" style="width: 90vw;min-width: 300px; max-width: 400px; height: auto; border-radius: 20px;">
              <div>
                <p class="text-body1 text-weight-bold col">INTRANET - Tribunal de Justiça do Estado do Amazonas</p>
                <p class="text-body2 col">Bem-vindo ao sistema de acesso do TJAM, por aqui você pode redefinir sua senha de funcionário caso tenha esquecido.</p>
                <p class="text-h4 col">Redefinição de senha</p>
                <p class="text-body2 col">Utilize seu login de usuário e matricula de funcionário do TJAM</p>
              </div>
              <div>
                <q-form @submit="onSubmit" class="col text-center">
                  <!-- Entrada de Nome, onde possui restrições de obrigatoriedade e tamanho máximo -->
                  <q-input
                  outlined
                  v-model = "form.login"
                  color="grey-7"
                  label="Login"
                  style="width: 25vw; min-width: 250px;"
                  class="q-mb-md"
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
                  <!-- Entrada de Matricula, onde possui restrições de obrigatoriedade e tamanho máximo -->
                  <q-input
                    outlined
                    v-model = "form.matricula"
                    color="grey-7"
                    label="Matricula"
                    style="width: 25vw; min-width: 250px;"
                    class="q-mb-md"
                    lazy-rules
                    :rules="[
                      val => val && val.length > 0 || 'Campo obrigatório',
                      val => val.length == 12 || 'A matricula deve possuir 12 caracteres'
                    ]"
                  >
                    <template v-slot:prepend>
                      <q-icon name="person" />
                    </template>
                  </q-input>
                  <q-input
                    outlined
                    v-model = "form.senha1"
                    color="grey-7"
                    label="Nova senha"
                    class="q-mb-md"
                    style="width: 25vw; min-width: 250px;"
                    filled :type="isPwd1 ? 'password' : 'text'"
                    lazy-rules
                    :rules="[
                      val => val && val.length > 0 || 'Campo obrigatório',
                      val => val.length <= 16 && val.length >=8 || 'A senha deve possuir entre 8 à 16 caracteres'
                    ]"
                  >
                    <template v-slot:append>
                      <q-icon
                        :name="isPwd1 ? 'visibility_off' : 'visibility'"
                        class="cursor-pointer"
                        @click="isPwd1 = !isPwd1"
                      />
                    </template>
                  </q-input>
                  <q-input
                    outlined
                    v-model = "form.senha2"
                    color="grey-7"
                    label="Digite novamente"
                    style="width: 25vw; min-width: 250px;"
                    class="q-mb-md"
                    filled :type="isPwd2 ? 'password' : 'text'"
                    lazy-rules
                    :rules="[
                      val => val && val.length > 0 || 'Campo obrigatório',
                      val => val.length <= 16 && val.length >=8 || 'A senha deve possuir entre 8 à 16 caracteres'
                    ]"
                  >
                    <template v-slot:append>
                      <q-icon
                        :name="isPwd2 ? 'visibility_off' : 'visibility'"
                        class="cursor-pointer"
                        @click="isPwd2 = !isPwd2"
                      />
                    </template>
                  </q-input>
                  <p v-if="form.senha1 != '' && form.senha2 != '' && form.senha1 != form.senha2" class="text-body2 text-red-14 q-mb-lg" align="left">Senhas diferentes</p>
                  <!--Definição do botão de Cadastrar usuário-->
                  <q-btn label="Redefinir" type="submit" color="primary" align="center"/>
                </q-form>
              </div>
              <p class="text-body2 col text-primary q-mt-sm"><a href="/login">Voltar</a></p>
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

  export default {
    name: 'RecuperarPagina',
    data () {
      return {
        //Variaveis do formulário
        form: {
          login: '',
          matricula: '',
          senha1: '',
          senha2: ''
        }
      }
    },
    setup () {
      return {
        //Variavel que faz o controle da visibilidade da senha para o usuário
        isPwd1: ref(true),
        isPwd2: ref(true)
      }
    },
    methods: {
      //Aqui nesta função é feito o envio dos dados no usuário para o servidor avaliar para criar o usuário
      //Caso o login e matricula não estejam cadastrados em outra conta o servidor vai cadastrar e confirmar, esta resposta vai acionar uma notificação de que o usuário foi cadastrado e redirecionar para a página de login
      //Caso contrário vai ser acionado uma notificação informando para verificar essas informações
      //Caso ocorra algum erro na comunição entre o servidor, será acionada uma notificação informando e para tentar novamente
      onSubmit(){
        api.post('/redefine', this.form)
        .then( response => {
          if(response.data.msg){
            this.$q.notify({
              message: 'Senha redefinida com sucesso!',
              color: 'positive',
              icon: 'check_circle_outline'
            })
            this.$router.push('/')
          }
          else{
            this.$q.notify({
              message: 'Senha não pode ser redefinida, verifique seus dados e tente novamente!',
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
