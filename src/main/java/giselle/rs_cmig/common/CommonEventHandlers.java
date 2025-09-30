package giselle.rs_cmig.common;

import com.refinedmods.refinedstorage.api.network.Network;
import com.refinedmods.refinedstorage.common.grid.AbstractGridBlockEntity;
import com.refinedmods.refinedstorage.common.grid.AbstractGridContainerMenu;

import giselle.rs_cmig.common.mixin.AbstractGridContainerMenuAccessor;
import giselle.rs_cmig.common.mixin.WirelessGridAccessor;
import giselle.rs_cmig.common.network.CGridShowButtonMessage;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class CommonEventHandlers
{
	@SubscribeEvent
	public static void onPlayerContainerOpen(PlayerContainerEvent.Open e)
	{
		if (e.getEntity() instanceof ServerPlayer player && e.getContainer() instanceof AbstractGridContainerMenu container)
		{
			var grid = ((AbstractGridContainerMenuAccessor) container).getGrid();
			Network network = null;

			if (grid instanceof AbstractGridBlockEntity blockEntity)
			{
				network = blockEntity.getNetworkForItem();
			}
			else if (grid instanceof WirelessGridAccessor wireless)
			{
				network = wireless.getContext().resolveNetwork().orElse(null);
			}

			if (network != null)
			{
				var autocraftingMontior = RS_CMIG.findAutocraftingMontior(player.server, network);

				if (autocraftingMontior != null)
				{
					PacketDistributor.sendToPlayer(player, new CGridShowButtonMessage(new LevelBlockPos(autocraftingMontior), container.containerId));
				}

			}

		}

	}

	private CommonEventHandlers()
	{

	}

}
