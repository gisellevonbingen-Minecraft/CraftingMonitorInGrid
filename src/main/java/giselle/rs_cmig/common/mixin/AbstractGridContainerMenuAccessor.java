package giselle.rs_cmig.common.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.refinedmods.refinedstorage.common.api.grid.Grid;
import com.refinedmods.refinedstorage.common.grid.AbstractGridContainerMenu;

@Mixin(value = AbstractGridContainerMenu.class, remap = false)
public interface AbstractGridContainerMenuAccessor
{
	@Accessor(value = "grid", remap = false)
	Grid getGrid();
}
