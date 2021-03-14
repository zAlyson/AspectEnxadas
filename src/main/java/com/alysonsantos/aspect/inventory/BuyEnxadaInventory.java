package com.alysonsantos.aspect.inventory;

import com.alysonsantos.aspect.AspectEnxada;
import com.alysonsantos.aspect.api.EconomyApi;
import com.alysonsantos.aspect.api.EconomyProvider;
import com.alysonsantos.aspect.configuration.ConfigValue;
import com.alysonsantos.aspect.manager.EnxadaManager;
import com.alysonsantos.aspect.model.Enxada;
import com.alysonsantos.aspect.model.EnxadaEnchantments;
import com.alysonsantos.aspect.util.NumberFormat;
import com.google.inject.Inject;
import com.henryfabio.minecraft.inventoryapi.editor.InventoryEditor;
import com.henryfabio.minecraft.inventoryapi.inventory.impl.simple.SimpleInventory;
import com.henryfabio.minecraft.inventoryapi.item.InventoryItem;
import com.henryfabio.minecraft.inventoryapi.viewer.Viewer;
import lombok.val;
import me.lucko.helper.item.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BuyEnxadaInventory extends SimpleInventory {

    private static final EconomyApi ECONOMY_API = EconomyProvider.get();

    @Inject
    private EnxadaManager enxadaManager;

    public BuyEnxadaInventory() {
        super("buyEnxadaInventory", "Enxada - Comprar:", 3 * 9);

        AspectEnxada.getInstace().getInjector().injectMembers(this);
    }

    @Override
    protected void configureInventory(Viewer viewer, InventoryEditor editor) {
        Player player = viewer.getPlayer();

        val account = ECONOMY_API.getAccount(player);

        double priceToButIt = ConfigValue.get(ConfigValue::priceToButIt);
        boolean haveEnoughTokens = !(account.getTokens() < priceToButIt);

        editor.setItem(13, InventoryItem.of(
                ItemStackBuilder.of(Material.GOLD_HOE)
                        .name("§6Enxada Especial")
                        .lore(
                                "",
                                " §eComo funciona?",
                                " §f Use essa §7§nEnxada§f para coletar plantações",
                                " §f em seu terreno, além de upar-la e ganhar itens",
                                " §f especiais, você upa com a moeda §d§nTokens§f,",
                                " §f e essa moeda vem Farmando, Minerando,",
                                " §f e Matando Mobs.",
                                "",
                                "§f Preço: §a" + NumberFormat.format(priceToButIt) + " Tokens.",
                                "",
                                haveEnoughTokens
                                        ? "§aClique aqui para efetuar a compra!"
                                        : "§cVocê não tem tokens suficientes para realizar a compra."
                        )
                        .build()
                ).defaultCallback(clickEvent -> {

                    if (!haveEnoughTokens) {
                        player.sendMessage("§e§l OPS §eVocê não possui tokens suficiente para realizar a compra.");
                        player.closeInventory();
                        return;
                    }

                    player.sendMessage(new String[]{
                            "",
                            "§a§l YAY! §aVocê acaba de efetuar a compra de uma enxada.",
                            "§c   §c- ✞" + NumberFormat.format(priceToButIt) + " tokens.",
                            ""
                    });

                    enxadaManager.createOne(Enxada.builder()
                            .owner(player.getName())
                            .level(1)
                            .storaged(true)
                            .brokenPlantions(0)
                            .enxadaEnchantments(EnxadaEnchantments.builder()
                                    .herbalism(1)
                                    .wealth(1)
                                    .rewarding(1)
                                    .build())
                            .build());
                })
        );
    }
}
