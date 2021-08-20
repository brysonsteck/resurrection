# Example Files

These files are crucial to Resurrection as they contain important timing data and resources for the plugin. 

This directory contains sample files for you to visualize what these files will/may contain. The plugin creates these files in the same directory as the Spigot server `plugins` directory.

Avoid touching these files while the plugin is enabled and avoid deleting or moving the files at any time after it's creation to prevent breaking the plugin.

## Formatting of `playerData.resurrection`

This file should only contain one line of data at all times as new users are appended to such line. 

The file is read as follows:
```
;username,dead,timeUntilResurrection;.............
```

* `;` are seperators between player data
* `username` is the String of the player's display name
    * This is planned to change to the player's UUID as development progresses.
* `dead` is a boolean indicating if the player has died.
* `timeUntilResurrection` is a long containing the milliseconds for when the user shall be resurrected.
    * This value is reset to 0 if the `dead` flag is false.

## Formatting of `settings.resurrection`

The file contains two types of lines: comments and settings.
```
# Comments start the line with a '#'. Anything in the line will be ignored by Resurrection.
# Settings contain the name of the settings, a '=' and then it's value as shown below.
setting=value
```

Currently, Resurrection looks for two settings:
* `resurrection_time`
    * The time between death and respawn (known as "resurrection" in my docs) in milliseconds. Value must be a number (long to be specific). Default value is 24 hours/`86400000`.
* `debug`
    * Displays debug messages for the command sender. Value must be a boolean (true/false). Default value is `false`.

If any settings are missing or have an invalid value, the plugin will shutdown and notify you of what needs to be changed.
