# Resurrection

Resurrection is a Spigot Minecraft Server plugin that forces players to wait a certain amount of time before rejoining the world. This allows for tactical planning for games such as faction survival and other PvP gamemodes as it can severely penalize an entire team if care is not taken.

Resurrection is intended to make players wait long amounts of time between death and respawn, preferably 24 hours minimum. Resurrection sets the time to be 24 hours by default. However, you can make players to wait any amount of time you wish, whether shorter or longer than the default 24 hours, but your experience with this plugin may be impacted.

## Compatibility 

Tested Versions: 1.13, 1.14, 1.15, 1.16, 1.17

Native Version: 1.16

You can keep track of compatibility with other versions by viewing my [TODO list.](TODO.md)

## Commands

* `/about`
    * Displays information about the plugin, including links to download, and also warns the command-runner if the plugin is outdated. 
* `/bug`
    * Displays contact information in case a bug occurs, such as links to the GitHub issues page and the Google Form.
* `/howlong [PLAYER]`
    * Shows the player how long they (or the specified player) have until they are resurrected.
    * This command requires a player to be specified when ran from the console. 
* `/resurrect PLAYER`
    * Manually resurrects a player if they are dead.
    * Operator-only command. 
* `/source`
    * Informs the user that this plugin is free and open source under the [GNU Affero General Public License Version 3.](LICENSE)

## Files

This program automatically generates a [`playerData.resurrection`](data/playerData.resurrection) and a [`settings.resurrection`](data/settings.resurrection) file in the `plugins` directory of your server. These files contain data for each player relevant for the plugin and settings for the plugin. It is important for these files to be present (and **UNMODIFIED** in the case of the `playerData` file) to make sure that the respawn timing stays in effect after a server shutdown and for the proper functioning of the plugin in general. If these files are deleted, moved or do not exist, blank/default files will be created in it's place upon enabling.

***Please be aware that modifying, moving or deleting this file after it's creation or while the plugin is enabled may cause the plugin to behave erratically,*** such as holding a player in a dead state for all of time and eternity (assuming you don't use commands to revert it or you delete the world). If you choose to do so, do it at your own risk. 

## Bugs and Feedback

If you have encountered a bug, would like to suggest new features or simply provide feedback, I'd highly recommend you use GitHub issues in order to do so. However, I am aware that people visit this site and are so uncultured that they don't know what they are looking at (jk i love all of you). In that case, I created this [Google Form](https://docs.google.com/forms/d/e/1FAIpQLSd8RWwcGTy7rtqPl4J7h1UWE-H0KKp1Usr1NOFdgZkVLmLUtw/viewform?usp=sf_link) as a secondary option to submit issues if you wish. Just know that you don't have as much flexibility with reporting things as you would here using GitHub issues. All reports from the Google Form are posted and tracked through GitHub issues regardless.

## License and Copyright Notice

All files in this repository are subject to copyright under the GNU Affero General Public License Version 3. To read the full license, click [here.](LICENSE)

Resurrection is a Minecraft plugin that forces players to wait a certain amount of time before respawning.
Copyright &copy; 2021 Bryson Steck

Resurrection is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Resurrection is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
