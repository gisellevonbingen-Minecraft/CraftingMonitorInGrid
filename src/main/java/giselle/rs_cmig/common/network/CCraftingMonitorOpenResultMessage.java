package giselle.rs_cmig.common.network;

import giselle.rs_cmig.client.RS_CMIGClient;
import giselle.rs_cmig.common.LevelBlockPos;
import giselle.rs_cmig.common.RS_CMIG;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class CCraftingMonitorOpenResultMessage extends NetworkMessage
{
	public static final ResourceLocation ID = new ResourceLocation(RS_CMIG.MODID, "crafting_monitor_open_result");

	private final Component displayName;

	public CCraftingMonitorOpenResultMessage(LevelBlockPos networkPos, Component displayName)
	{
		super(networkPos);
		this.displayName = displayName;
	}

	public CCraftingMonitorOpenResultMessage(FriendlyByteBuf buf)
	{
		super(buf);
		this.displayName = buf.readComponent();
	}

	@Override
	public void write(FriendlyByteBuf buf)
	{
		super.write(buf);
		buf.writeComponent(this.getDisplayName());
	}

	public static void handle(CCraftingMonitorOpenResultMessage message, PlayPayloadContext ctx)
	{
		ctx.workHandler().submitAsync(() ->
		{
			RS_CMIGClient.openScreen(message);
		});
	}

	public Component getDisplayName()
	{
		return this.displayName;
	}

	@Override
	public ResourceLocation id()
	{
		return ID;
	}

}
