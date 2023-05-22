package giselle.rs_cmig.common.network;

import giselle.rs_cmig.common.RS_CMIG;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler
{
	private final String protocolVersion = Integer.toString(1);
	private final ResourceLocation name = RS_CMIG.rl("main_channel");
	private final SimpleChannel channel = NetworkRegistry.ChannelBuilder//
			.named(name)//
			.clientAcceptedVersions(protocolVersion::equals)//
			.serverAcceptedVersions(protocolVersion::equals)//
			.networkProtocolVersion(() -> protocolVersion)//
			.simpleChannel();

	public void register()
	{
		int id = 0;
		this.channel.registerMessage(id++, CGridShowButtonMessage.class, CGridShowButtonMessage::encode, CGridShowButtonMessage::decode, CGridShowButtonMessage::handle);
		this.channel.registerMessage(id++, SCraftingMonitorOpenRequestMessage.class, SCraftingMonitorOpenRequestMessage::encode, SCraftingMonitorOpenRequestMessage::decode, SCraftingMonitorOpenRequestMessage::handle);
		this.channel.registerMessage(id++, CCraftingMonitorOpenResultMessage.class, CCraftingMonitorOpenResultMessage::encode, CCraftingMonitorOpenResultMessage::decode, CCraftingMonitorOpenResultMessage::handle);
		this.channel.registerMessage(id++, SCraftingMonitorStartMonitoringMessage.class, SCraftingMonitorStartMonitoringMessage::encode, SCraftingMonitorStartMonitoringMessage::decode, SCraftingMonitorStartMonitoringMessage::handle);
		this.channel.registerMessage(id++, SCraftingMonitorStopMonitoringMessage.class, SCraftingMonitorStopMonitoringMessage::encode, SCraftingMonitorStopMonitoringMessage::decode, SCraftingMonitorStopMonitoringMessage::handle);
		this.channel.registerMessage(id++, CCraftingMonitorUpdateMessage.class, CCraftingMonitorUpdateMessage::encode, CCraftingMonitorUpdateMessage::decode, CCraftingMonitorUpdateMessage::handle);
		this.channel.registerMessage(id++, SCraftingMonitorCancelMessage.class, SCraftingMonitorCancelMessage::encode, SCraftingMonitorCancelMessage::decode, SCraftingMonitorCancelMessage::handle);
	}

	public void sendTo(ServerPlayerEntity player, Object message)
	{
		this.channel.send(PacketDistributor.PLAYER.with(() -> player), message);
	}

	public void sendToServer(Object message)
	{
		this.channel.send(PacketDistributor.SERVER.noArg(), message);
	}

}
