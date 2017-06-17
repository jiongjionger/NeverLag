package cn.jiongjionger.neverlag.cleaner;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.system.RedStoneCounter;
import cn.jiongjionger.neverlag.NeverLag;

public class HighfrequencyRedStoneCleaner implements Listener {

	// 保存红石频率信息，按周期判断并且清空
	private HashMap<Location, Integer> syncRestoneRecord = new HashMap<Location, Integer>();
	private ConcurrentHashMap<Location, Integer> asyncRestoneRecord = new ConcurrentHashMap<Location, Integer>();

	private ConfigManager cm = ConfigManager.getInstance();
	private RedStoneCounter rc = RedStoneCounter.getInstance();
	private NeverLag plg = NeverLag.getInstance();

	public HighfrequencyRedStoneCleaner() {
		plg.getServer().getPluginManager().registerEvents(this, plg);
		plg.getServer().getScheduler().runTaskTimerAsynchronously(plg, new Runnable() {
			public void run() {
				if (cm.isCheckRedstoneOnAsync()) {
					asyncRestoneRecord.clear();
				}
			}
		}, cm.getRedstoneCheckDelay() * 20L, cm.getRedstoneCheckDelay() * 20L);
		plg.getServer().getScheduler().runTaskTimer(plg, new Runnable() {
			public void run() {
				if (!cm.isCheckRedstoneOnAsync()) {
					syncRestoneRecord.clear();
				}
			}
		}, cm.getRedstoneCheckDelay() * 20L, cm.getRedstoneCheckDelay() * 20L);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	// 当红石触发的时候，记录当前坐标红石事件的次数，超出阀值则清理
	private void onBlockRedstone(BlockRedstoneEvent e) {
		final int typeId = e.getBlock().getTypeId();
		if (!cm.getRedstoneClearType().contains(typeId)) {
			return;
		}
		final Location loc = e.getBlock().getLocation();
		if (cm.isCheckRedstoneOnAsync()) {
			asyncCheckAndRecord(loc, typeId);
		} else {
			syncCheckAndRecord(loc, typeId);
		}
	}

	private void syncCheckAndRecord(final Location loc, final int typeId) {
		Integer count = syncRestoneRecord.get(loc);
		if (count == null) {
			syncRestoneRecord.put(loc, 1);
			return;
		}
		count++;
		// 中继器需要单独判断
		if (typeId == 93 || typeId == 94) {
			if (count >= cm.getDiodeLimit()) {
				breakRestone(loc, true);
				syncRestoneRecord.remove(loc);
				return;
			}
		} else {
			if (count >= cm.getRedstoneLimit()) {
				breakRestone(loc, true);
				syncRestoneRecord.remove(loc);
				return;
			}
		}
		syncRestoneRecord.put(loc, count);
		rc.updateRedstoneCount(true);
	}

	private void asyncCheckAndRecord(final Location loc, final int typeId) {
		// 使用自带的任务调度而非Thead避免一些问题
		plg.getServer().getScheduler().runTaskAsynchronously(plg, new Runnable() {
			public void run() {
				Integer count = asyncRestoneRecord.get(loc);
				if (count == null) {
					asyncRestoneRecord.put(loc, 1);
					return;
				}
				count++;
				// 中继器需要单独判断
				if (typeId == 93 || typeId == 94) {
					if (count >= cm.getDiodeLimit()) {
						breakRestone(loc, false);
						asyncRestoneRecord.remove(loc);
						return;
					}
				} else {
					if (count >= cm.getRedstoneLimit()) {
						breakRestone(loc, false);
						asyncRestoneRecord.remove(loc);
						return;
					}
				}
				asyncRestoneRecord.put(loc, count);
				rc.updateRedstoneCount(false);
			}
		});
	}

	private void breakRestone(final Location loc, boolean isOnSync) {
		if (isOnSync) {
			if (cm.isRedstoneDrop()) {
				loc.getBlock().breakNaturally();
			} else {
				loc.getBlock().setType(Material.AIR);
			}
		} else {
			// 异步方法中强制切回同步处理
			plg.getServer().getScheduler().runTask(plg, new Runnable() {
				public void run() {
					// 防止重新加载已经卸载的区块
					if (loc.getWorld().isChunkLoaded(loc.getBlockX() >> 4, loc.getBlockZ() >> 4)) {
						if (cm.isRedstoneDrop()) {
							loc.getBlock().breakNaturally();
						} else {
							loc.getBlock().setType(Material.AIR);
						}
					}
				}
			});
		}
	}
}
