package giselle.grid_crafting_monitor.common.network;

import giselle.grid_crafting_monitor.common.LevelBlockPos;
import net.minecraft.network.PacketBuffer;

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

	protected static void decode(NetworkMessage message, PacketBuffer buf)
	{
		message.networkPos = new LevelBlockPos(buf);
	}

	protected static void encode(NetworkMessage message, PacketBuffer buf)
	{
		message.getNetworkPos().encode(buf);
	}

	public LevelBlockPos getNetworkPos()
	{
		return this.networkPos;
	}

}
