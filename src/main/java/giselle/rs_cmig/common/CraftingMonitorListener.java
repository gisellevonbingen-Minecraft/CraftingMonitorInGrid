package giselle.rs_cmig.common;

import java.util.List;

import com.refinedmods.refinedstorage.api.autocrafting.craftingmonitor.ICraftingMonitorListener;
import com.refinedmods.refinedstorage.api.autocrafting.task.ICraftingTask;
import com.refinedmods.refinedstorage.api.network.INetwork;
import com.refinedmods.refinedstorage.blockentity.craftingmonitor.ICraftingMonitor;
import com.refinedmods.refinedstorage.network.craftingmonitor.CraftingMonitorSyncTask;
import com.refinedmods.refinedstorage.network.craftingmonitor.CraftingMonitorUpdateMessage;

import giselle.rs_cmig.common.network.CCraftingMonitorUpdateMessage;
import net.minecraft.server.level.ServerPlayer;

public class CraftingMonitorListener implements ICraftingMonitorListener
{
	private final ServerPlayer player;
	private final INetwork network;
	private final ICraftingMonitor craftingMonitor;

	public CraftingMonitorListener(ServerPlayer player, INetwork network, ICraftingMonitor craftingMonitor)
	{
		this.player = player;
		this.network = network;
		this.craftingMonitor = craftingMonitor;
	}

	public void send()
	{
		List<CraftingMonitorSyncTask> tasks = this.getCraftingMonitor().getTasks().stream().map(this::create).toList();
		RS_CMIG.NETWORK_HANDLER.sendTo(this.getPlayer(), new CCraftingMonitorUpdateMessage(new CraftingMonitorUpdateMessage(tasks)));
	}

	private CraftingMonitorSyncTask create(ICraftingTask task)
	{
		return new CraftingMonitorSyncTask(//
				task.getId(), //
				task.getRequested(), //
				task.getQuantity(), //
				task.getStartTime(), //
				task.getCompletionPercentage(), //
				task.getCraftingMonitorElements()//
		);
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

	public ServerPlayer getPlayer()
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
