package me.mn7cc.nexus.custom;

import me.mn7cc.nexus.Nexus;

public class NexusModule {

	private Nexus instance;
	private boolean enabled;
	private String name;
	
	public NexusModule(Nexus instance, boolean enabled, String name) {
		this.instance = instance;
		this.enabled = enabled;
		this.name = name;
	}

	public Nexus getNexusInstance() { return instance; }
	public boolean isEnabled() { return enabled; }
	public String getName() { return name; }
	
}
