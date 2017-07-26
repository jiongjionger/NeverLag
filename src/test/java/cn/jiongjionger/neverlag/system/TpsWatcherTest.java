package cn.jiongjionger.neverlag.system;

import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

public class TpsWatcherTest extends TpsWatcher {
	final Random random = new Random();
	boolean enableRandomOffset = true;
	long lag;
	
	@Test
	public void test() {
		assertTps(0,  20d);
		assertTps(5,  18.1d);
		assertTps(10, 16.6d);
		assertTps(50, 10d);
		assertEquals(16.9d, getAverageTPS(), 1d);  // 正常情况下平均TPS应为 16.9
	}
	
	public void assertTps(long lag, double expected) {
		this.lag = lag;
		run();
		assertEquals(expected, getTps(), 1d);  // 计算出来的TPS允许有±1的误差
	}

	@Override   // 模拟时间
	protected long currentTime() {
		return lastPoll + 50 + lag + 
			(enableRandomOffset ? (random.nextInt(3) - 1) : 0); // 随机偏移±1ms
	}
}
