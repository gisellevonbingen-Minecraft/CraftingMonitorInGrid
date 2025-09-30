package giselle.rs_cmig.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public record LevelBlockPos(ResourceKey<Level> dimension, BlockPos pos)
{
	public LevelBlockPos(Level level, BlockPos pos)
	{
		this(level.dimension(), pos);
	}

	public LevelBlockPos(BlockEntity blockEntity)
	{
		this(blockEntity.getLevel(), blockEntity.getBlockPos());
	}

	public LevelBlockPos(GlobalPos globalPos)
	{
		this(globalPos.dimension(), globalPos.pos());
	}

	public BlockEntity blockEntity(MinecraftServer server)
	{
		ServerLevel level = server.getLevel(this.dimension);
		return level == null ? null : level.getBlockEntity(this.pos);
	}

	public static LevelBlockPos decode(FriendlyByteBuf buf)
	{
		ResourceKey<Level> dimension = buf.readResourceKey(Registries.DIMENSION);
		BlockPos pos = buf.readBlockPos();
		return new LevelBlockPos(dimension, pos);
	}

	public static void encode(FriendlyByteBuf buf, LevelBlockPos value)
	{
		buf.writeResourceKey(value.dimension);
		buf.writeBlockPos(value.pos);
	}

}
