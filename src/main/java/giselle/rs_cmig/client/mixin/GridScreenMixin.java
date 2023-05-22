package giselle.rs_cmig.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.refinedmods.refinedstorage.container.GridContainerMenu;
import com.refinedmods.refinedstorage.screen.BaseScreen;
import com.refinedmods.refinedstorage.screen.grid.GridScreen;

import giselle.rs_cmig.client.IGridScreenExtension;
import giselle.rs_cmig.client.screen.CraftingMonitorButton;
import giselle.rs_cmig.common.LevelBlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

@Mixin(GridScreen.class)
public abstract class GridScreenMixin extends BaseScreen<GridContainerMenu> implements IGridScreenExtension
{
	protected GridScreenMixin(GridContainerMenu container, int xSize, int ySize, Inventory inventory, Component title)
	{
		super(container, xSize, ySize, inventory, title);
	}

	private LevelBlockPos rs_cmig$networkPos;

	private void addCraftingMonitorButton()
	{
		LevelBlockPos networkPos = this.rs_cmig$getNetworkPos();

		if (networkPos != null)
		{
			this.addSideButton(new CraftingMonitorButton(this, networkPos));
		}

	}

	@Inject(method = "onPostInit", at = @At("TAIL"), remap = false)
	private void onPostInit(int x, int y, CallbackInfo ci)
	{
		this.addCraftingMonitorButton();
	}

	@Override
	public void rs_cmig$setNetworkPos(LevelBlockPos networkPos)
	{
		if (networkPos != null)
		{
			this.rs_cmig$networkPos = networkPos;
		}

		this.addCraftingMonitorButton();
	}

	@Override
	public LevelBlockPos rs_cmig$getNetworkPos()
	{
		return this.rs_cmig$networkPos;
	}

}
