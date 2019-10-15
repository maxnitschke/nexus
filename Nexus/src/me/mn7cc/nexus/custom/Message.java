package me.mn7cc.nexus.custom;

public enum Message {
	
	INSUFFICIENT_PERMISSIONS("&4You do not have permissions!"),
	COMMAND_ERROR_ARGUMENT_REQUIRES_A_NUMBER("&4Error: &7Argument {0} requires a number!"),
	COMMAND_ERROR_PLAYER_NOT_FOUND("&4Error: &7Player not found!"),
	COMMAND_ERROR_REQUIRES_PLAYER_AS_SENDER("&4This command cannot be run by the console!"),
	COMMAND_ERROR_TOO_FEW_ARGUMENTS("&4Error: &7Too few arguments!"),
	COMMAND_ERROR_TOO_MANY_ARGUMENTS("&4Error: &7Too many arguments!"),
	COMMAND_ERROR_WORLD_NOT_FOUND("&4Error: &7Player not found!"),
	COMMAND_HELP_HEADER("&c&l{0} &7&lHelp"),
	COMMAND_HELP_COMMAND("&8&l> &f{0}"),
	COMMAND_USAGE("&4Usage: &f{0}"),
	TELEPORT_OTHERS("&c&l! &7Teleported &f{0} &7to &f{1}&7."),
	TELEPORT_SOURCE("&c&l! &7Teleported to &f{0}&7."),
	TELEPORT_SOURCE_OTHERS("&c&l! &7&f{0} &7has teleported you to &f{1}&7."),
	TELEPORT_TARGET("&c&l! &f{0} &7has teleported to you."),
	TELEPORT_TARGET_OTHERS("&c&l! &f{0} &7has teleported &f{1} &7to you.");
	
	private String text;
	
	private Message(String text) {
		this.text = text;
	}
	
	public String getText() { return text; }
	
}
