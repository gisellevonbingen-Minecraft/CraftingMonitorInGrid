package giselle.rs_cmig.common.network;

import giselle.rs_cmig.common.LevelBlockPos;
import net.minecraft.network.FriendlyByteBuf;

public abstract class NetworkContainerMessage extends NetworkMessage
{
	private final int containerId;

	public NetworkContainerMessage(LevelBlockPos networkPos, int containerId)
	{
		super(networkPos);
		this.containerId = containerId;
	}

	public NetworkContainerMessage(FriendlyByteBuf buf)
	{
		super(buf);
		this.containerId = buf.readInt();
	}

	@Override
	public void write(FriendlyByteBuf buf)
	{
		super.write(buf);
		buf.writeInt(this.getContainerId());
	}

	public int getContainerId()
	{
		return this.containerId;
	}

}
