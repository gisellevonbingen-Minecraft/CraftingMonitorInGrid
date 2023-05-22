package giselle.rs_cmig.client;

import javax.annotation.Nullable;

import giselle.rs_cmig.common.LevelBlockPos;

public interface IGridScreenExtension
{
	void rs_cmig$setNetworkPos(LevelBlockPos pos);

	@Nullable
	LevelBlockPos rs_cmig$getNetworkPos();
}
