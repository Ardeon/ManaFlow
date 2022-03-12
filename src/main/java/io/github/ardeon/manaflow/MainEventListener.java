package io.github.ardeon.manaflow;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MainEventListener implements Listener {
    private ManaFlow plugin;
    public MainEventListener(ManaFlow plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        plugin.getMainManaHelper().addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        plugin.getMainManaHelper().removePlayer(event.getPlayer());
    }
}
