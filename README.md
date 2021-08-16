# Resurrection

Resurrection is a Spigot Minecraft Server plugin that forces players to wait a full 24 hours before rejoining the world. This allows for tactical planning for games such as faction survival as it can severely penalize an entire team if care is not taken.

**This plugin is currently in a beta state.** This means that the plugin's functionality may not be stable or as intended. Opening issues here on GitHub or contacting me on Discord at bryzinga#9971 is heavily recommended if a bug occurs. 

## Compatibility 

Tested Versions: 1.16
Native Version: 1.16

## Commands

* `/about`
    * Displays information about the plugin, including links to GitHub and Spigot, and also warns the player if the plugin is outdated. 
* `/bug`
    * Displays contact information in case a bug occurs, such as links to the GitHub issues page and my Discord.
* `/howlong [PLAYER]`
    * Shows the player how long they (or the specified player) have until they are resurrected.
    * This command requires a player to be specified when ran from the console. 
* `/resurrect PLAYER`
    * Manually resurrects a player if they are dead.
    * Operator-only command. 

## Files

This program automatically generates a [`playerData.resurrection`](data/playerData.resurrection) file in the same directory as the plugin Jar file. This file contains data for each player relevant for the plugin and is important to make sure that the resurrection timing stays in effect after a server shutdown. If this file is deleted, moved or doesn't exist, a blank file will be created in it's place upon enabling.

***Please be aware that modifying, moving or removing this file after it's creation or while the plugin is enabled may cause the plugin to behave erratically,*** such as holding a player in a dead state for all of time and eternity. If you choose to do so, do it at your own risk. 
