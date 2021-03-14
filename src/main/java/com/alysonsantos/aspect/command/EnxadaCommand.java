package com.alysonsantos.aspect.command;

import com.alysonsantos.aspect.inventory.registry.InventoryRegistry;
import com.alysonsantos.aspect.manager.EnxadaManager;
import com.alysonsantos.aspect.model.Enxada;
import com.alysonsantos.aspect.util.NumberFormat;
import com.google.inject.Inject;
import lombok.val;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

public final class EnxadaCommand {

    @Inject
    private EnxadaManager enxadaManager;

    @Inject
    private InventoryRegistry inventoryRegistry;

    @Command(
            name = "enxada",
            target = CommandTarget.PLAYER,
            async = true
    )
    public void enxadaCommand(Context<Player> context) {
        val player = context.getSender();

        val enxada = enxadaManager.getByPlayer(player.getName());
        if (enxada == null)
            inventoryRegistry.getBuyEnxadaInventory().openInventory(player);
        else
            inventoryRegistry.getEnxadaInventory().openInventory(player);
    }

    @Command(
            name = "enxada.remove",
            target = CommandTarget.ALL,
            permission = "enxada.admin",
            async = true
    )
    public void enxadaRemove(Context<?> context, String target) {
        if (enxadaManager.getByPlayer(target) == null) {
            context.sendMessage("§cO jogador informado é inválido.");
            return;
        }

        Enxada enxada = enxadaManager.getByPlayer(target);
        enxadaManager.getCache().remove(target);
        enxadaManager.getEnxadaDAO().deleteOne(enxada);

        context.sendMessage("§aOperação concluída com sucesso! ");
    }

    @Command(
            name = "enxada.teste"
    )
    public void enxadaRemove(Context<?> context, double value) {
        context.sendMessage(NumberFormat.format(value));
    }
}
