package giselle.grid_crafting_monitor.common.network;

import giselle.grid_crafting_monitor.common.LevelBlockPos;
import net.minecraft.network.PacketBuffer;

public abstract class NetworkContainerMessage extends NetworkMessage
{
	private int containerId;

	protected NetworkContainerMessage()
	{

	}

	public NetworkContainerMessage(LevelBlockPos networkPos, int containerId)
	{
		super(networkPos);
		this.containerId = containerId;
	}

	protected static void decode(NetworkContainerMessage message, PacketBuffer buf)
	{
		NetworkMessage.decode(message, buf);
		message.containerId = buf.readInt();
	}

	protected static void encode(NetworkContainerMessage message, PacketBuffer buf)
	{
		NetworkMessage.encode(message, buf);
		buf.writeInt(message.getContainerId());
	}

	public int getContainerId()
	{
		return this.containerId;
	}

}
