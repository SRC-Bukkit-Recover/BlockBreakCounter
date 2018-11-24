package me.hsgamer.blockbreakcounter.commands;

import me.hsgamer.blockbreakcounter.BlockBreakCounter;
import me.hsgamer.blockbreakcounter.files.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
    private BlockBreakCounter plugin = BlockBreakCounter.getPlugin();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("help")) {
                if (sender.hasPermission("blockbreakcounter.help")) {
                    sender.sendMessage(ChatColor.BLUE + "-------------------------------------");
                    sender.sendMessage(ChatColor.GREEN + "/" + label + " help" + ChatColor.WHITE + " : Show this help page");
                    sender.sendMessage(ChatColor.GREEN + "/" + label + " count" + ChatColor.WHITE + " : Display the number of blocks broken");
                    sender.sendMessage(ChatColor.GREEN + "/" + label + " add" + ChatColor.WHITE + " : Add value for a player");
                    sender.sendMessage(ChatColor.GREEN + "/" + label + " set" + ChatColor.WHITE + " : Set value for a player");
                    sender.sendMessage(ChatColor.GREEN + "/" + label + " clear" + ChatColor.WHITE + " : Clear data of a player");
                    sender.sendMessage(ChatColor.GREEN + "/" + label + " clearall" + ChatColor.WHITE + " : Clear data of all players");
                    sender.sendMessage(ChatColor.GREEN + "/" + label + " reload" + ChatColor.WHITE + " : Reload plugin");
                    sender.sendMessage(ChatColor.BLUE + "-------------------------------------");
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have permissions to do that");
                }
            } else if (args[0].equalsIgnoreCase("count")) {
                if (sender.hasPermission("blockbreakcounter.count")) {
                    if (args.length != 3) {
                        sender.sendMessage(ChatColor.RED + "Usage: /" + label + " count <playername> <material>");
                    } else if (DataManager.isAvailable(args[2])) {
                        sender.sendMessage(ChatColor.GOLD + args[1] + ChatColor.WHITE + ": " + DataManager.getDataFile(args[2]).getConfig().getInt(args[1], 0));
                    } else {
                        sender.sendMessage(ChatColor.RED + args[2] + " is invalid or not added on the config");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have permissions to do that");
                }
            } else if (args[0].equalsIgnoreCase("add")) {
                if (sender.hasPermission("blockbreakcounter.add")) {
                    if (args.length != 4) {
                        sender.sendMessage(ChatColor.RED + "Usage: /" + label + " add <playername> <material> <number>");
                    } else {
                        int value;
                        try {
                            value = Integer.valueOf(args[3]);
                        }
                        catch (IllegalArgumentException e) {
                            sender.sendMessage(ChatColor.RED + args[3] + " is not a number");
                            return true;
                        }
                        if (DataManager.isAvailable(args[2])) {
                            DataManager.add(args[2], args[1], value);
                            sender.sendMessage(ChatColor.GREEN + "Successfully Added");
                        } else {
                            sender.sendMessage(ChatColor.RED + args[2] + " is invalid or not added on the config");
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have permissions to do that");
                }
            } else if (args[0].equalsIgnoreCase("set")) {
                if (sender.hasPermission("blockbreakcounter.set")) {
                    if (args.length != 4) {
                        sender.sendMessage(ChatColor.RED + "Usage: /" + label + " set <playername> <material> <number>");
                    } else {
                        int value;
                        try {
                            value = Integer.valueOf(args[3]);
                        }
                        catch (NumberFormatException e) {
                            sender.sendMessage(ChatColor.RED + args[3] + " is not a number");
                            return true;
                        }
                        if (DataManager.isAvailable(args[2])) {
                            DataManager.set(args[2], args[1], value);
                            sender.sendMessage(ChatColor.GREEN + "Successfully Set");
                        } else {
                            sender.sendMessage(ChatColor.RED + args[2] + " is invalid or not added on the config");
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have permissions to do that");
                }
            } else if (args[0].equalsIgnoreCase("clear")) {
                if (sender.hasPermission("blockbreakcounter.clear")) {
                    if (args.length != 3) {
                        sender.sendMessage(ChatColor.RED + "Usage: /" + label + " clear <playername> <material>");
                    } else if (DataManager.isAvailable(args[2])) {
                        DataManager.clearData(args[2], args[1]);
                        sender.sendMessage(ChatColor.GREEN + "Successfully Cleared");
                    } else {
                        sender.sendMessage(ChatColor.RED + args[2] + " is invalid or not added on the config");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have permissions to do that");
                }
            } else if (args[0].equalsIgnoreCase("clearall")) {
                if (sender.hasPermission("blockbreakcounter.clearall")) {
                    if (args.length != 2) {
                        sender.sendMessage(ChatColor.RED + "Usage: /" + label + " clearall <material>");
                    } else if (DataManager.isAvailable(args[1])) {
                        DataManager.clearAll(args[1]);
                        sender.sendMessage(ChatColor.GREEN + "Successfully Cleared");
                    } else {
                        sender.sendMessage(ChatColor.RED + args[1] + " is invalid or not added on the config");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have permissions to do that");
                }
            } else if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("blockbreakcounter.reload")) {
                    if (args.length != 1) {
                        sender.sendMessage(ChatColor.RED + "Usage: /" + label + " reload");
                    } else {
                        BlockBreakCounter.reloadPlugin(sender);
                        sender.sendMessage(ChatColor.GREEN + "Successfully Reloaded");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have permissions to do that");
                }
            }
        } else {
            sender.sendMessage(ChatColor.BLUE + "-------------------------------------");
            sender.sendMessage(ChatColor.GOLD + "Plugin: " + ChatColor.WHITE + this.plugin.getDescription().getName());
            sender.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.WHITE + this.plugin.getDescription().getVersion());
            sender.sendMessage(ChatColor.GOLD + "Authors: " + ChatColor.WHITE + this.plugin.getDescription().getAuthors().toString());
            sender.sendMessage(ChatColor.BLUE + "-------------------------------------");
        }
        return true;
    }
}

