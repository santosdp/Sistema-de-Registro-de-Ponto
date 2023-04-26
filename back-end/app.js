//Bibliotecas necessárias para rodar o projeto
require('dotenv').config()
const express = require('express')
const bcrypt = require('bcrypt')
const jwt = require('jsonwebtoken')
const cors = require('cors')

//Inicialização do Express e configuração
const app = express()
app.use(express.json())

//Adicão de autorizações no header das respostas do servidor
app.use((req, res, next) => {
    res.header("Access-Control-Allow-Origin", "*")
    res.header("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE")
    res.header("Access-Control-Allow-Headers", "Origin, X-Api-Key, X-Requested-With, Content-Type, Accept, Authorization")
    app.use(cors())
    next()
})

//Models de campos das tabelas no banco de dados
const { user } = require('./models')
const {point } = require('./models')
//Função de procura de dados no Sequelize
const { Op } = require('sequelize');


// ----------------- Rotas -----------------

// Rota publica para verificar conexão com o banco de dados
app.get('/', async (req, res) => {
    return res.status(200).json({ msg : "Bem-vindo a minha API" })
})



// Rota de Cadastrar
// Primeiramente é verificado se o login e matricula enviados pela requisição já são utilizado
// Caso não sejam, será feito uma criptografia na senha usando o bcrypt e então criada a conta no banco de dados
app.post('/register/user', async (req, res) =>{
    const { login, senha, nome, matricula} = req.body
    const loginExists = await user.findOne({where : {login}})
    const matriculaExists = await user.findOne({where : {matricula}})
    if(loginExists || matriculaExists){
        console.log("Login invalido")
        return res.json({ msg: false})
    }
    else{
        const salt = await bcrypt.genSalt(12)
        const passwordhash = await bcrypt.hash(senha , salt)
        try{
            await user.create({login, senha : passwordhash, nome: nome.toUpperCase(), matricula})
            console.log("Conta criada")
            return res.status(201).json({ msg: true})
        }
        catch(ex){
            console.log("Erro: " + ex)
            return res.status(500).json({ msg: false})
        }
        
    }
})

// Rota de Verificar login
// Primeiramente é verificado se o login da requisição corresponde à algum presente no banco de dados
// Caso sim será verificada se a senha da requisição bate com a senha corresponde no banco de dados descriptografando com obcrypt
// Caso ambas essas condições sejam satisfetias será criado um token usando o JWT, caso o usuário tenha selecionado a opção de lembrar o token não terá prazo de expiração, caso contrário o token terá validade de 5 minutos
// Após isso será retornado o token e algumas informações do usuário
app.post('/auth', async (req, res) => {
    const { login, senha, lembrar} = req.body
    const usuario = await user.findOne({where : {login : login}})
    if(!usuario){
        return res.json({msg: false})
    }
    const checkpassword = await bcrypt.compare(senha, usuario.senha)
    if(!checkpassword){
        return res.json({msg: false})
    }
    else{
        try{
            const secret = process.env.SECRET
            const usuarioseguro = await user.findOne({where : {login : login}, attributes : ["login", "nome", "matricula"]})
            if(lembrar){
                const token = jwt.sign(
                    {
                        id: user.login
                    },
                    secret,
                )
                return res.status(200).json({ msg : true , token, user: usuarioseguro })
            }
            else{
                const token = jwt.sign(
                    { id: user.login },
                    secret,
                    { expiresIn: 300}
                )
                return res.status(200).json({ msg : true , token, user: usuarioseguro })
            }
            
        }
        catch(ex){
            console.log("Erro: " + ex)
            return res.status(500).json({ msg: false})
        }
    }
    
})

// Rota de Redefinição de senha
// Primeiramente é verificado se o login da requisição corresponde à algum presente no banco de dados e se as duas senhas enviadas sejam iguais
// Caso sim será verificada se a matricula da requisição bate com a matricula corresponde no banco de dados
// Caso ambas essas condições sejam satisfetias será feito uma criptografia na senha usando o bcrypt, então salvo a mudança na senha do usuário no banco de dados e será enviada uma confirmação como resposta 
// Caso nenhuma dessas condições sejam satisfetias, será enviada uma resposta negativa
app.post('/redefine', async (req, res) => {
    const { login, matricula, senha1, senha2} = req.body
    const usuario = await user.findOne({where : {login : login}})
    if(senha1 != senha2 || !usuario){
        return res.json({msg: false})
    }
    if(usuario.matricula != matricula){
        return res.json({msg: false})
    }
    else{
        const salt = await bcrypt.genSalt(12)
        const passwordhash = await bcrypt.hash(senha1 , salt)
        usuario.senha = passwordhash
        try{
            await usuario.save()
            return res.status(201).json({ msg: true})
        }
        catch(ex){
            console.log("Erro: " + ex)
            return res.status(500).json({ msg : false})
        }
    }
})
// Rota de verificação do token de usuario
// É verificado o header da requisição e então é feita a comparação com o JWT e a resposta mandada para o requisitor
app.get("/auth/verify", async (req, res) => {
    const authHeader = req.headers['authorization']
    try{
        const token = authHeader && authHeader.split(" ")[1]
        const secret = process.env.SECRET
        const verifytoken = jwt.verify(token, secret)
        return res.status(200).json({verifytoken})
    }
    catch(ex){
        console.log("Erro: " + ex)
        res.status(400).json({ msg: false})
    }
})

// Rota de Registrar ponto
// Primeiramente é verificado se o login e o loginconf (que se refere ao login digitado pelo usuário no front-end) enviados por requisição são iguais
// Caso sejam batam, será selecionado o usuário e o ponto corresponde à data atual, caso não exista ainda um ponto para a data atual e retornada uma confirmação para o requisitor
// Se não existir será criado um, caso contrário será verificado até que marcação foi feita
// Se faltar a marcação entre a segunda e sexta, a correspondente será marcada e retornada uma confirmação para o requisitor
// Caso contrário será retornada uma negação para o requisitor, significando que o máximo de marcações foi atingida
app.post('/register/point', async(req, res) =>{
    const {login , loginconf} = req.body
    if (login != loginconf) {
        return res.json({ msg: false})
    }
    const hojedata = (new Date()).toLocaleString('en-US', { timeZone: 'America/Manaus' }) //Criação da data atual no formato en-US
    const data = hojedata.split(', ')[0]
    const hojehora = (new Date()).toLocaleString('pt-BR', { timeZone: 'America/Manaus' }) //Criação da hora atual no formato pt-BR
    const hora = hojehora.split(', ')[1]
    const usuario = await user.findOne({where : {login:login}})
    const matricula = usuario.matricula
    const ponto = await point.findOne({where : {matricula , data}})
    if(!ponto){
        try{
            await point.create({matricula, data, hora1:hora , observacao: ' FALTA MARCACAO'})
            return res.status(201).json({ msg: true})
        }
        catch(ex){
            console.log("Erro: " + ex)
            return res.status(500).json({ msg : false})
        }
    }
    else{
        if(ponto.hora2 === '00:00:00'){
            ponto.hora2 = hora
            ponto.observacao = ''
            try{
                await ponto.save()
                return res.status(201).json({ msg: true})
            }
            catch(ex){
                console.log("Erro: " + ex)
                return res.status(500).json({ msg : false})
            }    
        }
        if(ponto.hora3 === '00:00:00'){
            ponto.hora3 = hora
            try{
                await ponto.save()
                return res.status(201).json({ msg: true})
            }
            catch(ex){
                console.log("Erro: " + ex)
                return res.status(500).json({ msg : false})
            }
        }
        if(ponto.hora4 === '00:00:00'){
            ponto.hora4 = hora
            try{
                await ponto.save()
                return res.status(201).json({ msg: true})
            }
            catch(ex){
                console.log("Erro: " + ex)
                return res.status(500).json({ msg : false})
            }
        }
        if(ponto.hora5 === '00:00:00'){
            ponto.hora5 = hora
            try{
                await ponto.save()
                return res.status(201).json({ msg: true})
            }
            catch(ex){
                console.log("Erro: " + ex)
                return res.status(500).json({ msg : false})
            }

        }
        if(ponto.hora6 === '00:00:00'){
            ponto.hora6 = hora
            try{
                await ponto.save()
                return res.status(201).json({ msg: true})
            }
            catch(ex){
                console.log("Erro: " + ex)
                return res.status(500).json({ msg : false})
            }

        }
        else{
            return res.json({ msg: false})
        }
    }
})



// Rota de Acessar Historico
// Primeiramente será verificado se o login está cadastrado no banco de dados e as datas data1 e data2 não são nulas
// Caso essas condições sejam verdadeiras será selecionado um ponto no banco de dados que seja referente à matricula do login e com data entre a data1 e data2
// Se este ponto existir será retornado ele com os dados que importam para o requisitor
// Caso contrário será retornada uma negação para o requisitor, significando que o ponto não foi encotrado
app.post('/historico', async (req, res) => {
    const {login, data1, data2} = req.body
    if(!data1 || !data2){
        return res.json({ msg: false })
    }
    const usuario = await user.findOne({where : {login}})
    if(usuario){
        const historico = await point.findAll({
            where : {
                matricula : usuario.matricula,
                data : { [Op.between] : [data1, data2] }
            },
            attributes : ["data", "hora1", "hora2", "hora3", "hora4", "hora5", "hora6", "observacao",]
        })
        if(historico.length != 0){
            return res.json({msg: true, historico })
        }
        else{
            return res.json({ msg : false })
        }
        
    }
    else{
        return res.json({ msg: false })
    }
})

// Rota de Justificar ponto
// Primeiramente é verificado se o login e o loginjusti (que se refere ao login digitado pelo usuário no front-end) enviados por requisição são iguais e a data não é nula
// Caso sejam, será selecionado o usuário e o ponto corresponde à data digitada, então alterado a observação referente a este ponto e enviada uma resposta positiva
// Se não existir será criado um ponto para esta data será enviada uma resposta negativa
app.post('/justify', async (req, res) => {
    const {login, loginjusti, data, obs} = req.body
    if(login != loginjusti || !data){
        return res.json({ msg: false })
    }
    const usuario = await user.findOne({where : {login}})
    const ponto = await point.findOne({where : {matricula: usuario.matricula , data}})
    if(ponto){
        ponto.observacao = obs
        try{
            await ponto.save()
            return res.json({msg: true})
        }
        catch(ex){
            console.log("Erro: " + ex)
            return res.status(500).json({ msg : false})
        }
    }
    else{
        return res.json({ msg : false })
    }

})

//Configuração para o Express iniciar na porta 3000
app.listen(3000)
