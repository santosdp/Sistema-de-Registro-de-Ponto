//Modelo da tabela usuario que será criado no banco de dados
const user = (sequelize, DataTypes) => {
    const User = sequelize.define('User', {
        login: {
            type: DataTypes.STRING,
            allowNull: false,
            primaryKey: true
        },
        senha: {
            type: DataTypes.STRING,
            allowNull: false
        },
        nome: {
            type: DataTypes.STRING,
            allowNull: false
        },
        matricula: {
            type: DataTypes.STRING,
            allowNull: false
        }
        
    }, {
        tableName: 'usuario'
    })

    return User
}

module.exports = user