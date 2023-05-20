package giselle.grid_crafting_monitor.client;

import javax.annotation.Nullable;

import giselle.grid_crafting_monitor.common.LevelBlockPos;

public interface IGridScreenExtension
{
	void gcm$setNetworkPos(LevelBlockPos pos);

	@Nullable
	LevelBlockPos gcm$getNetworkPos();
}
