package io.github.ardeon.manaflow;

import io.github.ardeon.manaflow.db.Database;
import io.github.ardeon.manaflow.db.table.DataBaseTable;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class ManaHelper {
    private final Map<Player, Mana> playersMana = new HashMap<>();
    private BukkitTask update;
    private BukkitTask updateDisplay;
    private final DataBaseTable dbt;

    public ManaHelper(Database db){
        dbt = new DataBaseTable(db);
        start();
    }

    public void addPlayer(Player player) {
        playersMana.put(player, dbt.get(player.getUniqueId().toString()));
        //playersMana.put(player, new Mana(player.getUniqueId().toString(),100, 100, 4, 1));
    }

    public void saveMana(Player player) {
        Mana mana = playersMana.get(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                dbt.save(mana);
            }
        }.runTaskAsynchronously(ManaFlow.getInstance());
    }

    public void removePlayer(Player player) {
        saveMana(player);
        playersMana.remove(player);
    }

    public Mana getMana(Player player){
        return playersMana.get(player);
    }

    private void tick(){
        playersMana.forEach( (p, m) -> {
            ManaState state = m.calculateTick();
            if (state!=null)
                playSoundManaState(p, state);
        });
    }

    private void displayTick(){
        playersMana.forEach( (p, m) -> {
            m.displayMana();
        });
    }

    private void playSoundManaState(Player player, ManaState state){
        Sound sound = Sound.ITEM_BOTTLE_FILL;
        float pitch = switch (state) {
            case EMPTY -> 1;
            case LOW -> 1.1f;
            case MEDIUM -> 1.4f;
            case HIGH -> 1.7f;
            case FULL -> 2.1f;
            case OVERFLOWING -> 3.1f;
        };
        player.playSound(player.getLocation(), sound, 10, pitch);
    }

    private void start() {
        BukkitRunnable timer = new BukkitRunnable() {
            @Override
            public void run() {
                tick();
            }
        };
        BukkitRunnable displayTimer = new BukkitRunnable() {
            @Override
            public void run() {
                displayTick();
            }
        };

        if (update!=null) {
            update.cancel();
        }
        update = timer.runTaskTimerAsynchronously(ManaFlow.getInstance(),1,1);
        if (updateDisplay!=null) {
            updateDisplay.cancel();
        }
        updateDisplay = displayTimer.runTaskTimer(ManaFlow.getInstance(),1,1);
    }

}
