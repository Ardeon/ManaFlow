package io.github.ardeon.manaflow.manadisplay.bossbar;

import io.github.ardeon.manaflow.Mana;
import io.github.ardeon.manaflow.ManaState;
import io.github.ardeon.manaflow.manadisplay.ManaDisplay;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossBarDisplay implements ManaDisplay {
    private final Player player;
    private final BossBar bar;

    public BossBarDisplay(Player player){
        this.player = player;
        bar = Bukkit.createBossBar("Mana", BarColor.BLUE, BarStyle.SOLID);
        bar.addPlayer(player);
        bar.setVisible(true);
    }

    @Override
    public void update(float current, float max, ManaState state, int cooldown) {
        if (current < 0 || max <= 0)
            return;
        boolean isOwerflow = (current / max) > 1;
        bar.setProgress(isOwerflow ? 1 : current / max);
        BarColor color = BarColor.BLUE;
        switch (state){
            case LOW -> color = BarColor.RED;
            case OVERFLOWING -> color = BarColor.PINK;
        }
        bar.setColor(color);
        bar.setTitle(String.format("Mana: (%d / %d)%s", (int)current, (int)max, state == ManaState.OVERFLOWING ? " OVERFLOWING" : ""));
    }

    @Override
    public void remove() {
        bar.setVisible(false);
        bar.removeAll();
    }
}
