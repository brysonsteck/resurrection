# Resurrection

Resurrection is a Spigot Minecraft Server plugin that forces players to wait a full 24 hours before rejoining the world. This allows for tactical planning for games such as faction survival and others as it can severely penalize an entire team if care is not taken.

This plugin currently runs on 1.16.5 servers and worlds with 1.17.\* support expected in the future.

# Commands

* `/about`
    * Displays information about the plugin, manually checks for updates.
* `/resurrect PLAYER`
    * An operator-only command. Manually resurrects a player if they are dead.
* `/howlong [PLAYER]` *(Currently not implemented)*
    * Shows the player how long they (or the specified player) have until they are resurrected.

# Files

This program automatically generates a 'player data' file in the same directory as the server jar. This file is important to make sure that the resurrection timing stays in effect after a server shutdown. If this file is deleted, moved or non-existent, a blank file will be created.

**Please be aware that removing this file may cause the plugin to behave differently,** such as holding a player in a dead state for all of time.
