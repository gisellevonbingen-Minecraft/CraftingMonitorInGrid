package giselle.grid_crafting_monitor.common.network;

import java.util.function.Supplier;

import giselle.grid_crafting_monitor.client.GCMClient;
import giselle.grid_crafting_monitor.common.LevelBlockPos;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

public class CCraftingMonitorOpenResultMessage extends NetworkMessage
{
	private ITextComponent displayName;

	protected CCraftingMonitorOpenResultMessage()
	{

	}

	public CCraftingMonitorOpenResultMessage(LevelBlockPos networkPos, ITextComponent displayName)
	{
		super(networkPos);
		this.displayName = displayName;
	}

	public static CCraftingMonitorOpenResultMessage decode(PacketBuffer buf)
	{
		CCraftingMonitorOpenResultMessage message = new CCraftingMonitorOpenResultMessage();
		NetworkMessage.decode(message, buf);
		message.displayName = buf.readComponent();
		return message;
	}

	public static void encode(CCraftingMonitorOpenResultMessage message, PacketBuffer buf)
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

	public ITextComponent getDisplayName()
	{
		return this.displayName;
	}

}
