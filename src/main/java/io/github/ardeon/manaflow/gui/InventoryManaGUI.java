package io.github.ardeon.manaflow.gui;

import io.github.ardeon.manaflow.Mana;
import io.github.ardeon.manaflow.ManaFlow;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class InventoryManaGUI implements InventoryHolder {
    private final Inventory inv;
    private final Player player;
    private final BukkitTask updateTask;

    public InventoryManaGUI(Player player){
        this.player = player;
        this.inv = Bukkit.createInventory(this, 9 * 5);
        BukkitRunnable timer = new BukkitRunnable() {
            @Override
            public void run() {
                tick();
            }
        };
        updateTask = timer.runTaskTimerAsynchronously(ManaFlow.getInstance(),1,1);
        updateItems();
    }

    private void updateItems(){
        Mana mana = ManaFlow.getInstance().getMainManaHelper().getMana(player);

        ItemStack item1 = new ItemStack(Material.GLASS_BOTTLE);
        ItemMeta meta1 = Bukkit.getItemFactory().getItemMeta(Material.GLASS_BOTTLE);
        meta1.setDisplayName("" + mana.getCurrent());
        item1.setItemMeta(meta1);
        inv.setItem(1, item1);

        ItemStack item2 = new ItemStack(Material.GLASS_BOTTLE);
        ItemMeta meta2 = Bukkit.getItemFactory().getItemMeta(Material.GLASS_BOTTLE);
        meta2.setDisplayName("" + mana.getMax());
        item2.setItemMeta(meta2);
        inv.setItem(2, item2);
    }

    public void click(int i){
        Mana mana = ManaFlow.getInstance().getMainManaHelper().getMana(player);
        switch (i){
            case 1:
                mana.setCurrent(0);
                break;
        }
    }

    private void tick(){
        if (player.getOpenInventory().getTopInventory() != inv){
            updateTask.cancel();
        }
        updateItems();
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inv;
    }
}
