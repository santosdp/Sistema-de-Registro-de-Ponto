const { sequelize } = require('./models')
//Arquivo para sincronizar com o banco de dados
sequelize.sync().then(() => {
    console.log('Conectado ao Banco de dados com sucesso!')
})