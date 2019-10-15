package me.mn7cc.nexus.custom;

import java.util.HashMap;
import java.util.Map.Entry;

public class PendingDatabaseUpdates {
	
	HashMap<String, Object> pendingUpdates;
	
	public PendingDatabaseUpdates() {
		this.pendingUpdates = new HashMap<String, Object>();
	}
	
	public void addUpdate(String id, Object value) {
		pendingUpdates.put(id, value);
	}
	
	public boolean hasUpdates() {
		return !pendingUpdates.isEmpty();
	}
	
	public String getSQLString() {
		
		if(pendingUpdates.isEmpty()) return null;
		
		StringBuilder query = new StringBuilder();

		for(Entry<String, Object> entry : pendingUpdates.entrySet()) {
			String id = entry.getKey();
			Object value = entry.getValue();
			query.append(id + " = " + ((value instanceof String) ? "'" + value + "'" : value) + ", ");
		}
		
		query.delete(query.length() - 2, query.length());
		return query.toString();
		
	}

}
