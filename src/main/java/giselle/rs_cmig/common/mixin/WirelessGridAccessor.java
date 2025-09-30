package giselle.rs_cmig.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.refinedmods.refinedstorage.common.api.support.network.item.NetworkItemContext;

@Mixin(targets = "com.refinedmods.refinedstorage.common.grid.WirelessGrid", remap = false)
public interface WirelessGridAccessor
{
	@Accessor(value = "context", remap = false)
	NetworkItemContext getContext();
}
