package dev.nuer.pp.cmd.adminSub;

import dev.nuer.pp.PassPlus;
import dev.nuer.pp.enable.FileManager;
import dev.nuer.pp.playerData.PlayerDataManager;
import dev.nuer.pp.tiers.PlayerTierManager;
import dev.nuer.pp.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class GiveCmd {

    public static void onCmd(CommandSender sender, String[] args) {
        Player target = null;
        try {
            target = Bukkit.getPlayer(args[1]);
        } catch (Exception e) {
            if (sender instanceof Player) {
                MessageUtil.message("messages", "command-debug", (Player) sender,
                        "{reason}", "The player you tried to give Pass+ cannot be found, please review the players name.");
            } else {
                PassPlus.log.severe("The player you tried to give Pass+ cannot be found, please review the players name.");
            }
        }
        //Permissions were not working as intended, switch to data boolean
        PlayerDataManager.giveCopy(target);
        //Send the admin a message
        if (sender instanceof Player) {
            MessageUtil.message("messages", "give-success", (Player) sender, "{player}", target.getName());
        }
        //Set their new tier (tier 1)
        if (PlayerTierManager.getTier(target) == 0) {
            PlayerTierManager.incrementTier(target, 1);
        }
        //Send the player a message
        if (FileManager.get("config").getBoolean("give-notification.message.enabled")) {
            MessageUtil.message("config", "give-notification.message.text", target);
        }
        //Play a sound
        if (FileManager.get("config").getBoolean("give-notification.sound.enabled")) {
            target.playSound(target.getLocation(),
                    Sound.valueOf(FileManager.get("config").getString("give-notification.sound.type").toUpperCase()),
                    FileManager.get("config").getInt("give-notification.sound.volume"),
                    FileManager.get("config").getInt("give-notification.sound.pitch"));
        }
        //Shoot a firework
        if (FileManager.get("config").getBoolean("give-notification.firework.enabled")) {
            target.getWorld().spawnEntity(target.getLocation(), EntityType.FIREWORK);
        }
        PassPlus.log.info(target.getName() + " has received a copy of Pass+, the executor was: " + sender.getName() + ".");
    }
}
