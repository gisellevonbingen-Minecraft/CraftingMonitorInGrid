package giselle.rs_cmig.client.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.refinedmods.refinedstorage.screen.CraftingMonitorScreen;

import giselle.rs_cmig.client.ICraftingMonitorScreenTaskExtension;

@Mixin(CraftingMonitorScreen.Task.class)
public abstract class CraftingMonitorScreenTaskMixin implements ICraftingMonitorScreenTaskExtension
{
	@Shadow(remap = false)
	private UUID id;

	@Override
	public UUID rs_cmig$getId()
	{
		return this.id;
	}

}
