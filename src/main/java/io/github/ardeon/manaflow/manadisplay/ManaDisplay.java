package io.github.ardeon.manaflow.manadisplay;

import io.github.ardeon.manaflow.Mana;
import io.github.ardeon.manaflow.ManaState;

public interface ManaDisplay {
    void update(float current, float max, ManaState state, int cooldown);
    void remove();
}
