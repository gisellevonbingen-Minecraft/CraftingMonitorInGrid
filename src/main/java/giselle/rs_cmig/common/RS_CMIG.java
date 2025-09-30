package giselle.rs_cmig.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.refinedmods.refinedstorage.api.network.Network;
import com.refinedmods.refinedstorage.api.network.node.GraphNetworkComponent;
import com.refinedmods.refinedstorage.api.network.node.container.NetworkNodeContainer;
import com.refinedmods.refinedstorage.common.api.support.network.InWorldNetworkNodeContainer;
import com.refinedmods.refinedstorage.common.autocrafting.monitor.AutocraftingMonitorBlockEntity;
import com.refinedmods.refinedstorage.common.support.network.AbstractBaseNetworkNodeContainerBlockEntity;

import giselle.rs_cmig.common.network.CGridShowButtonMessage;
import giselle.rs_cmig.common.network.SAutocraftingMonitorOpenRequestMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(RS_CMIG.MODID)
public class RS_CMIG
{
	public static final String MODID = "rs_cmig";
	public static final Logger LOGGER = LogManager.getLogger();

	private static final Map<Network, AutocraftingMonitorBlockEntity> BLOCK_ENTITY_CACHE = new HashMap<>();

	public RS_CMIG(ModContainer modContainer, Dist dist)
	{
		IEventBus fml_bus = ModLoadingContext.get().getActiveContainer().getEventBus();
		fml_bus.addListener(RS_CMIG::onRegisterPayloadHandlers);

		IEventBus forge_bus = NeoForge.EVENT_BUS;
		forge_bus.register(CommonEventHandlers.class);
	}

	private static void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent e)
	{
		String modVersion = ModList.get().getModContainerById(MODID).get().getModInfo().getVersion().toString();
		PayloadRegistrar registry = e.registrar(MODID).versioned(modVersion);

		CGridShowButtonMessage.BUILDER.playToClient(registry);
		SAutocraftingMonitorOpenRequestMessage.BUILDER.playToServer(registry);
	}

	public static Network getNetwork(ServerPlayer player, LevelBlockPos pos)
	{
		if (pos.blockEntity(player.server) instanceof AbstractBaseNetworkNodeContainerBlockEntity<?> blockEntity)
		{
			return blockEntity.getNetworkForItem();
		}
		else
		{
			return null;
		}

	}

	public static AutocraftingMonitorBlockEntity findAutocraftingMontior(MinecraftServer server, Network network)
	{
		AutocraftingMonitorBlockEntity blockEntity = BLOCK_ENTITY_CACHE.get(network);

		if (blockEntity != null)
		{
			if (blockEntity.getNetworkForItem() == network)
			{
				return blockEntity;
			}

		}

		AutocraftingMonitorBlockEntity found = findAutocraftingMontior0(server, network);

		if (found != null)
		{
			BLOCK_ENTITY_CACHE.put(network, found);
		}
		else
		{
			BLOCK_ENTITY_CACHE.remove(network);
		}

		return found;
	}

	private static AutocraftingMonitorBlockEntity findAutocraftingMontior0(MinecraftServer server, Network network)
	{
		if (network == null)
		{
			return null;
		}

		for (NetworkNodeContainer container : network.getComponent(GraphNetworkComponent.class).getContainers())
		{
			if (container instanceof InWorldNetworkNodeContainer inWorldContainer)
			{
				if (new LevelBlockPos(inWorldContainer.getPosition()).blockEntity(server) instanceof AutocraftingMonitorBlockEntity autocraftingMonitor)
				{
					return autocraftingMonitor;
				}

			}

		}

		return null;
	}

	public static ResourceLocation rl(String path)
	{
		return ResourceLocation.fromNamespaceAndPath(MODID, path);
	}

}
