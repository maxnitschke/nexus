package me.mn7cc.nexus;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.zaxxer.hikari.HikariDataSource;

public class NexusDatabase {
	
	private NexusSettings settings;
	private NexusDatabaseCache cache;
	private HikariDataSource datasource;
	private List<String> queue;
	
	public NexusDatabase(NexusSettings settings) {
		
		this.settings = settings;
		this.datasource  = new HikariDataSource();
		this.queue = new ArrayList<String>();
		this.cache = new NexusDatabaseCache();
		
		try {
			
			if(settings.isDatabaseUsingMySQL()) {
				
				datasource.setJdbcUrl("jdbc:mysql://" + settings.getDatabaseMySQLHostname() + ":" + settings.getDatabaseMySQLPort() + "/" + settings.getDatabaseMySQLDatabase());
				datasource.setUsername(settings.getDatabaseMySQLUsername());
				datasource.setPassword(settings.getDatabaseMySQLPassword());
				datasource.setMinimumIdle(settings.getDatabaseConnectionPoolSize());
				datasource.setMaximumPoolSize(settings.getDatabaseConnectionPoolSize());
				datasource.addDataSourceProperty("cachePrepStmts", "true");
				datasource.addDataSourceProperty("prepStmtCacheSize", "250");
				datasource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
				
			}
			else {
				
				datasource.setJdbcUrl("jdbc:sqlite:plugins/Nexus/database.db");
				datasource.setMinimumIdle(settings.getDatabaseConnectionPoolSize());
				datasource.setMaximumPoolSize(settings.getDatabaseConnectionPoolSize());
				
			}
			
			Connection connection = getConnection();
			DatabaseMetaData metadata = connection.getMetaData();
			ResultSet resultset = metadata.getTables(null, null, "%", null);
			
			List<String> tables = new ArrayList<String>();
			while(resultset.next()) tables.add(resultset.getString(3));
			
			List<String> updates = new ArrayList<String>();
			
			if(!tables.contains(settings.getDatabasePlayerTableId())) updates.add("CREATE TABLE " + settings.getDatabasePlayerTableId() + " (uuid TEXT, name TEXT, name_last TEXT, ip TEXT, nick TEXT, channel TEXT, mails TEXT, friends TEXT, blocked TEXT, mode_god DOUBLE, mode_fly DOUBLE, mode_spy DOUBLE, mode_invisible DOUBLE, mode_teleportable DOUBLE, time_joined DOUBLE, time_online DOUBLE, time_login DOUBLE, time_logout DOUBLE, location_death TEXT, location_logout TEXT, count_homes INT, count_warps INT, count_tickets INT)");
			else {
				
				/*
				 * List<String> columns = getTableColumns("nexus_player");
				 * Apply updates if available columns don't match the latest table framework.
				 */
				
			}
			
			if(!tables.contains(settings.getDatabaseWarpTableId())) updates.add("CREATE TABLE " + settings.getDatabaseWarpTableId() + " (id TEXT, server TEXT, world TEXT, x DOUBLE, y DOUBLE, z DOUBLE, yaw FLOAT, pitch FLOAT, owner TEXT, members TEXT, priv BOOLEAN, invited TEXT, banned TEXT, message TEXT)");
			else {
				
				/*
				 * List<String> columns = getTableColumns("nexus_warp");
				 * Apply updates if available columns don't match the latest table framework.
				 */
				
			}
			
			execute(connection, updates);
			
			resultset.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public NexusSettings getSettings() { return settings; }
	public NexusDatabaseCache getCache() { return cache; }
	
	public void load() {
		
		try {
			
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT id FROM " + settings.getDatabaseWarpTableId());
			
			while(resultSet.next()) cache.addWarpIdToSet(resultSet.getString("id"));
			
			resultSet.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean isClosed() {
		return datasource.isClosed();
	}
	
	public Connection getConnection() {
		try {
			return datasource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void execute(String execute) {	
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
	
	public void execute(Connection connection, String execute) {	
		try {
			
			Statement statement = connection.createStatement();
			statement.execute(execute);	
			statement.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void execute(List<String> execute) {	
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
	
	public void execute(Connection connection, List<String> execute) {	
		try {
		
			Statement statement = connection.createStatement();
			for(String e : execute) statement.execute(e);
			statement.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void queue(String sql) {
		queue.add(sql);
	}
	
	public void heartbeat() {
		execute(queue);
		queue.clear();
	}
	
	public void close() {
		execute(queue);
		queue.clear();
		datasource.close();
	}
	
	public List<String> getTableColumns(String table) {
		
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
	
	public List<String> getResultSetColumns(ResultSet resultSet) {
		
		List<String> columns = new ArrayList<String>();
		
		try {
			
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			for(int i = 1; i <= resultSetMetaData.getColumnCount(); i++) columns.add(resultSetMetaData.getColumnName(i));
			
		}
		catch (SQLException e) { e.printStackTrace(); }
		
		return columns;
		
	}
	
}
	