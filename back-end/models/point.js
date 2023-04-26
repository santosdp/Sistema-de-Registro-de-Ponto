const point = (sequelize, DataTypes) => {
    const Point = sequelize.define('Point', {
        id: {
            type: DataTypes.INTEGER,
            autoIncrement: true,
            allowNull: false,
            primaryKey: true
        },
        matricula: {
            type: DataTypes.STRING,
            allowNull: false
        },
        data: {
            type: DataTypes.DATEONLY,
            allowNull: false
        },
        hora1: {
            type: DataTypes.TIME,
            allowNull: true
        },
        hora2: {
            type: DataTypes.TIME,
            defaultValue: '00:00',
            allowNull: true
        },
        hora3: {
            type: DataTypes.TIME,
            defaultValue: '00:00',
            allowNull: true
        },
        hora4: {
            type: DataTypes.TIME,
            defaultValue: '00:00',
            allowNull: true
        },
        hora5: {
            type: DataTypes.TIME,
            defaultValue: '00:00',
            allowNull: true
        },
        hora6: {
            type: DataTypes.TIME,
            defaultValue: '00:00',
            allowNull: true
        },
        observacao: {
            type: DataTypes.STRING,
            allowNull: true
        }
        
    }, {
        tableName: 'ponto'
    })

    return Point
}

module.exports = point