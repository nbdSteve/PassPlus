package dev.nuer.pp.utils;

import dev.nuer.pp.PassPlus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class VersionUtil implements Listener {
    //Store the resource key from spigot
    private static String resourceKey = "69650";

    /**
     * Checks the latest version against the current version
     *
     * @param player Player, player to send the update message to
     */
    public static void checkVersion(Player player) {
        try {
            URLConnection urlConn = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceKey).openConnection();
            String version = new BufferedReader(new InputStreamReader(urlConn.getInputStream())).readLine();
            if (!version.equalsIgnoreCase(PassPlus.version)) {
                PassPlus.log.severe("There is a new version of Pass+ available for download, please update to the latest version.");
                if (player != null) {
                    MessageUtil.message("messages", "outdated-version", player, "{currentVersion}",
                            PassPlus.version, "{latestVersion}", version);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        if (event.getPlayer().isOp()) {
            Bukkit.getScheduler().runTaskLater(PassPlus.instance, () -> {
                checkVersion(event.getPlayer());
            }, 8L);
        }
    }
}
