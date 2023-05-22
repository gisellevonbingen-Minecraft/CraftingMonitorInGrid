package giselle.rs_cmig.client;

import com.refinedmods.refinedstorage.RSContainerMenus;
import com.refinedmods.refinedstorage.container.CraftingMonitorContainerMenu;

import giselle.rs_cmig.client.screen.GCMCraftingMonitorScreen;
import giselle.rs_cmig.common.network.CCraftingMonitorOpenResultMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;

public class RS_CMIGClient
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
		CraftingMonitorContainerMenu container = new CraftingMonitorContainerMenu(RSContainerMenus.CRAFTING_MONITOR, new EmptyCraftingMonitor(), null, player, 0);
		GCMCraftingMonitorScreen screen = new GCMCraftingMonitorScreen(container, player.getInventory(), message, minecraft.screen);
		minecraft.setScreen(screen);
	}

}
