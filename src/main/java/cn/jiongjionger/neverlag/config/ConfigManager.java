package cn.jiongjionger.neverlag.config;

import cn.jiongjionger.neverlag.NeverLag;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ConfigManager extends AbstractConfig {

	private static final class ConfigManagerHolder {
		private static final ConfigManager INSTANCE = new ConfigManager();
	}

	public static final ConfigManager getInstance() {
		return ConfigManagerHolder.INSTANCE;
	}

	@F("lang")
	public String lang = Locale.getDefault().toString();

	// TODO: 让各种世界名列表啊, 物品ID列表啊变成它们应该有的类型

	// 是否开启实体清理功能
	@F("Cleaner.Entity.Enabled")
	public boolean cleanEntityEnabled = false;
	// 实体清理间隔
	@F("Cleaner.Entity.Interval")
	public int cleanEntityInterval = 120;
	// 实体清理阈值。小于 0 则为禁用
	@F("Cleaner.Entity.Threshold")
	public int cleanEntityThreshold = 500;
	// 不清理哪些世界的实体
	@F("Cleaner.Entity.WorldWhitelist")
	public Set<String> cleanEntityWorldWhitelist = Collections.emptySet();
	// 在玩家距离实体多少格距离的时候，不清理这个实体。小于 0 则为禁用
	@F("Cleaner.Entity.DontCleanIfPlayerNearerThan")
	public int cleanEntityPlayerNearbyDistance = 10;
	// 清理后是否公告
	@F("Cleaner.Entity.Broadcast")
	public boolean cleanEntityBroadcast = true;

	// 清理种类是否包括所有动物
	@F("Cleaner.Entity.Included.Animals")
	public boolean cleanEntityIncludeAnimals = false;
	// 清理种类是否包括所有怪物
	@F("Cleaner.Entity.Included.Monsters")
	public boolean cleanEntityIncludeMonsters = false;
	// 清理种类是否包括鱿鱼
	@F("Cleaner.Entity.Included.Squids")
	public boolean cleanEntityIncludeSquids = true;
	// 清理种类是否包括抛射物（箭、雪球等）
	@F("Cleaner.Entity.Included.Projectiles")
	public boolean cleanEntityIncludeProjectiles = true;
	// 其他需要清理的种类
	@F("Cleaner.Entity.Included.Others")
	public Set<String> cleanEntityIncludeList = Collections.emptySet();

	// 需要从 include 设定里排除的种类
	@F("Cleaner.Entity.Excluded")
	public Set<String> cleanEntityExcludeList = Collections.emptySet();

	// 是否清理掉落物
	@F("Cleaner.DropItem.Enabled")
	public boolean cleanItemEnabled = true;
	// 物品清理间隔
	@F("Cleaner.DropItem.Interval")
	public int cleanItemInterval = 300;
	// 不清理哪些世界的掉落物
	@F("Cleaner.DropItem.WorldWhitelist")
	public Set<String> cleanItemWorldWhitelist = Collections.emptySet();
	// 不清理哪些类型的掉落物
	@F("Cleaner.DropItem.IdWhitelist")
	public Set<Integer> cleanItemIdWhitelist = Collections.emptySet();
	// 在玩家距离掉落物多少格距离的时候，不清理这个物品。小于 0 则为禁用
	@F("Cleaner.DropItem.DontCleanIfPlayerNearerThan")
	public int cleanItemPlayerNearbyDistance = 10;
	// 清理后是否公告
	@F("Cleaner.DropItem.Broadcast")
	public boolean cleanItemBroadcast = true;
	// 清理前是否预告
	@F("Cleaner.DropItem.Forenotice")
	public boolean cleanItemForenotice = true;
	// 清理前是否在物品之上悬浮显示倒计时
	@F("Cleaner.DropItem.HoloMessage")
	public boolean cleanItemHoloMessage = false;

	// 是否检测超频红石
	@F("Redstone.Enabled")
	public boolean redstoneCheckEnabled = false;
	// 是否异步检测
	@F("Redstone.AsyncCheck")
	public boolean redstoneAsyncCheck = true;
	// 红石频率阀值
	@F("Redstone.Threshold")
	public int redstoneThreshold = 10;
	// 红石中继器阀值
	@F("Redstone.DiodeThreshold")
	public int redstoneDiodeThreshold = 50;
	// 红石检测周期
	@F("Redstone.CheckInterval")
	public int redstoneCheckInterval = 50;
	// 红石清理列表
	@F("Redstone.RemoveTypeList")
	public Set<Integer> redstoneRemoveType = Collections.emptySet();
	// 直接清理还是自然掉落
	@F("Redstone.DropOnRemove")
	public boolean redstoneDropOnRemove = true;
	// 红石消息是否开启
	@F("Redstone.Broadcast")
	public boolean redstoneBroadcast = true;

	// 是否开启实体密集检测
	@F("MobCollision.Enabled")
	public boolean mobCollisionEnabled = true;
	// 实体密集检测大范围阀值
	@F("MobCollision.LargeLimit")
	public int mobCollisionLargeLimit = 30;
	// 实体密集检测小范围阀值
	@F("MobCollision.SmallLimit")
	public int mobCollisionSmallLimit = 5;
	// 实体密集检测周期
	@F("MobCollision.CheckInterval")
	public int mobCollisionCheckInterval = 600;

	// 是否开启刷铁轨检测
	@F("BugFix.AntiInfiniteRail.Enabled")
	public boolean isAntiInfiniteRail = true;
	// 是否开启防御聊天内容蹦服
	@F("BugFix.AntiChatCrash.Enabled")
	public boolean isAntiChatCrash = true;
	// 是否开启防御骨粉刷物品（新版的forge已经修复了这个问题）
	@F("BugFix.AntiBonemealDupe.Enabled")
	public boolean isAntiBonemealDupe = true;
	// 骨粉右键方块ID的黑名单
	@F("BugFix.AntiBonemealDupe.Blacklist")
	public Set<Integer> antiBonemealBlackList = Collections.emptySet();
	// 是否开启防止矿车通过传送门
	@F("Tweaker.AntiMinecartPortal.Enabled")
	public boolean isAntiMinecartPortal = false;
	// 是否开启防御负数物品无限使用的BUG（1.8.3以后的版本已经修复）
	@F("BugFix.AntiNegativeItemDupe.Enabled")
	public boolean isAntiNegativeItemDupe = true;
	// 是否防御刷丢弃物品的BUG（1.6.4~1.11.2的部分版本都存在这个问题）
	@F("BugFix.AntiDropItemDupe.Enabled")
	public boolean isAntiDropItemDupe = true;
	// 是否防御牌子蹦服（1.8.3+已修复）
	@F("BugFix.AntiSignCrash.Enabled")
	public boolean isAntiSignCrash = true;
	// 是否防御地狱漏斗卡服/刷物品（高版本已经不存在这个问题）
	@F("BugFix.AntiNetherHopper.Enabled")
	public boolean isAntiNetherHopper = false;
	// 是否防御使用门来刷甘蔗、仙人掌（例如在没有权限的领地内对着有甘蔗的沙子放置门，可以无限刷甘蔗）
	@F("BugFix.AntiPlaceDoorDupe.Enabled")
	public boolean isAntiPlaceDoorDupe = true;
	// 是否防御容器多人同时访问的时候刷物品（新版本服务端已修复，但是有部分插件依然会导致这个问题）
	@F("BugFix.AntiChestViewerDupe.Enabled")
	public boolean isAntiChestViewerDupe = true;
	// 是否防御利用漏斗+水流+头颅+比较器无限蹦服
	@F("BugFix.AntiSkullCrash.Enabled")
	public boolean isAntiCrashSkull = true;
	// 是否防御QS商店旁边放箱子变成大箱子
	@F("BugFix.AntiQuickShopDoubleChest.Enabled")
	public boolean isAntiQuickDoubleChest = true;
	// QS商店标识，英文一般为QuickShop，中文一般为快捷商店
	@F("BugFix.AntiQuickShopDoubleChest.Flag")
	public String quickshopSignFlag = "QuickShop";
	// 防止大小写ID同时登录
	@F("BugFix.AntiDoubleLogin.Enabled")
	public boolean isAntiDoubleLogin = true;

	// 防止使用怪物蛋修改刷怪笼的类型
	@F("Tweaker.DisableChangeSpawnerType.Enabled")
	public boolean isDisableChangeSpawnerType = false;
	// 是否开启补救措施（自动清理非法类型的刷怪笼）
	@F("Tweaker.DisableChangeSpawnerType.AutoCleanIllegalTypeSpawner")
	public boolean isAutoCleanIllegalTypeSpawner = false;
	// 非法刷怪笼的类型（保存小写）
	@F("Tweaker.DisableChangeSpawnerType.IllegalSpawnerType")
	public Set<String> illegalSpawnerType = Collections.emptySet();
	// 清理模式 0：删除刷怪笼 1：自动修改为自然类型（骷髅、蜘蛛、僵尸）
	@F("Tweaker.DisableChangeSpawnerType.CleanMode")
	public int illegalTypeSpawnerCleanMode = 1;
	// 是否禁止村民交易
	@F("Tweaker.DisableVillagerTrade.Enabled")
	public boolean isAntiVillagerTrade = false;
	// 禁止村民交易的世界
	@F("Tweaker.DisableVillagerTrade.WorldBlacklist")
	public Set<String> disableVillagerTradeWorld = Collections.emptySet();
	// 禁止使用铁砧破坏皮肤头
	@F("Tweaker.AntiAnvilBreakSkull.Enabled")
	public boolean isAntiDamageSkull = true;
	// 是否Spawn区块常驻内存
	@F("Tweaker.KeepSpawnChunkInMemory")
	public boolean isKeepSpawnChunkInMemory = false;
	// 是否开启实体生成概率控制
	@F("Tweaker.SpawnRate.Enabled")
	public boolean spawnRateTweakerEnabled = false;
	// 普通自然生成概率（例如设置成20，就是有80%取消生成）
	@F("Tweaker.SpawnRate.Natural")
	public int spawnRateNatural = 0;
	// 刷怪笼生成概率
	@F("Tweaker.SpawnRate.Spawner")
	public int spawnRateSpawner = 0;
	// 特殊区块（沼泽等）
	@F("Tweaker.SpawnRate.ChunkGen")
	public int spawnRateChunkGen = 0;
	// 刷铁塔（村民生成铁傀儡）
	@F("Tweaker.SpawnRate.Village")
	public int spawnRateVillage = 0;
	// 刷金塔（传送门生成猪人）
	@F("Tweaker.SpawnRate.Portal")
	public int spawnRatePortal = 0;
	// 是否开启高空流水检测（Y轴低于海平面的63会被忽略）
	@F("Tweaker.AntiWaterfall.Enabled")
	public boolean antiWaterfall = true;
	// 流水下方超过多少格都是空气判定为高空流水
	@F("Tweaker.AntiWaterfall.GroundHeightThreshold")
	public int antiWaterfallHeight = 10;
	// 是否禁用TNT链式爆炸反应
	@F("Tweaker.DisableTNTChainReaction")
	public boolean disableChainReaction = false;
	// 是否限制实体穿越传送门，主要防止骷髅刷怪笼+流水+地狱门刷信标和发射器+地狱门使得区块永不被卸载都破坏平衡性的机制
	@F("Tweaker.PortalLimit.Enabled")
	public boolean portalDisallowEnabled = false;
	@F("Tweaker.PortalLimit.DisallowItem")
	public boolean portalDisallowItem = true;
	@F("Tweaker.PortalLimit.DisallowMonster")
	public boolean portalDisallowMonster = true;
	@F("Tweaker.PortalLimit.DisallowAnimals")
	public boolean portalDisallowAnimals = true;
	@F("Tweaker.PortalLimit.DisallowProjectile")
	public boolean portalDisallowProjectile = true;

	// 是否开启聊天和命令使用间隔控制
	@F("ChatAndCommandCooldown.Enabled")
	public boolean cooldownEnabled = false;
	// 命令白名单
	@F("ChatAndCommandCooldown.Whitelist")
	public Set<String> commandCooldownWhiteList = Collections.emptySet();
	// 命令间隔（毫秒）
	@F("ChatAndCommandCooldown.CommandCooldownTime")
	public long commandCooldownTime = 1000;
	// 聊天间隔（毫秒）
	@F("ChatAndCommandCooldown.ChatCooldownTime")
	public long chatCooldownTime = 1000;

	// 是否开启实体数量限制
	@F("SpawnLimit.Enabled")
	public boolean spawnLimitEnabled = false;
	// 限制的阀值
	@F("SpawnLimit.MonsterThreshold")
	public int spawnLimitMonsterThreshold = 2000;
	@F("SpawnLimit.AnimalThreshold")
	public int spawnLimitAnimalThreshold = 500;
	// 实体数量限制当TPS低于多少的时候才生效
	@F("SpawnLimit.TpsThreshold")
	public float spawnLimitTpsThreshold = 10;
	// 区块实体数量大于多少的时候刷怪笼不再工作
	@F("SpawnLimit.MaxSpawnerEntityPerChunk")
	public int maxSpawnEntityPerChunk = 30;

	// 是否开启农作物保护（防止玩家/怪物跳跃破坏农田）
	@F("Protect.Farm.Enabled")
	public boolean farmProtectEnabled = true;
	// 是否关闭爆炸破坏方块
	@F("Protect.Explode.Enabled")
	public boolean disableExplode = false;
	// 是否限制粘液块+粘液活塞的永动机
	@F("Tweaker.DisablePMM")
	public boolean disablePMM = false;

	// 是否限制Tiles数量
	@F("TileLimit.Enabled")
	public boolean tileLimitEnabled = false;
	// 投掷器默认限制阀值
	@F("TileLimit.DefaultLimits.Dropper")
	public int tileLimitDefaultDropper = 10;
	// 漏斗默认限制阀值
	@F("TileLimit.DefaultLimits.Hopper")
	public int tileLimitDefaultHopper = 20;
	// 发射器默认限制阀值
	@F("TileLimit.DefaultLimits.Dispenser")
	public int tileLimitDefaultDispenser = 10;
	// 活塞默认限制阀值
	@F("TileLimit.DefaultLimits.Piston")
	public int tileLimitDefaultPiston = 20;

	// 是否开启热区块常驻内存
	@F("HotChunkKeeper.Enable")
	public boolean hotChunkEnabled = true;
	/*
	 * 热门区块时间阀值 当一个区块卸载以后，X毫秒以后又重新被加载 这样达到 hotChunkHolderCountLimit 次以后
	 * 就会被判断为热门区块，这个区块会被常驻内存 除非TPS低于阀值或者热门区块超出 hotChunkHolderNum 的范围
	 * 并且当前区块的判断热门阀值是最低的区块
	 */
	@F("HotChunkKeeper.ReloadIntervalThreshold")
	public long hotChunkReloadIntervalThreshold = 60000;
	// 热门区块次数判定阀值
	@F("HotChunkKeeper.ReloadCountThreshold")
	public long hotChunkReloadCountThreshold = 5;
	// 热门区块数量最大值
	@F("HotChunkKeeper.MaximumCount")
	public int hotChunkMaxCount = 100;
	// 热门区块在TPS低于多少的时候不常驻
	@F("HotChunkKeeper.TpsThreshold")
	public double hotChunkTpsThreshold = 12;
	// 热门区块尝试卸载多少次不再被标记为热门
	@F("HotChunkKeeper.UnloadCountThreshold")
	public int hotChunkUnloadCountThreshold = 5;

	// 是否开启火焰蔓延速度限制
	@F("FireSpreadController.Enabled")
	public boolean fireSpreadControllerEnabled = true;
	// 火焰蔓延间隔时间（毫秒）
	@F("FireSpreadController.SpreadInterval")
	public long fireSpreadInterval = 5000;

	// 是否开启区块卸载的时候清理实体
	@F("ChunkUnloadCleaner.Enabled")
	public boolean removeEntityWhenChunkUnload = true;
	@F("ChunkUnloadCleaner.RemoveItem")
	public boolean removeItemWhenChunkUnload = false;
	@F("ChunkUnloadCleaner.RemoveArrow")
	public boolean removeArrowWhenChunkUnload = true;
	@F("ChunkUnloadCleaner.RemoveMonster")
	public boolean removeMonsterWhenChunkUnload = false;
	@F("ChunkUnloadCleaner.RemoveSquid")
	public boolean removeSquidWhenChunkUnload = false;
	@F("ChunkUnloadCleaner.RemoveAnimal")
	public boolean removeAnimalsWhenChunkUnload = false;

	// 当TPS过低的时候是否执行某些操作/命令
	@F("LowTPSAction.Enabled")
	public boolean lowTPSActionEnabled = false;
	@F("LowTPSAction.CleanItem")
	public boolean lowTPSCleanItem = true;
	@F("LowTPSAction.CleanEntity")
	public boolean lowTPSCleanEntity = false;
	// 显性调用GC是非常没必要的，只适合一些非常特殊的应用场景，请测试你的服务端是否需要这个功能
	@F("LowTPSAction.ForceGC")
	public boolean lowTPSForceGC = false;
	@F("LowTPSAction.TpsThreshold")
	public double lowTPSThreshold = 15;
	@F("LowTPSAction.ActionInterval")
	public long lowTPSActionInterval = 60000;
	@F("LowTPSAction.Command")
	public List<String> lowTPSCommand = Collections.emptyList();

	// 是否开启客户端MOD关闭功能
	@F("ClientModDisabler.Enabled")
	public boolean clientModDisabler = false;
	@F("ClientModDisabler.MagicCode")
	public List<String> modMagicCode = Collections.emptyList();

	public ConfigManager() {
		super(NeverLag.getInstance().getConfig(), NeverLag.getInstance().getLogger());
	}

	protected boolean checkValue(String key, Object value) {
		switch (key) {
			case "lang":
				return value instanceof String &&
					((String) value).matches("^[a-zA-Z]{2}([-_])[a-zA-Z]{2}$");
			default:
				return super.checkValue(key, value);
		}
	}

	@Override
	protected void load0() throws IOException {
		config = NeverLag.getInstance().getConfig();
		config.options().copyDefaults(true);
	}

	@Override
	protected void save0() throws IOException {
		NeverLag.getInstance().saveConfig();
	}
}