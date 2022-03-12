package io.github.ardeon.manaflow;

public class EventHelper {
    public EventHelper(ManaFlow manaFlow) {
        manaFlow.getServer().getPluginManager().registerEvents(new MainEventListener(manaFlow), manaFlow);
    }
}
