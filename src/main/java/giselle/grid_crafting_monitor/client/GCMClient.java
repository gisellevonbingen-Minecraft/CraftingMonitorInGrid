package giselle.grid_crafting_monitor.client;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

public class GCMClient
{
	public static void init()
	{
		IEventBus forge_bus = MinecraftForge.EVENT_BUS;
		forge_bus.register(EventHandlersClient.class);
	}

}
