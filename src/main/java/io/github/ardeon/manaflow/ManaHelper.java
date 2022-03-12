package io.github.ardeon.manaflow;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class ManaHelper {
    private Map<Player, Mana> playersMana = new HashMap<>();
    private BukkitTask update;

    public ManaHelper(){
        start();
    }

    public void addPlayer(Player player) {
        playersMana.put(player, new Mana(100, 4, 1));
    }

    public void removePlayer(Player player) {
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

    private void playSoundManaState(Player player, ManaState state){
        Sound sound = Sound.ITEM_BOTTLE_FILL;
        float pitch = 1;
        switch (state){
            case EMPTY:
                pitch = 1;
                break;
            case LOW:
                pitch = 1.1f;
                break;
            case MEDIUM:
                pitch = 1.4f;
                break;
            case HIGH:
                pitch = 1.7f;
                break;
            case FULL:
                pitch = 2.1f;
                break;
            case OVER_FULL:
                pitch = 3.1f;
                break;
        }
        player.playSound(player.getLocation(), sound, 10, pitch);
    }

    private void start() {
        BukkitRunnable timer = new BukkitRunnable() {
            @Override
            public void run() {
                tick();
            }
        };

        if (update!=null) {
            update.cancel();
        }
        update = timer.runTaskTimerAsynchronously(ManaFlow.getInstance(),1,1);
    }

}
