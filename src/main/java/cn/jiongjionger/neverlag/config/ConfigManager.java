package cn.jiongjionger.neverlag.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import cn.jiongjionger.neverlag.NeverLag;

public class ConfigManager extends AbstractConfig {

	private final static class ConfigManagerHolder {
		private final static ConfigManager cm = new ConfigManager();
	}

	public final static ConfigManager getInstance() {
		return ConfigManagerHolder.cm;
	}

	/*
	 * TO DO : 修改配置文件获取的node
	 */

	// 是否开启清理掉落物功能
	@F("isClearDropItem")
	public boolean isClearDropItem;
	// 不清除物品的世界列表
	@F("noClearItemWorld")
	public HashSet<String> noClearItemWorld;
	// 玩家在掉落物附近时是否还清理这个物品
	@F("isClearItemPlayerNearby")
	public boolean isClearItemPlayerNearby;
	// 在玩家距离掉落物多少格距离的时候，不清理这个物品
	@F("clearItemPlayerNearbyDistance")
	public int clearItemPlayerNearbyDistance;
	// 是否清除掉落物品
	@F("isClearItem")
	public boolean isClearItem;
	// 掉落物清理周期
	@F("clearItemDelay")
	public int clearItemDelay;
	// 不清理的物品ID
	@F("noClearItemId")
	public HashSet<Integer> noClearItemId;
	// 是否清除展示框
	@F("isClearItemFrame")
	public boolean isClearItemFrame;
	// 是否清除船
	@F("isClearBoat")
	public boolean isClearBoat;
	// 是否清除经验球
	@F("isClearExpBall")
	public boolean isClearExpBall;
	// 是否清除正在掉落的物品
	@F("isClearFallingBlock")
	public boolean isClearFallingBlock;
	// 是否清除画
	@F("isClearPainting")
	public boolean isClearPainting;
	// 是否清理矿车
	@F("isClearMinecart")
	public boolean isClearMinecart;
	// 是否清理地上的箭头
	@F("isClearArrow")
	public boolean isClearArrow;
	// 是否清理地上的雪球
	@F("isClearSnowBall")
	public boolean isClearSnowBall;
	// 清理物品的时候是否公告
	@F("isBroadcastClearItem")
	public boolean isBroadcastClearItem;
	// 清理物品公告内容
	@F("clearItemBroadcastMessage")
	public String clearItemBroadcastMessage;
	// 清理物品公告提前通知内容
	@F("clearItemBroadcastPreMessage")
	public String clearItemBroadcastPreMessage;
	// 是否开启清理前60秒在物品上显示悬浮的倒计时
	@F("isClearItemPreHoloMessage")
	public boolean isClearItemPreHoloMessage = true;
	// 悬浮显示格式
	@F("clearItemPreHoloMessage")
	public String clearItemPreHoloMessage = "%TIME% 秒后将被清理";

	// 是否清理实体
	@F("isClearEntity")
	public boolean isClearEntity;
	// 不清除实体的世界列表
	@F("noClearEntityWorld")
	public HashSet<String> noClearEntityWorld;
	// 玩家在实体附近时是否还清理这个实体
	@F("isClearEntityPlayerNearby")
	public boolean isClearEntityPlayerNearby;
	// 在玩家距离实体多少格距离的时候，不清理这个实体
	@F("clearEntityPlayerNearbyDistance")
	public int clearEntityPlayerNearbyDistance;
	// 实体清理周期
	@F("clearMobDelay")
	public int clearMobDelay;
	// 是否达到设置实体上限才清除
	@F("isClearLimit")
	public boolean isClearLimit;
	// 实体清理阀值
	@F("clearEntityLimit")
	public int clearEntityLimit;
	// 是否清理动物
	@F("isClearAnimals")
	public boolean isClearAnimals;
	// 是否清理怪物
	@F("isClearMonster")
	public boolean isClearMonster;
	// 是否清理乌贼
	@F("isClearSquid")
	public boolean isClearSquid;
	// 是否清理村民
	@F("isClearVillager")
	public boolean isClearVillager;
	// 清理实体的黑名单
	@F("clearEntityTypeBlackList")
	public HashSet<String> clearEntityTypeBlackList;
	// 清理实体的白名单
	@F("clearEntityTypeWhiteList")
	public HashSet<String> clearEntityTypeWhiteList;
	// 清理实体的时候是否公告
	@F("isBroadcastClearEntity")
	public boolean isBroadcastClearEntity;
	// 清理实体公告内容
	@F("clearEntityBroadcastMessage")
	public String clearEntityBroadcastMessage;
	// 清理实体公告提前通知内容
	@F("clearEntityBroadcastPreMessage")
	public String clearEntityBroadcastPreMessage;

	// 是否检测超频红石
	@F("isCheckRedstone")
	public boolean isCheckRedstone;
	// 是否异步检测
	@F("isCheckRedstoneOnAsync")
	public boolean isCheckRedstoneOnAsync;
	// 红石频率阀值
	@F("redstoneLimit")
	public int redstoneLimit;
	// 红石中继器阀值
	@F("diodeLimit")
	public int diodeLimit;
	// 红石检测周期
	@F("redstoneCheckDelay")
	public int redstoneCheckDelay;
	// 红石清理列表
	@F("redstoneClearType")
	public HashSet<Integer> redstoneClearType;
	// 直接清理还是自然掉落
	@F("isRedstoneDrop")
	public boolean isRedstoneDrop;
	// 红石消息是否开启
	@F("isRedstoneMessage")
	public boolean isRedstoneMessage;

	// 是否开启实体密集检测
	@F("isCheckMobFarm")
	public boolean isCheckMobFarm;
	// 实体密集检测大范围阀值
	@F("checkMobFarmLooseLimit")
	public int checkMobFarmLooseLimit;
	// 实体密集检测小范围阀值
	@F("checkMobFarmTinyLimit")
	public int checkMobFarmTinyLimit;
	// 实体密集检测周期
	@F("checkMobFarmDelay")
	public int checkMobFarmDelay;

	// 是否开启刷铁轨检测
	@F("isAntiInfiniteRail")
	public boolean isAntiInfiniteRail;
	// 是否开启防御聊天内容蹦服
	@F("isAntiCrashChat")
	public boolean isAntiCrashChat;
	// 是否开启防御骨粉刷物品（新版的forge已经修复了这个问题）
	@F("isAntiBonemealDupe")
	public boolean isAntiBonemealDupe;
	// 骨粉右键方块ID的黑名单
	@F("antiBonemealBlackList")
	public HashSet<Integer> antiBonemealBlackList;
	// 是否开启防止矿车通过传送门
	@F("isAntiMinecartPortal")
	public boolean isAntiMinecartPortal;
	// 是否开启防御负数物品无限使用的BUG（1.8.3以后的版本已经修复）
	@F("isAntiNegativeItemDupe")
	public boolean isAntiNegativeItemDupe;
	// 是否防御刷丢弃物品的BUG（1.6.4~1.11.2的部分版本都存在这个问题）
	@F("isAntiDropItemDupe")
	public boolean isAntiDropItemDupe;
	// 是否防御牌子蹦服（1.8.3+已修复）
	@F("isAntiCrashSign")
	public boolean isAntiCrashSign;
	// 是否防御地狱漏斗卡服/刷物品（高版本已经不存在这个问题）
	@F("isAntiNetherHopper")
	public boolean isAntiNetherHopper;
	// 是否防御使用门来刷甘蔗、仙人掌（例如在没有权限的领地内对着有甘蔗的沙子放置门，可以无限刷甘蔗）
	@F("isAntiPlaceDoorDupe")
	public boolean isAntiPlaceDoorDupe;
	// 是否防御容器多人同时访问的时候刷物品（新版本服务端已修复，但是有部分插件依然会导致这个问题）
	@F("isAntiChestViewerDupe")
	public boolean isAntiChestViewerDupe;
	@F("antiChestViewerDupeMessage")
	public String antiChestViewerDupeMessage;
	// 是否防御利用漏斗+水流+比较器无限蹦服
	@F("isAntiCrashSkull")
	public boolean isAntiCrashSkull;
	// 是否防御QS商店旁边放箱子变成大箱子
	@F("isAntiQuickShopBigShop")
	public boolean isAntiQuickShopBigShop;
	// QS商店标识，英文一般为QuickShop，中文一般为快捷商店
	@F("quickshopFlag")
	public String quickshopFlag;
	// 提示语
	@F("antiQuickShopBigShopMessage")
	public String antiQuickShopBigShopMessage;
	// 防止大小写ID同时登录
	@F("isAntiDoubleLogin")
	public boolean isAntiDoubleLogin;
	@F("antiDoubleLoginMessage")
	public String antiDoubleLoginMessage;
	// 防止使用怪物蛋修改刷怪笼的类型
	@F("isAntiUseEggsChangeSpawnerType")
	public boolean isAntiUseEggsChangeSpawnerType;
	// 是否开启补救措施（自动清理非法类型的刷怪笼）
	@F("isAutoCleanIllegalTypeSpawner")
	public boolean isAutoCleanIllegalTypeSpawner;
	// 非法刷怪笼的类型（保存小写）
	@F("illegalSpawnerTypeSet")
	public HashSet<String> illegalSpawnerTypeSet = new HashSet<>();
	// 清理模式 0：删除刷怪笼 1：自动修改为自然类型（骷髅、蜘蛛、僵尸）
	@F("illegalTypeSpawnerCleanMode")
	public int illegalTypeSpawnerCleanMode;
	// 是否禁止村民交易
	@F("isAntiVillagerTrade")
	public boolean isAntiVillagerTrade;
	// 禁止村民交易的世界
	@F("disableVillagerTradeWorldSet")
	public HashSet<String> disableVillagerTradeWorldSet = new HashSet<>();
	// 禁止使用铁锭破坏皮肤头
	@F("isAntiDamageSkull")
	public boolean isAntiDamageSkull;

	// 是否开启聊天和命令使用间隔控制
	@F("isCooldownChatAndCommand")
	public boolean isCooldownChatAndCommand;
	// 命令白名单
	@F("commandCooldownWhiteList")
	public HashSet<String> commandCooldownWhiteList;
	// 命令间隔（毫秒）
	@F("commandCooldownTime")
	public long commandCooldownTime;
	// 聊天间隔（毫秒）
	@F("chatCooldownTime")
	public long chatCooldownTime;
	// 命令和聊天冷却时间提示语
	@F("commandCooldownMessage")
	public String commandCooldownMessage;
	@F("chatCooldownMessage")
	public String chatCooldownMessage;

	// 是否Spawn区块常驻内存
	@F("isSpawnChunkKeepInMemory")
	public boolean isSpawnChunkKeepInMemory;

	// 是否开启实体数量限制
	@F("isLimitEntitySpawn")
	public boolean isLimitEntitySpawn;
	// 限制的阀值
	@F("mobSpawnLimit")
	public int mobSpawnLimit;
	@F("animalsSpawnLimit")
	public int animalsSpawnLimit;
	// 实体数量限制是否考虑TPS因素
	@F("isLimitEntitySpawnByTps")
	public boolean isLimitEntitySpawnByTps;
	// 实体数量限制当TPS低于多少的时候才生效
	@F("entitySpawnLimitTpsLimit")
	public double entitySpawnLimitTpsLimit;
	// 区块实体数量大于多少的时候刷怪笼不再工作
	@F("spawnerEntityCountPerChunkLimit")
	public int spawnerEntityCountPerChunkLimit;

	// 是否开启农作物保护（防止玩家/怪物跳跃破坏农田）
	@F("isFarmProtect")
	public boolean isFarmProtect;
	// 是否关闭爆炸破坏方块
	@F("isDisableExplode")
	public boolean isDisableExplode;
	// 是否限制粘液块+粘液活塞的永动机
	@F("isDisablePMM")
	public boolean isDisablePMM;

	// 是否限制Tiles数量
	@F("isLimitTiles")
	public boolean isLimitTiles;
	// 投掷器默认限制阀值
	@F("limitTilesDropperDefault")
	public int limitTilesDropperDefault;
	// 漏斗默认限制阀值
	@F("limitTilesHopperDefault")
	public int limitTilesHopperDefault;
	// 发射器默认限制阀值
	@F("limitTilesDispenserDefault")
	public int limitTilesDispenserDefault;
	// 活塞默认限制阀值
	@F("limitTilesPistonDefault")
	public int limitTilesPistonDefault;
	// 提示语
	@F("limitTilesMessage")
	public String limitTilesMessage;

	// 是否开启实体生成概率控制
	@F("isSpawnRate")
	public boolean isSpawnRate;
	// 普通自然生成概率（例如设置成20，就是有80%取消生成）
	@F("normalSpawnRate")
	public int normalSpawnRate;
	// 刷怪笼生成概率
	@F("spawnerSpawnRate")
	public int spawnerSpawnRate;
	// 特殊区块（沼泽等）
	@F("chunkGenSpawnRate")
	public int chunkGenSpawnRate;
	// 刷铁塔（村民生成铁傀儡）
	@F("villageSpawnRate")
	public int villageSpawnRate;
	// 刷金塔（传送门生成猪人）
	@F("portalSpawnRate")
	public int portalSpawnRate;

	// 是否开启热区块常驻内存
	@F("isHotChunkHolder")
	public boolean isHotChunkHolder;
	/*
	 * 热门区块时间阀值 当一个区块卸载以后，X毫秒以后又重新被加载 这样达到 hotChunkHolderCountLimit 次以后
	 * 就会被判断为热门区块，这个区块会被常驻内存 除非TPS低于阀值或者热门区块超出 hotChunkHolderNum 的范围
	 * 并且当前区块的判断热门阀值是最低的区块
	 */
	@F("hotChunkHolderTimeLimit")
	public long hotChunkHolderTimeLimit;
	// 热门区块次数判定阀值
	@F("hotChunkHolderCountLimit")
	public long hotChunkHolderCountLimit;
	// 热门区块数量最大值
	@F("hotChunkHolderNum")
	public int hotChunkHolderNum;
	// 热门区块在TPS低于多少的时候不常驻
	@F("hotChunkHolderTpsLimit")
	public double hotChunkHolderTpsLimit;
	// 热门区块尝试卸载多少次不再被标记为热门
	@F("hotChunkUnloadTimeLimit")
	public int hotChunkUnloadTimeLimit;

	// 是否开启火焰蔓延速度限制
	@F("isReduceFireSpread")
	public boolean isReduceFireSpread;
	// 火焰蔓延间隔时间（毫秒）
	@F("reduceFireSpreadTime")
	public long reduceFireSpreadTime;

	// 是否开启TNT链式爆炸反应限制
	@F("noTNTChainReaction")
	public boolean noTNTChainReaction;

	// 是否开启高空流水检测（Y轴低于海平面的63会被忽略）
	@F("noHighFallWater")
	public boolean noHighFallWater;
	// 流水下方超过多少格都是空气判定为高空流水
	@F("noHighFallWaterDistance")
	public int noHighFallWaterDistance;

	// 是否开启区块卸载的时候清理实体
	@F("removeEntityWhenChunkUnload")
	public boolean removeEntityWhenChunkUnload;
	@F("removeDropItemWhenChunkUnload")
	public boolean removeDropItemWhenChunkUnload;
	@F("removeArrowWhenChunkUnload")
	public boolean removeArrowWhenChunkUnload;
	@F("removeMonsterWhenChunkUnload")
	public boolean removeMonsterWhenChunkUnload;
	@F("removeSquidWhenChunkUnload")
	public boolean removeSquidWhenChunkUnload;
	@F("removeAnimalsWhenChunkUnload")
	public boolean removeAnimalsWhenChunkUnload;

	// 是否限制实体穿越传送门，主要防止骷髅刷怪笼+流水+地狱门刷信标和发射器+地狱门使得区块永不被卸载都破坏平衡性的机制
	@F("isDisableEntityPortal")
	public boolean isDisableEntityPortal;
	@F("isDisableDropItemPortal")
	public boolean isDisableDropItemPortal;
	@F("isDisableMonsterPortal")
	public boolean isDisableMonsterPortal;
	@F("isDisableAnimalsPortal")
	public boolean isDisableAnimalsPortal;
	@F("isDisableProjectilePortal")
	public boolean isDisableProjectilePortal;

	// 当TPS过低的时候是否执行某些操作/命令
	@F("lowTPSAction")
	public boolean lowTPSAction;
	@F("lowTPSCleanItem")
	public boolean lowTPSCleanItem;
	@F("lowTPSCleanEntity")
	public boolean lowTPSCleanEntity;
	@F("lowTPSForceGC")
	public boolean lowTPSForceGC;
	@F("lowTPSLimit")
	public double lowTPSLimit;
	@F("lowTPSActionTimeLimit")
	public long lowTPSActionTimeLimit;
	@F("lowTPSCommand")
	public ArrayList<String> lowTPSCommand = new ArrayList<>();

	// 是否开启客户端MOD关闭功能
	@F("clientModDisabler")
	public boolean clientModDisabler;
	@F("modMagicCode")
	public ArrayList<String> modMagicCode = new ArrayList<>();

	// 命令提示
	@F("commandNoPerm")
	public String commandNoPerm = "你没有权限执行这个指令！";
	@F("commandGCMessage")
	public String commandGCMessage;
	@F("commandGCNoEffectMessage")
	public String commandGCNoEffectMessage;
	@F("commandInfoBaseMessage")
	public String commandInfoBaseMessage;
	@F("commandWorldInfoMessage")
	public String commandWorldInfoMessage;
	@F("commandStartBenchmark")
	public String commandStartBenchmark = "开始对CPU单线程性能进行测试..请稍等";
	@F("commandFinishBenchmark")
	public String commandFinishBenchmark = "测试完毕，单线程性能分数：%SCORE%";
	@F("commandStartFetchHardWareInfo")
	public String commandStartFetchHardWareInfo = "正在开始读取服务器硬件信息，请稍等...";
	@F("commandHardWareJVMInfo")
	public String commandHardWareJVMInfo = "JVM信息: %JVMINFO%";
	@F("commandHardWareJVMArg")
	public String commandHardWareJVMArg = "JVM参数: %JVMARG%";
	@F("commandHardWareCPUInfo")
	public String commandHardWareCPUInfo = "CPU信息: %CPUINFO%";
	@F("commandHardWareMemoryInfo")
	public String commandHardWareMemoryInfo = "内存信息 (剩余/总量): %MEMORYINFO%";
	@F("commandHardWareSystemInfo")
	public String commandHardWareSystemInfo = "系统信息: %SYSTEMINFO%";
	@F("commandNoFinishFetchHardWareInfo")
	public String commandNoFinishFetchHardWareInfo = "已经收到你的硬件信息查询请求，在查询完成前，请不要重复使用！";
	@F("commandNoPlayerOnline")
	public String commandNoPlayerOnline = "没有任何玩家在线";
	@F("commandClearNoTypeArg")
	public String commandClearNoTypeArg = "请输入要清理的实体类型";
	@F("commandClearMessage")
	public String commandClearMessage = "已成功清理实体类型 %TYPE% ，清理数量为 %COUNT% 个。";

	// GUI部分
	@F("guiPingTitle")
	public String guiPingTitle = "玩家网络延迟列表";
	@F("guiPingItemDisplay")
	public String guiPingItemDisplay = "%NAME%";
	@F("guiPingItemLore")
	public String guiPingItemLore = "延迟: %PING%";
	@F("guiPreItemDisplay")
	public String guiPreItemDisplay = "上一页";
	@F("guiNextItemDisplay")
	public String guiNextItemDisplay = "下一页";

	public ConfigManager() {
		super(NeverLag.getInstance().getConfig(), NeverLag.getInstance().getLogger());
	}

	public int getAnimalsSpawnLimit() {
		return animalsSpawnLimit;
	}

	public HashSet<Integer> getAntiBonemealBlackList() {
		return antiBonemealBlackList;
	}

	public String getAntiChestViewerDupeMessage() {
		return antiChestViewerDupeMessage;
	}

	public String getAntiDoubleLoginMessage() {
		return antiDoubleLoginMessage;
	}

	public String getAntiQuickShopBigShopMessage() {
		return antiQuickShopBigShopMessage;
	}

	public String getChatCooldownMessage() {
		return chatCooldownMessage;
	}

	public long getChatCooldownTime() {
		return chatCooldownTime;
	}

	public int getCheckMobFarmDelay() {
		return this.checkMobFarmDelay;
	}

	public int getCheckMobFarmLooseLimit() {
		return checkMobFarmLooseLimit;
	}

	public int getCheckMobFarmTinyLimit() {
		return this.checkMobFarmTinyLimit;
	}

	public int getChunkGenSpawnRate() {
		return chunkGenSpawnRate;
	}

	public String getClearEntityBroadcastMessage() {
		return clearEntityBroadcastMessage;
	}

	public String getClearEntityBroadcastPreMessage() {
		return clearEntityBroadcastPreMessage;
	}

	public int getClearEntityLimit() {
		return clearEntityLimit;
	}

	public int getClearEntityPlayerNearbyDistance() {
		return clearEntityPlayerNearbyDistance;
	}

	public HashSet<String> getClearEntityTypeBlackList() {
		return clearEntityTypeBlackList;
	}

	public HashSet<String> getClearEntityTypeWhiteList() {
		return clearEntityTypeWhiteList;
	}

	public String getClearItemBroadcastMessage() {
		return clearItemBroadcastMessage;
	}

	public String getClearItemBroadcastPreMessage() {
		return clearItemBroadcastPreMessage;
	}

	public int getClearItemDelay() {
		return clearItemDelay;
	}

	public int getClearItemPlayerNearbyDistance() {
		return clearItemPlayerNearbyDistance;
	}

	public String getClearItemPreHoloMessage() {
		return clearItemPreHoloMessage;
	}

	public int getClearMobDelay() {
		return clearMobDelay;
	}

	public String getCommandClearMessage() {
		return commandClearMessage;
	}

	public String getCommandClearNoTypeArg() {
		return commandClearNoTypeArg;
	}

	public String getCommandCooldownMessage() {
		return commandCooldownMessage;
	}

	public long getCommandCooldownTime() {
		return commandCooldownTime;
	}

	public HashSet<String> getCommandCooldownWhiteList() {
		return commandCooldownWhiteList;
	}

	public String getCommandFinishBenchmark() {
		return commandFinishBenchmark;
	}

	public String getCommandGCMessage() {
		return commandGCMessage;
	}

	public String getCommandGCNoEffectMessage() {
		return commandGCNoEffectMessage;
	}

	public String getCommandHardWareCPUInfo() {
		return commandHardWareCPUInfo;
	}

	public String getCommandHardWareJVMArg() {
		return commandHardWareJVMArg;
	}

	public String getCommandHardWareJVMInfo() {
		return commandHardWareJVMInfo;
	}

	public String getCommandHardWareMemoryInfo() {
		return commandHardWareMemoryInfo;
	}

	public String getCommandHardWareSystemInfo() {
		return commandHardWareSystemInfo;
	}

	public String getCommandInfoBaseMessage() {
		return commandInfoBaseMessage;
	}

	public String getCommandNoFinishFetchHardWareInfo() {
		return commandNoFinishFetchHardWareInfo;
	}

	public String getCommandNoPerm() {
		return commandNoPerm;
	}

	public String getCommandNoPlayerOnline() {
		return commandNoPlayerOnline;
	}

	public String getCommandStartBenchmark() {
		return commandStartBenchmark;
	}

	public String getCommandStartFetchHardWareInfo() {
		return commandStartFetchHardWareInfo;
	}

	public String getCommandWorldInfoMessage() {
		return commandWorldInfoMessage;
	}

	public int getDiodeLimit() {
		return diodeLimit;
	}

	public HashSet<String> getDisableVillagerTradeWorldSet() {
		return disableVillagerTradeWorldSet;
	}

	public double getEntitySpawnLimitTpsLimit() {
		return entitySpawnLimitTpsLimit;
	}

	public String getGuiNextItemDisplay() {
		return guiNextItemDisplay;
	}

	public String getGuiPingItemDisplay() {
		return guiPingItemDisplay;
	}

	public String getGuiPingItemLore() {
		return guiPingItemLore;
	}

	public String getGuiPingTitle() {
		return guiPingTitle;
	}

	public String getGuiPreItemDisplay() {
		return guiPreItemDisplay;
	}

	public long getHotChunkHolderCountLimit() {
		return hotChunkHolderCountLimit;
	}

	public int getHotChunkHolderNum() {
		return hotChunkHolderNum;
	}

	public long getHotChunkHolderTimeLimit() {
		return hotChunkHolderTimeLimit;
	}

	public double getHotChunkHolderTpsLimit() {
		return hotChunkHolderTpsLimit;
	}

	public int getHotChunkUnloadTimeLimit() {
		return hotChunkUnloadTimeLimit;
	}

	public HashSet<String> getIllegalSpawnerTypeSet() {
		return illegalSpawnerTypeSet;
	}

	public int getIllegalTypeSpawnerCleanMode() {
		return illegalTypeSpawnerCleanMode;
	}

	public int getLimitTilesDispenserDefault() {
		return limitTilesDispenserDefault;
	}

	public int getLimitTilesDropperDefault() {
		return limitTilesDropperDefault;
	}

	public int getLimitTilesHopperDefault() {
		return limitTilesHopperDefault;
	}

	public String getLimitTilesMessage() {
		return limitTilesMessage;
	}

	public int getLimitTilesPistonDefault() {
		return limitTilesPistonDefault;
	}

	public long getLowTPSActionTimeLimit() {
		return lowTPSActionTimeLimit;
	}

	public ArrayList<String> getLowTPSCommand() {
		return lowTPSCommand;
	}

	public double getLowTPSLimit() {
		return lowTPSLimit;
	}

	public int getMobSpawnLimit() {
		return mobSpawnLimit;
	}

	public ArrayList<String> getModMagicCode() {
		return modMagicCode;
	}

	public HashSet<String> getNoClearEntityWorld() {
		return noClearEntityWorld;
	}

	public HashSet<Integer> getNoClearItemId() {
		return noClearItemId;
	}

	public HashSet<String> getNoClearItemWorld() {
		return noClearItemWorld;
	}

	public int getNoHighFallWaterDistance() {
		return noHighFallWaterDistance;
	}

	public int getNormalSpawnRate() {
		return normalSpawnRate;
	}

	public int getPortalSpawnRate() {
		return portalSpawnRate;
	}

	public String getQuickshopFlag() {
		return quickshopFlag;
	}

	public int getRedstoneCheckDelay() {
		return redstoneCheckDelay;
	}

	public HashSet<Integer> getRedstoneClearType() {
		return redstoneClearType;
	}

	public int getRedstoneLimit() {
		return redstoneLimit;
	}

	public long getReduceFireSpreadTime() {
		return reduceFireSpreadTime;
	}

	public int getSpawnerEntityCountPerChunkLimit() {
		return spawnerEntityCountPerChunkLimit;
	}

	public int getSpawnerSpawnRate() {
		return spawnerSpawnRate;
	}

	public int getVillageSpawnRate() {
		return villageSpawnRate;
	}

	public boolean isAntiBonemealDupe() {
		return isAntiBonemealDupe;
	}

	public boolean isAntiChestViewerDupe() {
		return isAntiChestViewerDupe;
	}

	public boolean isAntiCrashChat() {
		return isAntiCrashChat;
	}

	public boolean isAntiCrashSign() {
		return isAntiCrashSign;
	}

	public boolean isAntiCrashSkull() {
		return isAntiCrashSkull;
	}

	public boolean isAntiDamageSkull() {
		return isAntiDamageSkull;
	}

	public boolean isAntiDoubleLogin() {
		return isAntiDoubleLogin;
	}

	public boolean isAntiDropItemDupe() {
		return isAntiDropItemDupe;
	}

	public boolean isAntiInfiniteRail() {
		return isAntiInfiniteRail;
	}

	public boolean isAntiMinecartPortal() {
		return isAntiMinecartPortal;
	}

	public boolean isAntiNegativeItemDupe() {
		return isAntiNegativeItemDupe;
	}

	public boolean isAntiNetherHopper() {
		return isAntiNetherHopper;
	}

	public boolean isAntiPlaceDoorDupe() {
		return isAntiPlaceDoorDupe;
	}

	public boolean isAntiQuickShopBigShop() {
		return isAntiQuickShopBigShop;
	}

	public boolean isAntiUseEggsChangeSpawnerType() {
		return isAntiUseEggsChangeSpawnerType;
	}

	public boolean isAntiVillagerTrade() {
		return isAntiVillagerTrade;
	}

	public boolean isAutoCleanIllegalTypeSpawner() {
		return isAutoCleanIllegalTypeSpawner;
	}

	public boolean isBroadcastClearEntity() {
		return isBroadcastClearEntity;
	}

	public boolean isBroadcastClearItem() {
		return isBroadcastClearItem;
	}

	public boolean isCheckMobFarm() {
		return isCheckMobFarm;
	}

	public boolean isCheckRedstone() {
		return isCheckRedstone;
	}

	public boolean isCheckRedstoneOnAsync() {
		return isCheckRedstoneOnAsync;
	}

	public boolean isClearAnimals() {
		return isClearAnimals;
	}

	public boolean isClearArrow() {
		return isClearArrow;
	}

	public boolean isClearBoat() {
		return isClearBoat;
	}

	public boolean isClearDropItem() {
		return isClearDropItem;
	}

	public boolean isClearEntity() {
		return isClearEntity;
	}

	public boolean isClearEntityPlayerNearby() {
		return isClearEntityPlayerNearby;
	}

	public boolean isClearExpBall() {
		return isClearExpBall;
	}

	public boolean isClearFallingBlock() {
		return isClearFallingBlock;
	}

	public boolean isClearItem() {
		return isClearItem;
	}

	public boolean isClearItemFrame() {
		return isClearItemFrame;
	}

	public boolean isClearItemPlayerNearby() {
		return isClearItemPlayerNearby;
	}

	public boolean isClearItemPreHoloMessage() {
		return isClearItemPreHoloMessage;
	}

	public boolean isClearLimit() {
		return isClearLimit;
	}

	public boolean isClearMinecart() {
		return isClearMinecart;
	}

	public boolean isClearMonster() {
		return isClearMonster;
	}

	public boolean isClearPainting() {
		return isClearPainting;
	}

	public boolean isClearSnowBall() {
		return isClearSnowBall;
	}

	public boolean isClearSquid() {
		return isClearSquid;
	}

	public boolean isClearVillager() {
		return isClearVillager;
	}

	public boolean isClientModDisabler() {
		return clientModDisabler;
	}

	public boolean isCooldownChatAndCommand() {
		return isCooldownChatAndCommand;
	}

	public boolean isDisableAnimalsPortal() {
		return isDisableAnimalsPortal;
	}

	public boolean isDisableDropItemPortal() {
		return isDisableDropItemPortal;
	}

	public boolean isDisableEntityPortal() {
		return isDisableEntityPortal;
	}

	public boolean isDisableExplode() {
		return isDisableExplode;
	}

	public boolean isDisableMonsterPortal() {
		return isDisableMonsterPortal;
	}

	public boolean isDisablePMM() {
		return isDisablePMM;
	}

	public boolean isDisableProjectilePortal() {
		return isDisableProjectilePortal;
	}

	public boolean isFarmProtect() {
		return isFarmProtect;
	}

	public boolean isHotChunkHolder() {
		return isHotChunkHolder;
	}

	public boolean isLimitEntitySpawn() {
		return isLimitEntitySpawn;
	}

	public boolean isLimitEntitySpawnByTps() {
		return isLimitEntitySpawnByTps;
	}

	public boolean isLimitTiles() {
		return isLimitTiles;
	}

	public boolean isLowTPSAction() {
		return lowTPSAction;
	}

	public boolean isLowTPSCleanEntity() {
		return lowTPSCleanEntity;
	}

	public boolean isLowTPSCleanItem() {
		return lowTPSCleanItem;
	}

	public boolean isLowTPSForceGC() {
		return lowTPSForceGC;
	}

	public boolean isNoHighFallWater() {
		return noHighFallWater;
	}

	public boolean isNoTNTChainReaction() {
		return noTNTChainReaction;
	}

	public boolean isRedstoneDrop() {
		return isRedstoneDrop;
	}

	public boolean isRedstoneMessage() {
		return isRedstoneMessage;
	}

	public boolean isReduceFireSpread() {
		return isReduceFireSpread;
	}

	public boolean isRemoveAnimalsWhenChunkUnload() {
		return removeAnimalsWhenChunkUnload;
	}

	public boolean isRemoveArrowWhenChunkUnload() {
		return removeArrowWhenChunkUnload;
	}

	public boolean isRemoveDropItemWhenChunkUnload() {
		return removeDropItemWhenChunkUnload;
	}

	public boolean isRemoveEntityWhenChunkUnload() {
		return removeEntityWhenChunkUnload;
	}

	public boolean isRemoveMonsterWhenChunkUnload() {
		return removeMonsterWhenChunkUnload;
	}

	public boolean isRemoveSquidWhenChunkUnload() {
		return removeSquidWhenChunkUnload;
	}

	public boolean isSpawnChunkKeepInMemory() {
		return isSpawnChunkKeepInMemory;
	}

	public boolean isSpawnRate() {
		return isSpawnRate;
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

	public void setAnimalsSpawnLimit(int animalsSpawnLimit) {
		this.animalsSpawnLimit = animalsSpawnLimit;
	}

	public void setAntiBonemealBlackList(HashSet<Integer> antiBonemealBlackList) {
		this.antiBonemealBlackList = antiBonemealBlackList;
	}

	public void setAntiBonemealDupe(boolean isAntiBonemealDupe) {
		this.isAntiBonemealDupe = isAntiBonemealDupe;
	}

	public void setAntiChestViewerDupe(boolean isAntiChestViewerDupe) {
		this.isAntiChestViewerDupe = isAntiChestViewerDupe;
	}

	public void setAntiChestViewerDupeMessage(String antiChestViewerDupeMessage) {
		this.antiChestViewerDupeMessage = antiChestViewerDupeMessage;
	}

	public void setAntiCrashChat(boolean isAntiCrashChat) {
		this.isAntiCrashChat = isAntiCrashChat;
	}

	public void setAntiCrashSign(boolean isAntiCrashSign) {
		this.isAntiCrashSign = isAntiCrashSign;
	}

	public void setAntiCrashSkull(boolean isAntiCrashSkull) {
		this.isAntiCrashSkull = isAntiCrashSkull;
	}

	public void setAntiDamageSkull(boolean isAntiDamageSkull) {
		this.isAntiDamageSkull = isAntiDamageSkull;
	}

	public void setAntiDoubleLogin(boolean isAntiDoubleLogin) {
		this.isAntiDoubleLogin = isAntiDoubleLogin;
	}

	public void setAntiDoubleLoginMessage(String antiDoubleLoginMessage) {
		this.antiDoubleLoginMessage = antiDoubleLoginMessage;
	}

	public void setAntiDropItemDupe(boolean isAntiDropItemDupe) {
		this.isAntiDropItemDupe = isAntiDropItemDupe;
	}

	public void setAntiInfiniteRail(boolean isAntiInfiniteRail) {
		this.isAntiInfiniteRail = isAntiInfiniteRail;
	}

	public void setAntiMinecartPortal(boolean isAntiMinecartPortal) {
		this.isAntiMinecartPortal = isAntiMinecartPortal;
	}

	public void setAntiNegativeItemDupe(boolean isAntiNegativeItemDupe) {
		this.isAntiNegativeItemDupe = isAntiNegativeItemDupe;
	}

	public void setAntiNetherHopper(boolean isAntiNetherHopper) {
		this.isAntiNetherHopper = isAntiNetherHopper;
	}

	public void setAntiPlaceDoorDupe(boolean isAntiPlaceDoorDupe) {
		this.isAntiPlaceDoorDupe = isAntiPlaceDoorDupe;
	}

	public void setAntiQuickShopBigShop(boolean isAntiQuickShopBigShop) {
		this.isAntiQuickShopBigShop = isAntiQuickShopBigShop;
	}

	public void setAntiQuickShopBigShopMessage(String antiQuickShopBigShopMessage) {
		this.antiQuickShopBigShopMessage = antiQuickShopBigShopMessage;
	}

	public void setAntiUseEggsChangeSpawnerType(boolean isAntiUseEggsChangeSpawnerType) {
		this.isAntiUseEggsChangeSpawnerType = isAntiUseEggsChangeSpawnerType;
	}

	public void setAntiVillagerTrade(boolean isAntiVillagerTrade) {
		this.isAntiVillagerTrade = isAntiVillagerTrade;
	}

	public void setAutoCleanIllegalTypeSpawner(boolean isAutoCleanIllegalTypeSpawner) {
		this.isAutoCleanIllegalTypeSpawner = isAutoCleanIllegalTypeSpawner;
	}

	public void setBroadcastClearEntity(boolean isBroadcastClearEntity) {
		this.isBroadcastClearEntity = isBroadcastClearEntity;
	}

	public void setBroadcastClearItem(boolean isBroadcastClearItem) {
		this.isBroadcastClearItem = isBroadcastClearItem;
	}

	public void setChatCooldownMessage(String chatCooldownMessage) {
		this.chatCooldownMessage = chatCooldownMessage;
	}

	public void setChatCooldownTime(long chatCooldownTime) {
		this.chatCooldownTime = chatCooldownTime;
	}

	public void setCheckMobFarm(boolean isCheckMobFarm) {
		this.isCheckMobFarm = isCheckMobFarm;
	}

	public void setCheckMobFarmDelay(int checkMobFarmDelay) {
		this.checkMobFarmDelay = checkMobFarmDelay;
	}

	public void setCheckMobFarmLooseLimit(int checkMobFarmLooseLimit) {
		this.checkMobFarmLooseLimit = checkMobFarmLooseLimit;
	}

	public void setCheckMobFarmTinyLimit(int checkMobFarmTinyLimit) {
		this.checkMobFarmTinyLimit = checkMobFarmTinyLimit;
	}

	public void setCheckRedstone(boolean isCheckRedstone) {
		this.isCheckRedstone = isCheckRedstone;
	}

	public void setCheckredStoneOnAsync(boolean isCheckredStoneOnAsync) {
		this.isCheckRedstoneOnAsync = isCheckredStoneOnAsync;
	}

	public void setChunkGenSpawnRate(int chunkGenSpawnRate) {
		this.chunkGenSpawnRate = chunkGenSpawnRate;
	}

	public void setClearAnimals(boolean isClearAnimals) {
		this.isClearAnimals = isClearAnimals;
	}

	public void setClearArrow(boolean isClearArrow) {
		this.isClearArrow = isClearArrow;
	}

	public void setClearBoat(boolean isClearBoat) {
		this.isClearBoat = isClearBoat;
	}

	public void setClearDropItem(boolean isClearDropItem) {
		this.isClearDropItem = isClearDropItem;
	}

	public void setClearEntity(boolean isClearEntity) {
		this.isClearEntity = isClearEntity;
	}

	public void setClearEntityBroadcastMessage(String clearEntityBroadcastMessage) {
		this.clearEntityBroadcastMessage = clearEntityBroadcastMessage;
	}

	public void setClearEntityBroadcastPreMessage(String clearEntityBroadcastPreMessage) {
		this.clearEntityBroadcastPreMessage = clearEntityBroadcastPreMessage;
	}

	public void setClearEntityLimit(int clearEntityLimit) {
		this.clearEntityLimit = clearEntityLimit;
	}

	public void setClearEntityPlayerNearby(boolean isClearEntityPlayerNearby) {
		this.isClearEntityPlayerNearby = isClearEntityPlayerNearby;
	}

	public void setClearEntityPlayerNearbyDistance(int clearEntityPlayerNearbyDistance) {
		this.clearEntityPlayerNearbyDistance = clearEntityPlayerNearbyDistance;
	}

	public void setClearEntityTypeBlackList(HashSet<String> clearEntityTypeBlackList) {
		this.clearEntityTypeBlackList = clearEntityTypeBlackList;
	}

	public void setClearEntityTypeWhiteList(HashSet<String> clearEntityTypeWhiteList) {
		this.clearEntityTypeWhiteList = clearEntityTypeWhiteList;
	}

	public void setClearExpBall(boolean isClearExpBall) {
		this.isClearExpBall = isClearExpBall;
	}

	public void setClearFallingBlock(boolean isClearFallingBlock) {
		this.isClearFallingBlock = isClearFallingBlock;
	}

	public void setClearItem(boolean isClearItem) {
		this.isClearItem = isClearItem;
	}

	public void setClearItemBroadcastMessage(String clearItemBroadcastMessage) {
		this.clearItemBroadcastMessage = clearItemBroadcastMessage;
	}

	public void setClearItemBroadcastPreMessage(String clearItemBroadcastPreMessage) {
		this.clearItemBroadcastPreMessage = clearItemBroadcastPreMessage;
	}

	public void setClearItemDelay(int clearItemDelay) {
		this.clearItemDelay = clearItemDelay;
	}

	public void setClearItemFrame(boolean isClearItemFrame) {
		this.isClearItemFrame = isClearItemFrame;
	}

	public void setClearItemPlayerNearby(boolean isClearItemPlayerNearby) {
		this.isClearItemPlayerNearby = isClearItemPlayerNearby;
	}

	public void setClearItemPlayerNearbyDistance(int clearItemPlayerNearbyDistance) {
		this.clearItemPlayerNearbyDistance = clearItemPlayerNearbyDistance;
	}

	public void setClearItemPreHoloMessage(boolean isClearItemPreHoloMessage) {
		this.isClearItemPreHoloMessage = isClearItemPreHoloMessage;
	}

	public void setClearItemPreHoloMessage(String clearItemPreHoloMessage) {
		this.clearItemPreHoloMessage = clearItemPreHoloMessage;
	}

	public void setClearLimit(boolean isClearLimit) {
		this.isClearLimit = isClearLimit;
	}

	public void setClearMinecart(boolean isClearMinecart) {
		this.isClearMinecart = isClearMinecart;
	}

	public void setClearMobDelay(int clearMobDelay) {
		this.clearMobDelay = clearMobDelay;
	}

	public void setClearMonster(boolean isClearMonster) {
		this.isClearMonster = isClearMonster;
	}

	public void setClearPainting(boolean isClearPainting) {
		this.isClearPainting = isClearPainting;
	}

	public void setClearSnowBall(boolean isClearSnowBall) {
		this.isClearSnowBall = isClearSnowBall;
	}

	public void setClearSquid(boolean isClearSquid) {
		this.isClearSquid = isClearSquid;
	}

	public void setClearVillager(boolean isClearVillager) {
		this.isClearVillager = isClearVillager;
	}

	public void setClientModDisabler(boolean clientModDisabler) {
		this.clientModDisabler = clientModDisabler;
	}

	public void setCommandClearMessage(String commandClearMessage) {
		this.commandClearMessage = commandClearMessage;
	}

	public void setCommandClearNoTypeArg(String commandClearNoTypeArg) {
		this.commandClearNoTypeArg = commandClearNoTypeArg;
	}

	public void setCommandCooldownMessage(String commandCooldownMessage) {
		this.commandCooldownMessage = commandCooldownMessage;
	}

	public void setCommandCooldownTime(long commandCooldownTime) {
		this.commandCooldownTime = commandCooldownTime;
	}

	public void setCommandCooldownWhiteList(HashSet<String> commandCooldownWhiteList) {
		this.commandCooldownWhiteList = commandCooldownWhiteList;
	}

	public void setCommandFinishBenchmark(String commandFinishBenchmark) {
		this.commandFinishBenchmark = commandFinishBenchmark;
	}

	public void setCommandGCMessage(String commandGCMessage) {
		this.commandGCMessage = commandGCMessage;
	}

	public void setCommandGCNoEffectMessage(String commandGCNoEffectMessage) {
		this.commandGCNoEffectMessage = commandGCNoEffectMessage;
	}

	public void setCommandHardWareCPUInfo(String commandHardWareCPUInfo) {
		this.commandHardWareCPUInfo = commandHardWareCPUInfo;
	}

	public void setCommandHardWareJVMArg(String commandHardWareJVMArg) {
		this.commandHardWareJVMArg = commandHardWareJVMArg;
	}

	public void setCommandHardWareJVMInfo(String commandHardWareJVMInfo) {
		this.commandHardWareJVMInfo = commandHardWareJVMInfo;
	}

	public void setCommandHardWareMemoryInfo(String commandHardWareMemoryInfo) {
		this.commandHardWareMemoryInfo = commandHardWareMemoryInfo;
	}

	public void setCommandHardWareSystemInfo(String commandHardWareSystemInfo) {
		this.commandHardWareSystemInfo = commandHardWareSystemInfo;
	}

	public void setCommandInfoBaseMessage(String commandInfoBaseMessage) {
		this.commandInfoBaseMessage = commandInfoBaseMessage;
	}

	public void setCommandNoFinishFetchHardWareInfo(String commandNoFinishFetchHardWareInfo) {
		this.commandNoFinishFetchHardWareInfo = commandNoFinishFetchHardWareInfo;
	}

	public void setCommandNoPerm(String commandNoPerm) {
		this.commandNoPerm = commandNoPerm;
	}

	public void setCommandNoPlayerOnline(String commandNoPlayerOnline) {
		this.commandNoPlayerOnline = commandNoPlayerOnline;
	}

	public void setCommandStartBenchmark(String commandStartBenchmark) {
		this.commandStartBenchmark = commandStartBenchmark;
	}

	public void setCommandStartFetchHardWareInfo(String commandStartFetchHardWareInfo) {
		this.commandStartFetchHardWareInfo = commandStartFetchHardWareInfo;
	}

	public void setCommandWorldInfoMessage(String commandWorldInfoMessage) {
		this.commandWorldInfoMessage = commandWorldInfoMessage;
	}

	public void setCooldownChatAndCommand(boolean isCooldownChatAndCommand) {
		this.isCooldownChatAndCommand = isCooldownChatAndCommand;
	}

	public void setDiodeLimit(int diodeLimit) {
		this.diodeLimit = diodeLimit;
	}

	public void setDisableAnimalsPortal(boolean isDisableAnimalsPortal) {
		this.isDisableAnimalsPortal = isDisableAnimalsPortal;
	}

	public void setDisableDropItemPortal(boolean isDisableDropItemPortal) {
		this.isDisableDropItemPortal = isDisableDropItemPortal;
	}

	public void setDisableEntityPortal(boolean isDisableEntityPortal) {
		this.isDisableEntityPortal = isDisableEntityPortal;
	}

	public void setDisableExplode(boolean isDisableExplode) {
		this.isDisableExplode = isDisableExplode;
	}

	public void setDisableMonsterPortal(boolean isDisableMonsterPortal) {
		this.isDisableMonsterPortal = isDisableMonsterPortal;
	}

	public void setDisablePMM(boolean isDisablePMM) {
		this.isDisablePMM = isDisablePMM;
	}

	public void setDisableProjectilePortal(boolean isDisableProjectilePortal) {
		this.isDisableProjectilePortal = isDisableProjectilePortal;
	}

	public void setDisableVillagerTradeWorldSet(HashSet<String> disableVillagerTradeWorldSet) {
		this.disableVillagerTradeWorldSet = disableVillagerTradeWorldSet;
	}

	public void setEntitySpawnLimitTpsLimit(double entitySpawnLimitTpsLimit) {
		this.entitySpawnLimitTpsLimit = entitySpawnLimitTpsLimit;
	}

	public void setFarmProtect(boolean isFarmProtect) {
		this.isFarmProtect = isFarmProtect;
	}

	public void setGuiNextItemDisplay(String guiNextItemDisplay) {
		this.guiNextItemDisplay = guiNextItemDisplay;
	}

	public void setGuiPingItemDisplay(String guiPingItemDisplay) {
		this.guiPingItemDisplay = guiPingItemDisplay;
	}

	public void setGuiPingItemLore(String guiPingItemLore) {
		this.guiPingItemLore = guiPingItemLore;
	}

	public void setGuiPingTitle(String guiPingTitle) {
		this.guiPingTitle = guiPingTitle;
	}

	public void setGuiPreItemDisplay(String guiPreItemDisplay) {
		this.guiPreItemDisplay = guiPreItemDisplay;
	}

	public void setHotChunkHolder(boolean isHotChunkHolder) {
		this.isHotChunkHolder = isHotChunkHolder;
	}

	public void setHotChunkHolderCountLimit(long hotChunkHolderCountLimit) {
		this.hotChunkHolderCountLimit = hotChunkHolderCountLimit;
	}

	public void setHotChunkHolderNum(int hotChunkHolderNum) {
		this.hotChunkHolderNum = hotChunkHolderNum;
	}

	public void setHotChunkHolderTimeLimit(long hotChunkHolderTimeLimit) {
		this.hotChunkHolderTimeLimit = hotChunkHolderTimeLimit;
	}

	public void setHotChunkHolderTpsLimit(double hotChunkHolderTpsLimit) {
		this.hotChunkHolderTpsLimit = hotChunkHolderTpsLimit;
	}

	public void setHotChunkUnloadTimeLimit(int hotChunkUnloadTimeLimit) {
		this.hotChunkUnloadTimeLimit = hotChunkUnloadTimeLimit;
	}

	public void setIllegalSpawnerTypeSet(HashSet<String> illegalSpawnerTypeSet) {
		this.illegalSpawnerTypeSet = illegalSpawnerTypeSet;
	}

	public void setIllegalTypeSpawnerCleanMode(int illegalTypeSpawnerCleanMode) {
		this.illegalTypeSpawnerCleanMode = illegalTypeSpawnerCleanMode;
	}

	public void setLimitEntitySpawn(boolean isLimitEntitySpawn) {
		this.isLimitEntitySpawn = isLimitEntitySpawn;
	}

	public void setLimitEntitySpawnByTps(boolean isLimitEntitySpawnByTps) {
		this.isLimitEntitySpawnByTps = isLimitEntitySpawnByTps;
	}

	public void setLimitTiles(boolean isLimitTiles) {
		this.isLimitTiles = isLimitTiles;
	}

	public void setLimitTilesDispenserDefault(int limitTilesDispenserDefault) {
		this.limitTilesDispenserDefault = limitTilesDispenserDefault;
	}

	public void setLimitTilesDropperDefault(int limitTilesDropperDefault) {
		this.limitTilesDropperDefault = limitTilesDropperDefault;
	}

	public void setLimitTilesHopperDefault(int limitTilesHopperDefault) {
		this.limitTilesHopperDefault = limitTilesHopperDefault;
	}

	public void setLimitTilesMessage(String limitTilesMessage) {
		this.limitTilesMessage = limitTilesMessage;
	}

	public void setLimitTilesPistonDefault(int limitTilesPistonDefault) {
		this.limitTilesPistonDefault = limitTilesPistonDefault;
	}

	public void setLowTPSAction(boolean lowTPSAction) {
		this.lowTPSAction = lowTPSAction;
	}

	public void setLowTPSActionTimeLimit(long lowTPSActionTimeLimit) {
		this.lowTPSActionTimeLimit = lowTPSActionTimeLimit;
	}

	public void setLowTPSCleanEntity(boolean lowTPSCleanEntity) {
		this.lowTPSCleanEntity = lowTPSCleanEntity;
	}

	public void setLowTPSCleanItem(boolean lowTPSCleanItem) {
		this.lowTPSCleanItem = lowTPSCleanItem;
	}

	public void setLowTPSCommand(ArrayList<String> lowTPSCommand) {
		this.lowTPSCommand = lowTPSCommand;
	}

	public void setLowTPSForceGC(boolean lowTPSForceGC) {
		this.lowTPSForceGC = lowTPSForceGC;
	}

	public void setLowTPSLimit(double lowTPSLimit) {
		this.lowTPSLimit = lowTPSLimit;
	}

	public void setMobSpawnLimit(int mobSpawnLimit) {
		this.mobSpawnLimit = mobSpawnLimit;
	}

	public void setModMagicCode(ArrayList<String> modMagicCode) {
		this.modMagicCode = modMagicCode;
	}

	public void setNoClearEntityWorld(HashSet<String> noClearEntityWorld) {
		this.noClearEntityWorld = noClearEntityWorld;
	}

	public void setNoClearItemId(HashSet<Integer> noClearItemId) {
		this.noClearItemId = noClearItemId;
	}

	public void setNoClearItemWorld(HashSet<String> noClearItemWorld) {
		this.noClearItemWorld = noClearItemWorld;
	}

	public void setNoHighFallWater(boolean noHighFallWater) {
		this.noHighFallWater = noHighFallWater;
	}

	public void setNoHighFallWaterDistance(int noHighFallWaterDistance) {
		this.noHighFallWaterDistance = noHighFallWaterDistance;
	}

	public void setNormalSpawnRate(int normalSpawnRate) {
		this.normalSpawnRate = normalSpawnRate;
	}

	public void setNoTNTChainReaction(boolean noTNTChainReaction) {
		this.noTNTChainReaction = noTNTChainReaction;
	}

	public void setPortalSpawnRate(int portalSpawnRate) {
		this.portalSpawnRate = portalSpawnRate;
	}

	public void setQuickshopFlag(String quickshopFlag) {
		this.quickshopFlag = quickshopFlag;
	}

	public void setRedstoneCheckDelay(int redstoneCheckDelay) {
		this.redstoneCheckDelay = redstoneCheckDelay;
	}

	public void setRedstoneClearType(HashSet<Integer> redstoneClearType) {
		this.redstoneClearType = redstoneClearType;
	}

	public void setRedstoneDrop(boolean isRedstoneDrop) {
		this.isRedstoneDrop = isRedstoneDrop;
	}

	public void setRedstoneLimit(int redstoneLimit) {
		this.redstoneLimit = redstoneLimit;
	}

	public void setRedstoneMessage(boolean isRedstoneMessage) {
		this.isRedstoneMessage = isRedstoneMessage;
	}

	public void setReduceFireSpread(boolean isReduceFireSpread) {
		this.isReduceFireSpread = isReduceFireSpread;
	}

	public void setReduceFireSpreadTime(long reduceFireSpreadTime) {
		this.reduceFireSpreadTime = reduceFireSpreadTime;
	}

	public void setRemoveAnimalsWhenChunkUnload(boolean removeAnimalsWhenChunkUnload) {
		this.removeAnimalsWhenChunkUnload = removeAnimalsWhenChunkUnload;
	}

	public void setRemoveArrowWhenChunkUnload(boolean removeArrowWhenChunkUnload) {
		this.removeArrowWhenChunkUnload = removeArrowWhenChunkUnload;
	}

	public void setRemoveDropItemWhenChunkUnload(boolean removeDropItemWhenChunkUnload) {
		this.removeDropItemWhenChunkUnload = removeDropItemWhenChunkUnload;
	}

	public void setRemoveEntityWhenChunkUnload(boolean removeEntityWhenChunkUnload) {
		this.removeEntityWhenChunkUnload = removeEntityWhenChunkUnload;
	}

	public void setRemoveMonsterWhenChunkUnload(boolean removeMonsterWhenChunkUnload) {
		this.removeMonsterWhenChunkUnload = removeMonsterWhenChunkUnload;
	}

	public void setRemoveSquidWhenChunkUnload(boolean removeSquidWhenChunkUnload) {
		this.removeSquidWhenChunkUnload = removeSquidWhenChunkUnload;
	}

	public void setSpawnChunkKeepInMemory(boolean isSpawnChunkKeepInMemory) {
		this.isSpawnChunkKeepInMemory = isSpawnChunkKeepInMemory;
	}

	public void setSpawnerEntityCountPerChunkLimit(int spawnerEntityCountPerChunkLimit) {
		this.spawnerEntityCountPerChunkLimit = spawnerEntityCountPerChunkLimit;
	}

	public void setSpawnerSpawnRate(int spawnerSpawnRate) {
		this.spawnerSpawnRate = spawnerSpawnRate;
	}

	public void setSpawnRate(boolean isSpawnRate) {
		this.isSpawnRate = isSpawnRate;
	}

	public void setVillageSpawnRate(int villageSpawnRate) {
		this.villageSpawnRate = villageSpawnRate;
	}

	public String translate(String input) {
		return input;
	}

}
