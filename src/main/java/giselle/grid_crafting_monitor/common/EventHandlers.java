package giselle.grid_crafting_monitor.common;

import com.refinedmods.refinedstorage.api.network.INetwork;
import com.refinedmods.refinedstorage.api.network.grid.INetworkAwareGrid;
import com.refinedmods.refinedstorage.container.GridContainer;

import giselle.grid_crafting_monitor.common.network.CGridShowButtonMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandlers
{
	@SubscribeEvent
	public static void onPlayerContainerOpen(PlayerContainerEvent.Open e)
	{
		if (e.getPlayer() instanceof ServerPlayerEntity && e.getContainer() instanceof GridContainer)
		{
			ServerPlayerEntity player = (ServerPlayerEntity) e.getPlayer();
			GridContainer container = (GridContainer) e.getContainer();

			if (container.getGrid() instanceof INetworkAwareGrid)
			{
				INetworkAwareGrid grid = (INetworkAwareGrid) container.getGrid();
				INetwork network = grid.getNetwork();

				if (GCM.findCraftingMontior(network) != null)
				{
					GCM.NETWORK_HANDLER.sendTo(player, new CGridShowButtonMessage(new LevelBlockPos(network), container.containerId));
				}

			}

		}

	}

	private EventHandlers()
	{

	}

}
