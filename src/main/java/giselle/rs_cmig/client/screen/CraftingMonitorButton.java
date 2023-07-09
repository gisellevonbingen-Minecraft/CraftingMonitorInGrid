package giselle.rs_cmig.client.screen;

import com.refinedmods.refinedstorage.RSBlocks;
import com.refinedmods.refinedstorage.screen.BaseScreen;
import com.refinedmods.refinedstorage.screen.widget.sidebutton.SideButton;
import com.refinedmods.refinedstorage.util.ColorMap;

import giselle.rs_cmig.common.LevelBlockPos;
import giselle.rs_cmig.common.RS_CMIG;
import giselle.rs_cmig.common.network.SCraftingMonitorOpenRequestMessage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.world.item.ItemStack;

public class CraftingMonitorButton extends SideButton
{
	public static final Tooltip TOOLTIP = Tooltip.create(RSBlocks.CRAFTING_MONITOR.get(ColorMap.DEFAULT_COLOR).get().getName());

	private final LevelBlockPos networkPos;

	public CraftingMonitorButton(BaseScreen<?> screen, LevelBlockPos networkPos)
	{
		super(screen);
		this.networkPos = networkPos;
	}

	@Override
	protected String getSideButtonTooltip()
	{
		return RSBlocks.CRAFTING_MONITOR.get(ColorMap.DEFAULT_COLOR).get().getName().getString();
	}

	@Override
	protected void renderButtonIcon(GuiGraphics arg0, int arg1, int arg2)
	{
		arg0.renderItem(new ItemStack(RSBlocks.CRAFTING_MONITOR.get(ColorMap.DEFAULT_COLOR).get()), arg1, arg2);
	}

	@Override
	public void onPress()
	{
		super.onPress();

		RS_CMIG.NETWORK_HANDLER.sendToServer(new SCraftingMonitorOpenRequestMessage(this.networkPos));
	}

}
