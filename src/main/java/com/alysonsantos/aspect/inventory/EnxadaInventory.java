package com.alysonsantos.aspect.inventory;

import com.alysonsantos.aspect.AspectEnxada;
import com.alysonsantos.aspect.inventory.registry.InventoryRegistry;
import com.alysonsantos.aspect.manager.EnxadaManager;
import com.google.inject.Inject;
import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import lombok.val;
import me.lucko.helper.item.ItemStackBuilder;
import org.bukkit.Material;

public class EnxadaInventory extends SimpleInventory {

    @Inject
    private InventoryRegistry inventoryRegistry;

    @Inject
    private EnxadaManager enxadaManager;

    public EnxadaInventory() {
        super("enxadaInventory", "§8Enxada Especial", 3 * 9);

        AspectEnxada.getInstace().getInjector().injectMembers(this);
    }

    @Override
    protected void configureInventory(Viewer viewer, InventoryEditor editor) {
        setUpdateItems(viewer, editor);

        editor.setItem(13, InventoryItem.of(
                ItemStackBuilder.of(Material.EXP_BOTTLE)
                        .name("§aEncantamentos")
                        .lore("§7Clique para abrir o menu de encatamentos.")
                        .build()
        ));

        editor.setItem(14, InventoryItem.of(
                ItemStackBuilder.of(Material.ARMOR_STAND)
                        .name("§aRanking")
                        .lore("§7Clique para abrir o ranking de jogadores.")
                        .build()
                ).defaultCallback(callback -> this.inventoryRegistry.getRankingInventory()
                        .openInventory(callback.getPlayer()))
        );

        editor.setItem(15, InventoryItem.of(
                ItemStackBuilder.of(Material.STORAGE_MINECART)
                        .name("§aMercado")
                        .lore("§7Clique para abrir o mercado.")
                        .build()
        ));
    }

    @Override
    protected void update(Viewer viewer, InventoryEditor editor) {
        setUpdateItems(viewer, editor);
    }

    private void setUpdateItems(Viewer viewer, InventoryEditor editor) {
        val player = viewer.getPlayer();
        val enxada = enxadaManager.getByPlayer(player.getName());

        boolean storaged = enxada.isStoraged();

        editor.setItem(11, InventoryItem.of(
                storaged ?
                        ItemStackBuilder.of(Material.GOLD_HOE)
                                .name("§eEnxada especial")
                                .lore("§7Clique para pegar a sua enxada.")
                                .build() :
                        ItemStackBuilder.of(Material.BARRIER)
                                .name("§eClique para guardar a sua enxada.")
                                .build()
        ).defaultCallback(clickEvent -> {
            enxada.setStoraged(!storaged);
            this.updateInventory(player);
        }));
    }
}
