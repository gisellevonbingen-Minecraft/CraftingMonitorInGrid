package giselle.grid_crafting_monitor.common.network;

import java.util.function.Supplier;

import com.refinedmods.refinedstorage.screen.grid.GridScreen;

import giselle.grid_crafting_monitor.client.IGridScreenExtension;
import giselle.grid_crafting_monitor.common.LevelBlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class CGridShowButtonMessage extends NetworkContainerMessage
{
	protected CGridShowButtonMessage()
	{

	}

	public CGridShowButtonMessage(LevelBlockPos networkPos, int containerId)
	{
		super(networkPos, containerId);
	}

	public static CGridShowButtonMessage decode(PacketBuffer buf)
	{
		CGridShowButtonMessage message = new CGridShowButtonMessage();
		NetworkContainerMessage.decode(message, buf);
		return message;
	}

	public static void encode(CGridShowButtonMessage message, PacketBuffer buf)
	{
		NetworkContainerMessage.encode(message, buf);
	}

	public static void handle(CGridShowButtonMessage message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			Minecraft minecraft = Minecraft.getInstance();

			if (minecraft.screen instanceof GridScreen)
			{
				GridScreen screen = (GridScreen) minecraft.screen;

				if (screen.getMenu().containerId == message.getContainerId())
				{
					((IGridScreenExtension) screen).gcm$setNetworkPos(message.getNetworkPos());
				}

			}

		});
		ctx.get().setPacketHandled(true);
	}

}
