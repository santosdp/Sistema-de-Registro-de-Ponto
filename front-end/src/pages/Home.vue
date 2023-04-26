<!-- Layout da Pagina Home-->
<template>
  <div class="q-pa-md no-padding">
    <!-- Definição do tipo de pagina (cabeçalho, conteudo e rodapé) -->
    <q-layout view="hHh Lpr lff" container style="height: 100vh" class="shadow-2 no-border bg-grey-4">


      <q-page-container>

        <q-page>
          <!-- Cabeçalho da página -->
          <q-toolbar class="bg-blue-9 q-py-xs">
            <q-img id="logoTJAM" class="q-gutter-sm" src="../assets/tjam.png" alt="Erro ao carregar" style="width: 100px; height: 100px;"></q-img>
            <q-toolbar-title id="tituloTJAM" class="text-weight-medium text-space-medium text-white q-gutter-sm"><span style="font-size: 15px;">Poder Judiciário</span><br><span style="font-size: 30px;">Tribunal de Justiça</span><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size: 15px;">do Estado do Amazonas</span></q-toolbar-title>
            <p id="textoTJAM" class="text-h2 text-bold q-gutter-sm text-white q-my-sm" style="font-family:'Orbitron', sans-serif;">Ponto Eletrônico</p>
          </q-toolbar>
          <!-- Propriamente o conteudo da página onde possui o Nome e Matricula do usuário -->
          <div class="text-body1 text-weight-medium bg-white q-pa-lg" style="height: 85px; line-height: 10px;">
            <p>Matricula: {{ dados.matricula }}</p>
            <p>Nome: {{ dados.nome }}</p>
          </div>
          <!-- Definição do conteudo "dinâmico" da página com um Tab que fará o controle do conteudo da página-->
          <div>
            <q-card class="no-box-shadow">
              <!-- Definição do Menu superior que faz o controle do conteúdo da página-->
              <q-tabs v-model="tab" indicator-color="primary" mobile-arrows class="text-teal bg-white no-border" style="height: auto;">
                <q-route-tab name="nav-home" icon="ti-home" class="text-primary" label="Home" to="/home"/>
                <q-route-tab name="nav-historico" icon="ti-search" class="text-primary" label="Histórico" to="/historico"/>
                <q-route-tab name="nav-justificar" icon="ti-clipboard" class="text-primary" label="Justificar" to="/justificar"/>
                <q-route-tab name="nav-sair" icon="ti-close" class="text-primary" label="Sair" @click="logout"/>
              </q-tabs>

              <!--Conteudo de cada sessão do menu superior-->
              <q-tab-panels v-model="tab" align="center" animated transition-prev="jump-right" transition-next="jump-left" style=" height: auto;">
                <!--Página de Registro de Ponto-->
                <q-tab-panel name="nav-home" class="bg-grey-4">
                  <div class="column items-center text-center shadow-8 rounded-border bg-white q-pa-md" style="width: 90vw; min-width: 280px; max-width: 900px; height: auto; border-radius: 20px;">
                    <div>
                      <p class="text-h4 col">Registro de Ponto</p>
                      <p class="text-body2 col">Confirme seu login e confirme para realizar marcação de ponto</p>
                    </div>
                    <div>
                      <!-- Sessão que está presente o formulário de registro de ponto -->
                      <q-form @submit="onSubmitPonto" class="text-center col">
                        <!-- Entrada de Login necessário para o registro de ponto -->
                        <q-input
                        outlined
                        v-model = "dados.loginconf"
                        color="grey-7"
                        label="Login"
                        style="width: 25vw; min-width: 250px;"
                        >
                          <template v-slot:prepend>
                              <q-icon name="ti-id-badge" />
                          </template>
                        <!--Definição do botão para Registrar ponto-->
                        </q-input>
                        <div class="q-mt-lg">
                          <q-btn label="Enviar" type="submit" color="primary" class="float-center"/>
                        </div>
                      </q-form>
                    </div>
                    <!-- Sessão que esta presente as marcações do dia atual-->
                    <p class="text-h5 col q-my-lg">Marcações de Hoje</p>
                    <!-- Tabela com o registro de pontos do dia atual, caso não tenha nenhum registro ainda será acionado uma notificação de falta de ponto registrado para hoje-->
                    <q-table
                    :rows="postHome"
                    :columns="columns"
                    row-key="name"
                    class="q-mt-md no-shadow"
                    style="width: 60vw; min-width: 250px;"
                    />
                  </div>
                </q-tab-panel>
                <!--Página de acessar o historico de algum funcionário-->
                <q-tab-panel name="nav-historico" class="bg-grey-4" padding >
                  <div class="column items-center text-center shadow-8 rounded-border bg-white q-pa-md" style="width: 90vw; min-width: 280px; max-width: 900px; height: auto; border-radius: 20px;">
                    <div>
                      <p class="text-h4 col">Consulta de histórico de marcação</p>
                      <p class="text-body2 col">Digite seu login, senha e o periodo em deseja acessar o histórico de marcações</p>
                    </div>
                    <div>
                      <!-- Sessão que está presente o formulário de pesquisa de histórico de algum funcionário -->
                      <q-form @submit="onSubmitHistorico" class="text-center col">
                        <!-- Entrada do Login do usuário que se deseja obter o histórico, esta entrada possui restrições de campo obrigatório e tamanho máximo-->
                        <q-input
                          outlined
                          v-model = "dados.loginhistorico"
                          color="grey-7"
                          label="Login"
                          style="width: 30vw; min-width: 250px;"
                          lazy-rules
                          :rules="[
                          val => val && val.length > 0 || 'Campo obrigatório',
                          val => val.length <= 16 || 'O login possui no máximo 16 caracteres'
                          ]"
                        >
                          <template v-slot:prepend>
                            <q-icon name="ti-id-badge" />
                          </template>
                          <!-- Entrada da primeira Data que se deseja procurar -->
                        </q-input>
                        <div class="row">
                          <q-input
                          outlined
                          v-model = "dados.data1"
                          color="grey-7"
                          label="Data"
                          class="col q-mr-md"
                          style="width: 9vw; min-width: 117px;"
                          filled type="date"
                          />
                          <!-- Entrada da segunda Data que se deseja procurar -->
                          <q-input
                          outlined
                          v-model = "dados.data2"
                          color="grey-7"
                          label="Data"
                          class="col"
                          style="width: 9vw; min-width: 117px;"
                          filled type="date"
                          />
                        </div>
                        <!--Definição do botão para Procurar Registro -->
                        <div class="q-my-md">
                          <q-btn label="Consultar" type="submit" color="primary" class="float-center"/>
                        </div>
                      </q-form>
                    </div>

                    <!-- Sessão que esta presente as marcações do para os dias selecionados-->
                    <p class="text-h5 col q-my-md">Histórico</p>
                    <!-- Tabela com o registro de pontos dos dias selecionados, caso não seja achado algum registro para esse intervalo de datas será acionada uma notificação informando isso-->
                    <q-table
                    :rows="dados.postHistorico"
                    :columns="columns"
                    row-key="name"
                    class="no-shadow"
                    style="width: 60vw; min-width: 250px;"
                    />
                  </div>
                </q-tab-panel>
                <q-tab-panel name="nav-justificar" class="bg-grey-4">
                  <div class="column items-center text-center shadow-8 rounded-border bg-white q-pa-md" style="width: 90vw; min-width: 280px; max-width: 500px; height: auto; border-radius: 20px;">
                    <div>
                      <p class="text-h4 col">Justificação de ponto não marcado</p>
                      <p class="text-body2 col">Confirme seu login e selecione o motivo da falta de marcação</p>
                    </div>
                    <div>
                      <!-- Sessão que está presente o formulário de justificação de falta de ponto -->
                      <q-form @submit="onSubmitJustificativa" class="text-center col">
                        <!-- Entrada de Login necessário para a justificação de falta de ponto -->
                        <q-input
                        outlined
                        v-model = "dados.loginjusti"
                        color="grey-7"
                        label="Login"
                        class="q-mb-lg"
                        style="width: 25vw; min-width: 250px;"
                        >
                          <template v-slot:prepend>
                              <q-icon name="ti-id-badge" />
                          </template>
                        <!--Definição do botão para Enviar justificativa de falta de ponto-->
                        </q-input>
                        <q-input
                          outlined
                          v-model = "dados.datajusti"
                          color="grey-7"
                          label="Data"
                          class="q-mb-lg"
                          style="width: 25vw; min-width: 250px;"
                          filled type="date"
                        />
                        <q-select
                        outlined
                        v-model="dados.motivo"
                        :options="dados.options"
                        label="Motivo"
                        class="q-mb-lg"
                        style="width: 25vw; min-width: 250px;"
                        >
                          <template v-slot:prepend>
                            <q-icon name="ti-clipboard" />
                          </template>
                        </q-select>
                        <q-file label="Anexar arquivo" standout v-model="dados.arquivo" v-if="dados.motivo == 'ATESTADO MÉDICO'">
                          <template v-slot:prepend>
                            <q-icon name="attach_file" />
                          </template>
                        </q-file>
                        <div class="q-mt-lg">
                          <q-btn label="Enviar" type="submit" color="primary" class="float-center"/>
                        </div>
                      </q-form>
                    </div>
                  </div>
                </q-tab-panel>
              </q-tab-panels>
            </q-card>
          </div>
        </q-page>
      </q-page-container>

    </q-layout>
  </div>
</template>

  <script>
  import { useAuth } from 'src/stores/auth'
  import { ref, onMounted } from 'vue'
  import { api } from 'boot/axios'
  import { useQuasar } from 'quasar'

  //Constantes para acesos dos dados do token e hora e data atual
  const auth = useAuth()
  const horadata = (new Date()).toLocaleString('en-US', {hour12: false , timeZone: 'America/Manaus' })
  export default {
    name: 'Home',
    data (){
      return {
        //Variaveis dos formulários e tabela
        dados: {
          matricula: auth.user.matricula,
          nome: auth.user.nome,
          login: auth.user.login,
          loginconf: '',
          loginjusti: '',
          hoje: horadata.split(', ')[0],
          data1: '',
          data2: '',
          loginhistorico: '',
          datajusti: '',
          motivo: '',
          arquivo: '',
          options: ['ESQUECI', 'ATESTADO MÉDICO', 'ATRASO'],
          postHistorico: [{data: '-', hora1: '-', hora2: '-', hora3: '-', hora4: '-', hora5: '-', hora6: '-', observacao: '-'}]
        }
      }
    },
    setup () {
      const $q = useQuasar() //Plugin de notificação
      const postHome = ref([])//{data: horadata.split(', ')[0], hora1: '', hora2: '', hora3: '', hora4: '', hora5: '', hora6: '', observacao: 'FALTA MARCAÇÃO'}]) //Linhas da tabela de Registro de Pontos do dia atual
      //Columnas das tabelas de Registro
      const columns = [
        { name: 'data', field: 'data', label: 'Data', sortable: true, align: 'left' },
        { name: 'hora1', field: 'hora1', label: '1 Marc.', align: 'left' },
        { name: 'hora2', field: 'hora2', label: '2 Marc.', align: 'left' },
        { name: 'hora3', field: 'hora3', label: '3 Marc.', align: 'left' },
        { name: 'hora4', field: 'hora4', label: '4 Marc.', align: 'left' },
        { name: 'hora5', field: 'hora5', label: '5 Marc.', align: 'left' },
        { name: 'hora6', field: 'hora6', label: '6 Marc.', align: 'left' },
        { name: 'observacao', field: 'observacao', label: 'Observação', align: 'left' }
      ]
      const horadata = (new Date()).toLocaleString('en-US', { timeZone: 'America/Manaus' })
      const hoje = horadata.split(', ')[0]
      const date = new Date()
      const hojeUS = date.getFullYear() + "-" +((date.getMonth()+1) <10 ? "0" + (date.getMonth() + 1) : (date.getMonth()+1)) + "-" + (date.getDate() < 10 ?"0" + date.getDate() : date.getDate())
      const auth = useAuth()
      //Chamada da função posthome para carregar a tabela de Registro de Pontos do dia atual
      onMounted(() => {
        posthome()
      })
      //Função que faz a comunicação com o servidor para pegar o Registro de Pontos do dia atual e retornar para as linhas da tabela de Registro de Pontos do dia atual
      // Caso exista registros de hoje será exibido na tela, caso contrario será acionada uma notificação informando que falta marcação de ponto para o dia atual
      const posthome = async () =>{
        try{
          const { data } = await api.post('/historico', {login: auth.user.login, data1: hoje, data2: hoje})
          if(data.msg){
            postHome.value = data.historico
          }
          else{
            postHome.value = [{data: hojeUS, hora1: '', hora2: '', hora3: '', hora4: '', hora5: '', hora6: '', observacao: 'FALTA MARCAÇÃO'}]
            $q.notify({
              message: 'Marcação de ponto ainda não realizada hoje!',
              color: 'negative',
              icon: 'ti-close'
            })
          }
        }
        catch(ex){
          console.log(ex)
        }
      }
      return {
          val: ref(false),
          postHome,
          columns,
          tab: ref('nav-home')
      }
    },
    methods: {
      //Aqui nesta função é feito o envio do login do usuário para o servidor validar a marcação de ponto
      //Caso o login de usuáro digitado esteja correto o servidor vai responder, caso o usuário faça este processo entre 1 à 6 vezes será acionar uma notificação de que o ponto foi marcado com sucesso
      //Caso contrário vai ser acionado uma notificação informando que o máximo de pontos marcados para o dia atual foram atingidos ou o login digitado não está correto
      //Caso ocorra algum erro na comunição entre o servidor, será acionada uma notificação informando e para tentar novamente
      onSubmitPonto () {
        api.post('/register/point', {login: this.dados.login, loginconf: this.dados.loginconf})
        .then( response => {
          if(response.data.msg){
            this.$q.notify({
              message: 'Ponto registrado com sucesso!',
              color: 'positive',
              icon: 'check_circle_outline'
            })
            location.reload()
          }
          else{
            this.$q.notify({
              message: 'Máximo de pontos registrados para hoje ou Login incorreto!',
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
      },
      //Aqui nesta função é feito o envio do login do usuário para o servidor validar a marcação de ponto
      // Caso existam registram para o login e datas digitadas será exibido na tela, caso contrario será acionada uma notificação informando que não foram encontrados registros
      //Caso ocorra algum erro na comunição entre o servidor, será acionada uma notificação informando e para tentar novamente
      onSubmitHistorico (){
        api.post('/historico', {login: this.dados.loginhistorico, data1: this.dados.data1, data2: this.dados.data2})
        .then( response => {
          if(response.data.msg){
            this.dados.postHistorico = response.data.historico
          }
          else{
            this.$q.notify({
              message: 'Não encontrados registros entre estas datas',
              color: 'negative',
              icon: 'ti-close'
            })
          }
        })
        .catch((ex) => {
          console.log(ex)
        })

      },
      logout(){
        auth.clear()
        this.$router.push('/')
      },
      onSubmitJustificativa (){
        api.post('/justify', {login: this.dados.login, loginjusti: this.dados.loginjusti, data: this.dados.datajusti, obs: this.dados.motivo})
        .then( response => {
          if(response.data.msg){
            this.$q.notify({
              message: 'Falta de ponto justificada com sucesso!',
              color: 'positive',
              icon: 'check_circle_outline'
            })
            location.reload()
            this.$router.push('/home')
          }
          else{
            this.$q.notify({
              message: 'Justificativa não pode ser realizada, verifique os dados digitados e tente novamente!',
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
  @import url('https://fonts.googleapis.com/css?family=Orbitron');
  @media screen and (max-width: 949px) {
    #tituloTJAM{
      width: 0px;
      height: 0px;
    }
  }
  @media screen and (max-width: 434px) {
    #textoTJAM{
      font-size: 40px;
    }
  }
</style>
