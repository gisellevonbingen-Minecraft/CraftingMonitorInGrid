package giselle.rs_cmig.common.network;

import java.util.UUID;

import javax.annotation.Nullable;

import com.refinedmods.refinedstorage.api.network.INetwork;

import giselle.rs_cmig.common.LevelBlockPos;
import giselle.rs_cmig.common.RS_CMIG;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class SCraftingMonitorCancelMessage extends NetworkMessage
{
	public static final ResourceLocation ID = new ResourceLocation(RS_CMIG.MODID, "crafting_monitor_cancel");

	@Nullable
	private final UUID taskId;

	public SCraftingMonitorCancelMessage(LevelBlockPos networkPos, @Nullable UUID taskId)
	{
		super(networkPos);
		this.taskId = taskId;
	}

	public SCraftingMonitorCancelMessage(FriendlyByteBuf buf)
	{
		super(buf);

		boolean hasTaskId = buf.readBoolean();

		if (hasTaskId)
		{
			this.taskId = buf.readUUID();
		}
		else
		{
			this.taskId = null;
		}

	}

	@Override
	public void write(FriendlyByteBuf buf)
	{
		super.write(buf);

		boolean hasTaskId = this.taskId != null;
		buf.writeBoolean(hasTaskId);

		if (hasTaskId)
		{
			buf.writeUUID(this.taskId);
		}

	}

	public static void handle(SCraftingMonitorCancelMessage message, PlayPayloadContext ctx)
	{
		ctx.player().ifPresent(player -> ctx.workHandler().submitAsync(() ->
		{
			INetwork network = RS_CMIG.getNetwork((ServerPlayer) player, message.getNetworkPos());

			if (network != null)
			{
				network.getItemGridHandler().onCraftingCancelRequested((ServerPlayer) player, message.getTaskId());
			}
		}));
	}

	@Nullable
	public UUID getTaskId()
	{
		return taskId;
	}

	@Override
	public ResourceLocation id()
	{
		return ID;
	}

}
