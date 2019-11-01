package me.mn7cc.nexus.module;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import me.mn7cc.nexus.Nexus;
import me.mn7cc.nexus.NexusCommandManager;
import me.mn7cc.nexus.NexusDatabase;
import me.mn7cc.nexus.custom.Argument;
import me.mn7cc.nexus.custom.ArgumentModel;
import me.mn7cc.nexus.custom.NexusModule;
import me.mn7cc.nexus.custom.NexusPlayer;
import me.mn7cc.nexus.custom.NexusWarp;
import me.mn7cc.nexus.event.NexusWarpDeleteEvent;
import me.mn7cc.nexus.event.NexusWarpSetEvent;
import me.mn7cc.nexus.event.NexusWarpTeleportEvent;
import me.mn7cc.nexus.util.CommandUtils;
import me.mn7cc.nexus.util.EventUtils;
import me.mn7cc.nexus.util.MessageUtils;
import me.mn7cc.nexus.util.StringUtils;
import me.mn7cc.nexus.custom.CommandContent;
import me.mn7cc.nexus.custom.CommandModel;
import me.mn7cc.nexus.custom.NexusCommandBuilder;
import me.mn7cc.nexus.custom.INexusCommand;
import me.mn7cc.nexus.custom.INexusModule;
import me.mn7cc.nexus.custom.Message;

public class NexusWarpsModule extends NexusModule implements INexusModule, Listener {
	
	public NexusWarpsModule(Nexus instance, boolean enabled) {
		super(instance, enabled);
	}
	
	@Override
	public void enableModule() {
		
		Nexus instance = getNexusInstance();
		
        instance.getCommandManager().registerCommand(
        		new NexusCommandBuilder(instance)
        		.setCommand(new CommandWarp(), "warp", "waypoint")
        		.addSubCommand(new CommandWarpSet(), "set", "s", "create", "c", "new", "n")
        		.addSubCommand(new CommandWarpDelete(), "delete", "del", "d", "remove", "rem", "r")
        		.getNexusCommand());
		
		Bukkit.getServer().getPluginManager().registerEvents(this, instance);
		
	}

	@Override
	public void disableModule() {
		
	}
	
	private class CommandWarp extends CommandModel implements INexusCommand {
		
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
				
				if(EventUtils.isCancelled(new NexusWarpTeleportEvent(nexusWarp, source))) return;
				
				nexusWarp.spawnPlayer(source);
				
				if(nexusWarp.hasMessage()) MessageUtils.send(sender, Message.WARP_MESSAGE_CUSTOM, nexusWarp.getMessage());
				else MessageUtils.send(sender, Message.WARP_MESSAGE_DEFAULT, nexusWarp.getId(), source.getName());
				
				return;
				
			}
			
			if(!CommandUtils.hasPermission(sender, "nexus.warp.others", Message.INSUFFICIENT_PERMISSIONS_WARP_OTHERS)) return;
			if(players.size() > 1 && !CommandUtils.hasPermission(sender, "nexus.warp.multiple", Message.INSUFFICIENT_PERMISSIONS_WARP_MULTIPLE)) return;
			
			MessageUtils.send(sender, Message.WARP_TELEPORT_OTHERS, StringUtils.toString(players), nexusWarp.getId());
			
			for(Player player : players) {
				
				if(EventUtils.isCancelled(new NexusWarpTeleportEvent(nexusWarp, player))) continue;
				
				nexusWarp.spawnPlayer(player);
				
				if(nexusWarp.hasMessage()) MessageUtils.send(player, Message.WARP_MESSAGE_CUSTOM, nexusWarp.getMessage());
				else MessageUtils.send(player, Message.WARP_MESSAGE_DEFAULT, nexusWarp.getId(), player.getName());
				
			}
			
		}

	}
	
	private class CommandWarpSet extends CommandModel implements INexusCommand {
		
		public CommandWarpSet() {
			super(true, "nexus.warp.set", "/warp set <id>",
			new ArgumentModel(1, "<id>", Argument.Type.STRING));
		}

		@Override
		public void execute(CommandSender sender, String label, String[] args, CommandContent content) {
			
			Nexus instance = getNexusInstance();
			Player source = (Player) sender;
			String id = content.getString(1).toLowerCase();
			NexusPlayer nexusPlayer = NexusPlayer.fromDatabase(instance.getDatabase(), source);
			int warpCount = nexusPlayer.getWarpCount();
			int warpLimit = nexusPlayer.getWarpLimit();
			
			if(warpCount >= 1 && warpLimit <= 1) {
				MessageUtils.send(sender, Message.INSUFFICIENT_PERMISSIONS_WARP_SET_MULTIPLE);
				return;
			}
			
			if(warpCount >= warpLimit) {
				MessageUtils.send(sender, Message.WARP_LIMIT_REACHED, StringUtils.toString(warpCount), StringUtils.toString(warpLimit));
				return;
			}
			
			if(NexusWarp.fromDatabase(instance.getDatabase(), id) != null) {
				MessageUtils.send(sender, Message.COMMAND_ERROR_WARP_ALREADY_EXISTS);
				return;
			}
			
			NexusWarp nexusWarp = new NexusWarp(id, instance.getSettings().getServerId(), source);
			
			if(EventUtils.isCancelled(new NexusWarpSetEvent(nexusWarp))) return;
			
			nexusWarp.insert(instance.getDatabase());
			nexusPlayer.setWarpCount(warpCount + 1);
			nexusPlayer.update(instance.getDatabase());
			
			MessageUtils.send(sender, Message.WARP_SET, id);
			
		}

	}
	
	private class CommandWarpDelete extends CommandModel implements INexusCommand {
		
		public CommandWarpDelete() {
			super(true, "nexus.warp.delete", "/warp delete <id>",
			new ArgumentModel(1, "<id>", Argument.Type.STRING));
		}

		@Override
		public void execute(CommandSender sender, String label, String[] args, CommandContent content) {
			
			Nexus instance = getNexusInstance();
			Player source = (Player) sender;
			String id = content.getString(1).toLowerCase();
			NexusPlayer nexusPlayer = NexusPlayer.fromDatabase(instance.getDatabase(), source);
			int warpCount = nexusPlayer.getWarpCount();
			
			NexusWarp nexusWarp = NexusWarp.fromDatabase(instance.getDatabase(), id);
			
			if(nexusWarp == null) {
				MessageUtils.send(sender, Message.COMMAND_ERROR_WARP_NOT_FOUND);
				return;
			}
			
			if(!nexusWarp.isOwner(source.getUniqueId().toString()) && !CommandUtils.hasPermission(sender, "nexus.warp.others", Message.INSUFFICIENT_PERMISSIONS_WARP_OTHERS)) return;
			
			if(EventUtils.isCancelled(new NexusWarpDeleteEvent(nexusWarp))) return;
			
			nexusWarp.delete(instance.getDatabase());
			nexusPlayer.setWarpCount(warpCount - 1);
			nexusPlayer.update(instance.getDatabase());
			
			MessageUtils.send(sender, Message.WARP_DELETED, id);
			
		}

	}

}
