package eu.filtastisch.minecraftcompanion.commands;

import eu.filtastisch.minecraftcompanion.MinecraftCompanion;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class SetupCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String aliases, @NotNull String[] args) {
        if (sender instanceof Player p){
            Block target = p.getTargetBlock(new HashSet<>(), 10);
            Location tLoc = target.getLocation();
            MinecraftCompanion.getInstance().setButtonLoc(tLoc);
        }
        return false;
    }
}
