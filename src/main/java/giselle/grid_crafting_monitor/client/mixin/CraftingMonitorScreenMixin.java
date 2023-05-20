package giselle.grid_crafting_monitor.client.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.refinedmods.refinedstorage.api.network.grid.IGridTab;
import com.refinedmods.refinedstorage.container.CraftingMonitorContainerMenu;
import com.refinedmods.refinedstorage.screen.CraftingMonitorScreen;
import com.refinedmods.refinedstorage.screen.widget.TabListWidget;

import giselle.grid_crafting_monitor.client.ICraftingMonitorScreenExtension;

@Mixin(CraftingMonitorScreen.class)
public abstract class CraftingMonitorScreenMixin implements ICraftingMonitorScreenExtension
{
	@Shadow(remap = false)
	private List<IGridTab> tasks;

	@Override
	public List<IGridTab> gcm$getTasks()
	{
		return this.tasks;
	}

	@Shadow(remap = false)
	private TabListWidget<CraftingMonitorContainerMenu> tabs;

	public TabListWidget<CraftingMonitorContainerMenu> gcm$getTabs()
	{
		return this.tabs;
	}

	@Shadow(remap = false)
	private IGridTab getCurrentTab()
	{
		return null;
	}

	@Override
	public IGridTab gcm$getCurrentTab()
	{
		return this.getCurrentTab();
	}

	@Shadow(remap = false)
	private boolean hasValidTabSelected()
	{
		return false;
	}

	@Override
	public boolean gcm$hasValidTabSelected()
	{
		return this.hasValidTabSelected();
	}

	@Shadow(remap = false)
	private void updateScrollbar()
	{

	}

	@Override
	public void gcm$updateScrollbar()
	{
		this.updateScrollbar();
	}

}
