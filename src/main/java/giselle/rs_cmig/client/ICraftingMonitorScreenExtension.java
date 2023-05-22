package giselle.rs_cmig.client;

import java.util.List;

import javax.annotation.Nullable;

import com.refinedmods.refinedstorage.api.network.grid.IGridTab;
import com.refinedmods.refinedstorage.container.CraftingMonitorContainerMenu;
import com.refinedmods.refinedstorage.screen.widget.TabListWidget;

public interface ICraftingMonitorScreenExtension
{
	List<IGridTab> rs_cmig$getTasks();

	TabListWidget<CraftingMonitorContainerMenu> rs_cmig$getTabs();

	@Nullable
	IGridTab rs_cmig$getCurrentTab();

	boolean rs_cmig$hasValidTabSelected();

	void rs_cmig$updateScrollbar();
}
