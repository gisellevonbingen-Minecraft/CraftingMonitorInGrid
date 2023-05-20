package giselle.grid_crafting_monitor.common;

import com.refinedmods.refinedstorage.api.network.INetwork;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LevelBlockPos
{
	private ResourceLocation levelName;
	private BlockPos pos;

	public LevelBlockPos(ResourceLocation levelName, BlockPos pos)
	{
		this.levelName = levelName;
		this.pos = pos;
	}

	public LevelBlockPos(World level, BlockPos pos)
	{
		this(level.dimension().location(), pos);
	}

	public LevelBlockPos(TileEntity blockEntity)
	{
		this(blockEntity.getLevel(), blockEntity.getBlockPos());
	}

	public LevelBlockPos(INetwork network)
	{
		this(network.getWorld(), network.getPosition());
	}

	public LevelBlockPos(PacketBuffer buf)
	{
		this.levelName = buf.readResourceLocation();
		this.pos = buf.readBlockPos();
	}

	public void encode(PacketBuffer buf)
	{
		buf.writeResourceLocation(this.levelName);
		buf.writeBlockPos(this.pos);
	}

	public ResourceLocation getLevelName()
	{
		return this.levelName;
	}

	public BlockPos getPos()
	{
		return this.pos;
	}

}
