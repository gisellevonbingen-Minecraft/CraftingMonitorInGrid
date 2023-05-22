package giselle.rs_cmig.client;

import javax.annotation.Nullable;

import giselle.rs_cmig.common.LevelBlockPos;

public interface IGridScreenExtension
{
	void gcm$setNetworkPos(LevelBlockPos pos);

	@Nullable
	LevelBlockPos gcm$getNetworkPos();
}
