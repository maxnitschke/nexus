package me.mn7cc.nexus.custom;

public class ArgumentModel {

	private int index;
	private String label;
	private Argument.Type requiredType;
	private boolean optional;
	
	public ArgumentModel(int index, String label, Argument.Type requiredType) {
		this.index = index;
		this.label = label;
		this.requiredType = requiredType;
		optional = false;
	}
	
	public ArgumentModel(int index, String label, Argument.Type requiredType, boolean optional) {
		this.index = index;
		this.label = label;
		this.requiredType = requiredType;
		this.optional = optional;
	}
	
	public int getIndex() { return index; }
	public String getLabel() { return label; }
	public Argument.Type getRequiredType() { return requiredType; }
	public boolean isOption() { return optional; }

	public Argument getArgument() { return new Argument(index, label, requiredType, optional); }
	
}
