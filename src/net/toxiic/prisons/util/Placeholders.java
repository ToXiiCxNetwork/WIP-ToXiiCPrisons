package net.toxiic.prisons.util;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import net.toxiic.prisons.Main;
import net.toxiic.prisons.util.files.Config;

@SuppressWarnings("deprecation")
public class Placeholders extends EZPlaceholderHook {
	
	public Placeholders(Main plugin) {
		super(plugin, "toxiicessentials");
	}
	
	@Override
	public String onPlaceholderRequest(Player p, String identifier) {
		
		if (p == null) {
			return "";
		}
		
		if (identifier.equals("rank")) {
			return Util.getRank(p);
		}
		
		if (identifier.equals("rankformatted")) {
			return Config.getString("Ranks." + Util.getRank(p) + ".Format");
		}
		
		if (identifier.equals("prestige")) {
			return Util.getPrestige(p);
		}
		
		return null;
	}
}
