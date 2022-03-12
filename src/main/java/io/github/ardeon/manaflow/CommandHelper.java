package io.github.ardeon.manaflow;

import org.bukkit.command.PluginCommand;

public class CommandHelper {
    private ManaFlow plugin;

    public CommandHelper(ManaFlow plugin){
        this.plugin = plugin;
        registerAll();
    }

    private void registerAll(){
        PluginCommand pluginCommandMana = plugin.getServer().getPluginCommand("mana");
        ManaCommand manaExecutor = new ManaCommand();
        pluginCommandMana.setExecutor(manaExecutor);
        pluginCommandMana.setTabCompleter(manaExecutor);
    }
}
