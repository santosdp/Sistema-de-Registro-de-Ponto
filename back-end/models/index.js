//Criação das duas tabelas no banco de dados
const sequelize = require('../Config/sequelize.js')
const Sequelize = require('sequelize')

const User = require('./user.js')
const Point = require('./point.js')

const user = User(sequelize, Sequelize.DataTypes)
const point = Point(sequelize, Sequelize.DataTypes)
//Criação das tabelas no banco de dados
const db = {
    user,
    point,
    sequelize
}

module.exports = db