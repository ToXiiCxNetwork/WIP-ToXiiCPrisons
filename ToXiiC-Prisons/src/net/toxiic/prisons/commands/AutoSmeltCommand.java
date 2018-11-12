package net.toxiic.prisons.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.toxiic.prisons.Main;
import net.toxiic.prisons.util.MessageUtil;
import net.toxiic.prisons.util.files.Messages;

public class AutoSmeltCommand
  implements CommandExecutor
{
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  {
    if (commandLabel.equalsIgnoreCase("autosmelt"))
    {
      if (!(sender instanceof Player))
      {
        sender.sendMessage("This command can only be run by a player!");
        return true;
      }
      Player p = (Player)sender;
      if (p.hasPermission("toxiicprisons.autosmelt"))
      {
        if (args.length == 1)
        {
          if (args[0].equalsIgnoreCase("on"))
          {
            if (!Main.playerSmelt.contains(p.getUniqueId()))
            {
              Main.playerSmelt.add(p.getUniqueId());
              p.sendMessage(MessageUtil.replace(Messages.getMessage("Messages.Pickup.smelton", p, null)));
            }
            else
            {
              p.sendMessage(MessageUtil.replace(Messages.getMessage("Messages.Pickup.smeltison", p, null)));
            }
          }
          else if (args[0].equalsIgnoreCase("off")) {
            if (Main.playerSmelt.contains(p.getUniqueId()))
            {
              Main.playerSmelt.remove(p.getUniqueId());
              p.sendMessage(MessageUtil.replace(Messages.getMessage("Messages.Pickup.smeltoff", p, null)));
            }
            else
            {
              p.sendMessage(MessageUtil.replace(Messages.getMessage("Messages.Pickup.smeltisoff", p, null)));
            }
          }
        }
        else
        {
          String msgs = Messages.getMessage("Messages.Pickup.Smelt.help", p, null);
  
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
