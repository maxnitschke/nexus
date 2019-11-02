package me.mn7cc.nexus.custom;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import me.mn7cc.nexus.NexusDatabase;
import me.mn7cc.nexus.util.PlayerUtils;
import me.mn7cc.nexus.util.StringUtils;
import me.mn7cc.nexus.util.VaultUtils;

import net.milkbowl.vault.permission.Permission;

public class NexusPlayer {
	
	private String uuid;
	private String name;
	private String name_last;
	private String ip;
	private String nick;
	private String channel;
	private List<String> mails;
	private List<String> friends;
	private List<String> blocked;
	private double mode_god;
	private double mode_fly;
	private double mode_spy;
	private double mode_invisible;
	private double mode_teleportable;
	private double time_joined;
	private double time_online;
	private double time_login;
	private double time_logout;	
	private Location location_death;
	private Location location_logout;
	private int count_homes;
	private int count_warps;
	private int count_tickets;
	
	private PendingDatabaseUpdates pendingDatabaseUpdates;
	private NexusPlayerSession session;
	
	public NexusPlayer(Player player) {
		
		this.uuid = player.getUniqueId().toString();
		this.name = player.getName().toLowerCase();
		this.name_last = player.getName();
		this.ip = PlayerUtils.getIP(player);
		this.nick = "";
		this.channel = "";
		this.mails = new ArrayList<String>();
		this.friends = new ArrayList<String>();
		this.blocked = new ArrayList<String>();
		this.mode_god = 0;
		this.mode_fly = 0;
		this.mode_spy = 0;
		this.mode_invisible = 0;
		this.mode_teleportable = 0;
		this.time_joined = System.currentTimeMillis();
		this.time_online = 0;
		this.time_login = System.currentTimeMillis();
		this.time_logout = 0;
		this.location_death = null;
		this.location_logout = null;
		this.count_homes = 0;
		this.count_warps = 0;
		this.count_tickets = 0;
		
		pendingDatabaseUpdates = new PendingDatabaseUpdates();
		
		session = new NexusPlayerSession(player);
		
	}
	
	public NexusPlayer(String uuid, String name, String name_last, String ip, String nick, String channel, List<String> mails, List<String> friends, List<String> blocked, double mode_god, double mode_fly, double mode_spy, double mode_invisible, double mode_teleportable, double time_joined, double time_online, double time_login, double time_logout, Location location_death, Location location_logout) {
		
		this.uuid = uuid;
		this.name = name;
		this.name_last = name_last;
		this.ip = ip;
		this.nick = nick;
		this.channel = channel;
		this.mails = mails;
		this.friends = friends;
		this.blocked = blocked;
		this.mode_god = mode_god;
		this.mode_fly = mode_fly;
		this.mode_spy = mode_spy;
		this.mode_invisible = mode_invisible;
		this.mode_teleportable = mode_teleportable;
		this.time_joined = time_joined;
		this.time_online = time_online;
		this.time_login = time_login;
		this.time_logout = time_logout;
		this.location_death = location_death;
		this.location_logout = location_logout;
		
		pendingDatabaseUpdates = new PendingDatabaseUpdates();
		
		Player player = Bukkit.getPlayer(UUID.fromString(uuid));
		if(player != null) session = new NexusPlayerSession(player);
		
	}
	
	public NexusPlayer(ResultSet resultSet) {
		
		try {
			
			if(resultSet.next()) {
		
				this.uuid = resultSet.getString("uuid");
				this.name = resultSet.getString("name");
				this.name_last = resultSet.getString("name_last");
				this.ip = resultSet.getString("ip");
				this.nick = resultSet.getString("nick");
				this.channel = resultSet.getString("channel");
				this.mails = Decoder.STRING_LIST(resultSet.getString("mails"));
				this.friends = Decoder.STRING_LIST(resultSet.getString("friends"));
				this.blocked = Decoder.STRING_LIST(resultSet.getString("blocked"));
				this.mode_god = resultSet.getDouble("mode_god");
				this.mode_fly = resultSet.getDouble("mode_fly");
				this.mode_spy = resultSet.getDouble("mode_spy");
				this.mode_invisible = resultSet.getDouble("mode_invisible");
				this.mode_teleportable = resultSet.getDouble("mode_teleportable");
				this.time_joined = resultSet.getDouble("time_joined");
				this.time_online = resultSet.getDouble("time_online");
				this.time_login = resultSet.getDouble("time_login");
				this.time_logout = resultSet.getDouble("time_logout");
				this.location_death = Decoder.LOCATION(resultSet.getString("location_death"));
				this.location_logout = Decoder.LOCATION(resultSet.getString("location_logout"));
				this.count_homes = resultSet.getInt("count_homes");
				this.count_warps = resultSet.getInt("count_warps");
				this.count_tickets = resultSet.getInt("count_tickets");
			
			}
		
		}
		catch(SQLException e) { e.printStackTrace(); }

		pendingDatabaseUpdates = new PendingDatabaseUpdates();
		
		Player player = Bukkit.getPlayer(UUID.fromString(uuid));
		if(player != null) session = new NexusPlayerSession(player);
		
	}
	
	public void setUUID(String uuid) { this.uuid = uuid; pendingDatabaseUpdates.addUpdate("uuid", uuid); }
	public void setName(String name) { this.name = name; pendingDatabaseUpdates.addUpdate("name", name); }
	public void setLastAccountName(String lastAccountName) { this.name_last = lastAccountName; pendingDatabaseUpdates.addUpdate("name_last", lastAccountName); }
	public void setIPAddress(String ipAddress) { this.ip = ipAddress; pendingDatabaseUpdates.addUpdate("ip", ipAddress); }
	public void setNick(String nick) { this.nick = nick; pendingDatabaseUpdates.addUpdate("nick", nick); }
	public void setChatChannel(String chatChannel) { this.channel = chatChannel; pendingDatabaseUpdates.addUpdate("channel", chatChannel); }
	public void setFriends(List<String> friends) { this.friends = friends; pendingDatabaseUpdates.addUpdate("friends", Encoder.STRING_LIST(friends)); }
	public void setBlocked(List<String> blocked) { this.blocked = blocked; pendingDatabaseUpdates.addUpdate("blocked", Encoder.STRING_LIST(blocked)); }
	public void setGodMode(double godMode) { this.mode_god = godMode; pendingDatabaseUpdates.addUpdate("mode_god", godMode); }
	public void setFlyMode(double flyMode) { this.mode_fly = flyMode; pendingDatabaseUpdates.addUpdate("mode_fly", flyMode); }
	public void setSpyMode(double spyMode) { this.mode_spy = spyMode; pendingDatabaseUpdates.addUpdate("mode_spy", spyMode); }
	public void setInvisible(double invisible) { this.mode_invisible = invisible; pendingDatabaseUpdates.addUpdate("mode_invisible", invisible); }
	public void setTeleportable(double teleportable) { this.mode_teleportable = teleportable; pendingDatabaseUpdates.addUpdate("mode_teleportable", teleportable); }
	public void setJoinedTime(double joinedTime) { this.time_joined = joinedTime; pendingDatabaseUpdates.addUpdate("time_joined", joinedTime); }
	public void setOnlineTime(double onlineTime) { this.time_online = onlineTime; pendingDatabaseUpdates.addUpdate("time_online", onlineTime); }
	public void setLoginTime(double loginTime) { this.time_login = loginTime; pendingDatabaseUpdates.addUpdate("time_login", loginTime); }
	public void setLogoutTime(double logoutTime) { this.time_logout = logoutTime; pendingDatabaseUpdates.addUpdate("time_logout", logoutTime); }
	public void setDeathLocation(Location deathLocation) { this.location_death = deathLocation; pendingDatabaseUpdates.addUpdate("location_death", Encoder.LOCATION(deathLocation)); }
	public void setLogoutLocation(Location logoutLocation) { this.location_logout = logoutLocation; pendingDatabaseUpdates.addUpdate("location_logout", Encoder.LOCATION(logoutLocation)); }
	public void setHomeCount(int homeCount) { this.count_homes = homeCount; pendingDatabaseUpdates.addUpdate("count_homes", homeCount); }
	public void setWarpCount(int warpCount) { this.count_warps = warpCount; pendingDatabaseUpdates.addUpdate("count_warps", warpCount); }
	public void setTicketCount(int ticketCount) { this.count_tickets = ticketCount; pendingDatabaseUpdates.addUpdate("count_tickets", ticketCount); }
	
	public String getUUID() { return uuid; }
	public String getName() { return name; }
	public String getLastName() { return name_last; }
	public String getIP() { return ip; }
	public String getNick() { return nick; }
	public String getChannel() { return channel; }
	public List<String> getMails() { return mails; }
	public List<String> getFriends() { return friends; }
	public List<String> getBlocked() { return blocked; }
	public double getGodMode() { return mode_god; }
	public double getFlyMode() { return mode_fly; }
	public double getSpyMode() { return mode_spy; }
	public double getInvisible() { return mode_invisible; }
	public double getTeleportable() { return mode_teleportable; }
	public double getJoinedTime() { return time_joined; }
	public double getOnlineTime() { return time_online; }
	public double getLoginTime() { return time_login; }
	public double getLogoutTime() { return time_logout; }
	public Location getDeathLocation() { return location_death; }
	public Location getLogoutLocation() { return location_logout; }
	public int getHomeCount() { return count_homes; }
	public int getWarpCount() { return count_warps; }
	public int getTicketCount() { return count_tickets; }
	
	public NexusPlayerSession getSession() { return session; }
	
	public int getHomeLimit() {
		
		int limit = 1;
		if(session == null) return limit;
		Player player = session.getPlayer();
		if(player.hasPermission("nexus.home.set.unlimited")) return Integer.MAX_VALUE;
		
		for(PermissionAttachmentInfo permissionAttachmentInfo : player.getEffectivePermissions()) {

			if(!permissionAttachmentInfo.getValue()) continue;
			
			String permission = permissionAttachmentInfo.getPermission().toLowerCase();
			if(!permission.contains("nexus.home.set.")) continue;
			
			int current = StringUtils.parseInteger(permission.replaceAll("nexus.home.set.", ""));
			if(current > limit) limit = current;
						
		}
		
		return limit;
		
	}
	
	public int getWarpLimit() {
		
		int limit = 1;
		if(session == null) return limit;
		Player player = session.getPlayer();
		if(player.hasPermission("nexus.warp.set.unlimited")) return Integer.MAX_VALUE;
		
		for(PermissionAttachmentInfo permissionAttachmentInfo : player.getEffectivePermissions()) {

			if(!permissionAttachmentInfo.getValue()) continue;
			
			String permission = permissionAttachmentInfo.getPermission().toLowerCase();
			if(!permission.contains("nexus.warp.set.")) continue;
			
			int current = StringUtils.parseInteger(permission.replaceAll("nexus.warp.set.", ""));
			if(current > limit) limit = current;
						
		}
		
		return limit;
		
	}
	
	public int getTicketLimit() {
		
		int limit = 1;
		if(session == null) return limit;
		Player player = session.getPlayer();
		if(player.hasPermission("nexus.ticket.unlimited")) return Integer.MAX_VALUE;
		
		for(PermissionAttachmentInfo permissionAttachmentInfo : player.getEffectivePermissions()) {

			if(!permissionAttachmentInfo.getValue()) continue;
			
			String permission = permissionAttachmentInfo.getPermission().toLowerCase();
			if(!permission.contains("nexus.ticket.")) continue;
			
			int current = StringUtils.parseInteger(permission.replaceAll("nexus.ticket.", ""));
			if(current > limit) limit = current;
						
		}
		
		return limit;
		
	}
	
	public List<String> getGroups() {
		
		if(session == null) return new ArrayList<String>();
		
		Permission permission = VaultUtils.getPermission();
		if(permission == null) return new ArrayList<String>();
		
		return Arrays.asList(permission.getPlayerGroups(session.getPlayer()));
		
	}
	
	public void insert(NexusDatabase database) {
		database.queue("INSERT INTO " + database.getSettings().getDatabasePlayerTableId() + " VALUES ('" + uuid + "', '" + name + "', '" + name_last + "', '" + ip + "', '" + nick + "', '" + channel + "', '" + Encoder.STRING_LIST(mails) + "', '" + Encoder.STRING_LIST(friends) + "', '" + Encoder.STRING_LIST(blocked) + "', " + mode_god + ", " + mode_fly + ", " + mode_spy + ", " + mode_invisible + ", " + mode_teleportable + ", " + time_joined + ", " + time_online + ", " + time_login + ", " + time_logout + ", '" + Encoder.LOCATION(location_death) + "', '" + Encoder.LOCATION(location_logout) + "', " + count_homes + ", " + count_warps + ", " + count_tickets + ")");
		database.getCache().addPlayer(this);
	}
	
	public void update(NexusDatabase database) {
		if(pendingDatabaseUpdates.hasUpdates()) database.queue("UPDATE " + database.getSettings().getDatabasePlayerTableId() + " SET " + pendingDatabaseUpdates.getSQLString() + " WHERE uuid = '" + uuid + "'");
		database.getCache().addPlayer(this);
	}
	
	public void delete(NexusDatabase database) {
		database.queue("DELETE FROM " + database.getSettings().getDatabasePlayerTableId() + " WHERE uuid = '" + uuid + "'");
		database.getCache().addPlayer(this);
	}
	
	public void quit(NexusDatabase database) {
		database.getCache().removePlayer(this);
	}
	
	public static NexusPlayer fromDatabase(NexusDatabase database, Player player) {
		
		String uuid = player.getUniqueId().toString();
		
		if(database.getCache().containsPlayer(uuid)) return database.getCache().getPlayer(uuid);
		
		NexusPlayer nexusPlayer = null;
		
		try {
			
			Connection connection = database.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + database.getSettings().getDatabasePlayerTableId() + " WHERE uuid = '" + uuid + "'");
			
			if(resultSet.isBeforeFirst()) nexusPlayer = new NexusPlayer(resultSet);
			
			resultSet.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(nexusPlayer != null && !database.getSettings().isProxy()) {
			if(player != null && player.isOnline()) database.getCache().addPlayer(nexusPlayer);
		}
		
		return nexusPlayer;
		
	}
	
	public static NexusPlayer fromDatabaseByUUID(NexusDatabase database, String uuid) {
		
		if(database.getCache().containsPlayer(uuid)) return database.getCache().getPlayer(uuid);
		
		NexusPlayer nexusPlayer = null;
		
		try {
			
			Connection connection = database.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + database.getSettings().getDatabasePlayerTableId() + " WHERE uuid = '" + uuid + "'");
			
			if(resultSet.isBeforeFirst()) nexusPlayer = new NexusPlayer(resultSet);
			
			resultSet.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(nexusPlayer != null && !database.getSettings().isProxy()) {
			Player player = Bukkit.getPlayer(UUID.fromString(uuid));
			if(player != null && player.isOnline()) database.getCache().addPlayer(nexusPlayer);
		}
		
		return nexusPlayer;
		
	}
	
	public static NexusPlayer fromDatabaseByName(NexusDatabase database, String name) {
		
		if(database.getCache().containsPlayer(name)) return database.getCache().getPlayer(name);
		
		NexusPlayer nexusPlayer = null;
		
		try {
			
			Connection connection = database.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + database.getSettings().getDatabasePlayerTableId() + " WHERE name = '" + name + "'");
			
			if(resultSet.isBeforeFirst()) nexusPlayer = new NexusPlayer(resultSet);
			
			resultSet.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(nexusPlayer != null && !database.getSettings().isProxy()) {
			Player player = Bukkit.getPlayer(UUID.fromString(nexusPlayer.getUUID()));
			if(player != null && player.isOnline()) database.getCache().addPlayer(nexusPlayer);
		}
		
		return nexusPlayer;
		
	}
	
}
