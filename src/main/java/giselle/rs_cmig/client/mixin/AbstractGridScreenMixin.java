package giselle.rs_cmig.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.refinedmods.refinedstorage.common.grid.AbstractGridContainerMenu;
import com.refinedmods.refinedstorage.common.grid.screen.AbstractGridScreen;
import com.refinedmods.refinedstorage.common.support.stretching.AbstractStretchingScreen;

import giselle.rs_cmig.client.IGridScreenExtension;
import giselle.rs_cmig.client.screen.AutocraftingMonitorButton;
import giselle.rs_cmig.common.LevelBlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

@Mixin(value = AbstractGridScreen.class, remap = false)
public abstract class AbstractGridScreenMixin<T extends AbstractGridContainerMenu> extends AbstractStretchingScreen<T> implements IGridScreenExtension
{
	protected AbstractGridScreenMixin(T menu, Inventory playerInventory, Component title)
	{
		super(menu, playerInventory, title);
	}

	private LevelBlockPos rs_cmig$autocraftingMonitor;

	@Inject(method = "init", at = @At("TAIL"), remap = false)
	private void init(int rows, CallbackInfo ci)
	{
		this.addSideButton(new AutocraftingMonitorButton(() -> this.rs_cmig$autocraftingMonitor));
	}

	@Override
	public void rs_cmig$setAutocraftingMonitor(LevelBlockPos autocraftingMonitor)
	{
		this.rs_cmig$autocraftingMonitor = autocraftingMonitor;
	}

	@Override
	public LevelBlockPos rs_cmig$getAutocraftingMonitor()
	{
		return this.rs_cmig$autocraftingMonitor;
	}

}
