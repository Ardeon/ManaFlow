package io.github.ardeon.manaflow;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ManaFlow extends JavaPlugin {

    private static ManaFlow instance;
    private ManaHelper mainManaHelper;

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
        mainManaHelper = new ManaHelper();
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
