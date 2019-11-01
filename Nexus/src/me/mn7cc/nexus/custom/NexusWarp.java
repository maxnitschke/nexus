package me.mn7cc.nexus.custom;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import me.mn7cc.nexus.NexusDatabase;

public class NexusWarp {
	
	private String id;
	private String server;
	private String world;
	private double x;
	private double y;
	private double z;
	private float yaw;
	private float pitch;
	private String owner;
	private List<String> members;
	private boolean priv;
	private AccessList invited;
	private AccessList banned;
	private String message;
	
	private PendingDatabaseUpdates pendingDatabaseUpdates;

	public NexusWarp(String id, String server, Player player) {
		
		Location location = player.getLocation();
		
		this.id = id;
		this.server = server;
		this.world = player.getWorld().getUID().toString();
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
		this.yaw = location.getYaw();
		this.pitch = location.getPitch();
		this.owner = player.getUniqueId().toString();
		this.members = new ArrayList<String>();
		this.priv = false;
		this.invited = new AccessList();
		this.banned = new AccessList();
		this.message = "";
		
	}
	
	public NexusWarp(String id, String server, String world, double x, double y, double z, float yaw, float pitch, String owner, List<String> members, boolean priv, AccessList invited, AccessList banned, String message) {
		
		this.id = id;
		this.server = server;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.owner = owner;
		this.members = members;
		this.invited = invited;
		this.banned = banned;
		this.priv = priv;
		this.message = message;
		
		pendingDatabaseUpdates = new PendingDatabaseUpdates();
		
	}
	
	public NexusWarp(ResultSet resultSet) {
		
		try {
			
			if(resultSet.next()) {
		
				this.id = resultSet.getString("id");
				this.server = resultSet.getString("server");
				this.world = resultSet.getString("world");
				this.x = resultSet.getDouble("x");
				this.y = resultSet.getDouble("y");
				this.z = resultSet.getDouble("z");
				this.yaw = resultSet.getFloat("yaw");
				this.pitch = resultSet.getFloat("pitch");
				this.owner = resultSet.getString("owner");
				this.members = Decoder.STRING_LIST(resultSet.getString("members"));
				this.invited = Decoder.ACCESS_LIST(resultSet.getString("invited"));
				this.banned = Decoder.ACCESS_LIST(resultSet.getString("banned"));
				this.priv = resultSet.getBoolean("priv");
				this.message = resultSet.getString("message");
			
			}
		
		}
		catch(SQLException e) { e.printStackTrace(); }

		pendingDatabaseUpdates = new PendingDatabaseUpdates();
		
	}
	
	public void setId(String id) { this.id = id; pendingDatabaseUpdates.addUpdate("id", id); }
	public void setServer(String server) { this.server = server; pendingDatabaseUpdates.addUpdate("server", server); }
	public void setWorld(String world) { this.world = world; pendingDatabaseUpdates.addUpdate("world", world); }
	public void setX(double x) { this.x = x; pendingDatabaseUpdates.addUpdate("x", x); }
	public void setY(double y) { this.y = y; pendingDatabaseUpdates.addUpdate("y", y); }
	public void setZ(double z) { this.z = z; pendingDatabaseUpdates.addUpdate("z", z); }
	public void setYaw(float yaw) { this.yaw = yaw; pendingDatabaseUpdates.addUpdate("yaw", yaw); }
	public void setPitch(float pitch) { this.pitch = pitch; pendingDatabaseUpdates.addUpdate("pitch", pitch); }
	public void setOwner(String owner) { this.owner = owner; pendingDatabaseUpdates.addUpdate("owner", owner); }
	public void setMembers(List<String> members) { this.members = members; pendingDatabaseUpdates.addUpdate("members", Encoder.STRING_LIST(members)); }
	public void setPrivate(boolean priv) { this.priv = priv; pendingDatabaseUpdates.addUpdate("priv", priv); }
	public void setInvited(AccessList invited) { this.invited = invited; pendingDatabaseUpdates.addUpdate("invited", Encoder.ACCESS_LIST(invited)); }
	public void setBanned(AccessList banned) { this.banned = banned; pendingDatabaseUpdates.addUpdate("banned", Encoder.ACCESS_LIST(banned)); }
	public void setMessage(String message) { this.message = message; pendingDatabaseUpdates.addUpdate("message", message); }
	
	public String getId() { return id; }
	public String getServer() { return server; }
	public String getWorld() { return world; }
	public double getX() { return x; }
	public double getY() { return y; }
	public double getZ() { return z; }
	public float getYaw() { return yaw; }
	public float getPitch() { return pitch; }
	public String getOwner() { return owner; }
	public List<String> getMembers() { return members; }
	public boolean isPrivate() { return priv; }
	public AccessList getInvited() { return invited; }
	public String getMessage() { return message; }
	
	public boolean isOwner(String uuid) {
		return owner.equals(uuid);
	}
	
	public boolean isMember(String uuid) {
		return members.contains(uuid);
	}
	
	public boolean isInvited(NexusPlayer nexusPlayer) {
		return invited.hasAccess(nexusPlayer);
	}
	
	public boolean hasMessage() {
		return message != null && !message.isEmpty();
	}
	
	public void addMember(String uuid) {
		if(!members.contains(uuid)) members.add(uuid);
	}
	
	public void removeMember(String uuid) {
		if(members.contains(uuid)) members.remove(uuid);
	}
	
	public void invitePlayer(NexusPlayer nexusPlayer) {
		invited.addUser(nexusPlayer.getUUID());
	}
	
	public void uninvitePlayer(NexusPlayer nexusPlayer) {
		invited.removeUser(nexusPlayer.getUUID());
	}
	
	public void banPlayer(NexusPlayer nexusPlayer) {
		banned.addUser(nexusPlayer.getUUID());
	}
	
	public void unbanPlayer(NexusPlayer nexusPlayer) {
		banned.removeUser(nexusPlayer.getUUID());
	}
	
	public void moveTo(Location location) {
		this.world = location.getWorld().getUID().toString();
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
		this.yaw = location.getYaw();
		this.pitch = location.getPitch();
	}
	
	public Location getLocation() {
		World world = Bukkit.getWorld(UUID.fromString(this.world));
		if(world == null) return null;
		return new Location(world, x, y, z, yaw, pitch);
	}
	
	public void spawnPlayer(Player player) {
		player.teleport(getLocation());
	}
	
	public void insert(NexusDatabase database) {
		database.queue("INSERT INTO " + database.getSettings().getDatabaseWarpTableId() + " VALUES ('" + id + "', '" + server + "', '" + world + "', " + x + ", " + y + ", " + z + ", " + yaw + ", " + pitch + ", '" + owner + "', '" + Encoder.STRING_LIST(members) + "', priv, '" + Encoder.ACCESS_LIST(invited) + "', '" + Encoder.ACCESS_LIST(banned) + "', '" + message + "')");
		database.getCache().addWarp(this);
	}
	
	public void update(NexusDatabase database) {
		database.queue("UPDATE " + database.getSettings().getDatabaseWarpTableId() + " SET " + pendingDatabaseUpdates.getSQLString() + " WHERE id = '" + id + "' AND server = '" + server + "'");
		database.getCache().addWarp(this);
	}
	
	public void delete(NexusDatabase database) {
		database.queue("DELETE FROM " + database.getSettings().getDatabaseWarpTableId() + " WHERE id = '" + id + "' AND server = '" + server + "'");
		database.getCache().removeWarp(id);
	}
	
	public static NexusWarp fromDatabase(NexusDatabase database, String id) {
		
		if(database.getCache().containsWarp(id)) return database.getCache().getWarp(id);
		
		NexusWarp nexusWarp = null;
		
		try {
			
			Connection connection = database.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + database.getSettings().getDatabaseWarpTableId() + " WHERE id = '" + id + "'");
			
			if(!resultSet.isBeforeFirst()) return null;
			
			nexusWarp = new NexusWarp(resultSet);
			
			resultSet.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(!database.getSettings().isProxy()) database.getCache().addWarp(nexusWarp);
		
		return nexusWarp;
		
	}
	
}
