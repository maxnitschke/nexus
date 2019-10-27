package me.mn7cc.nexus;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.zaxxer.hikari.HikariDataSource;

import me.mn7cc.nexus.custom.FileManager;
import me.mn7cc.nexus.custom.NexusPlayer;
import me.mn7cc.nexus.custom.NexusWarp;
import me.mn7cc.nexus.file.ConfigFile;

public class Database {
	
	public static final String TABLE_ID_PLAYER = FileManager.getConfigFile().getMySQLPrefix() + "player";
	public static final String TABLE_ID_WARP = FileManager.getConfigFile().getMySQLPrefix() + "warp";
	
	private static HikariDataSource datasource = new HikariDataSource();
	private static List<String> queue = new ArrayList<String>();
	private static boolean proxy = false;
	
	public static void setup() {

		try {
			
			ConfigFile config = FileManager.getConfigFile();
			
			if(config.isUsingMySQL()) {
				
				datasource.setJdbcUrl("jdbc:mysql://" + config.getMySQLHostname() + ":" + config.getMySQLPort() + "/" + config.getMySQLDatabase());
				datasource.setUsername(config.getMySQLUsername());
				datasource.setPassword(config.getMySQLPassword());
				datasource.setMinimumIdle(10);
				datasource.setMaximumPoolSize(10);
				datasource.addDataSourceProperty("cachePrepStmts", "true");
				datasource.addDataSourceProperty("prepStmtCacheSize", "250");
				datasource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
				
			}
			else {
				
				datasource.setJdbcUrl("jdbc:sqlite:plugins/Nexus/database.db");
				datasource.setMinimumIdle(3);
				datasource.setMaximumPoolSize(3);
				
			}
			
			Connection connection = getConnection();
			DatabaseMetaData metadata = connection.getMetaData();
			ResultSet resultset = metadata.getTables(null, null, "%", null);
			
			List<String> tables = new ArrayList<String>();
			while(resultset.next()) tables.add(resultset.getString(3));
			
			if(!tables.contains(TABLE_ID_PLAYER)) execute("CREATE TABLE " + TABLE_ID_PLAYER + " (uuid TEXT, name TEXT, name_last TEXT, ip TEXT, nick TEXT, channel TEXT, friends TEXT, blocked TEXT, mode_god DOUBLE, mode_fly DOUBLE, mode_spy DOUBLE, mode_invisible DOUBLE, mode_teleportable DOUBLE, time_joined DOUBLE, time_online DOUBLE, time_login DOUBLE, time_logout DOUBLE, location_death TEXT, location_logout TEXT)");
			else {
				
				/*
				 * List<String> columns = getTableColumns("nexus_player");
				 * Apply updates if available columns don't match the latest table framework.
				 */
				
			}
			
			if(!tables.contains(TABLE_ID_WARP)) execute("CREATE TABLE " + TABLE_ID_WARP + " (id TEXT, world TEXT, x DOUBLE, y DOUBLE, z DOUBLE, yaw DOUBLE, pitch DOUBLE, owner TEXT, restrictions TEXT, invited TEXT, message TEXT)");
			else {
				
				/*
				 * List<String> columns = getTableColumns("nexus_warp");
				 * Apply updates if available columns don't match the latest table framework.
				 */
				
			}
			
			
			resultset.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void load() {
		
//		try {
//			
//			Connection connection = getConnection();
//			Statement statement = connection.createStatement();
//			
//			ResultSet user = statement.executeQuery("SELECT * FROM shiftfly_user");
//			while(user.next()) users.put(UUID.fromString(user.getString("uuid")), user.getDouble("speed"));
//			
//			user.close();
//			statement.close();
//			connection.close();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
	}
	
	public static NexusPlayer getPlayer(Player player) {
		
		String uuid = player.getUniqueId().toString();
		
		if(players.containsKey(uuid)) return players.get(uuid);
		
		NexusPlayer nexusPlayer = null;
		
		try {
			
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_ID_PLAYER + " WHERE uuid = '" + uuid + "'");
			
			if(!resultSet.isBeforeFirst()) return null;
			
			nexusPlayer = new NexusPlayer(resultSet);
			
			resultSet.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(!proxy) {
			if(nexusPlayer != null && player != null && player.isOnline()) addPlayer(nexusPlayer);
		}
		
		return nexusPlayer;
		
	}
	
	public static NexusPlayer getPlayerByUUID(String uuid) {
		
		if(players.containsKey(uuid)) return players.get(uuid);
		
		NexusPlayer nexusPlayer = null;
		
		try {
			
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_ID_PLAYER + " WHERE uuid = '" + uuid + "'");
			
			if(!resultSet.isBeforeFirst()) return null;
			
			nexusPlayer = new NexusPlayer(resultSet);
			
			resultSet.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(!proxy) {
			Player player = Bukkit.getPlayer(UUID.fromString(uuid));
			if(nexusPlayer != null && player != null && player.isOnline()) addPlayer(nexusPlayer);
		}
		
		return nexusPlayer;
		
	}
	
	public static NexusPlayer getPlayerByName(String name) {
		
		if(players.containsKey(name)) return players.get(name);
		
		NexusPlayer nexusPlayer = null;
		
		try {
			
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_ID_PLAYER + " WHERE name = '" + name + "'");
			
			if(!resultSet.isBeforeFirst()) return null;
			
			nexusPlayer = new NexusPlayer(resultSet);
			
			resultSet.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(!proxy) {
			Player player = Bukkit.getPlayer(UUID.fromString(nexusPlayer.getUUID()));
			if(nexusPlayer != null && player != null && player.isOnline()) addPlayer(nexusPlayer);
		}
		
		return nexusPlayer;
		
	}
	
	public static boolean isClosed() {
		return datasource.isClosed();
	}
	
	public static Connection getConnection() {
		try {
			return datasource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void execute(String execute) {	
		try {
			
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			statement.execute(execute);	
			statement.close();
			connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void execute(List<String> execute) {	
		try {
		
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			for(String e : execute) statement.execute(e);
			statement.close();
			connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void queue(String sql) {
		queue.add(sql);
	}
	
	public static void heartbeat() {
		execute(queue);
		queue.clear();
	}
	
	public static void close() {
		execute(queue);
		queue.clear();
		datasource.close();
	}
	
	public static List<String> getTableColumns(String table) {
		
		List<String> columns = new ArrayList<String>();
		
		try {
			
			DatabaseMetaData databaseMetaData = getConnection().getMetaData();
			ResultSet resultSet = databaseMetaData.getColumns(null, null, table, null);
			while(resultSet.next()) {
				String column = resultSet.getString("COLUMN_NAME");
				if(!columns.contains(column)) columns.add(column);
			}
			
		}
		catch (SQLException e) { e.printStackTrace(); }
		
		return columns;
		
	}
	
	public static List<String> getResultSetColumns(ResultSet resultSet) {
		
		List<String> columns = new ArrayList<String>();
		
		try {
			
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			for(int i = 1; i <= resultSetMetaData.getColumnCount(); i++) columns.add(resultSetMetaData.getColumnName(i));
			
		}
		catch (SQLException e) { e.printStackTrace(); }
		
		return columns;
		
	}
	
	public static void updateDatabase() {
		
	}
	
	private static HashMap<String, NexusPlayer> players = new HashMap<String, NexusPlayer>();
	public static void addPlayer(NexusPlayer player) {
		players.put(player.getUUID(), player);
		players.put(player.getName(), player);
	}
	public static void removePlayer(NexusPlayer player) {
		players.remove(player.getUUID());
		players.remove(player.getName());
	}
	public static void removePlayer(String key) { players.remove(key); }
	public static HashMap<String, NexusPlayer> getPlayers() { return players; }
	
	private static HashMap<String, NexusWarp> warps = new HashMap<String, NexusWarp>();
	public static void addWarp(NexusWarp warp) { warps.put(warp.getId(), warp); }
	public static void removeWarp(String id) { warps.remove(id); }
	public static NexusWarp getWarp(String id) { return warps.containsKey(id) ? warps.get(id) : null; }
	public static HashMap<String, NexusWarp> getWarps() { return warps; }

}
	