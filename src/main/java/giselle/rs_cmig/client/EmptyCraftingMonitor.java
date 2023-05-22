package giselle.rs_cmig.client;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import com.refinedmods.refinedstorage.api.autocrafting.ICraftingManager;
import com.refinedmods.refinedstorage.api.autocrafting.task.ICraftingTask;
import com.refinedmods.refinedstorage.tile.craftingmonitor.ICraftingMonitor;
import com.refinedmods.refinedstorage.tile.data.TileDataParameter;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

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
	public TileDataParameter<Integer, ?> getRedstoneModeParameter()
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
	public ITextComponent getTitle()
	{
		return new TranslationTextComponent("gui.refinedstorage.crafting_monitor");
	}

	@Override
	public boolean isActiveOnClient()
	{
		return true;
	}

	@Override
	public void onCancelled(ServerPlayerEntity player, UUID id)
	{

	}

	@Override
	public void onClosed(PlayerEntity player)
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
