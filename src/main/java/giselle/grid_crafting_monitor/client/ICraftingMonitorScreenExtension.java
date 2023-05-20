package giselle.grid_crafting_monitor.client;

import java.util.List;

import javax.annotation.Nullable;

import com.refinedmods.refinedstorage.api.network.grid.IGridTab;
import com.refinedmods.refinedstorage.container.CraftingMonitorContainer;
import com.refinedmods.refinedstorage.screen.widget.TabListWidget;

public interface ICraftingMonitorScreenExtension
{
	List<IGridTab> gcm$getTasks();

	TabListWidget<CraftingMonitorContainer> gcm$getTabs();

	@Nullable
	IGridTab gcm$getCurrentTab();

	boolean gcm$hasValidTabSelected();

	void gcm$updateScrollbar();
}
