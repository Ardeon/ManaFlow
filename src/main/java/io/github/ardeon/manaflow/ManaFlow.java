package io.github.ardeon.manaflow;

import io.github.ardeon.manaflow.db.Database;
import io.github.ardeon.manaflow.db.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ManaFlow extends JavaPlugin {

    private static ManaFlow instance;
    private ManaHelper mainManaHelper;
    private Database db;

    public static ManaFlow getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        db = new SQLite(this, "manaStorage");
        mainManaHelper = new ManaHelper(db);
        new CommandHelper(this);
        new EventHelper(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ManaHelper getMainManaHelper() {
        return mainManaHelper;
    }
}
