package me.mn7cc.nexus.custom;

import org.bukkit.command.CommandSender;

public interface INexusCommand {
	
	void execute(CommandSender sender, String label, String[] args, CommandContent content);

}
