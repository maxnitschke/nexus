package me.mn7cc.nexus.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.mn7cc.nexus.NexusDatabase;
import me.mn7cc.nexus.custom.AccessList;
import me.mn7cc.nexus.custom.Decoder;
import me.mn7cc.nexus.custom.NexusWarp;

public class WarpUtils {

	public static List<String> getWarpIdList(NexusDatabase database, int page) {

		List<String> list = new ArrayList<String>();
	    Iterator<String> iterator = database.getCache().getWarpIdSet().iterator();
	    for(int i = 0; i < page * 50; i++) if(!iterator.hasNext()) return list; iterator.next();
	    for(int i = 0; i < 50; i++) if(!iterator.hasNext()) return list; list.add(iterator.next());
		return list;
		
	}
	
	public static String getLastId(NexusDatabase database, int page) {
	
		int index = page * 8 - 1;
		
	    Iterator<String> iterator = database.getCache().getWarpIdSet().iterator();
	    for(int i = 0; i < index; i++) { if(!iterator.hasNext()) return ""; iterator.next(); }
	    return iterator.hasNext() ? iterator.next() : "";
		
	}
	
	public static List<NexusWarp> getWarps(NexusDatabase database, String key, int limit) {
		
		List<NexusWarp> nexusWarps = new ArrayList<NexusWarp>();
		
		try {
			
			Connection connection = database.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + database.getSettings().getDatabaseWarpTableId() + " WHERE id > '" + key + "' LIMIT " + limit + " ORDER BY id ASC");
			
			while(resultSet.next()) {
				
				String id = resultSet.getString("id");
				
				if(database.getCache().containsWarp(id)) { nexusWarps.add(database.getCache().getWarp(id)); continue; }
				
				String server = resultSet.getString("server");
				String world = resultSet.getString("world");
				double x = resultSet.getDouble("x");
				double y = resultSet.getDouble("y");
				double z = resultSet.getDouble("z");
				float yaw = resultSet.getFloat("yaw");
				float pitch = resultSet.getFloat("pitch");
				String owner = resultSet.getString("owner");
				List<String> members = Decoder.STRING_LIST(resultSet.getString("members"));
				AccessList invited = Decoder.ACCESS_LIST(resultSet.getString("invited"));
				AccessList banned = Decoder.ACCESS_LIST(resultSet.getString("banned"));
				boolean priv = resultSet.getBoolean("priv");
				String message = resultSet.getString("message");
				
				nexusWarps.add(new NexusWarp(id, server, world, x, y, z, yaw, pitch, owner, members, priv, invited, banned, message));
				
			}
			
			resultSet.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return nexusWarps;
		
	}
	
}
