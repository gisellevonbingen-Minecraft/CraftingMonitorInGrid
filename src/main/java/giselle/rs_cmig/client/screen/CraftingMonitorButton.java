package giselle.rs_cmig.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.refinedmods.refinedstorage.RSBlocks;
import com.refinedmods.refinedstorage.screen.BaseScreen;
import com.refinedmods.refinedstorage.screen.widget.sidebutton.SideButton;
import com.refinedmods.refinedstorage.util.ColorMap;

import giselle.rs_cmig.common.RS_CMIG;
import giselle.rs_cmig.common.LevelBlockPos;
import giselle.rs_cmig.common.network.SCraftingMonitorOpenRequestMessage;
import net.minecraft.world.item.ItemStack;

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
	protected void renderButtonIcon(PoseStack arg0, int arg1, int arg2)
	{
		this.screen.getMinecraft().getItemRenderer().renderAndDecorateFakeItem(new ItemStack(RSBlocks.CRAFTING_MONITOR.get(ColorMap.DEFAULT_COLOR).get()), arg1, arg2);
	}

	@Override
	public void onPress()
	{
		super.onPress();

		RS_CMIG.NETWORK_HANDLER.sendToServer(new SCraftingMonitorOpenRequestMessage(this.networkPos));
	}

}