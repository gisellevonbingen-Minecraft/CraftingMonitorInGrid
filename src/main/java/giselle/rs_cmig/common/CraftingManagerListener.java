package giselle.rs_cmig.common;

import com.refinedmods.refinedstorage.api.autocrafting.craftingmonitor.ICraftingMonitorListener;
import com.refinedmods.refinedstorage.api.network.INetwork;
import com.refinedmods.refinedstorage.network.craftingmonitor.CraftingMonitorUpdateMessage;
import com.refinedmods.refinedstorage.tile.craftingmonitor.ICraftingMonitor;

import giselle.rs_cmig.common.network.CCraftingMonitorUpdateMessage;
import net.minecraft.entity.player.ServerPlayerEntity;

public class CraftingManagerListener implements ICraftingMonitorListener
{
	private final ServerPlayerEntity player;
	private final INetwork network;
	private final ICraftingMonitor craftingMonitor;

	public CraftingManagerListener(ServerPlayerEntity player, INetwork network, ICraftingMonitor craftingMonitor)
	{
		this.player = player;
		this.network = network;
		this.craftingMonitor = craftingMonitor;
	}

	public void send()
	{
		RS_CMIG.NETWORK_HANDLER.sendTo(this.getPlayer(), new CCraftingMonitorUpdateMessage(new CraftingMonitorUpdateMessage(this.getCraftingMonitor())));
	}

	@Override
	public void onAttached()
	{
		this.send();
	}

	@Override
	public void onChanged()
	{
		this.send();
	}

	public ServerPlayerEntity getPlayer()
	{
		return this.player;
	}

	public INetwork getNetwork()
	{
		return this.network;
	}

	public ICraftingMonitor getCraftingMonitor()
	{
		return this.craftingMonitor;
	}

}
