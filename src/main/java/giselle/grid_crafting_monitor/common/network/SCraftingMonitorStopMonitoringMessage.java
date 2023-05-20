package giselle.grid_crafting_monitor.common.network;

import java.util.function.Supplier;

import com.refinedmods.refinedstorage.api.network.INetwork;

import giselle.grid_crafting_monitor.common.GCM;
import giselle.grid_crafting_monitor.common.LevelBlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class SCraftingMonitorStopMonitoringMessage extends NetworkMessage
{
	protected SCraftingMonitorStopMonitoringMessage()
	{

	}

	public SCraftingMonitorStopMonitoringMessage(LevelBlockPos networkPos)
	{
		super(networkPos);
	}

	public static SCraftingMonitorStopMonitoringMessage decode(FriendlyByteBuf buf)
	{
		SCraftingMonitorStopMonitoringMessage message = new SCraftingMonitorStopMonitoringMessage();
		NetworkMessage.decode(message, buf);
		return message;
	}

	public static void encode(SCraftingMonitorStopMonitoringMessage message, FriendlyByteBuf buf)
	{
		NetworkMessage.encode(message, buf);
	}

	public static void handle(SCraftingMonitorStopMonitoringMessage message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			ServerPlayer player = ctx.get().getSender();
			INetwork network = GCM.getNetwork(player, message.getNetworkPos());

			if (network != null)
			{
				GCM.stopMonitoring(player, network);
			}
		});
		ctx.get().setPacketHandled(true);
	}

}
