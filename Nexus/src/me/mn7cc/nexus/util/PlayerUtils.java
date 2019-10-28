package me.mn7cc.nexus.util;

import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PlayerUtils {
	
//	public static Player getPlayer(FPlayer player) {
//		return Bukkit.getPlayer(UUID.fromString(player.getUUID()));
//	}
//	
//	public static Player getPlayer(FCharacter character) {
//		return Bukkit.getPlayer(UUID.fromString(character.getUUID()));
//	}
//	
//	public static String getName(FPlayer player) {
//		OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(player.getUUID()));
//		if(p != null) return p.getName();
//		return ServerUtils.getPlayerExact(player.getName()) != null ? ServerUtils.getPlayerExact(player.getName()).getName() : player.getName();
//	}
	
	public static String getIP(Player player) {
		return player.getAddress().getAddress().getHostAddress();
	}
	
	public static int getPing(Player player) {
		CraftPlayer p = (CraftPlayer) player;
		return p.getHandle().ping;
	}
	
//	public static String getCountryIsoCode(Player player) {
//		
//		String country = "unknown";
//		
//		File database = new File("plugins/Fyre-Server/GeoLite2-Country.mmdb");
//		try {
//			
//			DatabaseReader reader = new DatabaseReader.Builder(database).build();
//			CountryResponse response = reader.country(player.getAddress().getAddress());
//			
//			country = response.getCountry().getIsoCode();
//			
//		}
//		catch (IOException e) { e.printStackTrace(); }
//		catch (GeoIp2Exception e) { e.printStackTrace(); }
//		
//		return country;
//		
//	}
	
//	public static String getPermissionsGroup(Player player) {
//		return VaultUtils.chat.getPrimaryGroup(player);
//	}
	
}
