package giselle.rs_cmig.common.network;

import com.refinedmods.refinedstorage.api.network.INetwork;

import giselle.rs_cmig.common.LevelBlockPos;
import giselle.rs_cmig.common.RS_CMIG;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class SCraftingMonitorStartMonitoringMessage extends NetworkMessage
{
	public static final ResourceLocation ID = new ResourceLocation(RS_CMIG.MODID, "crafting_monitor_start_monitoring");

	public SCraftingMonitorStartMonitoringMessage(LevelBlockPos networkPos)
	{
		super(networkPos);
	}

	public SCraftingMonitorStartMonitoringMessage(FriendlyByteBuf buf)
	{
		super(buf);
	}

	@Override
	public void write(FriendlyByteBuf buf)
	{
		super.write(buf);
	}

	public static void handle(SCraftingMonitorStartMonitoringMessage message, PlayPayloadContext ctx)
	{
		ctx.player().ifPresent(player -> ctx.workHandler().submitAsync(() ->
		{
			INetwork network = RS_CMIG.getNetwork((ServerPlayer) player, message.getNetworkPos());

			if (network != null)
			{
				RS_CMIG.startMonitoring((ServerPlayer) player, network);
			}
		}));
	}

	@Override
	public ResourceLocation id()
	{
		return ID;
	}

}
