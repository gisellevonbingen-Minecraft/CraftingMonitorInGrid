package giselle.grid_crafting_monitor.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.refinedmods.refinedstorage.container.GridContainerMenu;
import com.refinedmods.refinedstorage.screen.BaseScreen;
import com.refinedmods.refinedstorage.screen.grid.GridScreen;

import giselle.grid_crafting_monitor.client.IGridScreenExtension;
import giselle.grid_crafting_monitor.client.screen.CraftingMonitorButton;
import giselle.grid_crafting_monitor.common.LevelBlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

@Mixin(GridScreen.class)
public abstract class GridScreenMixin extends BaseScreen<GridContainerMenu> implements IGridScreenExtension
{
	protected GridScreenMixin(GridContainerMenu container, int xSize, int ySize, Inventory inventory, Component title)
	{
		super(container, xSize, ySize, inventory, title);
	}

	private LevelBlockPos gcm$networkPos;

	private void addCraftingMonitorButton()
	{
		LevelBlockPos networkPos = this.gcm$getNetworkPos();

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
	public void gcm$setNetworkPos(LevelBlockPos networkPos)
	{
		if (networkPos != null)
		{
			this.gcm$networkPos = networkPos;
		}

		this.addCraftingMonitorButton();
	}

	@Override
	public LevelBlockPos gcm$getNetworkPos()
	{
		return this.gcm$networkPos;
	}

}
