package com.alysonsantos.aspect;

import com.alysonsantos.aspect.command.EnxadaCommand;
import com.alysonsantos.aspect.guice.PluginModule;
import com.alysonsantos.aspect.manager.EnxadaManager;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.henryfabio.sqlprovider.connector.SQLConnector;
import com.henryfabio.sqlprovider.connector.type.impl.MySQLDatabaseType;
import com.henryfabio.sqlprovider.connector.type.impl.SQLiteDatabaseType;
import lombok.Getter;
import me.bristermitten.pdm.PluginDependencyManager;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.command.message.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.logging.Logger;

@Getter
public final class AspectEnxada extends ExtendedJavaPlugin {

    private Injector injector;
    private SQLConnector sqlConnector;

    @Inject
    private EnxadaManager enxadaManager;

    @Inject
    @Named("main")
    private Logger log;

    public static AspectEnxada getInstace() {
        return getPlugin(AspectEnxada.class);
    }

    @Override
    protected void load() {
        saveDefaultConfig();
    }

    @Override
    protected void enable() {
        PluginDependencyManager.of(this).loadAllDependencies().thenRun(() -> {
            try {

                this.sqlConnector = configureSqlProvider(this.getConfig());

                this.injector = PluginModule.of(this).createInjector();
                this.injector.injectMembers(this);

                BukkitFrame bukkitFrame = new BukkitFrame(this);
                bukkitFrame.registerCommands(
                        this.injector.getInstance(EnxadaCommand.class)
                );

                bukkitFrame.getMessageHolder().setMessage(
                        MessageType.INCORRECT_USAGE,
                        "§cVocê não digitou os argumentos necessários."
                );

            } catch (Throwable throwable) {
                throwable.printStackTrace();
                log.severe("Um erro ocorreu na inicialização do plugin!");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        });
    }

    private SQLConnector configureSqlProvider(ConfigurationSection section) {

        SQLConnector connector;
        if (section.getBoolean("connection.mysql.enable")) {

            ConfigurationSection mysqlSection = section.getConfigurationSection("connection.mysql");

            connector = MySQLDatabaseType.builder()
                    .address(mysqlSection.getString("address"))
                    .username(mysqlSection.getString("username"))
                    .password(mysqlSection.getString("password"))
                    .database(mysqlSection.getString("database"))
                    .build()
                    .connect();

        } else {

            ConfigurationSection sqliteSection = section.getConfigurationSection("connection.sqlite");

            connector = SQLiteDatabaseType.builder()
                    .file(new File(sqliteSection.getString("file")))
                    .build()
                    .connect();

        }

        return connector;

    }
}
