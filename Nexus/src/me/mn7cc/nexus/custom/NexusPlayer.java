package me.mn7cc.nexus.custom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.mn7cc.nexus.Database;
import me.mn7cc.nexus.Decoder;
import me.mn7cc.nexus.Encoder;
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
	
	private PendingDatabaseUpdates pendingDatabaseUpdates;
	private NexusPlayerSession session;
	
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
	public void setGodMode(double godmode) { this.mode_god = godmode; pendingDatabaseUpdates.addUpdate("mode_god", godmode); }
	public void setFlyMode(double flymode) { this.mode_fly = flymode; pendingDatabaseUpdates.addUpdate("mode_fly", flymode); }
	public void setSpyMode(double spymode) { this.mode_spy = spymode; pendingDatabaseUpdates.addUpdate("mode_spy", spymode); }
	public void setInvisible(double invisible) { this.mode_invisible = invisible; pendingDatabaseUpdates.addUpdate("mode_invisible", invisible); }
	public void setTeleportable(double teleportable) { this.mode_teleportable = teleportable; pendingDatabaseUpdates.addUpdate("mode_teleportable", teleportable); }
	public void setJoinedTime(double joinedtime) { this.time_joined = joinedtime; pendingDatabaseUpdates.addUpdate("time_joined", joinedtime); }
	public void setOnlineTime(double onlinetime) { this.time_online = onlinetime; pendingDatabaseUpdates.addUpdate("time_online", onlinetime); }
	public void setLoginTime(double logintime) { this.time_login = logintime; pendingDatabaseUpdates.addUpdate("time_login", logintime); }
	public void setLogoutTime(double logouttime) { this.time_logout = logouttime; pendingDatabaseUpdates.addUpdate("time_logout", logouttime); }
	public void setDeathLocation(Location deathLocation) { this.location_death = deathLocation; pendingDatabaseUpdates.addUpdate("location_death", Encoder.LOCATION(deathLocation)); }
	public void setLogoutLocation(Location logoutLocation) { this.location_logout = logoutLocation; pendingDatabaseUpdates.addUpdate("location_logout", Encoder.LOCATION(logoutLocation)); }
	
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
	public double getFlymode() { return mode_fly; }
	public double getSpymode() { return mode_spy; }
	public double getInvisible() { return mode_invisible; }
	public double getTeleportable() { return mode_teleportable; }
	public double getJoinedTime() { return time_joined; }
	public double getOnlineTime() { return time_online; }
	public double getLoginTime() { return time_login; }
	public double getLogoutTime() { return time_logout; }
	public Location getDeathLocation() { return location_death; }
	public Location getLogoutLocation() { return location_logout; }
	
	public NexusPlayerSession getSession() { return session; }
	
	public List<String> getGroups() {
		
		if(session == null) return new ArrayList<String>();
		
		Permission permission = VaultUtils.getPermission();
		if(permission == null) return new ArrayList<String>();
		
		return Arrays.asList(permission.getPlayerGroups(session.getPlayer()));
		
	}
	
	public void insert() {
		Database.queue("INSERT INTO " + Database.TABLE_ID_PLAYER + " VALUES ('" + uuid + "', '" + name + "', '" + name_last + "', '" + ip + "', '" + nick + "', '" + channel + "', '" + Encoder.STRING_LIST(mails) + "', '" + Encoder.STRING_LIST(friends) + "', '" + Encoder.STRING_LIST(blocked) + "', " + mode_god + ", " + mode_fly + ", " + mode_spy + ", " + mode_invisible + ", " + mode_teleportable + ", " + time_joined + ", " + time_online + ", " + time_login + ", " + time_logout + ", '" + Encoder.LOCATION(location_death) + "', '" + Encoder.LOCATION(location_logout) + "')");
		Database.addPlayer(this);
	}
	
	public void update() {
		if(pendingDatabaseUpdates.hasUpdates()) Database.queue("UPDATE " + Database.TABLE_ID_PLAYER + " SET " + pendingDatabaseUpdates.getSQLString() + " WHERE uuid = '" + uuid + "'");
		Database.addPlayer(this);
	}
	
	public void delete() {
		Database.queue("DELETE FROM " + Database.TABLE_ID_PLAYER + " WHERE uuid = '" + uuid + "'");
		Database.removePlayer(this);
	}
	
	public void quit() {
		Database.removePlayer(this);
	}
	
}
