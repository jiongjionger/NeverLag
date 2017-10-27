package cn.jiongjionger.neverlag.utils;

import org.bukkit.Chunk;

public class ChunkInfo {
	private String world;
	private int chunkx;
	private int chunkz;

	public ChunkInfo(Chunk chunk) {
		this.world = chunk.getWorld().getName();
		this.chunkx = chunk.getX();
		this.chunkz = chunk.getZ();
	}

	/**
	 * 警告: 此构造器中的 x, z 参数代表着方块坐标, 而并非区块坐标.
	 *
	 * @param x 方块x坐标
	 * @param z 方块z坐标
	 */
	public ChunkInfo(String world, int x, int z) {
		this.world = world;
		this.chunkx = x >> 4;
		this.chunkz = z >> 4;
	}

	@Override
	public boolean equals(Object orc) {
		if (this == orc) {
			return true;
		}
		if (orc == null) {
			return false;
		}
		if (getClass() != orc.getClass()) {
			return false;
		}
		ChunkInfo rc = (ChunkInfo) orc;
		return (this.world.equals(rc.getWorld())) && (this.chunkx == rc.chunkx) && (this.chunkz == rc.chunkz);
	}

	public int getChunkx() {
		return chunkx;
	}

	public int getChunkz() {
		return chunkz;
	}

	public String getWorld() {
		return world;
	}

	@Override
	public int hashCode() {
		return this.world.hashCode() ^ this.chunkx ^ this.chunkz;
	}

	public void setChunkx(int chunkx) {
		this.chunkx = chunkx;
	}

	public void setChunkz(int chunkz) {
		this.chunkz = chunkz;
	}

	public void setWorld(String world) {
		this.world = world;
	}

	@Override
	public String toString() {
		return String.format("ChunkInfo [world=%s, chunkx=%d, chunkz=%d]", world, chunkx, chunkz);
	}
}
