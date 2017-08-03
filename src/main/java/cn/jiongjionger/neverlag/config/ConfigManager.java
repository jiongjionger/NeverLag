package cn.jiongjionger.neverlag.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import cn.jiongjionger.neverlag.NeverLag;
import java.util.Locale;

public class ConfigManager extends AbstractConfig {

	private final static class ConfigManagerHolder {
		private final static ConfigManager INSTANCE = new ConfigManager();
	}

	public final static ConfigManager getInstance() {
		return ConfigManagerHolder.INSTANCE;
	}

	/*
	 * TO DO : 修改配置文件获取的node
	 */
	
	@F("lang")
	public String lang = Locale.getDefault().toString();

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
	// 显性调用GC是非常没必要的，只适合一些非常特殊的应用场景，请测试你的服务端是否需要这个功能
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

	public ConfigManager() {
		super(NeverLag.getInstance().getConfig(), NeverLag.getInstance().getLogger());
	}

	protected boolean checkValue(String key, Object value) {
		switch(key) {
		case "lang": 
			if(!(value instanceof String)) {
				return false;
			}
			return ((String) value).matches("^[a-zA-Z]{2}(-|_)[a-zA-Z]{2}$");
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