package cn.jiongjionger.neverlag.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

interface ISubCommandExecutor extends CommandExecutor {

	public String getPermNode();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

}
