package com.alysonsantos.aspect.dao;

import com.alysonsantos.aspect.dao.adapter.EnxadaAdapter;
import com.alysonsantos.aspect.model.Enxada;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.henryfabio.sqlprovider.common.SQLProvider;
import lombok.val;

import java.sql.SQLException;
import java.util.List;

@Singleton
public final class EnxadaDAO {

    private final String TABLE = "aspect_enxada";

    @Inject
    private SQLProvider sqlProvider;

    public void createTable() {
        sqlProvider.executor().updateQuery("CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
                "owner VARCHAR(36) NOT NULL PRIMARY KEY, " +
                "level INTEGER(16) NOT NULL, " +
                "brokenPlantations DOUBLE NOT NULL, " +
                "enchant_herbalism INTEGER(16) NOT NULL, " +
                "enchant_rewarding INTEGER(16) NOT NULL, " +
                "enchant_wealth INTEGER(16) NOT NULL" +
                ");");
    }

    public Enxada selectOne(String owner) {
        return sqlProvider.executor().resultOneQuery(
                "SELECT * FROM " + TABLE + " WHERE owner = ?",
                preparedStatement -> {
                    try {
                        preparedStatement.setString(1, owner);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                },
                EnxadaAdapter.class
        );
    }

    public List<Enxada> selectAll() {
        return sqlProvider.executor().resultManyQuery(
                "SELECT * FROM " + TABLE,
                EnxadaAdapter.class
        );
    }

    public List<Enxada> selectAll(String query) {
        return sqlProvider.executor().resultManyQuery(
                "SELECT * FROM " + TABLE + " " + query,
                EnxadaAdapter.class
        );
    }

    public void insertOne(Enxada enxada) {
        sqlProvider.executor().updateOneQuery(
                "INSERT INTO " + TABLE + " VALUES(?,?,?,?,?,?);",
                EnxadaAdapter.class,
                enxada
        );
    }

    public void saveOne(Enxada enxada) {
        sqlProvider.executor().updateQuery(
                String.format("REPLACE INTO %s VALUES(?,?,?,?,?,?)", TABLE),
                statement -> {
                    try {
                        statement.setString(1, enxada.getOwner());
                        statement.setInt(2, enxada.getLevel());
                        statement.setDouble(3, enxada.getBrokenPlantions());

                        val enchantments = enxada.getEnxadaEnchantments();
                        statement.setInt(4, enchantments.getHerbalism());
                        statement.setInt(5, enchantments.getRewarding());
                        statement.setInt(6, enchantments.getWealth());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();

                    }
                }
        );
    }

    public void deleteOne(Enxada enxada) {
        sqlProvider.executor().updateQuery(
                "DELETE FROM " + TABLE + " WHERE owner = '" + enxada.getOwner() + "'"
        );
    }
}
