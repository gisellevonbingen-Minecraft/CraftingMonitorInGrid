package giselle.rs_cmig.common;

import com.refinedmods.refinedstorage.api.network.INetwork;
import com.refinedmods.refinedstorage.api.network.grid.INetworkAwareGrid;
import com.refinedmods.refinedstorage.container.GridContainerMenu;

import giselle.rs_cmig.common.network.CGridShowButtonMessage;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;

public class EventHandlers
{
	@SubscribeEvent
	public static void onPlayerContainerOpen(PlayerContainerEvent.Open e)
	{
		if (e.getEntity() instanceof ServerPlayer player && e.getContainer() instanceof GridContainerMenu container)
		{
			if (container.getGrid() instanceof INetworkAwareGrid grid)
			{
				INetwork network = grid.getNetwork();

				if (RS_CMIG.findCraftingMontior(network) != null)
				{
					RS_CMIG.NETWORK_HANDLER.sendTo(player, new CGridShowButtonMessage(new LevelBlockPos(network), container.containerId));
				}

			}

		}

	}

	private EventHandlers()
	{

	}

}
