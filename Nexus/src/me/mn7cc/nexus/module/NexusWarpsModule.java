package me.mn7cc.nexus.module;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import me.mn7cc.nexus.Database;
import me.mn7cc.nexus.custom.Argument;
import me.mn7cc.nexus.custom.ArgumentModel;
import me.mn7cc.nexus.custom.NexusModule;
import me.mn7cc.nexus.custom.NexusWarp;
import me.mn7cc.nexus.event.NexusWarpTeleportEvent;
import me.mn7cc.nexus.util.CommandUtils;
import me.mn7cc.nexus.util.EventUtils;
import me.mn7cc.nexus.util.MessageUtils;
import me.mn7cc.nexus.util.StringUtils;
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
        		new NexusCommandBuilder(new CommandWarp(), "warp", "waypoint")
        		.addSubCommand(new CommandWarpSet(), "set", "s", "create", "c")
        		.addSubCommand(new CommandWarpDelete(), "delete", "del", "d", "remove", "rem", "r")
        		.getNexusCommand());
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
		
	}

	@Override
	public void disableModule(Plugin plugin) {
		
	}
	
	public static class CommandWarp extends CommandModel implements INexusCommand {
		
		public CommandWarp() {
			super(true, "nexus.warp", "/warp <warp> [players]",
			new ArgumentModel(0, "<warp>", Argument.Type.NEXUS_WARP),
			new ArgumentModel(1, "[players]", Argument.Type.PLAYERS, true));
		}

		@Override
		public void execute(CommandSender sender, String label, String[] args, CommandContent content) {
			
			Player source = (Player) sender;
			NexusWarp nexusWarp = content.getNexusWarp(0);
			List<Player> players = content.getPlayers(1);
			
			if(players == null) {
				
				if(EventUtils.isCancelled(new NexusWarpTeleportEvent(source, nexusWarp))) return;
				
				nexusWarp.spawnPlayer(source);
				
				if(nexusWarp.hasMessage()) MessageUtils.send(sender, Message.WARP_MESSAGE_CUSTOM, nexusWarp.getMessage());
				else MessageUtils.send(sender, Message.WARP_MESSAGE_DEFAULT, nexusWarp.getId(), source.getName());
				
				return;
				
			}
			
			if(!CommandUtils.hasPermission(sender, "nexus.warp.others", Message.INSUFFICIENT_PERMISSIONS_WARP_OTHERS)) return;
			if(players.size() > 1 && !CommandUtils.hasPermission(sender, "nexus.warp.multiple", Message.INSUFFICIENT_PERMISSIONS_WARP_MULTIPLE)) return;
			
			MessageUtils.send(sender, Message.WARP_TELEPORT_OTHERS, StringUtils.toString(players), nexusWarp.getId());
			
			for(Player player : players) {
				
				if(EventUtils.isCancelled(new NexusWarpTeleportEvent(player, nexusWarp))) continue;
				
				nexusWarp.spawnPlayer(player);
				
				if(nexusWarp.hasMessage()) MessageUtils.send(player, Message.WARP_MESSAGE_CUSTOM, nexusWarp.getMessage());
				else MessageUtils.send(player, Message.WARP_MESSAGE_DEFAULT, nexusWarp.getId(), player.getName());
				
			}
			
		}

	}
	
	public static class CommandWarpSet extends CommandModel implements INexusCommand {
		
		public CommandWarpSet() {
			super(true, "nexus.warp.set", "/warp set <id>",
			new ArgumentModel(1, "<id>", Argument.Type.STRING));
		}

		@Override
		public void execute(CommandSender sender, String label, String[] args, CommandContent content) {
			
			Player source = (Player) sender;
			String id = content.getString(1).toLowerCase();
			
			if(Database.getWarp(id) != null) {
				MessageUtils.send(sender, Message.COMMAND_ERROR_WARP_ALREADY_EXISTS);
				return;
			}
			
			NexusWarp nexusWarp = new NexusWarp(id, source);
			nexusWarp.insert();
			
			MessageUtils.send(sender, Message.WARP_CREATED, id);
			
		}

	}
	
	public static class CommandWarpDelete extends CommandModel implements INexusCommand {
		
		public CommandWarpDelete() {
			super(false, "nexus.warp.delete", "/warp delete <id>",
			new ArgumentModel(1, "<id>", Argument.Type.STRING));
		}

		@Override
		public void execute(CommandSender sender, String label, String[] args, CommandContent content) {
			
			String id = content.getString(1).toLowerCase();
			
			NexusWarp nexusWarp = Database.getWarp(id);
			
			if(nexusWarp == null) {
				MessageUtils.send(sender, Message.COMMAND_ERROR_WARP_NOT_FOUND);
				return;
			}
			
			nexusWarp.delete();
			
			MessageUtils.send(sender, Message.WARP_REMOVED, id);
			
		}

	}

}
