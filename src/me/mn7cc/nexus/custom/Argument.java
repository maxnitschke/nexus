package me.mn7cc.nexus.custom;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.mn7cc.nexus.exception.NexusCommandException;
import me.mn7cc.nexus.NexusDatabase;
import me.mn7cc.nexus.exception.InvalidCommandModelException;
import me.mn7cc.nexus.util.MessageUtils;
import me.mn7cc.nexus.util.ServerUtils;
import me.mn7cc.nexus.util.StringUtils;

public class Argument extends ArgumentData {

	public enum Type {

		STRING(false),
		STRING_REMAINING(true),
		INTEGER(false),
		DOUBLE(false),
		PLAYER(false),
		PLAYERS(false),
		NEXUS_PLAYER(false),
		NEXUS_HOME(false),
		NEXUS_WARP(false);
		
		private boolean acceptsRemaining;
		
		private Type(boolean acceptsRemaining) {
			this.acceptsRemaining = acceptsRemaining;
		}
		
		public boolean doesAcceptRemainingArguments() { return acceptsRemaining; }
		
	}
	
	private int index;
	private String label;
	private String givenArgument;
	private Type requiredType;
	private boolean optional;
	
	public Argument(int index, String label, Type requiredType) {
		super();
		this.index = index;
		this.label = label;
		this.requiredType = requiredType;
		optional = false;
	}
	
	public Argument(int index, String label, Type requiredType, boolean optional) {
		super();
		this.index = index;
		this.label = label;
		this.requiredType = requiredType;
		this.optional = optional;
	}

	public void setGivenArgument(String givenArgument, NexusDatabase database) throws NexusCommandException, InvalidCommandModelException {
		
		this.givenArgument = givenArgument;
		
		if(requiredType == Type.STRING || requiredType == Type.STRING_REMAINING) {
			
			setString(givenArgument);
			return;
			
		}
		else if(requiredType == Type.DOUBLE) {
			
			if(!StringUtils.isDouble(givenArgument)) throw new NexusCommandException(MessageUtils.getMessage(Message.COMMAND_ERROR_ARGUMENT_REQUIRES_A_NUMBER, label), true);
			setDouble(StringUtils.parseDouble(givenArgument));
			return;
			
		}
		else if(requiredType == Type.INTEGER) {
			
			if(!StringUtils.isInteger(givenArgument)) throw new NexusCommandException(MessageUtils.getMessage(Message.COMMAND_ERROR_ARGUMENT_REQUIRES_A_NUMBER, label), true);
			setInteger(StringUtils.parseInteger(givenArgument));
			return;
			
		}
		else if(requiredType == Type.PLAYER) {
			
			Player player = ServerUtils.getPlayer(givenArgument);
			if(player == null) throw new NexusCommandException(MessageUtils.getMessage(Message.COMMAND_ERROR_PLAYER_NOT_FOUND), true);
			setPlayer(player);
			return;
			
		}
		else if(requiredType == Type.PLAYERS) {
			
			List<Player> players = new ArrayList<Player>();
			
			if(givenArgument.equals("*")) {
				players = new ArrayList<Player>(Bukkit.getOnlinePlayers());
			}
			else if(givenArgument.contains(",")) {
				for(String name : givenArgument.split(",")) {
					Player player = ServerUtils.getPlayer(name);
					if(player == null) throw new NexusCommandException(MessageUtils.getMessage(Message.COMMAND_ERROR_PLAYER_NOT_FOUND), true);
					if(!players.contains(player)) players.add(player);
				}
			}
			else {
				Player player = ServerUtils.getPlayer(givenArgument);
				if(player == null) throw new NexusCommandException(MessageUtils.getMessage(Message.COMMAND_ERROR_PLAYER_NOT_FOUND), true);
				players.add(player);
			}
			
			setPlayers(players);
			return;
			
		}
		else if(requiredType == Type.NEXUS_PLAYER) {
			
			NexusPlayer nexusPlayer = NexusPlayer.fromDatabaseByName(database, givenArgument);
			if(nexusPlayer == null) throw new NexusCommandException(MessageUtils.getMessage(Message.COMMAND_ERROR_PLAYER_NOT_FOUND), true);
			setNexusPlayer(nexusPlayer);
			return;
			
		}
		else if(requiredType == Type.NEXUS_WARP) {
			
			NexusWarp nexusWarp = NexusWarp.fromDatabase(database, givenArgument);
			if(nexusWarp == null) throw new NexusCommandException(MessageUtils.getMessage(Message.COMMAND_ERROR_WARP_NOT_FOUND), true);
			setNexusWarp(nexusWarp);
			return;
			
		}
		
		throw new InvalidCommandModelException();
		
	}
	
	public int getIndex() { return index; }
	public String getLabel() { return label; }
	public Type getRequiredType() { return requiredType; }
	
	public boolean isOptional() { return optional; }
	
	public ArgumentModel getModel() { return new ArgumentModel(index, label, requiredType, optional); }
	
}
