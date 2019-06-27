package dev.nuer.pp.enable;

import dev.nuer.pp.playerData.listeners.DataCreationOnJoin;
import dev.nuer.pp.tiers.listeners.PlayerTierListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.io.File;

/**
 * Class that handles setting up the plugin on start
 */
public class SetupManager {

    /**
     * Loads the files into the file manager
     *
     * @param fileManager FileManager, the plugins file manager
     */
    public static void setupFiles(FileManager fileManager) {
        fileManager.add("config", "pass+.yml");
        fileManager.add("messages", "messages.yml");
        fileManager.add("tier_config", "tiers" + File.separator + "tier-config.yml");
//        fileManager.add("tier_list", "tiers" + File.separator + "tier-list.yml");
    }

    /**
     * Register all of the events for the plugin
     *
     * @param instance Plugin, the main plugin instance
     */
    public static void registerEvents(Plugin instance) {
        PluginManager pm = instance.getServer().getPluginManager();
        pm.registerEvents(new DataCreationOnJoin(), instance);
        pm.registerEvents(new PlayerTierListener(), instance);
    }
}