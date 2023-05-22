package giselle.grid_crafting_monitor.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.refinedmods.refinedstorage.api.network.INetwork;
import com.refinedmods.refinedstorage.api.network.INetworkNodeGraphEntry;
import com.refinedmods.refinedstorage.api.network.node.INetworkNode;
import com.refinedmods.refinedstorage.apiimpl.API;
import com.refinedmods.refinedstorage.apiimpl.network.node.CraftingMonitorNetworkNode;
import com.refinedmods.refinedstorage.tile.craftingmonitor.CraftingMonitorTile;

import giselle.grid_crafting_monitor.client.GCMClient;
import giselle.grid_crafting_monitor.common.network.CCraftingMonitorOpenResultMessage;
import giselle.grid_crafting_monitor.common.network.NetworkHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
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

	public static void startMonitoring(ServerPlayerEntity player, INetwork network)
	{
		CraftingMonitorTile craftingMontior = GCM.findCraftingMontior(network);

		if (craftingMontior != null)
		{
			CraftingManagerListener listener = new CraftingManagerListener(player, network, craftingMontior.getNode());
			getMonitorings(player).add(listener);
			network.getCraftingManager().addListener(listener);
		}

	}

	public static void stopMonitoring(ServerPlayerEntity player, INetwork network)
	{
		List<CraftingManagerListener> list = getMonitorings(player);
		List<CraftingManagerListener> filetered = list.stream().filter(l -> l.getNetwork() == network).collect(Collectors.toList());

		for (CraftingManagerListener listener : filetered)
		{
			network.getCraftingManager().removeListener(listener);
			list.remove(listener);
		}

	}

	private static List<CraftingManagerListener> getMonitorings(ServerPlayerEntity player)
	{
		return LISTENERS.computeIfAbsent(player.getUUID(), k -> new ArrayList<>());
	}

	public static INetwork getNetwork(ServerPlayerEntity player, LevelBlockPos networkPos)
	{
		RegistryKey<World> networkLevelKey = RegistryKey.create(Registry.DIMENSION_REGISTRY, networkPos.getLevelName());
		ServerWorld networkLevel = player.getServer().getLevel(networkLevelKey);
		return API.instance().getNetworkManager(networkLevel).getNetwork(networkPos.getPos());
	}

	public static void openGui(ServerPlayerEntity player, INetwork network)
	{
		CraftingMonitorTile craftingMonitor = GCM.findCraftingMontior(network);
		CraftingMonitorNetworkNode node = craftingMonitor.getNode();
		GCM.NETWORK_HANDLER.sendTo(player, new CCraftingMonitorOpenResultMessage(new LevelBlockPos(node.getNetwork()), node.getTitle()));
	}

	public static CraftingMonitorTile findCraftingMontior(INetwork network)
	{
		CraftingMonitorNetworkNode node = NOCDE_CACHE.get(network);

		if (node != null)
		{
			BlockPos pos = node.getPos();
			TileEntity blockEntity = node.getWorld().getBlockEntity(pos);

			if (blockEntity instanceof CraftingMonitorTile)
			{
				CraftingMonitorTile craftingMonitorBlockEntity = (CraftingMonitorTile) blockEntity;

				if (craftingMonitorBlockEntity.getNode().getNetwork() == network)
				{
					return craftingMonitorBlockEntity;
				}

			}

		}

		CraftingMonitorTile craftingMonitorBlockEntity = findCraftingMontior0(network);

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

	private static CraftingMonitorTile findCraftingMontior0(INetwork network)
	{
		if (network == null)
		{
			return null;
		}

		for (INetworkNodeGraphEntry entry : network.getNodeGraph().all())
		{
			INetworkNode node = entry.getNode();

			if (node instanceof CraftingMonitorNetworkNode)
			{
				BlockPos pos = node.getPos();
				World level = node.getWorld();
				TileEntity blockEntity = level.getBlockEntity(pos);

				if (blockEntity instanceof CraftingMonitorTile)
				{
					return (CraftingMonitorTile) blockEntity;
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
