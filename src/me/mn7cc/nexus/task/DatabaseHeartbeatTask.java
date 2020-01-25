package me.mn7cc.nexus.task;

import me.mn7cc.nexus.NexusDatabase;

public class DatabaseHeartbeatTask implements Runnable {
	
	private NexusDatabase database;
	
	public DatabaseHeartbeatTask(NexusDatabase database) {
		this.database = database;
	}
	
	@Override
	public void run() {
		
		if(!database.isClosed()) database.heartbeat();
		
	}

}
