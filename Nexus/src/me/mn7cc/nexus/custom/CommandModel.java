package me.mn7cc.nexus.custom;

import java.util.HashMap;
import java.util.Map.Entry;

public class CommandModel {
	
	private boolean requiresPlayer;
	private String permission;
	private String usage;
	private HashMap<Integer, ArgumentModel> argumentModels;
	
	public CommandModel(boolean requiresPlayer, String permission, String usage, ArgumentModel... argumentModels) {
		this.requiresPlayer = requiresPlayer;
		this.permission = permission;
		this.usage = usage;
		this.argumentModels = new HashMap<Integer, ArgumentModel>();
		for(ArgumentModel argumentModel : argumentModels) this.argumentModels.put(argumentModel.getIndex(), argumentModel);
	}
	
	
	public boolean doesRequirePlayer() { return requiresPlayer; }
	public String getPermission() { return permission; }
	public String getUsage() { return usage; }
	public HashMap<Integer, Argument> getArguments() {
		HashMap<Integer, Argument> arguments = new HashMap<Integer, Argument>();
		for(Entry<Integer, ArgumentModel> entry : argumentModels.entrySet()) arguments.put(entry.getKey(), entry.getValue().getArgument());
		return arguments;
	}
	
}
