package me.mn7cc.nexus.custom;

import java.util.List;

import me.mn7cc.nexus.Database;

public class NexusWarp {
	
	private String id;
	private String world;
	private double x;
	private double y;
	private double z;
	private double yaw;
	private double pitch;
	private String owner;
	private boolean priv;
	private AccessList invited;
	private String message;
	
	private PendingDatabaseUpdates pendingDatabaseUpdates;

	public NexusWarp(String id, String world, double x, double y, double z, double yaw, double pitch, String owner, boolean priv, AccessList invited, String message) {
		this.id = id;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.owner = owner;
		this.invited = invited;
		this.priv = priv;
		this.message = message;
	}
	
	public void setId(String id) { this.id = id; }
	public void setWorld(String world) { this.world = world; }
	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	public void setZ(double z) { this.z = z; }
	public void setYaw(double yaw) { this.yaw = yaw; }
	public void setPitch(double pitch) { this.pitch = pitch; }
	public void setOwner(String owner) { this.owner = owner; }
	public void setPrivate(boolean priv) { this.priv = priv; }
	public void setInvited(AccessList invited) { this.invited = invited; }
	public void setMessage(String message) { this.message = message; }
	
	public String getId() { return id; }
	public String getWorld() { return world; }
	public double getX() { return x; }
	public double getY() { return y; }
	public double getZ() { return z; }
	public double getYaw() { return yaw; }
	public double getPitch() { return pitch; }
	public String getOwner() { return owner; }
	public boolean isPrivate() { return priv; }
	public AccessList getInvited() { return invited; }
	public String getMessage() { return message; }
	
	
//	public void insert() {
//		Database.queue("INSERT INTO warps VALUES ('" + id + "', '" + world + "', " + x + ", " + y + ", " + z + ", " + yaw + ", " + pitch + ", '" + owner + "', '" + SerializeUtils.list(invited) + "', '" + priv + "', '" + message + "')");
//		Database.addWarp(this);
//	}
//	
//	public void update() {
//		Database.queue("UPDATE warps SET world = '" + world + "', x = " + x + ", y = " + y + ", z = " + z + ", yaw = " + yaw + ", pitch = " + pitch + ", owner = '" + owner + "', invited = '" + SerializeUtils.list(invited) + "', private = '" + priv + "', message = '" + message + "' WHERE name = '" + name + "'");
//		Database.addWarp(this);
//	}
//	
//	public void delete() {
//		Database.queue("DELETE FROM warps WHERE name = '" + id + "'");
//		Database.removeWarp(id);
//	}
	
}
