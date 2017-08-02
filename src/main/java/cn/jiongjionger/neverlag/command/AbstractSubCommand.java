package cn.jiongjionger.neverlag.command;

import java.util.List;
import java.util.Objects;
import org.bukkit.command.CommandSender;
import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.config.ConfigManager;

public abstract class AbstractSubCommand {
	protected final NeverLag plg = NeverLag.getInstance();
	protected final ConfigManager cm = ConfigManager.getInstance();
	
	protected final String name;
	protected final String permission;
	protected final int minimumArgCount;

	public AbstractSubCommand(String name, int minimumArgCount) {
		this.name = name.toLowerCase();
		this.permission = "neverlag.command." + name;
		this.minimumArgCount = minimumArgCount;
	}
	
	public AbstractSubCommand(String name) {
		this(name, -1);
	}

	public abstract void onCommand(CommandSender sender, String[] args);

	public List<String> onTabComplete(CommandSender sender, String[] args) {
		return null;
	}

	public abstract String getUsage();

	public boolean isPlayerRequired() {
		return false;
	}
	
	public final String getName() {
		return name;
	}

	public String getPermission() {
		return permission;
	}

	public int getMinimumArgCount() {
		return minimumArgCount;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		return Objects.equals(this.name, ((AbstractSubCommand) obj).name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
