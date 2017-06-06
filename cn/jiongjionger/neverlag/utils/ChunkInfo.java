package cn.jiongjionger.neverlag.utils;

import org.bukkit.Chunk;

public class ChunkInfo {
	private String world;
	private int chunkx;
	private int chunkz;

	public ChunkInfo(String world, int x, int z) {
		this.world = world;
		this.chunkx = x >> 4;
		this.chunkz = z >> 4;
	}

	public ChunkInfo(Chunk chunk) {
		this.world = chunk.getWorld().getName();
		this.chunkx = chunk.getX();
		this.chunkz = chunk.getZ();
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}

	public int getChunkx() {
		return chunkx;
	}

	public void setChunkx(int chunkx) {
		this.chunkx = chunkx;
	}

	public int getChunkz() {
		return chunkz;
	}

	public void setChunkz(int chunkz) {
		this.chunkz = chunkz;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ChunkInfo [world=").append(world).append(", chunkx=").append(chunkx).append(", chunkz=").append(chunkz).append("]");
		return sb.toString();
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

	@Override
	public int hashCode() {
		return this.world.hashCode() ^ this.chunkx ^ this.chunkz;
	}
}
