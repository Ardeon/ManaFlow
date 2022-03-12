package io.github.ardeon.manaflow;

import io.github.ardeon.manaflow.gui.GUIEventListener;

public class EventHelper {
    public EventHelper(ManaFlow manaFlow) {
        manaFlow.getServer().getPluginManager().registerEvents(new MainEventListener(manaFlow), manaFlow);
        manaFlow.getServer().getPluginManager().registerEvents(new GUIEventListener(manaFlow), manaFlow);
    }
}
