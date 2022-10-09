package dev.heliosares.auxprotect.core;

import java.io.File;
import java.io.InputStream;

import dev.heliosares.auxprotect.database.DbEntry;
import dev.heliosares.auxprotect.database.SQLManager;

public interface IAuxProtect {

	File getDataFolder();

	InputStream getResource(String string);

	String translate(String key);

// TODO	String translate(String key, Object... args);

	void info(String msg);

	void debug(String msg);

	void debug(String msg, int verb);

	void warning(String msg);

	void print(Throwable t);

	boolean isBungee();

	SQLManager getSqlManager();

	public int getDebug();
	
	public void setDebug(int debug);

	APConfig getAPConfig();

	void add(DbEntry dbEntry);

	public void runAsync(Runnable run);

	public void runSync(Runnable runnable);

	public void reloadConfig();
	
	public String getCommandPrefix();
	
	public MySender getConsoleSender();
}
