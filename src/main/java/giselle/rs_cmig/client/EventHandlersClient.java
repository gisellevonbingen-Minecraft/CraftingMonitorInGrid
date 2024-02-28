package giselle.rs_cmig.client;

import giselle.rs_cmig.client.screen.CMIGCraftingMonitorScreen;
import giselle.rs_cmig.common.RS_CMIG;
import giselle.rs_cmig.common.network.SCraftingMonitorStartMonitoringMessage;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;

public class EventHandlersClient
{
	@SubscribeEvent
	public static void onGuiOpen(ScreenEvent.Opening e)
	{
		if (e.getScreen() instanceof CMIGCraftingMonitorScreen screen)
		{
			RS_CMIG.NETWORK_HANDLER.sendToServer(new SCraftingMonitorStartMonitoringMessage(screen.getNetworkPos()));
		}

	}

	private EventHandlersClient()
	{

	}

}
