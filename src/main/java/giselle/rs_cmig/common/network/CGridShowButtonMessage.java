package giselle.rs_cmig.common.network;

import com.refinedmods.refinedstorage.screen.grid.GridScreen;

import giselle.rs_cmig.client.IGridScreenExtension;
import giselle.rs_cmig.common.LevelBlockPos;
import giselle.rs_cmig.common.RS_CMIG;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class CGridShowButtonMessage extends NetworkContainerMessage
{
	public static final ResourceLocation ID = new ResourceLocation(RS_CMIG.MODID, "grid_show_button");

	public CGridShowButtonMessage(LevelBlockPos networkPos, int containerId)
	{
		super(networkPos, containerId);
	}

	public CGridShowButtonMessage(FriendlyByteBuf buf)
	{
		super(buf);
	}

	@Override
	public void write(FriendlyByteBuf buf)
	{
		super.write(buf);
	}

	public static void handle(CGridShowButtonMessage message, PlayPayloadContext ctx)
	{
		ctx.workHandler().submitAsync(() ->
		{
			Minecraft minecraft = Minecraft.getInstance();

			if (minecraft.screen instanceof GridScreen screen && screen.getMenu().containerId == message.getContainerId())
			{
				((IGridScreenExtension) screen).rs_cmig$setNetworkPos(message.getNetworkPos());
			}

		});
	}

	@Override
	public ResourceLocation id()
	{
		return ID;
	}

}
