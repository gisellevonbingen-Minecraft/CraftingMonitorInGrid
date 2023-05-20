package giselle.grid_crafting_monitor.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.refinedmods.refinedstorage.RSBlocks;
import com.refinedmods.refinedstorage.screen.BaseScreen;
import com.refinedmods.refinedstorage.screen.widget.sidebutton.SideButton;
import com.refinedmods.refinedstorage.util.ColorMap;

import giselle.grid_crafting_monitor.common.GCM;
import giselle.grid_crafting_monitor.common.LevelBlockPos;
import giselle.grid_crafting_monitor.common.network.SCraftingMonitorOpenRequestMessage;
import net.minecraft.item.ItemStack;

public class CraftingMonitorButton extends SideButton
{
	private final LevelBlockPos networkPos;

	public CraftingMonitorButton(BaseScreen<?> screen, LevelBlockPos networkPos)
	{
		super(screen);
		this.networkPos = networkPos;
	}

	@Override
	public String getTooltip()
	{
		return RSBlocks.CRAFTING_MONITOR.get(ColorMap.DEFAULT_COLOR).get().getName().getString();
	}

	@Override
	protected void renderButtonIcon(MatrixStack arg0, int arg1, int arg2)
	{
		this.screen.getMinecraft().getItemRenderer().renderAndDecorateFakeItem(new ItemStack(RSBlocks.CRAFTING_MONITOR.get(ColorMap.DEFAULT_COLOR).get()), arg1, arg2);
	}

	@Override
	public void onPress()
	{
		super.onPress();

		GCM.NETWORK_HANDLER.sendToServer(new SCraftingMonitorOpenRequestMessage(this.networkPos));
	}

}
