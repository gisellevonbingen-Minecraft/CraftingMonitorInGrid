package giselle.rs_cmig.client;

import giselle.rs_cmig.client.screen.GCMCraftingMonitorScreen;
import giselle.rs_cmig.common.RS_CMIG;
import giselle.rs_cmig.common.network.SCraftingMonitorStartMonitoringMessage;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventHandlersClient
{
	@SubscribeEvent
	public static void onGuiOpen(ScreenOpenEvent e)
	{
		if (e.getScreen() instanceof GCMCraftingMonitorScreen screen)
		{
			RS_CMIG.NETWORK_HANDLER.sendToServer(new SCraftingMonitorStartMonitoringMessage(screen.getNetworkPos()));
		}

	}

	private EventHandlersClient()
	{

	}

}
