package io.github.ardeon.manaflow;

import io.github.ardeon.manaflow.db.Entity;
import io.github.ardeon.manaflow.db.Field;
import io.github.ardeon.manaflow.manadisplay.ManaDisplay;
import io.github.ardeon.manaflow.manadisplay.bossbar.BossBarDisplay;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Mana {
    private final String guid;
    private float max;
    private int restoreDelayInTicks = 100;
    private float restorePerSecond;
    private float removeOveragePerSecond;

    public int getDisplay() {
        return display;
    }

    private int display;

    private Player player;
    private ManaDisplay manaDisplay = null;
    private float current = 0;
    private int currentRestoreDelayInTicks = 0;

    private ManaState currentState = ManaState.EMPTY;

    public  Mana (String guid){
        this(guid, 100, 100, 4, 1, 0);
    }

    public Mana (String guid, float max, int restoreDelayInTicks, float restorePerSecond, float removeOveragePerSecond, int display) {
        this.guid = guid;
        this.max = max;
        this.restoreDelayInTicks = restoreDelayInTicks;
        this.restorePerSecond = restorePerSecond;
        this.removeOveragePerSecond = removeOveragePerSecond;
        this.display = display;
        player = Bukkit.getPlayer(UUID.fromString(guid));
        changeDisplay(display);
    }

    public synchronized void setMax(float value){
        if (value >= 0)
            max = value;
    }

    public float getMax() {
        return max;
    }

    public float getCurrent() {
        return current;
    }

    public synchronized void setCurrent(float value) {
        if (value<0)
            return;
        current = value;
    }

    public void setRestorePerSecond(float restorePerSecond) {
        this.restorePerSecond = restorePerSecond;
    }

    public float getRestorePerSecond() {
        return restorePerSecond;
    }

    public synchronized boolean restoreMana(float value, boolean canOverFlow) {
        if (value < 0)
            return false;
        if (current <= max) {
            value += current;
            if (canOverFlow)
                current = value;
            else {
                if (current == max)
                    return false;
                current = Math.min(max, value);
            }
            return true;
        }
        else
            return false;
    }

    public synchronized boolean consume(float value) {
        if (value < 0)
            return false;
        if (value>current) {
            return false;
        }
        current -= value;
        currentRestoreDelayInTicks = restoreDelayInTicks;
        return true;
    }

    public boolean isOverFull() {
        return current>max;
    }

    protected ManaState calculateTick() {
        return calculateTick(0);
    }

    public ManaState getState() {
        return currentState;
    }

    private ManaState calculateState() {
        if (max!=0) {
            float value = current / max;
            if (value > 1)
                return ManaState.OVERFLOWING;
            if (value == 1)
                return ManaState.FULL;
            if (value > 0.8)
                return ManaState.HIGH;
            if (value > 0.2)
                return ManaState.MEDIUM;
            if (value > 0)
                return ManaState.LOW;

            return ManaState.EMPTY;
        }
        return ManaState.EMPTY;
    }

    protected synchronized ManaState calculateTick(float amplifier) {
        if (isOverFull()) {
            current -= removeOveragePerSecond;
        } else {
            if (currentRestoreDelayInTicks>0){
                currentRestoreDelayInTicks--;
            } else {
                float value = current + (restorePerSecond + amplifier)/ 20;
                current = Math.min(value, max);
            }
        }
        ManaState newState = calculateState();
        if (currentState != newState) {
            currentState = newState;
            return newState;
        }
        return null;
    }

    public void changeDisplay(int value) {
        display = value;
        if (manaDisplay != null)
            manaDisplay.remove();
        manaDisplay = switch (display){
            case 1 -> new BossBarDisplay(player);
            default -> null;
        };
    }

    public void disableDisplay(){
        if (manaDisplay != null){
            manaDisplay.remove();
        }
        manaDisplay = null;
    }

    public void displayMana() {
        if (manaDisplay != null){
            manaDisplay.update(current, max, getState(), currentRestoreDelayInTicks);
        }
    }

    @Override
    public String toString(){
        return "VALUES ('" + guid + "','" +
                max + "','" +
                restoreDelayInTicks + "','" +
                restorePerSecond + "','" +
                removeOveragePerSecond + "','" +
                display + "')";
    }
}
