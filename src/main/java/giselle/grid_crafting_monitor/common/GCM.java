package giselle.grid_crafting_monitor.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.refinedmods.refinedstorage.RSContainerMenus;
import com.refinedmods.refinedstorage.api.network.INetwork;
import com.refinedmods.refinedstorage.api.network.INetworkNodeGraphEntry;
import com.refinedmods.refinedstorage.apiimpl.API;
import com.refinedmods.refinedstorage.apiimpl.network.node.CraftingMonitorNetworkNode;
import com.refinedmods.refinedstorage.blockentity.craftingmonitor.CraftingMonitorBlockEntity;
import com.refinedmods.refinedstorage.container.factory.CraftingMonitorMenuProvider;

import giselle.grid_crafting_monitor.client.GCMClient;
import giselle.grid_crafting_monitor.common.network.CCraftingMonitorOpenResultMessage;
import giselle.grid_crafting_monitor.common.network.NetworkHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GCM.MODID)
public class GCM
{
	public static final String MODID = "grid_crafting_monitor";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final NetworkHandler NETWORK_HANDLER = new NetworkHandler();

	private static final Map<INetwork, CraftingMonitorNetworkNode> NOCDE_CACHE = new HashMap<>();
	private static final Map<UUID, List<CraftingManagerListener>> LISTENERS = new HashMap<>();

	public GCM()
	{
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> GCMClient::init);

		IEventBus fml_bus = FMLJavaModLoadingContext.get().getModEventBus();
		fml_bus.addListener(GCM::onCommonSetup);

		IEventBus forge_bus = MinecraftForge.EVENT_BUS;
		forge_bus.register(EventHandlers.class);
	}

	private static void onCommonSetup(FMLCommonSetupEvent e)
	{
		NETWORK_HANDLER.register();
	}

	public static void startMonitoring(ServerPlayer player, INetwork network)
	{
		CraftingMonitorBlockEntity craftingMontior = GCM.findCraftingMontior(network);

		if (craftingMontior != null)
		{
			CraftingManagerListener listener = new CraftingManagerListener(player, network, craftingMontior.getNode());
			getMonitorings(player).add(listener);
			network.getCraftingManager().addListener(listener);
		}

	}

	public static void stopMonitoring(ServerPlayer player, INetwork network)
	{
		List<CraftingManagerListener> list = getMonitorings(player);
		List<CraftingManagerListener> filetered = list.stream().filter(l -> l.getNetwork() == network).collect(Collectors.toList());

		for (CraftingManagerListener listener : filetered)
		{
			network.getCraftingManager().removeListener(listener);
			list.remove(listener);
		}

	}

	private static List<CraftingManagerListener> getMonitorings(ServerPlayer player)
	{
		return LISTENERS.computeIfAbsent(player.getUUID(), k -> new ArrayList<>());
	}

	public static INetwork getNetwork(ServerPlayer player, LevelBlockPos networkPos)
	{
		ResourceKey<Level> networkLevelKey = ResourceKey.create(Registry.DIMENSION_REGISTRY, networkPos.getLevelName());
		ServerLevel networkLevel = player.getServer().getLevel(networkLevelKey);
		return API.instance().getNetworkManager(networkLevel).getNetwork(networkPos.getPos());
	}

	public static void openGui(ServerPlayer player, CraftingMonitorBlockEntity craftingMonitorBE)
	{
		CraftingMonitorNetworkNode node = craftingMonitorBE.getNode();
		CraftingMonitorMenuProvider provider = new CraftingMonitorMenuProvider(RSContainerMenus.CRAFTING_MONITOR, node, craftingMonitorBE);
		Component displayName = provider.getDisplayName();
		GCM.NETWORK_HANDLER.sendTo(player, new CCraftingMonitorOpenResultMessage(new LevelBlockPos(node.getNetwork()), displayName));
	}

	public static CraftingMonitorBlockEntity findCraftingMontior(INetwork network)
	{
		CraftingMonitorNetworkNode node = NOCDE_CACHE.get(network);

		if (node != null)
		{
			if (node.getLevel().getBlockEntity(node.getPos()) instanceof CraftingMonitorBlockEntity blockEntity)
			{
				if (blockEntity.getNode().getNetwork() == network)
				{
					return blockEntity;
				}

			}

		}

		CraftingMonitorBlockEntity craftingMonitorBlockEntity = findCraftingMontior0(network);

		if (craftingMonitorBlockEntity != null)
		{
			NOCDE_CACHE.put(network, craftingMonitorBlockEntity.getNode());
		}
		else
		{
			NOCDE_CACHE.remove(network);
		}

		return craftingMonitorBlockEntity;
	}

	private static CraftingMonitorBlockEntity findCraftingMontior0(INetwork network)
	{
		if (network == null)
		{
			return null;
		}

		for (INetworkNodeGraphEntry entry : network.getNodeGraph().all())
		{
			if (entry.getNode() instanceof CraftingMonitorNetworkNode node)
			{
				BlockPos pos = node.getPos();
				Level level = node.getLevel();

				if (level.getBlockEntity(pos) instanceof CraftingMonitorBlockEntity blockEntity)
				{
					return blockEntity;
				}

			}

		}

		return null;
	}

	public static ResourceLocation rl(String path)
	{
		return new ResourceLocation(MODID, path);
	}

}
