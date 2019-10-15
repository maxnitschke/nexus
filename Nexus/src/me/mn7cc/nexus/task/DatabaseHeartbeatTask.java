package me.mn7cc.nexus.task;

import me.mn7cc.nexus.Database;

public class DatabaseHeartbeatTask implements Runnable {
	
	@Override
	public void run() {
		
		if(!Database.isClosed()) Database.heartbeat();
		
	}

}
