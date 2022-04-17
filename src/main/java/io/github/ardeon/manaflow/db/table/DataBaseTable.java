package io.github.ardeon.manaflow.db.table;

import io.github.ardeon.manaflow.Mana;
import io.github.ardeon.manaflow.ManaFlow;
import io.github.ardeon.manaflow.db.Database;

import java.sql.*;

public class DataBaseTable {
    protected Database dataBase;

    public DataBaseTable(Database dataBase) {
        this.dataBase=dataBase;
        Connection connection = dataBase.getSQLConnection();
        String columns = "guid VARCHAR(36) PRIMARY KEY," +
                        "max FLOAT," +
                        "restoreDelayInTicks INT," +
                        "restorePerSecond FLOAT," +
                        "removeOveragePerSecond FLOAT," +
                        "display INT NOT NULL DEFAULT 0";
        String creating = String.format("CREATE TABLE IF NOT EXISTS %s (%s);", "Mana", columns);
        //ManaFlow.getInstance().getLogger().info(creating);
        create(connection, creating);
    }

    public void save(Mana mana) {
        Connection connection = dataBase.getSQLConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("REPLACE INTO Mana " +
                    "(guid, max, restoreDelayInTicks, restorePerSecond, removeOveragePerSecond, display) "+mana.toString());
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Mana get(String guid) {
        Connection connection = dataBase.getSQLConnection();
        Mana mana = null;
        ManaFlow.getInstance().getLogger().info("Loading " + guid + "  SELECT * FROM 'Mana' WHERE guid = '" + guid +"'");
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM 'Mana' WHERE guid = '" + guid +"'");
            ResultSet set = statement.executeQuery();
            ManaFlow.getInstance().getLogger().info(set.toString());
            while (set.next()) {
                ManaFlow.getInstance().getLogger().info("Loading");
                mana = new Mana(guid, set.getFloat("max"),
                        set.getInt("restoreDelayInTicks"),
                        set.getFloat("restorePerSecond"),
                        set.getFloat("removeOveragePerSecond"),
                        set.getInt("display"));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mana == null ? new Mana(guid) : mana;
    }

    private void create(Connection connection, String creating) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(creating);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}