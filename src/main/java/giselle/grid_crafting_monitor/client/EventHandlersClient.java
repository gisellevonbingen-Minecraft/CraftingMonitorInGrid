package giselle.grid_crafting_monitor.client;

import giselle.grid_crafting_monitor.client.screen.GCMCraftingMonitorScreen;
import giselle.grid_crafting_monitor.common.GCM;
import giselle.grid_crafting_monitor.common.network.SCraftingMonitorStartMonitoringMessage;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandlersClient
{
	@SubscribeEvent
	public static void onGuiOpen(ScreenOpenEvent e)
	{
		if (e.getScreen() instanceof GCMCraftingMonitorScreen screen)
		{
			GCM.NETWORK_HANDLER.sendToServer(new SCraftingMonitorStartMonitoringMessage(screen.getNetworkPos()));
		}

	}

	private EventHandlersClient()
	{

	}

}
