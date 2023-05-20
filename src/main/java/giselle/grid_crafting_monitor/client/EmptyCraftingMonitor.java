package giselle.grid_crafting_monitor.client;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import com.refinedmods.refinedstorage.api.autocrafting.ICraftingManager;
import com.refinedmods.refinedstorage.api.autocrafting.task.ICraftingTask;
import com.refinedmods.refinedstorage.blockentity.craftingmonitor.ICraftingMonitor;
import com.refinedmods.refinedstorage.blockentity.data.BlockEntitySynchronizationParameter;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class EmptyCraftingMonitor implements ICraftingMonitor
{
	private Optional<UUID> tabSelected = Optional.empty();
	private int tabPage;

	public EmptyCraftingMonitor()
	{

	}

	@Override
	public ICraftingManager getCraftingManager()
	{
		return null;
	}

	@Override
	public BlockEntitySynchronizationParameter<Integer, ?> getRedstoneModeParameter()
	{
		return null;
	}

	@Override
	public int getSlotId()
	{
		return -1;
	}

	@Override
	public int getTabPage()
	{
		return this.tabPage;
	}

	@Override
	public Optional<UUID> getTabSelected()
	{
		return this.tabSelected;
	}

	@Override
	public Collection<ICraftingTask> getTasks()
	{
		return Collections.emptyList();
	}

	@Override
	public Component getTitle()
	{
		return Component.translatable("gui.refinedstorage.crafting_monitor");
	}

	@Override
	public boolean isActiveOnClient()
	{
		return true;
	}

	@Override
	public void onCancelled(ServerPlayer player, UUID id)
	{

	}

	@Override
	public void onClosed(Player player)
	{

	}

	@Override
	public void onTabPageChanged(int page)
	{
		if (page >= 0)
		{
			this.tabPage = page;
		}

	}

	@Override
	public void onTabSelectionChanged(Optional<UUID> taskId)
	{
		if (taskId.isPresent() && this.tabSelected.isPresent() && taskId.get().equals(tabSelected.get()))
		{
			this.tabSelected = Optional.empty();
		}
		else
		{
			this.tabSelected = taskId;
		}

	}

}
