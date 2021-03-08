package com.alysonsantos.aspect.dao;

import com.alysonsantos.aspect.dao.adapter.EnxadaAdapter;
import com.alysonsantos.aspect.model.Enxada;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.henryfabio.sqlprovider.executor.SQLExecutor;
import lombok.val;

import java.util.Set;

@Singleton
public final class EnxadaDAO {

    private final String TABLE = "aspect_enxada";

    @Inject
    private SQLExecutor sqlExecutor;

    public void createTable() {
        sqlExecutor.updateQuery("CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
                "id INTEGER(16) NOT NULL PRIMARY KEY, " +
                "owner VARCHAR(36) NOT NULL, " +
                "level INTEGER(16) NOT NULL, " +
                "brokenPlantations DOUBLE NOT NULL, " +
                "enchant_herbalism INTEGER(16) NOT NULL, " +
                "enchant_rewarding INTEGER(16) NOT NULL, " +
                "enchant_wealth INTEGER(16) NOT NULL" +
                ");");
    }

    public Enxada selectOne(String owner) {
        return sqlExecutor.resultOneQuery(
                "SELECT * FROM " + TABLE + "WHERE owner = ?",
                statement -> statement.set(1, owner),
                EnxadaAdapter.class
        );
    }

    public Set<Enxada> selectAll() {
        return sqlExecutor.resultManyQuery(
                "SELECT * FROM " + TABLE,
                k -> {
                },
                EnxadaAdapter.class
        );
    }

    public Set<Enxada> selectAll(String query) {
        return sqlExecutor.resultManyQuery(
                "SELECT * FROM " + TABLE + " " + query,
                k -> {
                },
                EnxadaAdapter.class
        );
    }

    public void insertOne(Enxada enxada) {
        sqlExecutor.updateQuery(
                "INSERT INTO " + TABLE + " VALUES(?,?,?,?,?,?,?);",
                statement -> {
                    statement.set(1, enxada.getId());
                    statement.set(2, enxada.getOwner());
                    statement.set(3, enxada.getLevel());
                    statement.set(4, enxada.getBrokenPlantions());

                    val enchantments = enxada.getEnxadaEnchantments();
                    statement.set(5, enchantments.getHerbalism());
                    statement.set(6, enchantments.getRewarding());
                    statement.set(7, enchantments.getWealth());
                }
        );
    }

    public void saveOne(Enxada enxada) {
        sqlExecutor.updateQuery(
                String.format("REPLACE INTO %s VALUES(?,?,?,?,?,?)", TABLE),
                statement -> {
                    statement.set(1, enxada.getOwner());
                    statement.set(2, enxada.getLevel());
                    statement.set(3, enxada.getBrokenPlantions());

                    val enchantments = enxada.getEnxadaEnchantments();
                    statement.set(4, enchantments.getHerbalism());
                    statement.set(5, enchantments.getRewarding());
                    statement.set(6, enchantments.getWealth());
                }
        );
    }

    public void deleteOne(Enxada enxada) {
        sqlExecutor.updateQuery(
                "DELETE FROM " + TABLE + " WHERE id = '" + enxada.getId() + "'"
        );
    }
}
