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
	private ArrayList<WorldInfo> worldInfo = new ArrayList<WorldInfo>();

	public ServerInfo() {
		this.realtimeTPS = NeverLag.getTpsWatcher().getLastTPS();
		this.avgTPS = NeverLag.getTpsWatcher().getAverageTPS();
		this.realtimeRedstone = RedStoneCounter.getInstance().getRedstoneRealTimeCount(true);
		this.avgRedstone = RedStoneCounter.getInstance().getRedstoneAvgCount(true);
		this.serverUpTime = DateUtils.formatDateDiff(ManagementFactory.getRuntimeMXBean().getStartTime());
		for (World world : Bukkit.getWorlds()) {
			worldInfo.add(new WorldInfo(world));
		}
	}

	public double getRealtimeTPS() {
		return realtimeTPS;
	}

	public void setRealtimeTPS(double realtimeTPS) {
		this.realtimeTPS = realtimeTPS;
	}

	public double getAvgTPS() {
		return avgTPS;
	}

	public void setAvgTPS(double avgTPS) {
		this.avgTPS = avgTPS;
	}

	public int getRealtimeRedstone() {
		return realtimeRedstone;
	}

	public void setRealtimeRedstone(int realtimeRedstone) {
		this.realtimeRedstone = realtimeRedstone;
	}

	public int getAvgRedstone() {
		return avgRedstone;
	}

	public void setAvgRedstone(int avgRedstone) {
		this.avgRedstone = avgRedstone;
	}

	public int getRuntimeTotalMemory() {
		return runtimeTotalMemory;
	}

	public void setRuntimeTotalMemory(int runtimeTotalMemory) {
		this.runtimeTotalMemory = runtimeTotalMemory;
	}

	public int getRuntimeUsedMemory() {
		return runtimeUsedMemory;
	}

	public void setRuntimeUsedMemory(int runtimeUsedMemory) {
		this.runtimeUsedMemory = runtimeUsedMemory;
	}

	public int getRuntimeAvailableMemory() {
		return runtimeAvailableMemory;
	}

	public void setRuntimeAvailableMemory(int runtimeAvailableMemory) {
		this.runtimeAvailableMemory = runtimeAvailableMemory;
	}

	public ArrayList<WorldInfo> getWorldInfo() {
		return worldInfo;
	}

	public void setWorldInfo(ArrayList<WorldInfo> worldInfo) {
		this.worldInfo = worldInfo;
	}

	public int getRuntimeMaxMemory() {
		return runtimeMaxMemory;
	}

	public void setRuntimeMaxMemory(int runtimeMaxMemory) {
		this.runtimeMaxMemory = runtimeMaxMemory;
	}

	public String getServerUpTime() {
		return serverUpTime;
	}

	public void setServerUpTime(String serverUpTime) {
		this.serverUpTime = serverUpTime;
	}

}
