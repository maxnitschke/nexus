package me.mn7cc.nexus.module;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.mn7cc.nexus.Nexus;
import me.mn7cc.nexus.custom.Argument;
import me.mn7cc.nexus.custom.ArgumentModel;
import me.mn7cc.nexus.custom.NexusModule;
import me.mn7cc.nexus.custom.NexusPlayer;
import me.mn7cc.nexus.util.CommandUtils;
import me.mn7cc.nexus.util.MessageUtils;
import me.mn7cc.nexus.util.StringUtils;
import me.mn7cc.nexus.custom.CommandContent;
import me.mn7cc.nexus.custom.CommandModel;
import me.mn7cc.nexus.custom.NexusCommandBuilder;
import me.mn7cc.nexus.custom.INexusCommand;
import me.mn7cc.nexus.custom.INexusModule;
import me.mn7cc.nexus.custom.Message;

public class NexusTeleportationModule extends NexusModule implements INexusModule, Listener {
	
	public NexusTeleportationModule(Nexus instance, boolean enabled, String name) {
		super(instance, enabled, name);
	}
	
	@Override
	public void enableModule() {
		
		Nexus instance = getNexusInstance();
		
		instance.getCommandManager().registerCommand(
        		new NexusCommandBuilder(instance)
        		.setCommand(new CommandTP(), "tp", "teleport")
        		.getNexusCommand());
		
        instance.getCommandManager().registerCommand(
        		new NexusCommandBuilder(instance)
        		.setCommand(new CommandTPHere(), "tphere", "tph", "summon")
        		.getNexusCommand());
        
        instance.getCommandManager().registerCommand(
        		new NexusCommandBuilder(instance)
        		.setCommand(new CommandTPA(), "tpa", "tpr")
        		.getNexusCommand());
        
        instance.getCommandManager().registerCommand(
        		new NexusCommandBuilder(instance)
        		.setCommand(new CommandTPAHere(), "tpahere", "tprhere")
        		.getNexusCommand());
        
		Bukkit.getServer().getPluginManager().registerEvents(this, instance);
		
	}

	@Override
	public void disableModule() {
		
	}
	
	private class CommandTP extends CommandModel implements INexusCommand {
		
		public CommandTP() {
			super(true, "nexus.tp", "/tp <player(s)> [target]",
			new ArgumentModel(0, "<player>", Argument.Type.PLAYERS),
			new ArgumentModel(1, "[target]", Argument.Type.PLAYER, true));
		}

		@Override
		public void execute(CommandSender sender, String label, String[] args, CommandContent content) {
			
			Player source = (Player) sender;
			List<Player> players = content.getPlayers(0);
			Player target = content.getPlayer(1);

			if(target == null) {
				
				Player player = players.get(0);
				
				source.teleport(player);
				
				MessageUtils.send(source, Message.TELEPORT_SOURCE, player.getName());
				if(player != source) MessageUtils.send(player, Message.TELEPORT_TARGET, source.getName());
				
				return;
				
			}
			
			if(!CommandUtils.hasPermission(sender, "nexus.tp.others", Message.INSUFFICIENT_PERMISSIONS_TELEPORT_OTHERS)) return;
			if(players.size() > 1 && !CommandUtils.hasPermission(sender, "nexus.tp.multiple", Message.INSUFFICIENT_PERMISSIONS_TELEPORT_MULTIPLE)) return;
			
			MessageUtils.send(source, Message.TELEPORT_OTHERS, StringUtils.toString(players), target.getName());
			
			for(Player player : players) {
				
				player.teleport(target);
				
				if(player != source) MessageUtils.send(player, Message.TELEPORT_SOURCE_OTHERS, source.getName(), target.getName());
				if(player != target) MessageUtils.send(target, Message.TELEPORT_TARGET_OTHERS, source.getName(), player.getName());
				
			}
			
		}

	}
	
	private class CommandTPHere extends CommandModel implements INexusCommand {
		
		public CommandTPHere() {
			super(true, "nexus.tphere", "/tphere <player(s)>",
			new ArgumentModel(0, "<player>", Argument.Type.PLAYERS));
		}

		@Override
		public void execute(CommandSender sender, String label, String[] args, CommandContent content) {
			
			Player source = (Player) sender;
			List<Player> players = content.getPlayers(0);

			MessageUtils.send(source, Message.TELEPORT_TARGET, StringUtils.toString(players));
			
			for(Player player : players) {
				
				player.teleport(source);
				
				MessageUtils.send(player, Message.TELEPORT_SOURCE, source.getName());
				
			}
			
		}

	}
	
	private class CommandTPA extends CommandModel implements INexusCommand {
		
		public CommandTPA() {
			super(true, "nexus.tpa", "/tpa <player>",
			new ArgumentModel(0, "<player>", Argument.Type.PLAYER));
		}

		@Override
		public void execute(CommandSender sender, String label, String[] args, CommandContent content) {
			
			Nexus instance = getNexusInstance();
			Player source = (Player) sender;
			Player player = content.getPlayer(0);

			if(source == player) {
				MessageUtils.send(source, Message.TELEPORT_REQUEST_SELF);
				return;
			}
			
			NexusPlayer nexusPlayer = NexusPlayer.fromDatabase(instance.getDatabase(), player);
			
			if(nexusPlayer.getSession().hasTPARequest(source) || nexusPlayer.getSession().hasTPAHereRequest(source)) {
				MessageUtils.send(source, Message.TELEPORT_REQUEST_STILL_PENDING);
				return;
			}
			
			nexusPlayer.getSession().addTPARequest(source, System.currentTimeMillis() + instance.getSettings().getTeleportRequestTimeout());
			
			MessageUtils.send(source, Message.TELEPORT_REQUEST_SENT, player.getName());
			MessageUtils.send(player, Message.TELEPORT_REQUEST, source.getName());
			
		}

	}
	
	private class CommandTPAHere extends CommandModel implements INexusCommand {
		
		public CommandTPAHere() {
			super(true, "nexus.tpahere", "/tpahere <player>",
			new ArgumentModel(0, "<player>", Argument.Type.PLAYER));
		}

		@Override
		public void execute(CommandSender sender, String label, String[] args, CommandContent content) {
			
			Nexus instance = getNexusInstance();
			Player source = (Player) sender;
			Player player = content.getPlayer(0);

			if(source == player) {
				MessageUtils.send(source, Message.TELEPORT_REQUEST_SELF);
				return;
			}
			
			NexusPlayer nexusPlayer = NexusPlayer.fromDatabase(instance.getDatabase(), player);
			
			if(nexusPlayer.getSession().hasTPARequest(source) || nexusPlayer.getSession().hasTPAHereRequest(source)) {
				MessageUtils.send(source, Message.TELEPORT_REQUEST_STILL_PENDING);
				return;
			}
			
			nexusPlayer.getSession().addTPAHereRequest(source, System.currentTimeMillis() + instance.getSettings().getTeleportRequestTimeout());
			
			MessageUtils.send(source, Message.TELEPORT_REQUEST_SENT, player.getName());
			MessageUtils.send(player, Message.TELEPORT_REQUEST_HERE, source.getName());
			
		}

	}

}
