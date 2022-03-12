package io.github.ardeon.manaflow;

public class Mana {
    private float max;
    private float current = 0;
    private float restorePerSecond;
    private float removeOveragePerSecond;
    private int currentRestoreDelayInTicks = 0;
    private int restoreDelayInTicks = 100;
    private ManaState currentState = ManaState.EMPTY;

    public Mana (float max, float restorePerSecond, float removeOveragePerSecond) {
        this.max = max;
        this.restorePerSecond = restorePerSecond;
        this.removeOveragePerSecond = removeOveragePerSecond;
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

    public ManaState calculateTick() {
        return calculateTick(0);
    }

    public ManaState getState() {
        return currentState;
    }

    private ManaState calculateState() {
        if (max!=0) {
            float value = current / max;
            if (value > 1)
                return ManaState.OVER_FULL;
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

    public synchronized ManaState calculateTick(float amplifier) {
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
}
