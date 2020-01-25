package me.mn7cc.nexus.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.mn7cc.nexus.NexusFileManager;

public class BaseFile {
	
    private File file;
    private FileConfiguration data;

    public BaseFile(NexusFileManager fileManager, String resource, File file, LinkedHashMap<String, Object> defaults) {
    	
    	this.file = file;
    	
		if(!file.exists()) fileManager.copyFile(resource, file);
		
		try { data = YamlConfiguration.loadConfiguration(file); }
        catch (IllegalArgumentException e) { data = new YamlConfiguration(); }
		
		for(Entry<String, Object> entry : defaults.entrySet()) if(!data.contains(entry.getKey())) data.set(entry.getKey(), entry.getValue());
		
		saveData();
		
    }
	
    public File getFile() { return file; }
	public FileConfiguration getData() { return data; }
	
    public void reloadData() {
        data = YamlConfiguration.loadConfiguration(file);
    }
    
	public void saveData() {
		try { data.save(file); }
		catch (IOException e) { e.printStackTrace(); }
	}
	
	public void setValue(String path, Object value) {
		data.set(path, value);
	}
	
	public String getStringValue(String path) {
		return data.contains(path) ? data.getString(path) : null;
	}
	
	public double getDoubleValue(String path) {
		return data.contains(path) ? data.getDouble(path) : 0;
	}
	
	public int getIntegerValue(String path) {
		return data.contains(path) ? data.getInt(path) : 0;
	}
	
	public boolean getBooleanValue(String path) {
		return data.contains(path) ? data.getBoolean(path) : null;
	}
	
	public List<String> getStringListValue(String path) {
		return data.contains(path) ? data.getStringList(path) : new ArrayList<String>();
	}
	
	public ConfigurationSection getConfigurationSection(String path) {
		return data.contains(path) ? data.getConfigurationSection(path) : null;
	}
	
	public boolean containsValue(String path) {
		return data.contains(path);
	}
	
	public void setHeader(String header) {
		data.options().header(header);
	}

}