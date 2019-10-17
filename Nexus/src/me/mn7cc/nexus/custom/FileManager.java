package me.mn7cc.nexus.custom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import me.mn7cc.nexus.Nexus;
import me.mn7cc.nexus.file.ConfigFile;
import me.mn7cc.nexus.file.MessagesFile;
import me.mn7cc.nexus.file.ModulesFile;

public class FileManager {
	
	private static ConfigFile config;
	private static ModulesFile modules;
	private static MessagesFile messages;
	
	public static void loadFiles() {
		
		config = new ConfigFile();
		modules = new ModulesFile();
		messages = new MessagesFile();
		
	}
	
	public static void reloadFiles() {
		
		config.reloadData();
		modules.reloadData();
		messages.reloadData();
		
	}
	
	public static ConfigFile getConfigFile() { return config; }
	public static ModulesFile getModulesFile() { return modules; }
	public static MessagesFile getMessagesFile() { return messages; }
	
	public static void copyFile(String resource, File file) {
		
		if(!file.exists()) {
			
	    	if(!Nexus.getPlugin().getDataFolder().exists()) Nexus.getPlugin().getDataFolder().mkdir();
	    	if(!file.getParentFile().exists()) file.getParentFile().mkdir();
	    	
	        try {
	        	
	        	file.createNewFile();
	        	
	        	InputStream in = Nexus.getPlugin().getResource(resource);
		        OutputStream out = new FileOutputStream(file);
		        byte[] buffer = new byte[1024];
		        int current = 0;
		     
		        while((current = in.read(buffer)) > -1) {
		            out.write(buffer, 0, current);
		        }
		     
		        out.close();
		        in.close();
		        
			}
	        
	        catch (IOException e) { e.printStackTrace(); }
			
		}
		
	}
	
}
