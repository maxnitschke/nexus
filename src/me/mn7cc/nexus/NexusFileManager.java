package me.mn7cc.nexus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import me.mn7cc.nexus.file.CommandsFile;
import me.mn7cc.nexus.file.ConfigFile;
import me.mn7cc.nexus.file.MessagesFile;
import me.mn7cc.nexus.file.ModulesFile;

public class NexusFileManager {
	
	private Nexus instance;
	private ConfigFile config;
	private ModulesFile modules;
	private CommandsFile commands;
	private MessagesFile messages;
	
	public NexusFileManager(Nexus instance) {
		this.instance = instance;
	}
	
	public void loadFiles() {
		
		config = new ConfigFile(this);
		modules = new ModulesFile(this);
		commands = new CommandsFile(this);
		messages = new MessagesFile(this);
		
	}
	
	public void reloadFiles() {
		
		config.reloadData();
		modules.reloadData();
		commands.reloadData();
		messages.reloadData();
		
	}
	
	public ConfigFile getConfigFile() { return config; }
	public ModulesFile getModulesFile() { return modules; }
	public CommandsFile getCommandsFile() { return commands; }
	public MessagesFile getMessagesFile() { return messages; }
	
	public void copyFile(String resource, File file) {
		
		if(!file.exists()) {
			
	    	if(!instance.getDataFolder().exists()) instance.getDataFolder().mkdir();
	    	if(!file.getParentFile().exists()) file.getParentFile().mkdir();
	    	
	        try {
	        	
	        	file.createNewFile();
	        	
	        	InputStream in = instance.getResource(resource);
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
