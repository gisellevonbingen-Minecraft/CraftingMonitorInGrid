package giselle.rs_cmig.common.network;

import com.refinedmods.refinedstorage.network.craftingmonitor.CraftingMonitorUpdateMessage;
import com.refinedmods.refinedstorage.screen.BaseScreen;

import giselle.rs_cmig.client.screen.CMIGCraftingMonitorScreen;
import giselle.rs_cmig.common.RS_CMIG;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class CCraftingMonitorUpdateMessage implements CustomPacketPayload
{
	public static final ResourceLocation ID = new ResourceLocation(RS_CMIG.MODID, "crafting_monitor_update");

	private final CraftingMonitorUpdateMessage impl;

	public CCraftingMonitorUpdateMessage(CraftingMonitorUpdateMessage impl)
	{
		this.impl = impl;
	}

	public CCraftingMonitorUpdateMessage(FriendlyByteBuf buf)
	{
		this.impl = CraftingMonitorUpdateMessage.decode(buf);
	}

	@Override
	public void write(FriendlyByteBuf pBuffer)
	{
		this.impl.write(pBuffer);
	}

	public static void handle(CCraftingMonitorUpdateMessage message, PlayPayloadContext ctx)
	{
		ctx.workHandler().submitAsync(() ->
		{
			BaseScreen.executeLater(CMIGCraftingMonitorScreen.class, screen -> screen.setTasks(message.getImpl().getTasks()));
		});
	}

	public CraftingMonitorUpdateMessage getImpl()
	{
		return this.impl;
	}

	@Override
	public ResourceLocation id()
	{
		return ID;
	}

}
