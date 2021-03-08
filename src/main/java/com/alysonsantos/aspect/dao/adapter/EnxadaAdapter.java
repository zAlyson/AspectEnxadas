package com.alysonsantos.aspect.dao.adapter;

import com.alysonsantos.aspect.model.Enxada;
import com.alysonsantos.aspect.model.EnxadaEnchantments;
import com.henryfabio.sqlprovider.executor.adapter.SQLResultAdapter;
import com.henryfabio.sqlprovider.executor.result.SimpleResultSet;

public final class EnxadaAdapter implements SQLResultAdapter<Enxada> {


    @Override
    public Enxada adaptResult(SimpleResultSet resultSet) {
        return Enxada.builder()
                .id(resultSet.get("id"))
                .owner(resultSet.get("owner"))
                .level(resultSet.get("level"))
                .brokenPlantions(resultSet.get("brokenPlantations"))
                .enxadaEnchantments(enchantments(resultSet))
                .build();
    }

    private final EnxadaEnchantments enchantments(SimpleResultSet resultSet) {
        return EnxadaEnchantments.builder()
                .wealth(resultSet.get("enchant_wealth"))
                .herbalism(resultSet.get("enchant_herbalism"))
                .rewarding(resultSet.get("enchant_rewarding"))
                .build();
    }

}
