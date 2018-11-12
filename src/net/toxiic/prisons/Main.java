package net.toxiic.prisons;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.AsyncCatcher;

import net.milkbowl.vault.economy.Economy;
import net.toxiic.prisons.commands.AutoSmeltCommand;
import net.toxiic.prisons.commands.DropsCommand;
import net.toxiic.prisons.commands.Prestige;
import net.toxiic.prisons.commands.Ranks;
import net.toxiic.prisons.commands.Rankup;
import net.toxiic.prisons.commands.ToXiiCCommand;
import net.toxiic.prisons.util.DebugManager;
import net.toxiic.prisons.util.HoloAPI;
import net.toxiic.prisons.util.MessageUtil;
import net.toxiic.prisons.util.Placeholders;
import net.toxiic.prisons.util.Util;
import net.toxiic.prisons.util.files.Config;
import net.toxiic.prisons.util.files.Data;
import net.toxiic.prisons.util.files.Messages;

public class Main extends JavaPlugin {
	public static String fullInv;
	public static String world;
	public static List<String> allowedWorlds;
	public static List<UUID> playerSmelt = new ArrayList<UUID>();
	public static List<UUID> playerDrops = new ArrayList<UUID>();
	private static final Logger log = Logger.getLogger("Minecraft");
	public static Economy economy = null;
	public static Main plugin;

	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		if (!setupEconomy()) { //Check if a vault based economy is setup
			log.severe(String.format("[%s] - Disabled due to no Vault based economy found! Try installing Essentials!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		Bukkit.getLogger().info("[ToXiiCxPrisons] This plugin is managed by ToXiiCxMonster and ToXiiCxUltra");
		Bukkit.getLogger().info("[ToXiiCxPrisons] Feel free to contact us at http://discord.toxiic.net");
		plugin = this;
		Data.setup(plugin);
		Config.setup(plugin);
		Messages.setup(plugin);
		AsyncCatcher.enabled = false;

		Util.loadAll();

		registerCommands();
		registerListener(new Listeners());
		
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			new Placeholders(this).hook();
		}
		 getServer().getScheduler().runTaskTimer(this, new Runnable()
		    {
		      public void run()
		      {
		        net.toxiic.prisons.util.HoloAPI.clearOldHolograms();
		      }
		      
		    }, 10L, 10L);
		    
	}


	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = Logger.getLogger("Minecraft");

		logger.info(pdfFile.getName() + " has been disable!");
		getServer().getScheduler().cancelTasks(this);
	    HoloAPI.clearHolograms();
	    MessageUtil.clearLastMessages();
	    DebugManager.clearDebuggers();
	    if (allowedWorlds != null) {
	      allowedWorlds.clear();
	    }
	    playerSmelt.clear();
	    playerDrops.clear();
	    
	    plugin = null;
	  }

	private boolean setupEconomy() { //Registering Economy
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		economy = rsp.getProvider();
		return economy != null;
	}

	public static Economy getEconomy() {
		return economy;
	}

	public void registerCommands() { //register commands
		getCommand("prestige").setExecutor(new Prestige());
		getCommand("ranks").setExecutor(new Ranks());
		getCommand("rankup").setExecutor(new Rankup());
		getCommand("toxiicprisons").setExecutor(new ToXiiCCommand());	
		getCommand("autopickup").setExecutor(new DropsCommand());
		getCommand("autosmelt").setExecutor(new AutoSmeltCommand());
		
	}

	public void registerListener(Listener listener) { //register listeners
		Bukkit.getServer().getPluginManager().registerEvents(listener, this);
	}
	  
	public void loadConfig() {
	    allowedWorlds = getConfig().getStringList("worlds");
	    fullInv = Messages.getMessage("Messages.Pickup.fullinv", null, null);
	    world = getConfig().getString("world");
	}
	public static Main getInstance(){
	    return plugin;
	}

}
