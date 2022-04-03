package io.github.ardeon.manaflow.db.table;

import io.github.ardeon.manaflow.db.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DataBaseTable {
    protected Database dataBase;

    protected DataBaseTable(Database dataBase, String creating){
        this.dataBase=dataBase;
        Connection connection = dataBase.getSQLConnection();
        create(connection, creating);
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