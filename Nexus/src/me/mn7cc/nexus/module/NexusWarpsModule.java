package me.mn7cc.nexus.module;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import me.mn7cc.nexus.custom.Argument;
import me.mn7cc.nexus.custom.ArgumentModel;
import me.mn7cc.nexus.custom.NexusModule;
import me.mn7cc.nexus.custom.NexusWarp;
import me.mn7cc.nexus.event.NexusWarpTeleportEvent;
import me.mn7cc.nexus.util.EventUtils;
import me.mn7cc.nexus.util.MessageUtils;
import me.mn7cc.nexus.custom.CommandContent;
import me.mn7cc.nexus.custom.CommandManager;
import me.mn7cc.nexus.custom.CommandModel;
import me.mn7cc.nexus.custom.NexusCommandBuilder;
import me.mn7cc.nexus.custom.INexusCommand;
import me.mn7cc.nexus.custom.INexusModule;
import me.mn7cc.nexus.custom.Message;

public class NexusWarpsModule extends NexusModule implements INexusModule, Listener {
	
	public NexusWarpsModule(boolean enabled) {
		super(enabled);
	}
	
	@Override
	public void enableModule(Plugin plugin) {
		
        CommandManager.registerCommand(
        		new NexusCommandBuilder("warp", "waypoint")
        		.setCommand(new CommandWarp())
//        		.addSubCommand("create", new CommandWarpCreate())
//        		.addSubCommand("remove", new CommandWarpRemove())
//        		.addSubCommand("list", new CommandWarpList())
        		.getNexusCommand());
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
		
	}

	@Override
	public void disableModule(Plugin plugin) {
		
	}
	
	public static class CommandWarp extends CommandModel implements INexusCommand {
		
		public CommandWarp() {
			super(true, "nexus.warp", "/warp <warp> [player]",
			new ArgumentModel(0, "<warp>", Argument.Type.NEXUS_WARP),
			new ArgumentModel(1, "[player]", Argument.Type.PLAYER, true));
		}

		@Override
		public void execute(CommandSender sender, String label, String[] args, CommandContent content) {
			
			Player player = (Player) sender;
			NexusWarp nexusWarp = content.getNexusWarp(0);
			Player target = content.getPlayer(1);
			
			if(target == null) {
				
				if(EventUtils.isCancelled(new NexusWarpTeleportEvent(player, nexusWarp))) return;
				
				nexusWarp.spawnPlayer(player);
				
				if(nexusWarp.hasMessage()) MessageUtils.send(sender, Message.WARP_MESSAGE_CUSTOM, nexusWarp.getMessage());
				else MessageUtils.send(sender, Message.WARP_MESSAGE_DEFAULT, nexusWarp.getId(), player.getName());
				
			}
			
		}

	}

}
