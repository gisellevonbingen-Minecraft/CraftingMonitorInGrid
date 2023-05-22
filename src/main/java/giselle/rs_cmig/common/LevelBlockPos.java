package giselle.rs_cmig.common;

import com.refinedmods.refinedstorage.api.network.INetwork;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class LevelBlockPos
{
	private ResourceLocation levelName;
	private BlockPos pos;

	public LevelBlockPos(ResourceLocation levelName, BlockPos pos)
	{
		this.levelName = levelName;
		this.pos = pos;
	}

	public LevelBlockPos(Level level, BlockPos pos)
	{
		this(level.dimension().location(), pos);
	}

	public LevelBlockPos(BlockEntity blockEntity)
	{
		this(blockEntity.getLevel(), blockEntity.getBlockPos());
	}

	public LevelBlockPos(INetwork network)
	{
		this(network.getLevel(), network.getPosition());
	}

	public LevelBlockPos(FriendlyByteBuf buf)
	{
		this.levelName = buf.readResourceLocation();
		this.pos = buf.readBlockPos();
	}

	public void encode(FriendlyByteBuf buf)
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
