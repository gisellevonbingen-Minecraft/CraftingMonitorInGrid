package giselle.grid_crafting_monitor.client;

import giselle.grid_crafting_monitor.client.screen.GCMCraftingMonitorScreen;
import giselle.grid_crafting_monitor.common.GCM;
import giselle.grid_crafting_monitor.common.network.SCraftingMonitorStartMonitoringMessage;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandlersClient
{
	@SubscribeEvent
	public static void onGuiOpen(GuiOpenEvent e)
	{
		if (e.getGui() instanceof GCMCraftingMonitorScreen)
		{
			GCMCraftingMonitorScreen screen = (GCMCraftingMonitorScreen) e.getGui();
			GCM.NETWORK_HANDLER.sendToServer(new SCraftingMonitorStartMonitoringMessage(screen.getNetworkPos()));
		}

	}

	private EventHandlersClient()
	{

	}

}
