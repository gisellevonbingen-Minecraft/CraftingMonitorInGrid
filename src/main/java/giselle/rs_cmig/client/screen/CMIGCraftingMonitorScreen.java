package giselle.rs_cmig.client.screen;

import java.util.List;
import java.util.UUID;

import com.refinedmods.refinedstorage.api.network.grid.IGridTab;
import com.refinedmods.refinedstorage.container.CraftingMonitorContainerMenu;
import com.refinedmods.refinedstorage.screen.CraftingMonitorScreen;
import com.refinedmods.refinedstorage.screen.widget.TabListWidget;

import giselle.rs_cmig.client.ICraftingMonitorScreenExtension;
import giselle.rs_cmig.client.ICraftingMonitorScreenTaskExtension;
import giselle.rs_cmig.common.LevelBlockPos;
import giselle.rs_cmig.common.RS_CMIG;
import giselle.rs_cmig.common.network.CCraftingMonitorOpenResultMessage;
import giselle.rs_cmig.common.network.SCraftingMonitorCancelMessage;
import giselle.rs_cmig.common.network.SCraftingMonitorStopMonitoringMessage;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class CMIGCraftingMonitorScreen extends CraftingMonitorScreen
{
	private final LevelBlockPos networkPos;
	private final Screen parent;

	private Button cancelButton;
	private Button cancelAllButton;

	public CMIGCraftingMonitorScreen(CraftingMonitorContainerMenu container, Inventory inventory, CCraftingMonitorOpenResultMessage message, Screen parent)
	{
		super(container, inventory, message.getDisplayName());

		this.networkPos = message.getNetworkPos();
		this.parent = parent;
	}

	@Override
	public void onPostInit(int x, int y)
	{
		ICraftingMonitorScreenExtension extension = (ICraftingMonitorScreenExtension) this;
		TabListWidget<CraftingMonitorContainerMenu> tabs = extension.rs_cmig$getTabs();
		List<IGridTab> tasks = extension.rs_cmig$getTasks();

		tabs.init(this.width);

		Component cancel = Component.translatable("gui.cancel");
		Component cancelAll = Component.translatable("misc.refinedstorage.cancel_all");

		int cancelButtonWidth = 14 + this.font.width(cancel.getString());
		int cancelAllButtonWidth = 14 + this.font.width(cancelAll.getString());

		this.cancelButton = addButton(x + 7, y + 201 - 20 - 7, cancelButtonWidth, 20, cancel, false, true, btn ->
		{
			if (extension.rs_cmig$hasValidTabSelected())
			{
				LevelBlockPos networkPos = this.getNetworkPos();
				UUID taskId = ((ICraftingMonitorScreenTaskExtension) extension.rs_cmig$getCurrentTab()).rs_cmig$getId();
				RS_CMIG.NETWORK_HANDLER.sendToServer(new SCraftingMonitorCancelMessage(networkPos, taskId));
			}
		});
		this.cancelAllButton = addButton(x + 7 + cancelButtonWidth + 4, y + 201 - 20 - 7, cancelAllButtonWidth, 20, cancelAll, false, true, btn ->
		{
			if (!tasks.isEmpty())
			{
				LevelBlockPos networkPos = this.getNetworkPos();
				RS_CMIG.NETWORK_HANDLER.sendToServer(new SCraftingMonitorCancelMessage(networkPos, null));
			}
		});
	}

	@Override
	public void tick(int x, int y)
	{
		super.tick(x, y);

		ICraftingMonitorScreenExtension extension = (ICraftingMonitorScreenExtension) this;
		List<IGridTab> tasks = extension.rs_cmig$getTasks();

		if (this.cancelButton != null)
		{
			this.cancelButton.active = extension.rs_cmig$hasValidTabSelected();
		}

		if (this.cancelAllButton != null)
		{
			this.cancelAllButton.active = !tasks.isEmpty();
		}

	}

	@Override
	public void onClose()
	{
		this.minecraft.setScreen(this.getParent());
		RS_CMIG.NETWORK_HANDLER.sendToServer(new SCraftingMonitorStopMonitoringMessage(this.getNetworkPos()));
	}

	public LevelBlockPos getNetworkPos()
	{
		return this.networkPos;
	}

	public Screen getParent()
	{
		return this.parent;
	}

}
