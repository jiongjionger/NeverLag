package cn.jiongjionger.neverlag.utils;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;

import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.system.RedStoneCounter;

public class ServerInfo {

	// 实时TPS
	private double realtimeTPS;
	// 平均TPS
	private double avgTPS;
	// 实时红石每秒操作数量
	private int realtimeRedstone;
	// 平均红石每秒操作数量
	private int avgRedstone;
	// 开服时长
	private String serverUpTime;
	// JVM最大内存
	private int runtimeMaxMemory;
	// JVM分配内存
	private int runtimeTotalMemory;
	// JVM已经使用的内存
	private int runtimeUsedMemory;
	// JVM剩余内存
	private int runtimeAvailableMemory;
	// 世界信息
	private ArrayList<WorldInfo> worldInfo = new ArrayList<>();

	public ServerInfo() {
		this.realtimeTPS = NeverLag.getTpsWatcher().getTps();
		this.avgTPS = NeverLag.getTpsWatcher().getAverageTPS();
		this.realtimeRedstone = RedStoneCounter.getInstance().getRedstoneRealTimeCount(true);
		this.avgRedstone = RedStoneCounter.getInstance().getRedstoneAvgCount(true);
		this.serverUpTime = DateUtils.formatDateDiff(ManagementFactory.getRuntimeMXBean().getStartTime());
		for (World world : Bukkit.getWorlds()) {
			worldInfo.add(new WorldInfo(world));
		}
	}

	public int getAvgRedstone() {
		return avgRedstone;
	}

	public double getAvgTPS() {
		return avgTPS;
	}

	public int getRealtimeRedstone() {
		return realtimeRedstone;
	}

	public double getRealtimeTPS() {
		return realtimeTPS;
	}

	public int getRuntimeAvailableMemory() {
		return runtimeAvailableMemory;
	}

	public int getRuntimeMaxMemory() {
		return runtimeMaxMemory;
	}

	public int getRuntimeTotalMemory() {
		return runtimeTotalMemory;
	}

	public int getRuntimeUsedMemory() {
		return runtimeUsedMemory;
	}

	public String getServerUpTime() {
		return serverUpTime;
	}

	public ArrayList<WorldInfo> getWorldInfo() {
		return worldInfo;
	}

	public void setAvgRedstone(int avgRedstone) {
		this.avgRedstone = avgRedstone;
	}

	public void setAvgTPS(double avgTPS) {
		this.avgTPS = avgTPS;
	}

	public void setRealtimeRedstone(int realtimeRedstone) {
		this.realtimeRedstone = realtimeRedstone;
	}

	public void setRealtimeTPS(double realtimeTPS) {
		this.realtimeTPS = realtimeTPS;
	}

	public void setRuntimeAvailableMemory(int runtimeAvailableMemory) {
		this.runtimeAvailableMemory = runtimeAvailableMemory;
	}

	public void setRuntimeMaxMemory(int runtimeMaxMemory) {
		this.runtimeMaxMemory = runtimeMaxMemory;
	}

	public void setRuntimeTotalMemory(int runtimeTotalMemory) {
		this.runtimeTotalMemory = runtimeTotalMemory;
	}

	public void setRuntimeUsedMemory(int runtimeUsedMemory) {
		this.runtimeUsedMemory = runtimeUsedMemory;
	}

	public void setServerUpTime(String serverUpTime) {
		this.serverUpTime = serverUpTime;
	}

	public void setWorldInfo(ArrayList<WorldInfo> worldInfo) {
		this.worldInfo = worldInfo;
	}

}
