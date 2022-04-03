package io.github.ardeon.manaflow.db;

import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public abstract class Database {
    Connection connection;
    Plugin plugin;

    public Database(Plugin plugin) {
        this.plugin = plugin;
    }

    public abstract Connection getSQLConnection();

    public abstract void load();

    public void initialize(){
        connection = getSQLConnection();
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void close(PreparedStatement preparedStatement, ResultSet result){
        try {
            if (preparedStatement != null)
                preparedStatement.close();
            if (result != null)
                result.close();
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "Failed to close SQL connection:", ex);
        }
    }
}