package giselle.grid_crafting_monitor.client;

import com.refinedmods.refinedstorage.RSContainerMenus;
import com.refinedmods.refinedstorage.container.CraftingMonitorContainerMenu;

import giselle.grid_crafting_monitor.client.screen.GCMCraftingMonitorScreen;
import giselle.grid_crafting_monitor.common.network.CCraftingMonitorOpenResultMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
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
		Player player = minecraft.player;
		CraftingMonitorContainerMenu container = new CraftingMonitorContainerMenu(RSContainerMenus.CRAFTING_MONITOR.get(), new EmptyCraftingMonitor(), null, player, 0);
		GCMCraftingMonitorScreen screen = new GCMCraftingMonitorScreen(container, player.getInventory(), message, minecraft.screen);
		minecraft.setScreen(screen);
	}

}
