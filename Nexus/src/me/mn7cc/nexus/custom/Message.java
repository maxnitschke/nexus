package me.mn7cc.nexus.custom;

public enum Message {
	
	CHAT_BUTTON_ACCEPT("&a[&lACCEPT&a]"),
	CHAT_BUTTON_ACCEPT_HOVER("&aClick to accept."),
	CHAT_BUTTON_DECLINE("&c[&lDECLINE&c]"),
	CHAT_BUTTON_DECLINE_HOVER("&cClick to decline."),
	CHAT_BUTTON_SPACER(" &8&l| "),
	COMMAND_ERROR_ARGUMENT_REQUIRES_A_NUMBER("&4Error: &7Argument {0} requires a number!"),
	COMMAND_ERROR_PLAYER_NOT_FOUND("&4Error: &7Player not found!"),
	COMMAND_ERROR_REQUIRES_PLAYER_AS_SENDER("&4This command cannot be run by the console!"),
	COMMAND_ERROR_TOO_FEW_ARGUMENTS("&4Error: &7Too few arguments!"),
	COMMAND_ERROR_TOO_MANY_ARGUMENTS("&4Error: &7Too many arguments!"),
	COMMAND_ERROR_WARP_ALREADY_EXISTS("&4Error: &7Warp does already exist!"),
	COMMAND_ERROR_WARP_NOT_FOUND("&4Error: &7Warp not found!"),
	COMMAND_ERROR_WORLD_NOT_FOUND("&4Error: &7Player not found!"),
	COMMAND_HELP_HEADER("&c&l{0} &7&lHelp"),
	COMMAND_HELP_COMMAND("&8&l> &f{0}"),
	COMMAND_USAGE("&4Usage: &f{0}"),
	INSUFFICIENT_PERMISSIONS("&4You do not have permissions!"),
	INSUFFICIENT_PERMISSIONS_TELEPORT_OTHERS("&4You do not have permissions to teleport other players!"),
	INSUFFICIENT_PERMISSIONS_TELEPORT_MULTIPLE("&4You do not have permissions to teleport multiple players!"),
	INSUFFICIENT_PERMISSIONS_WARP_OTHERS("&4You do not have permissions to warp other players!"),
	INSUFFICIENT_PERMISSIONS_WARP_MULTIPLE("&4You do not have permissions to warp multiple players!"),
	PLAYER_JOINED("&f{0} &7joined."),
	PLAYER_JOINED_FIRST("&f{0} &7joined for the first time!"),
	PLAYER_JOINED_NEW_NAME("&f{0} &7joined with new name &f{1}&7."),
	PLAYER_LEFT("&f{0} &7left."),
	TELEPORT_DELAY("&c&l! &7Teleporting in &f{0} &7seconds.."),
	TELEPORT_OTHERS("&c&l! &7Teleported &f{0} &7to &f{1}&7."),
	TELEPORT_REQUEST("&c&l! &f{0} &7has requested to teleported to you."),
	TELEPORT_REQUEST_ACCEPTED("&c&l! &7Teleport request accepted."),
	TELEPORT_REQUEST_ACCEPTED_SOURCE("&c&l! &f{0} &7has accepted your teleport request."),
	TELEPORT_REQUEST_DECLINED("&c&l! &7Teleport request declined."),
	TELEPORT_REQUEST_DECLINED_SOURCE("&c&l! &f{0} &7has declined your teleport request."),
	TELEPORT_REQUEST_HERE("&c&l! &f{0} &7has requested that you teleport to them."),
	TELEPORT_REQUEST_NONE("&c&l! &7No pending request found from that player."),
	TELEPORT_REQUEST_SELF("&c&l! &7You cannot target yourself."),
	TELEPORT_REQUEST_SENT("&c&l! &7Teleport request sent to &f{0}&7."),
	TELEPORT_REQUEST_STILL_PENDING("&c&l! &f{0} &7already has a pending request from you."),
	TELEPORT_SOURCE("&c&l! &7Teleported to &f{0}&7."),
	TELEPORT_SOURCE_OTHERS("&c&l! &7&f{0} &7has teleported you to &f{1}&7."),
	TELEPORT_TARGET("&c&l! &f{0} &7has teleported to you."),
	TELEPORT_TARGET_OTHERS("&c&l! &f{0} &7has teleported &f{1} &7to you."),
	WARP_BANNED("&c&l! &f{0} &7has been banned from &f{0}&7."),
	WARP_CREATED("&c&l! &7Warp &f{0} &7has been created."),
	WARP_INVITED("&c&l! &f{0} &7has been invited to &f{1}&7."),
	WARP_INVITED_LIMIT_REACHED("&c&l! &7You cannot invite more than &f{0} &7players."),
	WARP_LIMIT_INFO("&c&l! &7You currently own &f{0}&7/&f{1} &7warps."),
	WARP_LIMIT_REACHED("&c&l! &7You cannot own more than &f{0} &7warps."),
	WARP_MEMBER_ADDED("&c&l! &f{0} &7has been added to &f{1}&7."),
	WARP_MEMBER_LIMIT_REACHED("&c&l! &7You cannot add more than &f{0} &7members."),
	WARP_MEMBER_REMOVED("&c&l! &f{0} &7has been removed from &f{1}&7."),
	WARP_MESSAGE_CUSTOM("&c&l! &f{0}"),
	WARP_MESSAGE_DEFAULT("&c&l! &7Warped to &f{0}&7."),
	WARP_MOVED("&c&l! &7Warp &f{0} &7has been moved."),
	WARP_REMOVED("&c&l! &7Warp &f{0} &7has been removed."),
	WARP_TELEPORT_OTHERS("&c&l! &7Warped &f{0} &7to &f{1}&7."),
	WARP_TRANSFERRED_OWNERSHIP("&c&l! &7Ownership of &f{0} &7has been transferred to &f{1}&7."),
	WARP_UNBANNED("&c&l! &f{0} &7has been unbanned from &f{0}&7."),
	WARP_UNINVITED("&c&l! &f{0} &7has been uninvited from &f{1}&7.");
	
	private String text;
	
	private Message(String text) {
		this.text = text;
	}
	
	public String getText() { return text; }
	
}
