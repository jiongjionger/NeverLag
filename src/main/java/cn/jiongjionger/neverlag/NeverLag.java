package cn.jiongjionger.neverlag;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import cn.jiongjionger.neverlag.command.CommandBase;
import cn.jiongjionger.neverlag.command.CommandBenchmark;
import cn.jiongjionger.neverlag.command.CommandClear;
import cn.jiongjionger.neverlag.command.CommandGC;
import cn.jiongjionger.neverlag.command.CommandHardWare;
import cn.jiongjionger.neverlag.command.CommandInfo;
import cn.jiongjionger.neverlag.command.CommandPing;
import cn.jiongjionger.neverlag.command.CommandTabComplete;
import cn.jiongjionger.neverlag.fixer.AntiInfiniteRail;
import cn.jiongjionger.neverlag.gui.GUISortPingListener;
import cn.jiongjionger.neverlag.system.TpsWatcher;
import cn.jiongjionger.neverlag.system.WatchDog;
import cn.jiongjionger.neverlag.utils.PingUtils;

public class NeverLag extends JavaPlugin implements Listener {

	@SuppressWarnings("unused")
	private static final String OPENSOURCE_EN = "The plugin has been completely open source, you DO NOT have to decompile to see the source code, github here https://github.com/jiongjionger/NeverLag, it should be noted that if you use the plugin source, even if part of the plugin. You must also declare the source and completely opensource your software project!";
	@SuppressWarnings("unused")
	private static final String OPENSOURCE_CN = "本插件已经完全开源，你不必反编译来查看源代码，开源地址为：https://github.com/jiongjionger/NeverLag，需要注意的是，如果你使用了本插件的源码，哪怕是一部分，你也必须申明来源并且完全开源你的软件项目！";
	private static NeverLag instance;
	private static boolean isInstallProtocoLib;
	private static WatchDog watchDog;
	private static TpsWatcher tpsWatcher;

	public static NeverLag getInstance() {
		return instance;
	}

	public static boolean isInstallProtocoLib() {
		return isInstallProtocoLib;
	}

	public static WatchDog getWatchDog() {
		return watchDog;
	}

	public static TpsWatcher getTpsWatcher() {
		return tpsWatcher;
	}

	@Override
	public void onEnable() {
		instance = this;
		isInstallProtocoLib = Bukkit.getPluginManager().isPluginEnabled("ProtocolLib");
		// 计算TPS
		tpsWatcher = new TpsWatcher();
		Bukkit.getScheduler().runTaskTimer(this, tpsWatcher, 1000L, 50L);
		// 开启主线程停顿检测线程
		watchDog = new WatchDog();
		// 初始化getPing的反射
		PingUtils.init();
		// TO DO 一堆new实例和配置文件
		this.registerCommand();
		this.registerListener();

	}

	@Override
	public void onDisable() {
		// 兼容PlugMan等插件
		watchDog.stop();
		Bukkit.getScheduler().cancelTasks(instance);
	}

	private void registerCommand() {
		CommandBase baseCommandExecutor = new CommandBase();
		getCommand("neverlag").setExecutor(baseCommandExecutor);
		getCommand("neverlag").setTabCompleter(new CommandTabComplete());
		baseCommandExecutor.registerSubCommand("benchmark", new CommandBenchmark());
		baseCommandExecutor.registerSubCommand("hardware", new CommandHardWare());
		baseCommandExecutor.registerSubCommand("gc", new CommandGC());
		baseCommandExecutor.registerSubCommand("info", new CommandInfo());
		baseCommandExecutor.registerSubCommand("ping", new CommandPing());
		baseCommandExecutor.registerSubCommand("clear", new CommandClear());
	}

	private void registerListener() {
		Bukkit.getServer().getPluginManager().registerEvents(new GUISortPingListener(), this);
	}
}
