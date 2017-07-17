package me.perotin.daysplus;

import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class DaysPlus extends JavaPlugin {

	HashSet<World> toChange;
	int toDecreaseByEveryX;
	@Override
	public void onEnable(){
		toChange = new HashSet<World>();
		saveDefaultConfig();
		loadWorlds();
		toDecreaseByEveryX = getConfig().getInt("delete-tick-every");
		BukkitScheduler scheduler = getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				for(World world : toChange){
					world.setTime(world.getTime() - 1);
				}
			}
		}, 0L, toDecreaseByEveryX);
	}
	
	private void loadWorlds(){
		FileConfiguration config = getConfig();
		List<String> worlds = config.getStringList("worlds");
		for(String world : worlds){
			if(Bukkit.getWorld(world) != null){
				toChange.add(Bukkit.getWorld(world));
			}else{
				getLogger().severe("[DaysPlus] Could not load " + world + "!");
				getLogger().severe("[DaysPlus] Make sure it exists!");
			}
		}
	}



}
