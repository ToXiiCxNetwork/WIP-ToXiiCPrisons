package net.toxiic.prisons.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.toxiic.prisons.Main;
import net.toxiic.prisons.util.DebugManager;
import net.toxiic.prisons.util.MessageUtil;
import net.toxiic.prisons.util.files.Messages;

public class DropsCommand
  implements CommandExecutor
{
  
  @SuppressWarnings("deprecation")
public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  {
    if (commandLabel.equalsIgnoreCase("autopickup"))
    {
      if (!(sender instanceof Player))
      {
        sender.sendMessage("This command can only be run by a player!");
        return true;
      }
      Player p = (Player)sender;
      if (DebugManager.isDebugger(p))
      {
        p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.FIREWORK_STAR, 1, (short) 80) {} });
        p.updateInventory();
        return true;
      }
      if (p.hasPermission("toxiicprisons.autopickup"))
      {
        if (args.length == 1)
        {
          if (args[0].equalsIgnoreCase("on"))
          {
            if (!Main.playerDrops.contains(p.getUniqueId()))
            {
              Main.playerDrops.add(p.getUniqueId());
              p.sendMessage(MessageUtil.replace(Messages.getMessage("Messages.Pickup.autopickupon", p, null)));
            }
            else
            {
              p.sendMessage(MessageUtil.replace(Messages.getMessage("Messages.Pickup.autopickupison", p, null)));
            }
          }
          else if (args[0].equalsIgnoreCase("off"))
          {
            if (Main.playerDrops.contains(p.getUniqueId()))
            {
              Main.playerDrops.remove(p.getUniqueId());
              p.sendMessage(MessageUtil.replace(Messages.getMessage("Messages.Pickup.autopickupoff", p, null)));
            }
            else
            {
              p.sendMessage(MessageUtil.replace(Messages.getMessage("Messages.Pickup.autopickupisoff", p, null)));
            }
          }
          else
          {
            String msgs = Messages.getMessage("Messages.Pickup.AutoPickup.help", p, null);
            p.sendMessage(MessageUtil.replace(msgs));
            
          }
        }
        else
        {
          String msgs = Messages.getMessage("Messages.Pickup.AutoPickup.help", p, null);
          p.sendMessage(MessageUtil.replace(msgs));
        }
      }
      else {
        p.sendMessage(MessageUtil.replace(Messages.getMessage("Messages.Pickup.nopermission", p, null)));
      }
      return true;
    }
    return false;
  }
}
