package giselle.rs_cmig.common.network;

import java.util.function.Supplier;

import com.refinedmods.refinedstorage.api.network.INetwork;

import giselle.rs_cmig.common.RS_CMIG;
import giselle.rs_cmig.common.LevelBlockPos;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class SCraftingMonitorStopMonitoringMessage extends NetworkMessage
{
	protected SCraftingMonitorStopMonitoringMessage()
	{

	}

	public SCraftingMonitorStopMonitoringMessage(LevelBlockPos networkPos)
	{
		super(networkPos);
	}

	public static SCraftingMonitorStopMonitoringMessage decode(PacketBuffer buf)
	{
		SCraftingMonitorStopMonitoringMessage message = new SCraftingMonitorStopMonitoringMessage();
		NetworkMessage.decode(message, buf);
		return message;
	}

	public static void encode(SCraftingMonitorStopMonitoringMessage message, PacketBuffer buf)
	{
		NetworkMessage.encode(message, buf);
	}

	public static void handle(SCraftingMonitorStopMonitoringMessage message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			ServerPlayerEntity player = ctx.get().getSender();
			INetwork network = RS_CMIG.getNetwork(player, message.getNetworkPos());

			if (network != null)
			{
				RS_CMIG.stopMonitoring(player, network);
			}
		});
		ctx.get().setPacketHandled(true);
	}

}
