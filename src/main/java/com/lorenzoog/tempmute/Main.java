package com.lorenzoog.tempmute;

import com.lorenzoog.tempmute.command.MuteCommand;
import com.lorenzoog.tempmute.command.UnmuteCommand;
import com.lorenzoog.tempmute.datasource.UserDao;
import com.lorenzoog.tempmute.listener.PlayerListener;
import dev.king.universal.UniversalWrapper;
import dev.king.universal.api.JdbcProvider;
import dev.king.universal.mysql.UniversalCredentials;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public final class Main extends JavaPlugin {

    private static final UniversalWrapper UNIVERSAL_WRAPPER = new UniversalWrapper();

    @Override
    public void onEnable() {
        PluginManager pluginManager = getServer().getPluginManager();

        saveResource("config.yml", false);

        JdbcProvider connection = setupConnection().preOpen();

        if(!connection.hasConnection()) {
            getLogger().warning("Plugin couldn't connect to database.");

            pluginManager.disablePlugin(this);

            return;
        }

        UserDao userDao = new UserDao(connection);
        userDao.createTableIfNotExists();

        getCommand("mute").setExecutor(new MuteCommand(userDao));
        getCommand("unmute").setExecutor(new UnmuteCommand(userDao));

        pluginManager.registerEvents(new PlayerListener(userDao), this);
    }

    private JdbcProvider setupConnection() {
        FileConfiguration fileConfiguration = getConfig();

        String host = fileConfiguration.getString("database.host", "localhost");
        int port = fileConfiguration.getInt("database.port", 3306);

        String fullHost = String.format("%s:%s", host, port);

        String database = fileConfiguration.getString("database.database");
        String user = fileConfiguration.getString("database.user");
        String password = fileConfiguration.getString("database.password");

        UniversalCredentials credentials = new UniversalCredentials(fullHost, database, user, password);

       return UNIVERSAL_WRAPPER.newMysqlProvider(credentials, 4);
    }
}
