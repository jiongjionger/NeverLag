package cn.jiongjionger.neverlag.command;

import cn.jiongjionger.neverlag.I18n;
import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.config.ConfigManager;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Objects;

public abstract class AbstractSubCommand {
	protected final NeverLag plg = NeverLag.getInstance();
	protected final ConfigManager cm = ConfigManager.getInstance();

	/** 命名空间: <code>command.[name]</code> . */
	protected final I18n i18n;

	protected final String name;
	protected final String permission;
	protected final int minimumArgCount;

	public AbstractSubCommand(String name, int minimumArgCount) {
		this.name = name.toLowerCase();
		this.permission = "neverlag.command." + name;
		this.minimumArgCount = minimumArgCount;
		this.i18n = NeverLag.i18n("command.".concat(this.name));
	}

	public AbstractSubCommand(String name) {
		this(name, -1);
	}

	public abstract void onCommand(CommandSender sender, String[] args);

	public List<String> onTabComplete(CommandSender sender, String[] args) {
		return null;
	}

	public String getUsage() {
		return i18n.tr("usage");
	}

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
		return !(obj == null || getClass() != obj.getClass()) &&
			Objects.equals(this.name, ((AbstractSubCommand) obj).name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
