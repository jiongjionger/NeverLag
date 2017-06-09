package cn.jiongjionger.neverlag;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import cn.jiongjionger.neverlag.system.TpsWatcher;
import cn.jiongjionger.neverlag.system.WatchDog;
import cn.jiongjionger.neverlag.utils.PingUtils;

public class NeverLag extends JavaPlugin implements Listener {

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
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, tpsWatcher, 1000L, 50L);
		// 开启主线程停顿检测线程
		watchDog = new WatchDog();
		watchDog.start();
		// 初始化getPing的反射
		PingUtils.init();
		// TO DO 一堆new实例和配置文件
	}

	@Override
	public void onDisable() {
		// 兼容PlugMan等插件
		watchDog.interrupt();
		Bukkit.getScheduler().cancelTasks(instance);
	}
}
