package giselle.grid_crafting_monitor.client.screen;

import java.util.List;
import java.util.UUID;

import com.refinedmods.refinedstorage.api.network.grid.IGridTab;
import com.refinedmods.refinedstorage.container.CraftingMonitorContainer;
import com.refinedmods.refinedstorage.screen.CraftingMonitorScreen;
import com.refinedmods.refinedstorage.screen.widget.TabListWidget;

import giselle.grid_crafting_monitor.client.ICraftingMonitorScreenExtension;
import giselle.grid_crafting_monitor.client.ICraftingMonitorScreenTaskExtension;
import giselle.grid_crafting_monitor.common.GCM;
import giselle.grid_crafting_monitor.common.LevelBlockPos;
import giselle.grid_crafting_monitor.common.network.CCraftingMonitorOpenResultMessage;
import giselle.grid_crafting_monitor.common.network.SCraftingMonitorCancelMessage;
import giselle.grid_crafting_monitor.common.network.SCraftingMonitorStopMonitoringMessage;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GCMCraftingMonitorScreen extends CraftingMonitorScreen
{
	private final LevelBlockPos networkPos;
	private final Screen parent;

	private Button cancelButton;
	private Button cancelAllButton;

	public GCMCraftingMonitorScreen(CraftingMonitorContainer container, PlayerInventory inventory, CCraftingMonitorOpenResultMessage message, Screen parent)
	{
		super(container, inventory, message.getDisplayName());

		this.networkPos = message.getNetworkPos();
		this.parent = parent;
	}

	@Override
	public void onPostInit(int x, int y)
	{
		ICraftingMonitorScreenExtension extension = (ICraftingMonitorScreenExtension) this;
		TabListWidget<CraftingMonitorContainer> tabs = extension.gcm$getTabs();
		List<IGridTab> tasks = extension.gcm$getTasks();

		tabs.init(this.width);

		ITextComponent cancel = new TranslationTextComponent("gui.cancel");
		ITextComponent cancelAll = new TranslationTextComponent("misc.refinedstorage.cancel_all");

		int cancelButtonWidth = 14 + this.font.width(cancel.getString());
		int cancelAllButtonWidth = 14 + this.font.width(cancelAll.getString());

		this.cancelButton = addButton(x + 7, y + 201 - 20 - 7, cancelButtonWidth, 20, cancel, false, true, btn ->
		{
			if (extension.gcm$hasValidTabSelected())
			{
				LevelBlockPos networkPos = this.getNetworkPos();
				UUID taskId = ((ICraftingMonitorScreenTaskExtension) extension.gcm$getCurrentTab()).gcm$getId();
				GCM.NETWORK_HANDLER.sendToServer(new SCraftingMonitorCancelMessage(networkPos, taskId));
			}
		});
		this.cancelAllButton = addButton(x + 7 + cancelButtonWidth + 4, y + 201 - 20 - 7, cancelAllButtonWidth, 20, cancelAll, false, true, btn ->
		{
			if (!tasks.isEmpty())
			{
				LevelBlockPos networkPos = this.getNetworkPos();
				GCM.NETWORK_HANDLER.sendToServer(new SCraftingMonitorCancelMessage(networkPos, null));
			}
		});
	}

	@Override
	public void tick(int x, int y)
	{
		super.tick(x, y);

		ICraftingMonitorScreenExtension extension = (ICraftingMonitorScreenExtension) this;
		List<IGridTab> tasks = extension.gcm$getTasks();

		if (this.cancelButton != null)
		{
			this.cancelButton.active = extension.gcm$hasValidTabSelected();
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
		GCM.NETWORK_HANDLER.sendToServer(new SCraftingMonitorStopMonitoringMessage(this.getNetworkPos()));
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
