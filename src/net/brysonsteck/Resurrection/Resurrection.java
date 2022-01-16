package net.brysonsteck.Resurrection;

import net.brysonsteck.Resurrection.commands.*;
import net.brysonsteck.Resurrection.player.PlayerListener;
import net.brysonsteck.Resurrection.startup.CheckForUpdate;
import net.brysonsteck.Resurrection.startup.ParseSettings;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Resurrection extends JavaPlugin implements Listener {

    //spigot things
    @Override
    public void onDisable() {
        super.onDisable();
        Logger log = this.getLogger();
        log.info("Resurrection has completed shutdown.");
    }

    @Override
    public void onEnable() {
        Logger log = this.getLogger();
        super.onEnable();
        log.info("---------------------------------------------------------");

        log.info("Resurrection is starting!");
        PluginDescriptionFile pluginInfo = getDescription();
        getServer().getPluginManager().registerEvents(this, this);

        boolean stop = false;
        if (pluginInfo.getVersion().contains("beta")) {
            // beta message
            log.warning("---------------------------------------------------------");
            log.warning("WARNING!!!!");
            log.warning("You are running a beta version of Resurrection!");
            log.warning("");
            log.warning("This means that this plugin is early in development and not completely finished, and as a result you may experience unexpected doodads. Make sure that the plugin is up-to-date for more features and bug fixes. The plugin will now check for updates.");
            log.warning("---------------------------------------------------------");
        } else if (Bukkit.getVersion().contains("1.8")) {
            if (!pluginInfo.getDescription().toLowerCase().contains("minecraft 1.8")) {
                log.severe("---------------------------------------------------------");
                log.severe("ERROR!");
                log.severe("This version of Resurrection is not compatible with Minecraft 1.8 due to specific API calls that were changed in subsequent releases of the game. In order to use Resurrection with Minecraft 1.8, you must download the specific Jar titled \"Resurrection_1.8.jar\" listed in the latest release of Resurrection found at https://github.com/brysonsteck/resurrection/releases.");
                log.severe("Resurrection will now disable to prevent crashing.");
                log.severe("---------------------------------------------------------");
                stop = true;
                Bukkit.getPluginManager().disablePlugin(this);
            } else {
                log.info("---------------------------------------------------------");
            }
        } else {
            log.info("---------------------------------------------------------");

        }

        if (!stop) {
            // check for updates
            log.info("Checking for updates...");
            CheckForUpdate check = new CheckForUpdate();
            boolean outdated = false;
            if (check.isSuccess()) {
                String newestVersion = check.getVersion();
                String newestVersionURL = check.getVersionURL();
                if (pluginInfo.getVersion().equals(newestVersion)) {
                    log.info(newestVersion + " is the latest version of Resurrection.");
               } else {
                   log.info("A new version of Resurrection is available! (current: " + pluginInfo.getVersion() + ", newest: " + newestVersion + ")");
                   log.info("You can download the latest release on GitHub here \\/");
                   log.info(newestVersionURL);
                  outdated = true;
               }
            }

            log.info("---------------------------------------------------------");

            log.info("Locating player data and settings files...");
            // check if playerData.resurrection exists
            File playerFile = new File("plugins/playerData.resurrection");
            File settingsFile = new File("plugins/settings.resurrection");

            boolean fileFail = false;
            if (!playerFile.exists()) {
                log.info("Player data file does not exist. Creating now in the \"plugins\" directory...");
                try {
                    playerFile.createNewFile();
                    log.info("Player data file created successfully.");
                } catch (IOException e) {
                    log.severe("An error has occurred creating the player data file!");
                    e.printStackTrace();
                    log.severe("This file is crucial to Resurrection. Since the file could not be created, the plugin will now stop.");
                    Bukkit.getPluginManager().disablePlugin(this);
                    fileFail = true;
                }
            } else {
                log.info("The player data file has been found!");
            }
            if (!settingsFile.exists()) {
                log.info("Settings file does not exist. Creating now in the \"plugins\" directory...");
                new ParseSettings();
                log.info("Settings file created successfully.");
            } else {
                log.info("The settings file has also been found!");
            }

            ParseSettings parseSettings = new ParseSettings();

            log.info("---------------------------------------------------------");

            if (parseSettings.isSettingsComplete() && !fileFail) {
                if (Boolean.parseBoolean(parseSettings.getSetting("debug"))) {
                    log.warning("[Res. DEBUG]: DEBUG MODE ENABLED!");
                    log.warning("[Res. DEBUG]: Resurrection's debug mode has been enabled in the settings file. All debug messages after this disclaimer will be broadcasted (sent to everyone) prefaced with the tag \"[Res. DEBUG]\" and sent in bold yellow text. Several messages may be sent at a time. Therefore, debug mode should be disabled for anything other than, well, debugging.");
                    log.warning("---------------------------------------------------------");
                }

                log.info("Essential files found and valid. Registering listeners and adding commands...");
                // register listener
                this.getServer().getPluginManager().registerEvents(new PlayerListener(parseSettings), this);

                // register commands
                this.getCommand("about").setExecutor(new CommandAbout(parseSettings.getSetting("debug"), pluginInfo.getVersion(), outdated));
                this.getCommand("bug").setExecutor(new CommandBug(parseSettings.getSetting("debug")));
                this.getCommand("resurrect").setExecutor(new CommandResurrect(parseSettings.getSetting("debug")));
                this.getCommand("howlong").setExecutor(new CommandHowLong(parseSettings.getSetting("debug")));
                this.getCommand("source").setExecutor(new CommandSource(parseSettings.getSetting("debug")));
                this.getCommand("dead").setExecutor(new CommandDead(parseSettings.getSetting("debug")));

                log.info("---------------------------------------------------------");
                log.info("Successfully Started!");
                log.info("---------------------------------------------------------");
            }
        }

    }

    public static void main(String[] args) {}

}
