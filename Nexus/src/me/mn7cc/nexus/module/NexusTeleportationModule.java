package me.mn7cc.nexus.module;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import me.mn7cc.nexus.custom.Argument;
import me.mn7cc.nexus.custom.ArgumentModel;
import me.mn7cc.nexus.custom.NexusModule;
import me.mn7cc.nexus.util.MessageUtils;
import me.mn7cc.nexus.custom.CommandContent;
import me.mn7cc.nexus.custom.CommandManager;
import me.mn7cc.nexus.custom.CommandModel;
import me.mn7cc.nexus.custom.NexusCommandBuilder;
import me.mn7cc.nexus.custom.INexusCommand;
import me.mn7cc.nexus.custom.INexusModule;
import me.mn7cc.nexus.custom.Message;

public class NexusTeleportationModule extends NexusModule implements INexusModule, Listener {
	
	public NexusTeleportationModule(boolean enabled) {
		super(enabled);
	}
	
	@Override
	public void enableModule(Plugin plugin) {
		
        CommandManager.registerCommand(
        		new NexusCommandBuilder("tp", "teleport")
        		.setCommand(new CommandTP())
        		.getNexusCommand());
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
		
	}

	@Override
	public void disableModule(Plugin plugin) {
		
	}
	
	public static class CommandTP extends CommandModel implements INexusCommand {
		
		public CommandTP() {
			super(true, "nexus.tp", "/tp <player> [target]",
			new ArgumentModel(0, "<player>", Argument.Type.PLAYER),
			new ArgumentModel(1, "[target]", Argument.Type.PLAYER, true));
		}

		@Override
		public void execute(CommandSender sender, String label, String[] args, CommandContent content) {
			
			Player source = (Player) sender;
			Player player = content.getPlayer(0);
			Player target = content.getPlayer(1);

			if(target == null) {
				source.teleport(player);
				MessageUtils.send(source, Message.TELEPORT_SOURCE, player.getName());
				if(player != source) MessageUtils.send(player, Message.TELEPORT_TARGET, source.getName());
				return;
			}

			player.teleport(target);
			if(player != source && target != source) MessageUtils.send(source, Message.TELEPORT_OTHERS, player.getName(), target.getName());
			MessageUtils.send(player, Message.TELEPORT_SOURCE_OTHERS, source.getName(), target.getName());
			if(player != target) MessageUtils.send(target, Message.TELEPORT_TARGET_OTHERS, source.getName(), player.getName());
			
		}

	}

}
