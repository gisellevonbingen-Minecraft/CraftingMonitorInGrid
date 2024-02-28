package giselle.rs_cmig.common.network;

import giselle.rs_cmig.common.LevelBlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public abstract class NetworkMessage implements CustomPacketPayload
{
	private final LevelBlockPos networkPos;

	public NetworkMessage(LevelBlockPos networkPos)
	{
		this.networkPos = networkPos;
	}

	public NetworkMessage(FriendlyByteBuf buf)
	{
		this.networkPos = new LevelBlockPos(buf);
	}

	@Override
	public void write(FriendlyByteBuf buf)
	{
		this.getNetworkPos().encode(buf);
	}

	public LevelBlockPos getNetworkPos()
	{
		return this.networkPos;
	}

}
