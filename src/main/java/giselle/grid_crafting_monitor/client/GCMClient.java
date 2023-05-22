package giselle.grid_crafting_monitor.client;

import com.refinedmods.refinedstorage.RSContainers;
import com.refinedmods.refinedstorage.container.CraftingMonitorContainer;

import giselle.grid_crafting_monitor.client.screen.GCMCraftingMonitorScreen;
import giselle.grid_crafting_monitor.common.network.CCraftingMonitorOpenResultMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

public class GCMClient
{
	public static void init()
	{
		IEventBus forge_bus = MinecraftForge.EVENT_BUS;
		forge_bus.register(EventHandlersClient.class);
	}

	public static void openScreen(CCraftingMonitorOpenResultMessage message)
	{
		Minecraft minecraft = Minecraft.getInstance();
		PlayerEntity player = minecraft.player;
		CraftingMonitorContainer container = new CraftingMonitorContainer(RSContainers.CRAFTING_MONITOR, new EmptyCraftingMonitor(), null, player, 0);
		GCMCraftingMonitorScreen screen = new GCMCraftingMonitorScreen(container, player.inventory, message, minecraft.screen);
		minecraft.setScreen(screen);
	}

}
