package giselle.rs_cmig.common.network;

import com.refinedmods.refinedstorage.api.network.INetwork;

import giselle.rs_cmig.common.LevelBlockPos;
import giselle.rs_cmig.common.RS_CMIG;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class SCraftingMonitorStopMonitoringMessage extends NetworkMessage
{
	public static final ResourceLocation ID = new ResourceLocation(RS_CMIG.MODID, "crafting_monitor_stop_monitoring");

	public SCraftingMonitorStopMonitoringMessage(LevelBlockPos networkPos)
	{
		super(networkPos);
	}

	public SCraftingMonitorStopMonitoringMessage(FriendlyByteBuf buf)
	{
		super(buf);
	}

	@Override
	public void write(FriendlyByteBuf buf)
	{
		super.write(buf);
	}

	public static void handle(SCraftingMonitorStopMonitoringMessage message, PlayPayloadContext ctx)
	{
		ctx.player().ifPresent(player -> ctx.workHandler().submitAsync(() ->
		{
			INetwork network = RS_CMIG.getNetwork((ServerPlayer) player, message.getNetworkPos());

			if (network != null)
			{
				RS_CMIG.stopMonitoring((ServerPlayer) player, network);
			}
		}));
	}

	@Override
	public ResourceLocation id()
	{
		return ID;
	}

}
