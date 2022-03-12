package io.github.ardeon.manaflow.gui;

import io.github.ardeon.manaflow.ManaFlow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;

public class GUIEventListener implements Listener {
    private ManaFlow plugin;
    public GUIEventListener(ManaFlow manaFlow) {
        plugin = manaFlow;
    }

    @EventHandler
    public void guiClick(InventoryClickEvent event) {
        Inventory inv = event.getClickedInventory();
        if (inv!=null && inv.getHolder() instanceof InventoryManaGUI) {
            InventoryManaGUI gui = (InventoryManaGUI) inv.getHolder();
            gui.click(event.getSlot());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void guiInteract(InventoryInteractEvent event) {
        Inventory inv = event.getInventory();
        if (inv.getHolder() instanceof InventoryManaGUI) {
            event.setCancelled(true);
        }
    }
}
