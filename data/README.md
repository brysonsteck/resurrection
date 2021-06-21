# `playerData.resurrection` Example File

This file is crucial to Resurrection as it contains important timing data for the plugin. This file should only contain one line of data at all times and new users are appended to such line. 

This directory contains a sample file for you to visualize what this file will contain. The plugin creates this file in the same directory as the Spigot server jar file.

Avoid touching this file while the plugin is enabled and avoid deleting or moving the file at any time after it's creation to prevent breaking the plugin.

## Formatting

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

