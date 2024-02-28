package giselle.rs_cmig.common;

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
import com.refinedmods.refinedstorage.apiimpl.API;
import com.refinedmods.refinedstorage.apiimpl.network.node.CraftingMonitorNetworkNode;
import com.refinedmods.refinedstorage.blockentity.craftingmonitor.CraftingMonitorBlockEntity;

import giselle.rs_cmig.client.RS_CMIGClient;
import giselle.rs_cmig.common.network.CCraftingMonitorOpenResultMessage;
import giselle.rs_cmig.common.network.NetworkHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;

@Mod(RS_CMIG.MODID)
public class RS_CMIG
{
	public static final String MODID = "rs_cmig";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final NetworkHandler NETWORK_HANDLER = new NetworkHandler();

	private static final Map<INetwork, CraftingMonitorNetworkNode> NOCDE_CACHE = new HashMap<>();
	private static final Map<UUID, List<CraftingMonitorListener>> LISTENERS = new HashMap<>();

	public RS_CMIG(IEventBus fml_bus)
	{
		if (FMLEnvironment.dist == Dist.CLIENT)
		{
			RS_CMIGClient.init(fml_bus);
		}

		fml_bus.addListener(RS_CMIG::onRegisterNetworkPackets);

		IEventBus forge_bus = NeoForge.EVENT_BUS;
		forge_bus.register(EventHandlers.class);
	}

	private static void onRegisterNetworkPackets(RegisterPayloadHandlerEvent event)
	{
		NETWORK_HANDLER.register(event.registrar(MODID));
	}

	public static void startMonitoring(ServerPlayer player, INetwork network)
	{
		CraftingMonitorBlockEntity craftingMontior = RS_CMIG.findCraftingMontior(network);

		if (craftingMontior != null)
		{
			CraftingMonitorListener listener = new CraftingMonitorListener(player, network, craftingMontior.getNode());
			getMonitorings(player).add(listener);
			network.getCraftingManager().addListener(listener);
		}

	}

	public static void stopMonitoring(ServerPlayer player, INetwork network)
	{
		List<CraftingMonitorListener> list = getMonitorings(player);
		List<CraftingMonitorListener> filetered = list.stream().filter(l -> l.getNetwork() == network).collect(Collectors.toList());

		for (CraftingMonitorListener listener : filetered)
		{
			network.getCraftingManager().removeListener(listener);
			list.remove(listener);
		}

	}

	private static List<CraftingMonitorListener> getMonitorings(ServerPlayer player)
	{
		return LISTENERS.computeIfAbsent(player.getUUID(), k -> new ArrayList<>());
	}

	public static INetwork getNetwork(ServerPlayer player, LevelBlockPos networkPos)
	{
		ResourceKey<Level> networkLevelKey = ResourceKey.create(Registries.DIMENSION, networkPos.getLevelName());
		ServerLevel networkLevel = player.getServer().getLevel(networkLevelKey);
		return API.instance().getNetworkManager(networkLevel).getNetwork(networkPos.getPos());
	}

	public static void openGui(ServerPlayer player, INetwork network)
	{
		CraftingMonitorBlockEntity craftingMonitor = RS_CMIG.findCraftingMontior(network);
		CraftingMonitorNetworkNode node = craftingMonitor.getNode();
		RS_CMIG.NETWORK_HANDLER.sendTo(player, new CCraftingMonitorOpenResultMessage(new LevelBlockPos(node.getNetwork()), node.getTitle()));
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
