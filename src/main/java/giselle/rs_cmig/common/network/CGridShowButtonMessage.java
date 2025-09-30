package giselle.rs_cmig.common.network;

import com.refinedmods.refinedstorage.common.grid.screen.AbstractGridScreen;

import giselle.rs_cmig.client.IGridScreenExtension;
import giselle.rs_cmig.common.LevelBlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class CGridShowButtonMessage extends AbstractMessage
{
	public static final MessageBuilder<CGridShowButtonMessage> BUILDER = new MessageBuilder<>("grid_show_button", CGridShowButtonMessage::new);

	private final LevelBlockPos autocraftingMonitor;
	private final int containerId;

	public CGridShowButtonMessage(LevelBlockPos autocraftingMonitor, int containerId)
	{
		this.autocraftingMonitor = autocraftingMonitor;
		this.containerId = containerId;
	}

	public CGridShowButtonMessage(RegistryFriendlyByteBuf buf)
	{
		super(buf);

		this.autocraftingMonitor = LevelBlockPos.decode(buf);
		this.containerId = buf.readInt();
	}

	@Override
	public void encode(RegistryFriendlyByteBuf buf)
	{
		super.encode(buf);

		LevelBlockPos.encode(buf, this.autocraftingMonitor);
		buf.writeInt(this.containerId);
	}

	@Override
	public void handle(IPayloadContext ctx)
	{
		super.handle(ctx);
		ctx.enqueueWork(() ->
		{
			Minecraft minecraft = Minecraft.getInstance();

			if (minecraft.screen instanceof AbstractGridScreen screen && screen.getMenu().containerId == this.getContainerId())
			{
				((IGridScreenExtension) screen).rs_cmig$setAutocraftingMonitor(this.getAutocraftingMonitor());
			}

		});
	}

	@Override
	public Type<? extends CustomPacketPayload> type()
	{
		return BUILDER.type();
	}

	public LevelBlockPos getAutocraftingMonitor()
	{
		return this.autocraftingMonitor;
	}

	public int getContainerId()
	{
		return this.containerId;
	}

}
