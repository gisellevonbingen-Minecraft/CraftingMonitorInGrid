package giselle.rs_cmig.common.network;

import com.refinedmods.refinedstorage.RS;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

public class NetworkHandler
{
	public void register(IPayloadRegistrar registrar)
	{
		registrar.play(CGridShowButtonMessage.ID, CGridShowButtonMessage::new, handler -> handler.client(CGridShowButtonMessage::handle));

		registrar.play(SCraftingMonitorOpenRequestMessage.ID, SCraftingMonitorOpenRequestMessage::new, handler -> handler.server(SCraftingMonitorOpenRequestMessage::handle));
		registrar.play(CCraftingMonitorOpenResultMessage.ID, CCraftingMonitorOpenResultMessage::new, handler -> handler.client(CCraftingMonitorOpenResultMessage::handle));

		registrar.play(SCraftingMonitorStartMonitoringMessage.ID, SCraftingMonitorStartMonitoringMessage::new, handler -> handler.server(SCraftingMonitorStartMonitoringMessage::handle));
		registrar.play(SCraftingMonitorStopMonitoringMessage.ID, SCraftingMonitorStopMonitoringMessage::new, handler -> handler.server(SCraftingMonitorStopMonitoringMessage::handle));

		registrar.play(CCraftingMonitorUpdateMessage.ID, CCraftingMonitorUpdateMessage::new, handler -> handler.client(CCraftingMonitorUpdateMessage::handle));
		registrar.play(SCraftingMonitorCancelMessage.ID, SCraftingMonitorCancelMessage::new, handler -> handler.server(SCraftingMonitorCancelMessage::handle));
	}

	public void sendTo(ServerPlayer player, CustomPacketPayload message)
	{
		RS.NETWORK_HANDLER.sendTo(player, message);
	}

	public void sendToServer(CustomPacketPayload message)
	{
		RS.NETWORK_HANDLER.sendToServer(message);
	}

}
