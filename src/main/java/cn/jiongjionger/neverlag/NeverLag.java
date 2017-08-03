package cn.jiongjionger.neverlag;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import cn.jiongjionger.neverlag.command.CommandDispatcher;
import cn.jiongjionger.neverlag.command.CommandBenchmark;
import cn.jiongjionger.neverlag.command.CommandClear;
import cn.jiongjionger.neverlag.command.CommandGC;
import cn.jiongjionger.neverlag.command.CommandHardWare;
import cn.jiongjionger.neverlag.command.CommandInfo;
import cn.jiongjionger.neverlag.command.CommandPing;
import cn.jiongjionger.neverlag.command.CommandTimings;
import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.fixer.AntiAUWMod;
import cn.jiongjionger.neverlag.gui.GUISortPingListener;
import cn.jiongjionger.neverlag.monitor.MonitorUtils;
import cn.jiongjionger.neverlag.system.TpsWatcher;
import cn.jiongjionger.neverlag.system.WatchDog;
import cn.jiongjionger.neverlag.utils.PingUtils;
import java.io.File;

public class NeverLag extends JavaPlugin implements Listener {

	@SuppressWarnings("unused")
	private static final String OPENSOURCE_EN = "The plugin has been completely open source, you DO NOT have to decompile to see the source code, github here https://github.com/jiongjionger/NeverLag, it should be noted that if you use the plugin source, even if part of the plugin. You must also declare the source and completely opensource your software project!";
	@SuppressWarnings("unused")
	private static final String OPENSOURCE_CN = "本插件已经完全开源，你不必反编译来查看源代码，开源地址为：https://github.com/jiongjionger/NeverLag，需要注意的是，如果你使用了本插件的源码，哪怕是一部分，你也必须申明来源并且完全开源你的软件项目！";
	private static NeverLag instance;
	private static I18n i18n;
	private static boolean isInstallProtocoLib;
	private static WatchDog watchDog;
	private static TpsWatcher tpsWatcher;

	public static NeverLag getInstance() {
		// 如果还没初始化就直接抛错, 不然过了很久才抛NPE, 定位起来麻烦
		if(instance == null) throw new IllegalStateException();
		return instance;
	}

	public static TpsWatcher getTpsWatcher() {
		return tpsWatcher;
	}

	public static WatchDog getWatchDog() {
		return watchDog;
	}

	public static I18n i18n() {
		return i18n;
	}
	
	/** 返回指定了命名空间的 I18n 实例. */
	public static I18n i18n(String namespace) {
		return i18n.clone(namespace);
	}

	public static boolean isInstallProtocoLib() {
		return isInstallProtocoLib;
	}

	@Override
	public void onEnable() {
		instance = this;
		ConfigManager.getInstance();
		i18n = I18n.load(new File(getDataFolder(), "lang/"), ConfigManager.getInstance().lang);

		// 判断是否安装了ProtocolLib前置插件
		isInstallProtocoLib = Bukkit.getPluginManager().isPluginEnabled("ProtocolLib");
		// 计算TPS
		tpsWatcher = new TpsWatcher();
		Bukkit.getScheduler().runTaskTimer(this, tpsWatcher, 1L, 1L);
		// 开启主线程停顿检测线程
		watchDog = new WatchDog();
		// 初始化getPing的反射
		PingUtils.init();
		// 初始化防御ALL-U-WANT模组
		if (isInstallProtocoLib) {
			AntiAUWMod.init();
		}
		// TO DO 一堆new实例和配置文件
		this.registerCommand();
		this.registerListener();
	}
	
	@Override
	public void onDisable() {
		try {
			// 兼容PlugMan等插件
			if(watchDog != null) 
				watchDog.stop();
			Bukkit.getScheduler().cancelTasks(instance);
			MonitorUtils.disable();
		} catch (NoClassDefFoundError ex) {
			// TODO Bukkit 插件加载系统的BUG
		}
	}

	private void registerCommand() {
		CommandDispatcher dispatcher = new CommandDispatcher();
		getCommand("neverlag").setExecutor(dispatcher);
		getCommand("neverlag").setTabCompleter(dispatcher);
		dispatcher.registerSubCommand(new CommandBenchmark());
		dispatcher.registerSubCommand(new CommandHardWare());
		dispatcher.registerSubCommand(new CommandGC());
		dispatcher.registerSubCommand(new CommandInfo());
		dispatcher.registerSubCommand(new CommandPing());
		dispatcher.registerSubCommand(new CommandClear());
		dispatcher.registerSubCommand(new CommandTimings());
	}

	private void registerListener() {
		Bukkit.getServer().getPluginManager().registerEvents(new GUISortPingListener(), this);
	}
}
