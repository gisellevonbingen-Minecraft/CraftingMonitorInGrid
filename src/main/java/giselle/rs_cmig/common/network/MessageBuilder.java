package giselle.rs_cmig.common.network;

import java.util.function.Function;

import giselle.rs_cmig.common.RS_CMIG;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class MessageBuilder<MESSAGE extends AbstractMessage>
{
	private final CustomPacketPayload.Type<MESSAGE> type;
	private final Function<RegistryFriendlyByteBuf, MESSAGE> decoder;

	public MessageBuilder(String name, Function<RegistryFriendlyByteBuf, MESSAGE> decoder)
	{
		this.type = new CustomPacketPayload.Type<>(RS_CMIG.rl(name));
		this.decoder = decoder;
	}

	public CustomPacketPayload.Type<MESSAGE> type()
	{
		return this.type;
	}

	public void playCommon(PlayRegister<MESSAGE> register)
	{
		register.apply(this.type, StreamCodec.<RegistryFriendlyByteBuf, MESSAGE> of((buffer, message) ->
		{
			message.encode(buffer);
		}, this.decoder::apply), MESSAGE::handle);
	}

	public void playToBidirectional(PayloadRegistrar registry)
	{
		this.playCommon(registry::playBidirectional);
	}

	public void playToClient(PayloadRegistrar registry)
	{
		this.playCommon(registry::playToClient);
	}

	public void playToServer(PayloadRegistrar registry)
	{
		playCommon(registry::playToServer);
	}

	@FunctionalInterface
	public interface PlayRegister<MESSAGE extends AbstractMessage>
	{
		void apply(CustomPacketPayload.Type<MESSAGE> type, StreamCodec<? super RegistryFriendlyByteBuf, MESSAGE> reader, IPayloadHandler<MESSAGE> handler);
	}

}
