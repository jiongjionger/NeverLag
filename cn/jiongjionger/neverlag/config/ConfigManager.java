package cn.jiongjionger.neverlag.config;

import java.util.ArrayList;
import java.util.HashSet;

import cn.jiongjionger.neverlag.NeverLag;

public class ConfigManager {

	private FileConfig config;
	// 不清除物品的世界列表
	private HashSet<String> noClearItemWorld;
	// 玩家在掉落物附近时是否还清理这个物品
	private Boolean isClearItemPlayerNearby;
	// 在玩家距离掉落物多少格距离的时候，不清理这个物品
	private int clearItemPlayerNearbyDistance;
	// 是否清除掉落物品
	private Boolean isClearItem;
	// 掉落物清理周期
	private int clearItemDelay;
	// 不清理的物品ID
	private HashSet<Integer> noClearItemId;
	// 是否清除展示框
	private Boolean isClearItemFrame;
	// 是否清除船
	private Boolean isClearBoat;
	// 是否清除经验球
	private Boolean isClearExpBall;
	// 是否清除正在掉落的物品
	private Boolean isClearFallingBlock;
	// 是否清除画
	private Boolean isClearPainting;
	// 是否清理矿车
	private Boolean isClearMinecart;
	// 是否清理地上的箭头
	private Boolean isClearArrow;
	// 是否清理地上的雪球
	private Boolean isClearSnowBall;
	// 清理物品的时候是否公告
	private Boolean isBroadcastClearItem;
	// 清理物品公告内容
	private String clearItemBroadcastMessage;
	// 清理物品公告提前通知内容
	private String clearItemBroadcastPreMessage;

	// 是否清理实体
	private Boolean isClearEntity;
	// 不清除实体的世界列表
	private HashSet<String> noClearEntityWorld;
	// 玩家在实体附近时是否还清理这个实体
	private Boolean isClearEntityPlayerNearby;
	// 在玩家距离实体多少格距离的时候，不清理这个实体
	private int clearEntityPlayerNearbyDistance;
	// 实体清理周期
	private int clearMobDelay;
	// 是否达到设置实体上限才清除
	private Boolean isClearLimit;
	// 实体清理阀值
	private int clearEntityLimit;
	// 是否清理动物
	private Boolean isClearAnimals;
	// 是否清理怪物
	private Boolean isClearMonster;
	// 是否清理乌贼
	private Boolean isClearSquid;
	// 是否清理村民
	private Boolean isClearVillager;
	// 清理实体的黑名单
	private HashSet<String> clearEntityTypeBlackList;
	// 清理实体的白名单
	private HashSet<String> clearEntityTypeWhiteList;
	// 清理实体的时候是否公告
	private Boolean isBroadcastClearEntity;
	// 清理实体公告内容
	private String clearEntityBroadcastMessage;
	// 清理实体公告提前通知内容
	private String clearEntityBroadcastPreMessage;

	// 是否检测超频红石
	private Boolean isCheckRedstone;
	// 是否异步检测
	private Boolean isCheckRedstoneOnAsync;
	// 红石频率阀值
	private int redstoneLimit;
	// 红石中继器阀值
	private int diodeLimit;
	// 红石检测周期
	private int redstoneCheckDelay;
	// 红石清理列表
	private HashSet<Integer> redstoneClearType;
	// 直接清理还是自然掉落
	private Boolean isRedstoneDrop;
	// 红石消息是否开启
	private Boolean isRedstoneMessage;

	// 是否开启实体密集检测
	private boolean isCheckMobFarm;
	// 实体密集检测大范围阀值
	private int checkMobFarmLooseLimit;
	// 实体密集检测小范围阀值
	private int checkMobFarmTinyLimit;
	// 实体密集检测周期
	private int checkMobFarmDelay;

	// 是否开启刷铁轨检测
	private boolean isAntiInfiniteRail;
	// 是否开启防御聊天内容蹦服
	private boolean isAntiCrashChat;
	// 是否开启防御骨粉刷物品（新版的forge已经修复了这个问题）
	private boolean isAntiBonemealDupe;
	// 骨粉右键方块ID的黑名单
	private HashSet<Integer> antiBonemealBlackList;
	// 是否开启防止矿车通过传送门
	private boolean isAntiMinecartPortal;
	// 是否开启防御负数物品无限使用的BUG（1.8.3以后的版本已经修复）
	private boolean isAntiNegativeItemDupe;
	// 是否防御刷丢弃物品的BUG（1.6.4~1.11.2的部分版本都存在这个问题）
	private boolean isAntiDropItemDupe;
	// 是否防御牌子蹦服（1.8.3+已修复）
	private boolean isAntiCrashSign;
	// 是否防御地狱漏斗卡服/刷物品（高版本已经不存在这个问题）
	private boolean isAntiNetherHopper;
	// 是否防御使用门来刷甘蔗、仙人掌（例如在没有权限的领地内对着有甘蔗的沙子放置门，可以无限刷甘蔗）
	private boolean isAntiPlaceDoorDupe;
	// 是否防御容器多人同时访问的时候刷物品（新版本服务端已修复，但是有部分插件依然会导致这个问题）
	private boolean isAntiChestViewerDupe;
	private String antiChestViewerDupeMessage;
	// 是否防御利用漏斗+水流+比较器无限蹦服
	private boolean isAntiCrashSkull;
	// 是否防御QS商店旁边放箱子变成大箱子
	private boolean isAntiQuickShopBigShop;
	// QS商店标识，英文一般为QuickShop，中文一般为快捷商店
	private String quickshopFlag;
	// 提示语
	private String antiQuickShopBigShopMessage;
	// 防止大小写ID同时登录
	private boolean isAntiDoubleLogin;
	private String antiDoubleLoginMessage;

	// 是否开启聊天和命令使用间隔控制
	private boolean isCooldownChatAndCommand;
	// 命令白名单
	private HashSet<String> commandCooldownWhiteList;
	// 命令间隔（毫秒）
	private long commandCooldownTime;
	// 聊天间隔（毫秒）
	private long chatCooldownTime;
	// 命令和聊天冷却时间提示语
	private String commandCooldownMessage;
	private String chatCooldownMessage;

	// 是否Spawn区块常驻内存
	private boolean isSpawnChunkKeepInMemory;

	// 是否开启实体数量限制
	private boolean isLimitEntitySpawn;
	// 限制的阀值
	private int mobSpawnLimit;
	private int animalsSpawnLimit;
	// 实体数量限制是否考虑TPS因素
	private boolean isLimitEntitySpawnByTps;
	// 实体数量限制当TPS低于多少的时候才生效
	private double entitySpawnLimitTpsLimit;
	// 区块实体数量大于多少的时候刷怪笼不再工作
	private int spawnerEntityCountPerChunkLimit;

	// 是否开启农作物保护（防止玩家/怪物跳跃破坏农田）
	private boolean isFarmProtect;
	// 是否关闭爆炸破坏方块
	private boolean isDisableExplode;
	// 是否限制粘液块+粘液活塞的永动机
	private boolean isDisablePMM;

	// 是否限制Tiles数量
	private boolean isLimitTiles;
	// 投掷器默认限制阀值
	private int limitTilesDropperDefault;
	// 漏斗默认限制阀值
	private int limitTilesHopperDefault;
	// 发射器默认限制阀值
	private int limitTilesDispenserDefault;
	// 活塞默认限制阀值
	private int limitTilesPistonDefault;
	// 提示语
	private String limitTilesMessage;

	// 是否开启实体生成概率控制
	private boolean isSpawnRate;
	// 普通自然生成概率（例如设置成20，就是有80%取消生成）
	private int normalSpawnRate;
	// 刷怪笼生成概率
	private int spawnerSpawnRate;
	// 特殊区块（沼泽等）
	private int chunkGenSpawnRate;
	// 刷铁塔（村民生成铁傀儡）
	private int villageSpawnRate;
	// 刷金塔（传送门生成猪人）
	private int portalSpawnRate;

	// 是否开启热区块常驻内存
	private boolean isHotChunkHolder;
	/*
	 * 热门区块时间阀值 当一个区块卸载以后，X毫秒以后又重新被加载 这样达到 hotChunkHolderCountLimit 次以后
	 * 就会被判断为热门区块，这个区块会被常驻内存 除非TPS低于阀值或者热门区块超出 hotChunkHolderNum 的范围
	 * 并且当前区块的判断热门阀值是最低的区块
	 */
	private long hotChunkHolderTimeLimit;
	// 热门区块次数判定阀值
	private long hotChunkHolderCountLimit;
	// 热门区块数量最大值
	private int hotChunkHolderNum;
	// 热门区块在TPS低于多少的时候不常驻
	private double hotChunkHolderTpsLimit;
	// 热门区块尝试卸载多少次不再被标记为热门
	private int hotChunkUnloadTimeLimit;

	// 是否开启火焰蔓延速度限制
	private boolean isReduceFireSpread;
	// 火焰蔓延间隔时间（毫秒）
	private long reduceFireSpreadTime;

	// 是否开启TNT链式爆炸反应限制
	private boolean noTNTChainReaction;

	// 是否开启高空流水检测（Y轴低于海平面的63会被忽略）
	private boolean noHighFallWater;
	// 流水下方超过多少格都是空气判定为高空流水
	private int noHighFallWaterDistance;

	// 是否开启区块卸载的时候清理实体
	private boolean removeEntityWhenChunkUnload;
	private boolean removeDropItemWhenChunkUnload;
	private boolean removeArrowWhenChunkUnload;
	private boolean removeMonsterWhenChunkUnload;
	private boolean removeSquidWhenChunkUnload;
	private boolean removeAnimalsWhenChunkUnload;

	// 是否限制实体穿越传送门，主要防止骷髅刷怪笼+流水+地狱门刷信标和发射器+地狱门使得区块永不被卸载都破坏平衡性的机制
	private boolean isDisableEntityPortal;
	private boolean isDisableDropItemPortal;
	private boolean isDisableMonsterPortal;
	private boolean isDisableAnimalsPortal;
	private boolean isDisableProjectilePortal;
	
	// 当TPS过低的时候是否执行某些操作/命令
	private boolean lowTPSAction;
	private boolean lowTPSCleanItem;
	private boolean lowTPSCleanEntity;
	private boolean lowTPSForceGC;
	private double lowTPSLimit;
	private long lowTPSActionTimeLimit;
	private ArrayList<String> lowTPSCommand = new ArrayList<String>();

	// 是否开启客户端MOD关闭功能
	private boolean clientModDisabler;
	private ArrayList<String> modMagicCode = new ArrayList<String>();

	// 命令提示
	private String commandNoPerm = "你没有权限执行这个指令！";
	private String commandGCMessage;
	private String commandGCNoEffectMessage;
	private String commandInfoBaseMessage;
	private String commandWorldInfoMessage;
	private String commandStartBenchmark = "开始对CPU单线程性能进行测试..请稍等";
	private String commandFinishBenchmark = "测试完毕，单线程性能分数：%SCORE%";
	private String commandStartFetchHardWareInfo = "正在开始读取服务器硬件信息，请稍等...";
	private String commandHardWareJVMInfo = "JVM信息: %JVMINFO%";
	private String commandHardWareJVMArg = "JVM参数: %JVMARG%";
	private String commandHardWareCPUInfo = "CPU信息: %CPUINFO%";
	private String commandHardWareMemoryInfo = "内存信息 (剩余/总量): %MEMORYINFO%";
	private String commandHardWareSystemInfo = "系统信息: %SYSTEMINFO%";
	private String commandNoFinishFetchHardWareInfo = "已经收到你的硬件信息查询请求，在查询完成前，请不要重复使用！";
	private String commandNoPlayerOnline = "没有任何玩家在线";
	private String commandClearNoTypeArg = "请输入要清理的实体类型";
	private String commandClearMessage = "已成功清理实体类型 %TYPE% ，清理数量为 %COUNT% 个。";
	
	// GUI部分
	private String guiPingTitle = "玩家网络延迟列表";
	private String guiPingItemDisplay = "%NAME%";
	private String guiPingItemLore = "延迟: %PING%";
	private String guiPreItemDisplay = "上一页";
	private String guiNextItemDisplay = "下一页";
	
	
	private final static class ConfigManagerHolder {
		private final static ConfigManager cm = new ConfigManager();
	}

	public final static ConfigManager getInstance() {
		return ConfigManagerHolder.cm;
	}

	// 读取配置
	public void load() {
		this.reload();
	}

	// 重载配置
	public void reload() {
		this.noClearItemWorld.clear();
		this.noClearItemWorld.addAll(config.getStringList("ClearItem.NoClearItemWorld"));
	}

	public String translate(String input) {
		return input;
	}

	public ConfigManager() {
		config = new FileConfig(NeverLag.getInstance(), "config.yml");
	}

	public HashSet<String> getNoClearItemWorld() {
		return noClearItemWorld;
	}

	public void setNoClearItemWorld(HashSet<String> noClearItemWorld) {
		this.noClearItemWorld = noClearItemWorld;
	}

	public Boolean isClearItem() {
		return isClearItem;
	}

	public void setClearItem(Boolean isClearItem) {
		this.isClearItem = isClearItem;
	}

	public int getClearItemDelay() {
		return clearItemDelay;
	}

	public void setClearItemDelay(int clearItemDelay) {
		this.clearItemDelay = clearItemDelay;
	}

	public HashSet<Integer> getNoClearItemId() {
		return noClearItemId;
	}

	public void setNoClearItemId(HashSet<Integer> noClearItemId) {
		this.noClearItemId = noClearItemId;
	}

	public Boolean isClearItemFrame() {
		return isClearItemFrame;
	}

	public void setClearItemFrame(Boolean isClearItemFrame) {
		this.isClearItemFrame = isClearItemFrame;
	}

	public Boolean isClearBoat() {
		return isClearBoat;
	}

	public void setClearBoat(Boolean isClearBoat) {
		this.isClearBoat = isClearBoat;
	}

	public Boolean isClearExpBall() {
		return isClearExpBall;
	}

	public void setClearExpBall(Boolean isClearExpBall) {
		this.isClearExpBall = isClearExpBall;
	}

	public Boolean isClearFallingBlock() {
		return isClearFallingBlock;
	}

	public void setClearFallingBlock(Boolean isClearFallingBlock) {
		this.isClearFallingBlock = isClearFallingBlock;
	}

	public Boolean isClearPainting() {
		return isClearPainting;
	}

	public void setClearPainting(Boolean isClearPainting) {
		this.isClearPainting = isClearPainting;
	}

	public Boolean isClearMinecart() {
		return isClearMinecart;
	}

	public void setClearMinecart(Boolean isClearMinecart) {
		this.isClearMinecart = isClearMinecart;
	}

	public Boolean isClearArrow() {
		return isClearArrow;
	}

	public void setClearArrow(Boolean isClearArrow) {
		this.isClearArrow = isClearArrow;
	}

	public Boolean isClearSnowBall() {
		return isClearSnowBall;
	}

	public void setClearSnowBall(Boolean isClearSnowBall) {
		this.isClearSnowBall = isClearSnowBall;
	}

	public Boolean isBroadcastClearItem() {
		return isBroadcastClearItem;
	}

	public void setBroadcastClearItem(Boolean isBroadcastClearItem) {
		this.isBroadcastClearItem = isBroadcastClearItem;
	}

	public String getClearItemBroadcastMessage() {
		return clearItemBroadcastMessage;
	}

	public void setClearItemBroadcastMessage(String clearItemBroadcastMessage) {
		this.clearItemBroadcastMessage = clearItemBroadcastMessage;
	}

	public String getClearItemBroadcastPreMessage() {
		return clearItemBroadcastPreMessage;
	}

	public void setClearItemBroadcastPreMessage(String clearItemBroadcastPreMessage) {
		this.clearItemBroadcastPreMessage = clearItemBroadcastPreMessage;
	}

	public Boolean isClearEntity() {
		return isClearEntity;
	}

	public void setClearEntity(Boolean isClearEntity) {
		this.isClearEntity = isClearEntity;
	}

	public HashSet<String> getNoClearEntityWorld() {
		return noClearEntityWorld;
	}

	public void setNoClearEntityWorld(HashSet<String> noClearEntityWorld) {
		this.noClearEntityWorld = noClearEntityWorld;
	}

	public int getClearMobDelay() {
		return clearMobDelay;
	}

	public void setClearMobDelay(int clearMobDelay) {
		this.clearMobDelay = clearMobDelay;
	}

	public Boolean isClearLimit() {
		return isClearLimit;
	}

	public void setClearLimit(Boolean isClearLimit) {
		this.isClearLimit = isClearLimit;
	}

	public int getClearEntityLimit() {
		return clearEntityLimit;
	}

	public void setClearEntityLimit(int clearEntityLimit) {
		this.clearEntityLimit = clearEntityLimit;
	}

	public Boolean isClearAnimals() {
		return isClearAnimals;
	}

	public void setClearAnimals(Boolean isClearAnimals) {
		this.isClearAnimals = isClearAnimals;
	}

	public Boolean isClearMonster() {
		return isClearMonster;
	}

	public void setClearMonster(Boolean isClearMonster) {
		this.isClearMonster = isClearMonster;
	}

	public Boolean isClearSquid() {
		return isClearSquid;
	}

	public void setClearSquid(Boolean isClearSquid) {
		this.isClearSquid = isClearSquid;
	}

	public Boolean isClearVillager() {
		return isClearVillager;
	}

	public void setClearVillager(Boolean isClearVillager) {
		this.isClearVillager = isClearVillager;
	}

	public HashSet<String> getClearEntityTypeBlackList() {
		return clearEntityTypeBlackList;
	}

	public void setClearEntityTypeBlackList(HashSet<String> clearEntityTypeBlackList) {
		this.clearEntityTypeBlackList = clearEntityTypeBlackList;
	}

	public HashSet<String> getClearEntityTypeWhiteList() {
		return clearEntityTypeWhiteList;
	}

	public void setClearEntityTypeWhiteList(HashSet<String> clearEntityTypeWhiteList) {
		this.clearEntityTypeWhiteList = clearEntityTypeWhiteList;
	}

	public Boolean isBroadcastClearEntity() {
		return isBroadcastClearEntity;
	}

	public void setBroadcastClearEntity(Boolean isBroadcastClearEntity) {
		this.isBroadcastClearEntity = isBroadcastClearEntity;
	}

	public String getClearEntityBroadcastMessage() {
		return clearEntityBroadcastMessage;
	}

	public void setClearEntityBroadcastMessage(String clearEntityBroadcastMessage) {
		this.clearEntityBroadcastMessage = clearEntityBroadcastMessage;
	}

	public String getClearEntityBroadcastPreMessage() {
		return clearEntityBroadcastPreMessage;
	}

	public void setClearEntityBroadcastPreMessage(String clearEntityBroadcastPreMessage) {
		this.clearEntityBroadcastPreMessage = clearEntityBroadcastPreMessage;
	}

	public Boolean isCheckRedstone() {
		return isCheckRedstone;
	}

	public void setCheckRedstone(Boolean isCheckRedstone) {
		this.isCheckRedstone = isCheckRedstone;
	}

	public Boolean isCheckRedstoneOnAsync() {
		return isCheckRedstoneOnAsync;
	}

	public void setCheckredStoneOnAsync(Boolean isCheckredStoneOnAsync) {
		this.isCheckRedstoneOnAsync = isCheckredStoneOnAsync;
	}

	public int getRedstoneLimit() {
		return redstoneLimit;
	}

	public void setRedstoneLimit(int redstoneLimit) {
		this.redstoneLimit = redstoneLimit;
	}

	public int getDiodeLimit() {
		return diodeLimit;
	}

	public void setDiodeLimit(int diodeLimit) {
		this.diodeLimit = diodeLimit;
	}

	public int getRedstoneCheckDelay() {
		return redstoneCheckDelay;
	}

	public void setRedstoneCheckDelay(int redstoneCheckDelay) {
		this.redstoneCheckDelay = redstoneCheckDelay;
	}

	public HashSet<Integer> getRedstoneClearType() {
		return redstoneClearType;
	}

	public void setRedstoneClearType(HashSet<Integer> redstoneClearType) {
		this.redstoneClearType = redstoneClearType;
	}

	public Boolean isRedstoneDrop() {
		return isRedstoneDrop;
	}

	public void setRedstoneDrop(Boolean isRedstoneDrop) {
		this.isRedstoneDrop = isRedstoneDrop;
	}

	public Boolean isRedstoneMessage() {
		return isRedstoneMessage;
	}

	public void setRedstoneMessage(Boolean isRedstoneMessage) {
		this.isRedstoneMessage = isRedstoneMessage;
	}

	public Boolean isClearItemPlayerNearby() {
		return isClearItemPlayerNearby;
	}

	public void setClearItemPlayerNearby(Boolean isClearItemPlayerNearby) {
		this.isClearItemPlayerNearby = isClearItemPlayerNearby;
	}

	public int getClearItemPlayerNearbyDistance() {
		return clearItemPlayerNearbyDistance;
	}

	public void setClearItemPlayerNearbyDistance(int clearItemPlayerNearbyDistance) {
		this.clearItemPlayerNearbyDistance = clearItemPlayerNearbyDistance;
	}

	public Boolean isClearEntityPlayerNearby() {
		return isClearEntityPlayerNearby;
	}

	public void setClearEntityPlayerNearby(Boolean isClearEntityPlayerNearby) {
		this.isClearEntityPlayerNearby = isClearEntityPlayerNearby;
	}

	public int getClearEntityPlayerNearbyDistance() {
		return clearEntityPlayerNearbyDistance;
	}

	public void setClearEntityPlayerNearbyDistance(int clearEntityPlayerNearbyDistance) {
		this.clearEntityPlayerNearbyDistance = clearEntityPlayerNearbyDistance;
	}

	public boolean isCheckMobFarm() {
		return isCheckMobFarm;
	}

	public void setCheckMobFarm(boolean isCheckMobFarm) {
		this.isCheckMobFarm = isCheckMobFarm;
	}

	public int getCheckMobFarmLooseLimit() {
		return checkMobFarmLooseLimit;
	}

	public void setCheckMobFarmLooseLimit(int checkMobFarmLooseLimit) {
		this.checkMobFarmLooseLimit = checkMobFarmLooseLimit;
	}

	public int getCheckMobFarmTinyLimit() {
		return this.checkMobFarmTinyLimit;
	}

	public void setCheckMobFarmTinyLimit(int checkMobFarmTinyLimit) {
		this.checkMobFarmTinyLimit = checkMobFarmTinyLimit;
	}

	public int getCheckMobFarmDelay() {
		return this.checkMobFarmDelay;
	}

	public void setCheckMobFarmDelay(int checkMobFarmDelay) {
		this.checkMobFarmDelay = checkMobFarmDelay;
	}

	public boolean isAntiInfiniteRail() {
		return isAntiInfiniteRail;
	}

	public void setAntiInfiniteRail(boolean isAntiInfiniteRail) {
		this.isAntiInfiniteRail = isAntiInfiniteRail;
	}

	public boolean isAntiCrashChat() {
		return isAntiCrashChat;
	}

	public void setAntiCrashChat(boolean isAntiCrashChat) {
		this.isAntiCrashChat = isAntiCrashChat;
	}

	public boolean isAntiBonemealDupe() {
		return isAntiBonemealDupe;
	}

	public void setAntiBonemealDupe(boolean isAntiBonemealDupe) {
		this.isAntiBonemealDupe = isAntiBonemealDupe;
	}

	public HashSet<Integer> getAntiBonemealBlackList() {
		return antiBonemealBlackList;
	}

	public void setAntiBonemealBlackList(HashSet<Integer> antiBonemealBlackList) {
		this.antiBonemealBlackList = antiBonemealBlackList;
	}

	public boolean isAntiMinecartPortal() {
		return isAntiMinecartPortal;
	}

	public void setAntiMinecartPortal(boolean isAntiMinecartPortal) {
		this.isAntiMinecartPortal = isAntiMinecartPortal;
	}

	public boolean isAntiNegativeItemDupe() {
		return isAntiNegativeItemDupe;
	}

	public void setAntiNegativeItemDupe(boolean isAntiNegativeItemDupe) {
		this.isAntiNegativeItemDupe = isAntiNegativeItemDupe;
	}

	public boolean isAntiDropItemDupe() {
		return isAntiDropItemDupe;
	}

	public void setAntiDropItemDupe(boolean isAntiDropItemDupe) {
		this.isAntiDropItemDupe = isAntiDropItemDupe;
	}

	public boolean isAntiCrashSign() {
		return isAntiCrashSign;
	}

	public void setAntiCrashSign(boolean isAntiCrashSign) {
		this.isAntiCrashSign = isAntiCrashSign;
	}

	public boolean isAntiNetherHopper() {
		return isAntiNetherHopper;
	}

	public void setAntiNetherHopper(boolean isAntiNetherHopper) {
		this.isAntiNetherHopper = isAntiNetherHopper;
	}

	public boolean isAntiPlaceDoorDupe() {
		return isAntiPlaceDoorDupe;
	}

	public void setAntiPlaceDoorDupe(boolean isAntiPlaceDoorDupe) {
		this.isAntiPlaceDoorDupe = isAntiPlaceDoorDupe;
	}

	public boolean isAntiChestViewerDupe() {
		return isAntiChestViewerDupe;
	}

	public void setAntiChestViewerDupe(boolean isAntiChestViewerDupe) {
		this.isAntiChestViewerDupe = isAntiChestViewerDupe;
	}

	public String getAntiChestViewerDupeMessage() {
		return antiChestViewerDupeMessage;
	}

	public void setAntiChestViewerDupeMessage(String antiChestViewerDupeMessage) {
		this.antiChestViewerDupeMessage = antiChestViewerDupeMessage;
	}

	public boolean isCooldownChatAndCommand() {
		return isCooldownChatAndCommand;
	}

	public void setCooldownChatAndCommand(boolean isCooldownChatAndCommand) {
		this.isCooldownChatAndCommand = isCooldownChatAndCommand;
	}

	public HashSet<String> getCommandCooldownWhiteList() {
		return commandCooldownWhiteList;
	}

	public void setCommandCooldownWhiteList(HashSet<String> commandCooldownWhiteList) {
		this.commandCooldownWhiteList = commandCooldownWhiteList;
	}

	public long getCommandCooldownTime() {
		return commandCooldownTime;
	}

	public void setCommandCooldownTime(long commandCooldownTime) {
		this.commandCooldownTime = commandCooldownTime;
	}

	public long getChatCooldownTime() {
		return chatCooldownTime;
	}

	public void setChatCooldownTime(long chatCooldownTime) {
		this.chatCooldownTime = chatCooldownTime;
	}

	public String getCommandCooldownMessage() {
		return commandCooldownMessage;
	}

	public void setCommandCooldownMessage(String commandCooldownMessage) {
		this.commandCooldownMessage = commandCooldownMessage;
	}

	public String getChatCooldownMessage() {
		return chatCooldownMessage;
	}

	public void setChatCooldownMessage(String chatCooldownMessage) {
		this.chatCooldownMessage = chatCooldownMessage;
	}

	public boolean isSpawnChunkKeepInMemory() {
		return isSpawnChunkKeepInMemory;
	}

	public void setSpawnChunkKeepInMemory(boolean isSpawnChunkKeepInMemory) {
		this.isSpawnChunkKeepInMemory = isSpawnChunkKeepInMemory;
	}

	public boolean isLimitEntitySpawn() {
		return isLimitEntitySpawn;
	}

	public void setLimitEntitySpawn(boolean isLimitEntitySpawn) {
		this.isLimitEntitySpawn = isLimitEntitySpawn;
	}

	public int getMobSpawnLimit() {
		return mobSpawnLimit;
	}

	public void setMobSpawnLimit(int mobSpawnLimit) {
		this.mobSpawnLimit = mobSpawnLimit;
	}

	public int getAnimalsSpawnLimit() {
		return animalsSpawnLimit;
	}

	public void setAnimalsSpawnLimit(int animalsSpawnLimit) {
		this.animalsSpawnLimit = animalsSpawnLimit;
	}

	public boolean isLimitEntitySpawnByTps() {
		return isLimitEntitySpawnByTps;
	}

	public void setLimitEntitySpawnByTps(boolean isLimitEntitySpawnByTps) {
		this.isLimitEntitySpawnByTps = isLimitEntitySpawnByTps;
	}

	public double getEntitySpawnLimitTpsLimit() {
		return entitySpawnLimitTpsLimit;
	}

	public void setEntitySpawnLimitTpsLimit(double entitySpawnLimitTpsLimit) {
		this.entitySpawnLimitTpsLimit = entitySpawnLimitTpsLimit;
	}

	public int getSpawnerEntityCountPerChunkLimit() {
		return spawnerEntityCountPerChunkLimit;
	}

	public void setSpawnerEntityCountPerChunkLimit(int spawnerEntityCountPerChunkLimit) {
		this.spawnerEntityCountPerChunkLimit = spawnerEntityCountPerChunkLimit;
	}

	public boolean isFarmProtect() {
		return isFarmProtect;
	}

	public void setFarmProtect(boolean isFarmProtect) {
		this.isFarmProtect = isFarmProtect;
	}

	public boolean isDisableExplode() {
		return isDisableExplode;
	}

	public void setDisableExplode(boolean isDisableExplode) {
		this.isDisableExplode = isDisableExplode;
	}

	public boolean isAntiCrashSkull() {
		return isAntiCrashSkull;
	}

	public void setAntiCrashSkull(boolean isAntiCrashSkull) {
		this.isAntiCrashSkull = isAntiCrashSkull;
	}

	public boolean isLimitTiles() {
		return isLimitTiles;
	}

	public void setLimitTiles(boolean isLimitTiles) {
		this.isLimitTiles = isLimitTiles;
	}

	public String getLimitTilesMessage() {
		return limitTilesMessage;
	}

	public void setLimitTilesMessage(String limitTilesMessage) {
		this.limitTilesMessage = limitTilesMessage;
	}

	public int getLimitTilesDropperDefault() {
		return limitTilesDropperDefault;
	}

	public void setLimitTilesDropperDefault(int limitTilesDropperDefault) {
		this.limitTilesDropperDefault = limitTilesDropperDefault;
	}

	public int getLimitTilesHopperDefault() {
		return limitTilesHopperDefault;
	}

	public void setLimitTilesHopperDefault(int limitTilesHopperDefault) {
		this.limitTilesHopperDefault = limitTilesHopperDefault;
	}

	public int getLimitTilesDispenserDefault() {
		return limitTilesDispenserDefault;
	}

	public void setLimitTilesDispenserDefault(int limitTilesDispenserDefault) {
		this.limitTilesDispenserDefault = limitTilesDispenserDefault;
	}

	public int getLimitTilesPistonDefault() {
		return limitTilesPistonDefault;
	}

	public void setLimitTilesPistonDefault(int limitTilesPistonDefault) {
		this.limitTilesPistonDefault = limitTilesPistonDefault;
	}

	public boolean isDisablePMM() {
		return isDisablePMM;
	}

	public void setDisablePMM(boolean isDisablePMM) {
		this.isDisablePMM = isDisablePMM;
	}

	public String getAntiQuickShopBigShopMessage() {
		return antiQuickShopBigShopMessage;
	}

	public void setAntiQuickShopBigShopMessage(String antiQuickShopBigShopMessage) {
		this.antiQuickShopBigShopMessage = antiQuickShopBigShopMessage;
	}

	public String getQuickshopFlag() {
		return quickshopFlag;
	}

	public void setQuickshopFlag(String quickshopFlag) {
		this.quickshopFlag = quickshopFlag;
	}

	public boolean isAntiQuickShopBigShop() {
		return isAntiQuickShopBigShop;
	}

	public void setAntiQuickShopBigShop(boolean isAntiQuickShopBigShop) {
		this.isAntiQuickShopBigShop = isAntiQuickShopBigShop;
	}

	public boolean isSpawnRate() {
		return isSpawnRate;
	}

	public void setSpawnRate(boolean isSpawnRate) {
		this.isSpawnRate = isSpawnRate;
	}

	public int getNormalSpawnRate() {
		return normalSpawnRate;
	}

	public void setNormalSpawnRate(int normalSpawnRate) {
		this.normalSpawnRate = normalSpawnRate;
	}

	public int getSpawnerSpawnRate() {
		return spawnerSpawnRate;
	}

	public void setSpawnerSpawnRate(int spawnerSpawnRate) {
		this.spawnerSpawnRate = spawnerSpawnRate;
	}

	public int getChunkGenSpawnRate() {
		return chunkGenSpawnRate;
	}

	public void setChunkGenSpawnRate(int chunkGenSpawnRate) {
		this.chunkGenSpawnRate = chunkGenSpawnRate;
	}

	public int getVillageSpawnRate() {
		return villageSpawnRate;
	}

	public void setVillageSpawnRate(int villageSpawnRate) {
		this.villageSpawnRate = villageSpawnRate;
	}

	public int getPortalSpawnRate() {
		return portalSpawnRate;
	}

	public void setPortalSpawnRate(int portalSpawnRate) {
		this.portalSpawnRate = portalSpawnRate;
	}

	public boolean isHotChunkHolder() {
		return isHotChunkHolder;
	}

	public void setHotChunkHolder(boolean isHotChunkHolder) {
		this.isHotChunkHolder = isHotChunkHolder;
	}

	public long getHotChunkHolderTimeLimit() {
		return hotChunkHolderTimeLimit;
	}

	public void setHotChunkHolderTimeLimit(long hotChunkHolderTimeLimit) {
		this.hotChunkHolderTimeLimit = hotChunkHolderTimeLimit;
	}

	public long getHotChunkHolderCountLimit() {
		return hotChunkHolderCountLimit;
	}

	public void setHotChunkHolderCountLimit(long hotChunkHolderCountLimit) {
		this.hotChunkHolderCountLimit = hotChunkHolderCountLimit;
	}

	public int getHotChunkHolderNum() {
		return hotChunkHolderNum;
	}

	public void setHotChunkHolderNum(int hotChunkHolderNum) {
		this.hotChunkHolderNum = hotChunkHolderNum;
	}

	public double getHotChunkHolderTpsLimit() {
		return hotChunkHolderTpsLimit;
	}

	public void setHotChunkHolderTpsLimit(double hotChunkHolderTpsLimit) {
		this.hotChunkHolderTpsLimit = hotChunkHolderTpsLimit;
	}

	public int getHotChunkUnloadTimeLimit() {
		return hotChunkUnloadTimeLimit;
	}

	public void setHotChunkUnloadTimeLimit(int hotChunkUnloadTimeLimit) {
		this.hotChunkUnloadTimeLimit = hotChunkUnloadTimeLimit;
	}

	public boolean isReduceFireSpread() {
		return isReduceFireSpread;
	}

	public void setReduceFireSpread(boolean isReduceFireSpread) {
		this.isReduceFireSpread = isReduceFireSpread;
	}

	public long getReduceFireSpreadTime() {
		return reduceFireSpreadTime;
	}

	public void setReduceFireSpreadTime(long reduceFireSpreadTime) {
		this.reduceFireSpreadTime = reduceFireSpreadTime;
	}

	public boolean isNoTNTChainReaction() {
		return noTNTChainReaction;
	}

	public void setNoTNTChainReaction(boolean noTNTChainReaction) {
		this.noTNTChainReaction = noTNTChainReaction;
	}

	public boolean isNoHighFallWater() {
		return noHighFallWater;
	}

	public void setNoHighFallWater(boolean noHighFallWater) {
		this.noHighFallWater = noHighFallWater;
	}

	public int getNoHighFallWaterDistance() {
		return noHighFallWaterDistance;
	}

	public void setNoHighFallWaterDistance(int noHighFallWaterDistance) {
		this.noHighFallWaterDistance = noHighFallWaterDistance;
	}

	public boolean isRemoveEntityWhenChunkUnload() {
		return removeEntityWhenChunkUnload;
	}

	public void setRemoveEntityWhenChunkUnload(boolean removeEntityWhenChunkUnload) {
		this.removeEntityWhenChunkUnload = removeEntityWhenChunkUnload;
	}

	public boolean isRemoveDropItemWhenChunkUnload() {
		return removeDropItemWhenChunkUnload;
	}

	public void setRemoveDropItemWhenChunkUnload(boolean removeDropItemWhenChunkUnload) {
		this.removeDropItemWhenChunkUnload = removeDropItemWhenChunkUnload;
	}

	public boolean isRemoveArrowWhenChunkUnload() {
		return removeArrowWhenChunkUnload;
	}

	public void setRemoveArrowWhenChunkUnload(boolean removeArrowWhenChunkUnload) {
		this.removeArrowWhenChunkUnload = removeArrowWhenChunkUnload;
	}

	public boolean isRemoveMonsterWhenChunkUnload() {
		return removeMonsterWhenChunkUnload;
	}

	public void setRemoveMonsterWhenChunkUnload(boolean removeMonsterWhenChunkUnload) {
		this.removeMonsterWhenChunkUnload = removeMonsterWhenChunkUnload;
	}

	public boolean isRemoveSquidWhenChunkUnload() {
		return removeSquidWhenChunkUnload;
	}

	public void setRemoveSquidWhenChunkUnload(boolean removeSquidWhenChunkUnload) {
		this.removeSquidWhenChunkUnload = removeSquidWhenChunkUnload;
	}

	public boolean isRemoveAnimalsWhenChunkUnload() {
		return removeAnimalsWhenChunkUnload;
	}

	public void setRemoveAnimalsWhenChunkUnload(boolean removeAnimalsWhenChunkUnload) {
		this.removeAnimalsWhenChunkUnload = removeAnimalsWhenChunkUnload;
	}

	public boolean isLowTPSAction() {
		return lowTPSAction;
	}

	public void setLowTPSAction(boolean lowTPSAction) {
		this.lowTPSAction = lowTPSAction;
	}

	public boolean isLowTPSCleanItem() {
		return lowTPSCleanItem;
	}

	public void setLowTPSCleanItem(boolean lowTPSCleanItem) {
		this.lowTPSCleanItem = lowTPSCleanItem;
	}

	public boolean isLowTPSCleanEntity() {
		return lowTPSCleanEntity;
	}

	public void setLowTPSCleanEntity(boolean lowTPSCleanEntity) {
		this.lowTPSCleanEntity = lowTPSCleanEntity;
	}

	public ArrayList<String> getLowTPSCommand() {
		return lowTPSCommand;
	}

	public void setLowTPSCommand(ArrayList<String> lowTPSCommand) {
		this.lowTPSCommand = lowTPSCommand;
	}

	public boolean isLowTPSForceGC() {
		return lowTPSForceGC;
	}

	public void setLowTPSForceGC(boolean lowTPSForceGC) {
		this.lowTPSForceGC = lowTPSForceGC;
	}

	public double getLowTPSLimit() {
		return lowTPSLimit;
	}

	public void setLowTPSLimit(double lowTPSLimit) {
		this.lowTPSLimit = lowTPSLimit;
	}

	public long getLowTPSActionTimeLimit() {
		return lowTPSActionTimeLimit;
	}

	public void setLowTPSActionTimeLimit(long lowTPSActionTimeLimit) {
		this.lowTPSActionTimeLimit = lowTPSActionTimeLimit;
	}

	public boolean isClientModDisabler() {
		return clientModDisabler;
	}

	public void setClientModDisabler(boolean clientModDisabler) {
		this.clientModDisabler = clientModDisabler;
	}

	public ArrayList<String> getModMagicCode() {
		return modMagicCode;
	}

	public void setModMagicCode(ArrayList<String> modMagicCode) {
		this.modMagicCode = modMagicCode;
	}

	public String getCommandGCMessage() {
		return commandGCMessage;
	}

	public void setCommandGCMessage(String commandGCMessage) {
		this.commandGCMessage = commandGCMessage;
	}

	public String getCommandGCNoEffectMessage() {
		return commandGCNoEffectMessage;
	}

	public void setCommandGCNoEffectMessage(String commandGCNoEffectMessage) {
		this.commandGCNoEffectMessage = commandGCNoEffectMessage;
	}

	public String getCommandInfoBaseMessage() {
		return commandInfoBaseMessage;
	}

	public void setCommandInfoBaseMessage(String commandInfoBaseMessage) {
		this.commandInfoBaseMessage = commandInfoBaseMessage;
	}

	public String getCommandWorldInfoMessage() {
		return commandWorldInfoMessage;
	}

	public void setCommandWorldInfoMessage(String commandWorldInfoMessage) {
		this.commandWorldInfoMessage = commandWorldInfoMessage;
	}

	public boolean isDisableEntityPortal() {
		return isDisableEntityPortal;
	}

	public void setDisableEntityPortal(boolean isDisableEntityPortal) {
		this.isDisableEntityPortal = isDisableEntityPortal;
	}

	public boolean isDisableDropItemPortal() {
		return isDisableDropItemPortal;
	}

	public void setDisableDropItemPortal(boolean isDisableDropItemPortal) {
		this.isDisableDropItemPortal = isDisableDropItemPortal;
	}

	public boolean isDisableMonsterPortal() {
		return isDisableMonsterPortal;
	}

	public void setDisableMonsterPortal(boolean isDisableMonsterPortal) {
		this.isDisableMonsterPortal = isDisableMonsterPortal;
	}

	public boolean isDisableAnimalsPortal() {
		return isDisableAnimalsPortal;
	}

	public void setDisableAnimalsPortal(boolean isDisableAnimalsPortal) {
		this.isDisableAnimalsPortal = isDisableAnimalsPortal;
	}

	public boolean isDisableProjectilePortal() {
		return isDisableProjectilePortal;
	}

	public void setDisableProjectilePortal(boolean isDisableProjectilePortal) {
		this.isDisableProjectilePortal = isDisableProjectilePortal;
	}

	public String getCommandStartBenchmark() {
		return commandStartBenchmark;
	}

	public void setCommandStartBenchmark(String commandStartBenchmark) {
		this.commandStartBenchmark = commandStartBenchmark;
	}

	public String getCommandFinishBenchmark() {
		return commandFinishBenchmark;
	}

	public void setCommandFinishBenchmark(String commandFinishBenchmark) {
		this.commandFinishBenchmark = commandFinishBenchmark;
	}

	public String getCommandStartFetchHardWareInfo() {
		return commandStartFetchHardWareInfo;
	}

	public void setCommandStartFetchHardWareInfo(String commandStartFetchHardWareInfo) {
		this.commandStartFetchHardWareInfo = commandStartFetchHardWareInfo;
	}

	public String getCommandHardWareJVMInfo() {
		return commandHardWareJVMInfo;
	}

	public void setCommandHardWareJVMInfo(String commandHardWareJVMInfo) {
		this.commandHardWareJVMInfo = commandHardWareJVMInfo;
	}

	public String getCommandHardWareMemoryInfo() {
		return commandHardWareMemoryInfo;
	}

	public void setCommandHardWareMemoryInfo(String commandHardWareMemoryInfo) {
		this.commandHardWareMemoryInfo = commandHardWareMemoryInfo;
	}

	public String getCommandHardWareCPUInfo() {
		return commandHardWareCPUInfo;
	}

	public void setCommandHardWareCPUInfo(String commandHardWareCPUInfo) {
		this.commandHardWareCPUInfo = commandHardWareCPUInfo;
	}

	public String getCommandHardWareSystemInfo() {
		return commandHardWareSystemInfo;
	}

	public void setCommandHardWareSystemInfo(String commandHardWareSystemInfo) {
		this.commandHardWareSystemInfo = commandHardWareSystemInfo;
	}

	public String getCommandNoFinishFetchHardWareInfo() {
		return commandNoFinishFetchHardWareInfo;
	}

	public void setCommandNoFinishFetchHardWareInfo(String commandNoFinishFetchHardWareInfo) {
		this.commandNoFinishFetchHardWareInfo = commandNoFinishFetchHardWareInfo;
	}

	public String getCommandHardWareJVMArg() {
		return commandHardWareJVMArg;
	}

	public void setCommandHardWareJVMArg(String commandHardWareJVMArg) {
		this.commandHardWareJVMArg = commandHardWareJVMArg;
	}

	public String getCommandNoPlayerOnline() {
		return commandNoPlayerOnline;
	}

	public void setCommandNoPlayerOnline(String commandNoPlayerOnline) {
		this.commandNoPlayerOnline = commandNoPlayerOnline;
	}

	public String getGuiPingTitle() {
		return guiPingTitle;
	}

	public void setGuiPingTitle(String guiPingTitle) {
		this.guiPingTitle = guiPingTitle;
	}

	public String getGuiPingItemDisplay() {
		return guiPingItemDisplay;
	}

	public void setGuiPingItemDisplay(String guiPingItemDisplay) {
		this.guiPingItemDisplay = guiPingItemDisplay;
	}

	public String getGuiPingItemLore() {
		return guiPingItemLore;
	}

	public void setGuiPingItemLore(String guiPingItemLore) {
		this.guiPingItemLore = guiPingItemLore;
	}

	public String getGuiPreItemDisplay() {
		return guiPreItemDisplay;
	}

	public void setGuiPreItemDisplay(String guiPreItemDisplay) {
		this.guiPreItemDisplay = guiPreItemDisplay;
	}

	public String getGuiNextItemDisplay() {
		return guiNextItemDisplay;
	}

	public void setGuiNextItemDisplay(String guiNextItemDisplay) {
		this.guiNextItemDisplay = guiNextItemDisplay;
	}

	public String getCommandClearNoTypeArg() {
		return commandClearNoTypeArg;
	}

	public void setCommandClearNoTypeArg(String commandClearNoTypeArg) {
		this.commandClearNoTypeArg = commandClearNoTypeArg;
	}

	public String getCommandClearMessage() {
		return commandClearMessage;
	}

	public void setCommandClearMessage(String commandClearMessage) {
		this.commandClearMessage = commandClearMessage;
	}

	public String getCommandNoPerm() {
		return commandNoPerm;
	}

	public void setCommandNoPerm(String commandNoPerm) {
		this.commandNoPerm = commandNoPerm;
	}

	public String getAntiDoubleLoginMessage() {
		return antiDoubleLoginMessage;
	}

	public void setAntiDoubleLoginMessage(String antiDoubleLoginMessage) {
		this.antiDoubleLoginMessage = antiDoubleLoginMessage;
	}

	public boolean isAntiDoubleLogin() {
		return isAntiDoubleLogin;
	}

	public void setAntiDoubleLogin(boolean isAntiDoubleLogin) {
		this.isAntiDoubleLogin = isAntiDoubleLogin;
	}

}
