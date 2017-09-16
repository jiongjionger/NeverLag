package cn.jiongjionger.neverlag.cleaner;

import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.system.RedstoneCounter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class HighfrequencyRedstoneCleaner implements Listener {

	// 保存红石频率信息，按周期判断并且清空
	private final HashMap<Location, Integer> syncRestoneRecord = new HashMap<>();
	private final ConcurrentHashMap<Location, Integer> asyncRestoneRecord = new ConcurrentHashMap<>();

	private final ConfigManager cm = ConfigManager.getInstance();
	private final RedstoneCounter rc = RedstoneCounter.getInstance();
	private final NeverLag plg = NeverLag.getInstance();

	public HighfrequencyRedstoneCleaner() {
		plg.getServer().getPluginManager().registerEvents(this, plg);
		plg.getServer().getScheduler().runTaskTimerAsynchronously(plg, new Runnable() {
			@Override
			public void run() {
				if (cm.isCheckRedstoneOnAsync) {
					asyncRestoneRecord.clear();
				}
			}
		}, cm.redstoneCheckDelay * 20L, cm.redstoneCheckDelay * 20L);
		plg.getServer().getScheduler().runTaskTimer(plg, new Runnable() {
			@Override
			public void run() {
				if (!cm.isCheckRedstoneOnAsync) {
					syncRestoneRecord.clear();
				}
			}
		}, cm.redstoneCheckDelay * 20L, cm.redstoneCheckDelay * 20L);
	}

	/*
	 * 异步方法统计某个位置的红石时间次数，并且在达到阀值以后采取措施
	 * 
	 * @param loc 需要检查阀值和统计的位置
	 * 
	 * @param typeid 触发红石时间的方块类型id
	 * 
	 */
	private void asyncCheckAndRecord(final Location loc, final int typeId) {
		// 使用自带的任务调度而非Thead避免一些问题
		plg.getServer().getScheduler().runTaskAsynchronously(plg, new Runnable() {
			@Override
			public void run() {
				Integer count = asyncRestoneRecord.get(loc);
				if (count == null) {
					asyncRestoneRecord.put(loc, 1);
					return;
				}
				count++;
				// 中继器需要单独判断
				if (typeId == 93 || typeId == 94) {
					if (count >= cm.diodeLimit) {
						breakRestone(loc, false);
						asyncRestoneRecord.remove(loc);
						return;
					}
				} else {
					if (count >= cm.redstoneLimit) {
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

	/*
	 * 破坏达到阀值的红石方块
	 * 
	 * @param loc 需要破坏的方块位置
	 * 
	 * @param isOnSync 是否同步方法破坏
	 */
	private void breakRestone(final Location loc, boolean isOnSync) {
		if (isOnSync) {
			if (cm.isRedstoneDrop) {
				loc.getBlock().breakNaturally();
			} else {
				loc.getBlock().setType(Material.AIR);
			}
		} else {
			// 异步方法中强制切回同步处理
			plg.getServer().getScheduler().runTask(plg, new Runnable() {
				@Override
				public void run() {
					// 防止重新加载已经卸载的区块
					if (loc.getWorld().isChunkLoaded(loc.getBlockX() >> 4, loc.getBlockZ() >> 4)) {
						if (cm.isRedstoneDrop) {
							loc.getBlock().breakNaturally();
						} else {
							loc.getBlock().setType(Material.AIR);
						}
					}
				}
			});
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	// 当红石触发的时候，记录当前坐标红石事件的次数，超出阀值则清理
	public void onBlockRedstone(BlockRedstoneEvent e) {
		int typeId = e.getBlock().getTypeId();
		if (!cm.redstoneClearType.contains(typeId)) {
			return;
		}
		final Location loc = e.getBlock().getLocation();
		if (cm.isCheckRedstoneOnAsync) {
			asyncCheckAndRecord(loc, typeId);
		} else {
			syncCheckAndRecord(loc, typeId);
		}
	}

	/*
	 * 同步方法统计某个位置的红石时间次数，并且在达到阀值以后采取措施
	 * 
	 * @param loc 需要检查阀值和统计的位置
	 * 
	 * @param typeid 触发红石时间的方块类型id
	 * 
	 */
	private void syncCheckAndRecord(final Location loc, final int typeId) {
		Integer count = syncRestoneRecord.get(loc);
		if (count == null) {
			syncRestoneRecord.put(loc, 1);
			return;
		}
		count++;
		// 中继器需要单独判断
		if (typeId == 93 || typeId == 94) {
			if (count >= cm.diodeLimit) {
				breakRestone(loc, true);
				syncRestoneRecord.remove(loc);
				return;
			}
		} else {
			if (count >= cm.redstoneLimit) {
				breakRestone(loc, true);
				syncRestoneRecord.remove(loc);
				return;
			}
		}
		syncRestoneRecord.put(loc, count);
		rc.updateRedstoneCount(true);
	}
}
