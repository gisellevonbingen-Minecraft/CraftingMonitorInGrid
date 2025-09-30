package giselle.rs_cmig.client;

import javax.annotation.Nullable;

import giselle.rs_cmig.common.LevelBlockPos;

public interface IGridScreenExtension
{
	void rs_cmig$setAutocraftingMonitor(@Nullable LevelBlockPos autocraftingMonitor);

	@Nullable
	LevelBlockPos rs_cmig$getAutocraftingMonitor();
}
