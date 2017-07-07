package cn.jiongjionger.neverlag.utils;

import org.junit.*;
import static org.junit.Assert.*;

import cn.jiongjionger.neverlag.utils.VersionUtils.Version;

public class VersionUtilsTest {

	@Test
	public void testParsing() {
		assertEquals(new Version("1.6").toString(), "1.6");
		assertEquals(new Version("1.7.0").toString(), "1.7");
		assertEquals(new Version("1.7.10").toString(), "1.7.10");
		assertEquals(new Version("1.11.2").toString(), "1.11.2");
		assertEquals(VersionUtils.V1_10.toString(), "1.10");
		
		assertEquals(new Version("1.2"), new Version("1.2"));
		assertEquals(new Version("1.3.0"), new Version("1.3"));
		assertNotEquals(new Version("1.4.1"), new Version("1.4"));
		
		assertEquals(VersionUtils.extractVersion("git-PaperSpigot-5063a06-18fbb24 (MC: 1.10.11)"), "1.10.11");
		assertEquals(VersionUtils.extractVersion("Hello (MC: 6.66.6 )"), "6.66.6");
		assertEquals(VersionUtils.extractVersion("Bukkit 1.7.2 (MC:  1.8)"), "1.8");
	}
	
	@Test(expected = IllegalStateException.class)
	public void testParsingIllegalVersion(){
		new Version("1.8.a").toString();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testParsingSnapshotVersion1(){
		new Version("14w32c").toString();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testParsingSnapshotVersion2(){
		new Version("1.8.8-SNAPSHOT").toString();
	}

	@Test
	public void testComparing() {
		assertTrue(VersionUtils.V1_11.compareTo(VersionUtils.V1_10) > 0);
		assertTrue(VersionUtils.V1_8.compareTo(new Version(1, 8, 0)) == 0);
		assertFalse(VersionUtils.V1_7.compareTo(VersionUtils.V1_9) > 0);
	}
}
