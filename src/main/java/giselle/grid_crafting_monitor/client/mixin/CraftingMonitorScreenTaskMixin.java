package giselle.grid_crafting_monitor.client.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.refinedmods.refinedstorage.screen.CraftingMonitorScreen;

import giselle.grid_crafting_monitor.client.ICraftingMonitorScreenTaskExtension;

@Mixin(CraftingMonitorScreen.Task.class)
public abstract class CraftingMonitorScreenTaskMixin implements ICraftingMonitorScreenTaskExtension
{
	@Shadow(remap = false)
	private UUID id;

	@Override
	public UUID gcm$getId()
	{
		return this.id;
	}

}
