package giselle.rs_cmig.common.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public abstract class AbstractMessage implements CustomPacketPayload
{
	public AbstractMessage()
	{

	}

	public AbstractMessage(RegistryFriendlyByteBuf buf)
	{

	}

	public void encode(RegistryFriendlyByteBuf buf)
	{

	}

	public void handle(IPayloadContext ctx)
	{

	}

}
