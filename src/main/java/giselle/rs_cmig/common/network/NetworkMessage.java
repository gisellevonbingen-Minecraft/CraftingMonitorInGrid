package giselle.rs_cmig.common.network;

import giselle.rs_cmig.common.LevelBlockPos;
import net.minecraft.network.FriendlyByteBuf;

public abstract class NetworkMessage
{
	private LevelBlockPos networkPos;

	protected NetworkMessage()
	{

	}

	public NetworkMessage(LevelBlockPos networkPos)
	{
		this.networkPos = networkPos;
	}

	protected static void decode(NetworkMessage message, FriendlyByteBuf buf)
	{
		message.networkPos = new LevelBlockPos(buf);
	}

	protected static void encode(NetworkMessage message, FriendlyByteBuf buf)
	{
		message.getNetworkPos().encode(buf);
	}

	public LevelBlockPos getNetworkPos()
	{
		return this.networkPos;
	}

}
