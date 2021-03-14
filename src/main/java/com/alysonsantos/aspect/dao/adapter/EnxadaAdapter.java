package com.alysonsantos.aspect.dao.adapter;

import com.alysonsantos.aspect.model.Enxada;
import com.alysonsantos.aspect.model.EnxadaEnchantments;
import com.henryfabio.sqlprovider.common.adapter.SQLAdapter;
import com.henryfabio.sqlprovider.common.result.SimpleResultSet;
import lombok.val;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class EnxadaAdapter implements SQLAdapter<Enxada> {

    @Override
    public Enxada adaptResult(SimpleResultSet resultSet) throws NullPointerException {
        if (!resultSet.next()) {
            return null;
        }

        return Enxada.builder()
                .owner(resultSet.get("owner"))
                .level(resultSet.get("level"))
                .storaged(true)
                .brokenPlantions(resultSet.get("brokenPlantations"))
                .enxadaEnchantments(enchantments(resultSet))
                .build();
    }

    @Override
    public void adaptStatement(PreparedStatement statement, Enxada target) throws SQLException {
        statement.setString(1, target.getOwner());
        statement.setInt(2, target.getLevel());
        statement.setDouble(3, target.getBrokenPlantions());

        val enchantments = target.getEnxadaEnchantments();
        statement.setDouble(4, enchantments.getHerbalism());
        statement.setDouble(5, enchantments.getRewarding());
        statement.setDouble(6, enchantments.getWealth());
    }

    private final EnxadaEnchantments enchantments(SimpleResultSet resultSet) {
        return EnxadaEnchantments.builder()
                .wealth(resultSet.get("enchant_wealth"))
                .herbalism(resultSet.get("enchant_herbalism"))
                .rewarding(resultSet.get("enchant_rewarding"))
                .build();
    }
}
