package io.github.ardeon.manaflow;

import io.github.ardeon.manaflow.gui.InventoryManaGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player))
            return false;
        Player player = (Player) sender;
        Mana mana = ManaFlow.getInstance().getMainManaHelper().getMana((Player) sender);
        if (args.length == 0) {
            sender.sendMessage(String.format("Mana: current = %f  max = %f", mana.getCurrent(), mana.getMax()));
            InventoryManaGUI gui = new InventoryManaGUI(player);
            player.openInventory(gui.getInventory());
        }
        if (args.length > 0){
            switch (args[0].toUpperCase()){
                case "MAX":
                    mana.setMax(100);
                    mana.setCurrent(100);
                    break;
                case "USE":
                    mana.consume(100);
                    break;
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
