package giselle.grid_crafting_monitor.common.network;

import java.util.function.Supplier;

import com.refinedmods.refinedstorage.RSContainers;
import com.refinedmods.refinedstorage.container.CraftingMonitorContainer;

import giselle.grid_crafting_monitor.client.EmptyCraftingMonitor;
import giselle.grid_crafting_monitor.client.screen.GCMCraftingMonitorScreen;
import giselle.grid_crafting_monitor.common.LevelBlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
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
			Minecraft minecraft = Minecraft.getInstance();
			PlayerEntity player = minecraft.player;
			CraftingMonitorContainer container = new CraftingMonitorContainer(RSContainers.CRAFTING_MONITOR, new EmptyCraftingMonitor(), null, player, 0);
			GCMCraftingMonitorScreen screen = new GCMCraftingMonitorScreen(container, player.inventory, message, minecraft.screen);
			minecraft.setScreen(screen);
		});
		ctx.get().setPacketHandled(true);
	}

	public ITextComponent getDisplayName()
	{
		return this.displayName;
	}

}
