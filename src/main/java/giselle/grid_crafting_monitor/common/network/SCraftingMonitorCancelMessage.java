package giselle.grid_crafting_monitor.common.network;

import java.util.UUID;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.refinedmods.refinedstorage.api.network.INetwork;

import giselle.grid_crafting_monitor.common.GCM;
import giselle.grid_crafting_monitor.common.LevelBlockPos;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class SCraftingMonitorCancelMessage extends NetworkMessage
{
	@Nullable
	private UUID taskId;

	protected SCraftingMonitorCancelMessage()
	{

	}

	public SCraftingMonitorCancelMessage(LevelBlockPos networkPos, @Nullable UUID taskId)
	{
		super(networkPos);
		this.taskId = taskId;
	}

	public static SCraftingMonitorCancelMessage decode(PacketBuffer buf)
	{
		SCraftingMonitorCancelMessage message = new SCraftingMonitorCancelMessage();
		NetworkMessage.decode(message, buf);

		boolean hasTaskId = buf.readBoolean();

		if (hasTaskId)
		{
			message.taskId = buf.readUUID();
		}
		else
		{
			message.taskId = null;
		}

		return message;
	}

	public static void encode(SCraftingMonitorCancelMessage message, PacketBuffer buf)
	{
		NetworkMessage.encode(message, buf);

		boolean hasTaskId = message.taskId != null;
		buf.writeBoolean(hasTaskId);

		if (hasTaskId)
		{
			buf.writeUUID(message.taskId);
		}

	}

	public static void handle(SCraftingMonitorCancelMessage message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			ServerPlayerEntity player = ctx.get().getSender();
			INetwork network = GCM.getNetwork(player, message.getNetworkPos());

			if (network != null)
			{
				network.getItemGridHandler().onCraftingCancelRequested(player, message.getTaskId());
			}
		});
		ctx.get().setPacketHandled(true);
	}

	@Nullable
	public UUID getTaskId()
	{
		return taskId;
	}

}
