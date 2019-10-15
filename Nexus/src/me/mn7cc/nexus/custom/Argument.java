package me.mn7cc.nexus.custom;

import org.bukkit.entity.Player;

import me.mn7cc.nexus.exception.NexusCommandException;
import me.mn7cc.nexus.exception.InvalidCommandModelException;
import me.mn7cc.nexus.util.MessageUtils;
import me.mn7cc.nexus.util.ServerUtils;
import me.mn7cc.nexus.util.StringUtils;

public class Argument {

	public enum Type {

		STRING(false),
		STRING_REMAINING(true),
		INTEGER(false),
		DOUBLE(false),
		PLAYER(false),
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
	private Object data;
	
	public Argument(int index, String label, Type requiredType) {
		this.index = index;
		this.label = label;
		this.requiredType = requiredType;
		optional = false;
	}
	
	public Argument(int index, String label, Type requiredType, boolean optional) {
		this.index = index;
		this.label = label;
		this.requiredType = requiredType;
		this.optional = optional;
	}

	public void setGivenArgument(String givenArgument) throws NexusCommandException, InvalidCommandModelException {
		
		this.givenArgument = givenArgument;
		
		if(requiredType == Type.STRING || requiredType == Type.STRING_REMAINING) {
			
			setData(givenArgument);
			return;
			
		}
		else if(requiredType == Type.DOUBLE) {
			
			if(!StringUtils.isDouble(givenArgument)) throw new NexusCommandException(MessageUtils.getMessage(Message.COMMAND_ERROR_ARGUMENT_REQUIRES_A_NUMBER, label));
			setData(StringUtils.parseDouble(givenArgument));
			return;
			
		}
		else if(requiredType == Type.INTEGER) {
			
			if(!StringUtils.isInteger(givenArgument)) throw new NexusCommandException(MessageUtils.getMessage(Message.COMMAND_ERROR_ARGUMENT_REQUIRES_A_NUMBER, label));
			setData(StringUtils.parseInteger(givenArgument));
			return;
			
		}
		else if(requiredType == Type.PLAYER) {
			
			Player player = ServerUtils.getPlayer(givenArgument);
			if(player == null) throw new NexusCommandException(MessageUtils.getMessage(Message.COMMAND_ERROR_PLAYER_NOT_FOUND));
			setData(player);
			return;
			
		}
		
		throw new InvalidCommandModelException();
		
	}
	
	public void setData(Object data) { this.data = data; }
	
	public int getIndex() { return index; }
	public String getLabel() { return label; }
	public Type getRequiredType() { return requiredType; }
	public Object getData() { return data; }
	
	public boolean isOptional() { return optional; }
	
	public ArgumentModel getModel() { return new ArgumentModel(index, label, requiredType, optional); }
	
	public String getString() { return data != null && data instanceof String ? (String) data : null; }
	public double getDouble() { return data != null && data instanceof Double ? (double) data : null; }
	public int getInteger() { return data != null && data instanceof Integer ? (int) data : null; }
	public Player getPlayer() { return data != null && data instanceof Player ? (Player) data : null; }
	
}
