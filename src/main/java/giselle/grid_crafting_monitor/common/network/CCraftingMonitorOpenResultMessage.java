package giselle.grid_crafting_monitor.common.network;

import java.util.function.Supplier;

import giselle.grid_crafting_monitor.client.GCMClient;
import giselle.grid_crafting_monitor.common.LevelBlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

public class CCraftingMonitorOpenResultMessage extends NetworkMessage
{
	private Component displayName;

	protected CCraftingMonitorOpenResultMessage()
	{

	}

	public CCraftingMonitorOpenResultMessage(LevelBlockPos networkPos, Component displayName)
	{
		super(networkPos);
		this.displayName = displayName;
	}

	public static CCraftingMonitorOpenResultMessage decode(FriendlyByteBuf buf)
	{
		CCraftingMonitorOpenResultMessage message = new CCraftingMonitorOpenResultMessage();
		NetworkMessage.decode(message, buf);
		message.displayName = buf.readComponent();
		return message;
	}

	public static void encode(CCraftingMonitorOpenResultMessage message, FriendlyByteBuf buf)
	{
		NetworkMessage.encode(message, buf);
		buf.writeComponent(message.getDisplayName());
	}

	public static void handle(CCraftingMonitorOpenResultMessage message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			GCMClient.openScreen(message);
		});
		ctx.get().setPacketHandled(true);
	}

	public Component getDisplayName()
	{
		return this.displayName;
	}

}
