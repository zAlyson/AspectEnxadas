package com.alysonsantos.aspect.inventory;

import com.alysonsantos.aspect.AspectEnxada;
import com.alysonsantos.aspect.storage.RankingStorage;
import com.alysonsantos.aspect.util.TimeUtils;
import com.google.inject.Inject;
import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.item.enums.DefaultItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import com.henryfabio.minecraft.inventoryapi.viewer.configuration.ViewerConfiguration;
import com.henryfabio.minecraft.inventoryapi.viewer.impl.simple.SimpleViewer;
import me.lucko.helper.item.ItemStackBuilder;
import org.bukkit.Material;

public class RankingInventory extends SimpleInventory {

    @Inject
    private RankingStorage rankingStorage;

    @Inject
    public RankingInventory() {
        super("rankingInventory", "§8Ranking - §nQuebrados§8:", 5 * 9);

        AspectEnxada.getInstace().getInjector().injectMembers(this);
    }

    @Override
    protected void configureInventory(Viewer viewer, InventoryEditor editor) {

        editor.setItem(38, DefaultItem.BACK.toInventoryItem(viewer));

        this.updateItems(viewer, editor);

        editor.setItem(42, InventoryItem.of(
                ItemStackBuilder.of(Material.HOPPER)
                        .name("§bRanking de Tokens")
                        .lore("§7Clique aqui para visualizar.")
                        .build()
        ).defaultCallback(callback -> {


        }));

    }

    @Override
    protected void configureViewer(SimpleViewer viewer) {
        ViewerConfiguration configuration = viewer.getConfiguration();
        configuration.backInventory("enxadaInventory");
    }

    @Override
    protected void update(Viewer viewer, InventoryEditor editor) {
        this.updateItems(viewer, editor);
    }

    private void updateItems(Viewer viewer, InventoryEditor editor) {
        System.out.println(this.rankingStorage.getNextUpdate());

        editor.setItem(40, InventoryItem.of(
                ItemStackBuilder.of(Material.DOUBLE_PLANT)
                        .name("&6Próxima Atualização")
                        .lore(
                                "&7O top tempo será atualizado em",
                                "&f" + TimeUtils.formatTime(this.rankingStorage.getNextUpdate() - System.currentTimeMillis())
                        )
                        .build()));

    }
}
