package io.github.ardeon.manaflow.db;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class SQLite extends Database{
    String dbname;
    File dataFolder;

    public SQLite(Plugin plugin, String name){
        super(plugin);
        dbname = name;
        dataFolder = new File(plugin.getDataFolder(), dbname+".db");
        load();
    }

    private boolean createFileIfNotExist() {
        if (!dataFolder.exists()){
            try {
                plugin.getDataFolder().mkdirs();
                dataFolder.createNewFile();
                plugin.getLogger().info(String.format("create db %s", dbname));
                return true;
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File write error: "+dbname+".db");
            }
        }
        return false;
    }

    public Connection getSQLConnection() {
        createFileIfNotExist();
        try {
            if(connection!=null&&!connection.isClosed()){
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE,"SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }
        return null;
    }


    public void load() {
        initialize();
    }
}