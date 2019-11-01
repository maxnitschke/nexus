package me.mn7cc.nexus.custom;

import me.mn7cc.nexus.Nexus;

public class NexusModule {

	private Nexus instance;
	private boolean enabled;
	
	public NexusModule(Nexus instance, boolean enabled) {
		this.instance = instance;
		this.enabled = enabled;
	}

	public Nexus getNexusInstance() { return instance; }
	public boolean isEnabled() { return enabled; }
	
}
