# About
Simple mana system plugin for paper
## How to add to plugin
plugin.yml
```sh
depend:
- ManaFlow
```
or
```sh
softdepend:
- ManaFlow
```
## How to use
Get player mana
```sh
Mana mana = ManaFlow.getInstance().getMainManaHelper().getMana(player);

// getting the maximum possible number of mana points
// player can have more mp than this value
// Overages removes over time
// until the maximum is reached, mana is restored at a rate of mana.getRestorePerSecond()
float value = mana.getMax();

// getting number of mp restored per second
float value = mana.getRestorePerSecond();

// get current mana state. can be 
// EMPTY,
// LOW,
// MEDIUM,
// HIGH,
// FULL,
// OVERFLOWING
ManaState state = mana.getState();

// add 120.5 mp, if current state is OVERFLOWING do nothing (even if canOverFlow = true)
// result - shows whether there have been changes
// can used for mana potion
boolean canOverFlow = true;
boolean result = mana.restoreMana(120.5, canOverFlow);

// trying to use 40 mp
// if player has the required amount mp - removing this amount and returning true;
// else returning false
boolean result = mana.consume(40);
```