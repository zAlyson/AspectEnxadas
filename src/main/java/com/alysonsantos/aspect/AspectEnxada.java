package com.alysonsantos.aspect;

import com.alysonsantos.aspect.command.EnxadaCommand;
import com.alysonsantos.aspect.guice.PluginModule;
import com.alysonsantos.aspect.inventory.registry.InventoryRegistry;
import com.alysonsantos.aspect.manager.EnxadaManager;
import com.alysonsantos.aspect.storage.RankingStorage;
import com.alysonsantos.aspect.task.RankingTask;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.henryfabio.minecraft.inventoryapi.manager.InventoryManager;
import com.henryfabio.sqlprovider.common.SQLProvider;
import com.henryfabio.sqlprovider.mysql.MySQLProvider;
import com.henryfabio.sqlprovider.mysql.configuration.MySQLConfiguration;
import com.henryfabio.sqlprovider.sqlite.SQLiteProvider;
import com.henryfabio.sqlprovider.sqlite.configuration.SQLiteConfiguration;
import lombok.Getter;
import me.lucko.helper.Schedulers;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.command.message.MessageType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public final class AspectEnxada extends JavaPlugin {

    @Getter private Injector injector;
    @Getter private SQLProvider sqlProvider;

    @Inject @Named("main") private Logger logger;

    @Inject private EnxadaManager enxadaManager;

    @Inject private InventoryRegistry inventoryRegistry;
    @Inject private RankingStorage rankingStorage;
    @Inject private RankingTask rankingTask;

    public static AspectEnxada getInstace() {
        return getPlugin(AspectEnxada.class);
    }

    @Override
    public void onLoad() {
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        InventoryManager.enable(this);

        configureSqlProvider();
        this.sqlProvider.connect();

        this.injector = PluginModule.of(this).createInjector();
        this.injector.injectMembers(this);

        this.enxadaManager.init();

        this.inventoryRegistry.register();

        registerRankingTask();

        BukkitFrame bukkitFrame = new BukkitFrame(this);
        bukkitFrame.registerCommands(injector.getInstance(EnxadaCommand.class));

        bukkitFrame.getMessageHolder().setMessage(
                MessageType.INCORRECT_USAGE,
                "§cVocê não digitou os argumentos necessários."
        );
    }

    private void configureSqlProvider() {
        FileConfiguration configuration = getConfig();
        if (configuration.getBoolean("connection.mysql.enable")) {
            ConfigurationSection mysqlSection = configuration.getConfigurationSection("connection.mysql");
            sqlProvider = new MySQLProvider(new MySQLConfiguration()
                    .address(mysqlSection.getString("address"))
                    .username(mysqlSection.getString("username"))
                    .password(mysqlSection.getString("password"))
                    .database(mysqlSection.getString("database"))
            );
        } else {
            ConfigurationSection sqliteSection = configuration.getConfigurationSection("connection.sqlite");
            sqlProvider = new SQLiteProvider(new SQLiteConfiguration()
                    .file(new File(this.getDataFolder(), sqliteSection.getString("file")))
            );
        }
    }

    private void registerRankingTask() {
        Schedulers.builder()
                .async()
                .every(5, TimeUnit.MINUTES)
                .run(rankingTask);
    }
}
