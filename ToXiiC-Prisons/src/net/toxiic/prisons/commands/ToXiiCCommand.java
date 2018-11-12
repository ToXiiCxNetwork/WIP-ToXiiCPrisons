package net.toxiic.prisons.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.toxiic.prisons.Main;
import net.toxiic.prisons.util.DebugManager;
import net.toxiic.prisons.util.MessageUtil;
import net.toxiic.prisons.util.files.Messages;
public class ToXiiCCommand
  implements CommandExecutor
{
  private Main plugin;
  
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  {
    if (commandLabel.equalsIgnoreCase("toxiicprisons"))
    {
      if (!(sender instanceof Player))
      {
        sender.sendMessage("This command can only be run by a player!");
        return true;
      }
      Player p = (Player)sender;
      if (args.length == 1)
      {
        if (args[0].equalsIgnoreCase("reload"))
        {
          if (p.hasPermission("toxiicprisons.admin"))
          {
            this.plugin.reloadConfig();
            this.plugin.loadConfig();
            
            p.sendMessage(MessageUtil.replace(Messages.getMessage("Messages.reloadedconfig", p, null)));
          }
          else
          {
            p.sendMessage(MessageUtil.replace(Messages.getMessage("Messages.Pickup.nopermission", p, null)));
          }
        }
        else if (args[0].equalsIgnoreCase("debug"))
        {
          if ((p.hasPermission("toxiicprisons.admin")) || (this.plugin.getDescription().getAuthors().contains(p.getName())))
          {
            if (DebugManager.toggleDebugger(p.getUniqueId())) {
              p.sendMessage(ChatColor.AQUA + "You are now debugging ToXiiCxPrisons.");
            } else {
              p.sendMessage(ChatColor.AQUA + "You are no longer debugging ToXiiCxPrisons.");
            }
          }
          else {
            p.sendMessage(MessageUtil.replace(Messages.getMessage("Messages.Pickup.nopermission", p, null)));
          }
        }
        else if (p.hasPermission("toxiicpickup.admin"))
        {
          String msgs = Messages.getMessage("Messages.Pickup.AdminPickup.help", p, null);
            p.sendMessage(MessageUtil.replace(msgs));
        
        }
        else
        {
          p.sendMessage(MessageUtil.replace(Messages.getMessage("Messages.Pickup.nopermission", p, null)));
        }
      }
      else if (p.hasPermission("toxiicpickup.admin"))
      {
        String msgs = Messages.getMessage("Messages.Pickup.AdminPickup.help", p, null);
          p.sendMessage(MessageUtil.replace(msgs));
      }
      else
      {
        p.sendMessage(MessageUtil.replace(Messages.getMessage("Messages.Pickup.nopermission", p, null)));
      }
      return true;
    }
    return false;
  }
}
