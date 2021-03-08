package com.alysonsantos.aspect.command;

import com.alysonsantos.aspect.manager.EnxadaManager;
import com.google.inject.Inject;
import lombok.val;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.entity.Player;

public class EnxadaCommand {

    @Inject private EnxadaManager enxadaManager;

    @Command(
            name = "enxada",
            target = CommandTarget.ALL
    )
    public void enxadaCommand(Context<Player> context) {
        val player = context.getSender();
        player.sendMessage("aaa");
        //TODO: Open the main inventory
    }

}
