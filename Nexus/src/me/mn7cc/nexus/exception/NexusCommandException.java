package me.mn7cc.nexus.exception;

public class NexusCommandException extends Exception {

	private static final long serialVersionUID = -2090039187058265669L;

	private boolean showUsage;
	
	public NexusCommandException(String message, boolean showUsage) {
		super(message);
		this.showUsage = showUsage;
	}
	
	public boolean showUsage() { return showUsage; }
	
}