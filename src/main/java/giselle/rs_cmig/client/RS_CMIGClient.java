package giselle.rs_cmig.client;

import com.refinedmods.refinedstorage.RSContainerMenus;
import com.refinedmods.refinedstorage.container.CraftingMonitorContainerMenu;

import giselle.rs_cmig.client.screen.CMIGCraftingMonitorScreen;
import giselle.rs_cmig.common.network.CCraftingMonitorOpenResultMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;

public class RS_CMIGClient
{
	public static void init(IEventBus fml_bus)
	{
		IEventBus forge_bus = NeoForge.EVENT_BUS;
		forge_bus.register(EventHandlersClient.class);
	}

	public static void openScreen(CCraftingMonitorOpenResultMessage message)
	{
		Minecraft minecraft = Minecraft.getInstance();
		Player player = minecraft.player;
		CraftingMonitorContainerMenu container = new CraftingMonitorContainerMenu(RSContainerMenus.CRAFTING_MONITOR.get(), new EmptyCraftingMonitor(), null, player, 0);
		CMIGCraftingMonitorScreen screen = new CMIGCraftingMonitorScreen(container, player.getInventory(), message, minecraft.screen);
		minecraft.setScreen(screen);
	}

}
