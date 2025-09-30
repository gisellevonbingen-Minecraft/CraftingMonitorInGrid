package giselle.rs_cmig.common.network;

import com.refinedmods.refinedstorage.common.Platform;
import com.refinedmods.refinedstorage.common.autocrafting.monitor.AutocraftingMonitorBlockEntity;

import giselle.rs_cmig.common.LevelBlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SAutocraftingMonitorOpenRequestMessage extends AbstractMessage
{
	public static final MessageBuilder<SAutocraftingMonitorOpenRequestMessage> BUILDER = new MessageBuilder<>("crafting_monitor_open_request", SAutocraftingMonitorOpenRequestMessage::new);

	private final LevelBlockPos autocraftingMonitor;

	public SAutocraftingMonitorOpenRequestMessage(LevelBlockPos autocraftingMonitor)
	{
		this.autocraftingMonitor = autocraftingMonitor;
	}

	public SAutocraftingMonitorOpenRequestMessage(RegistryFriendlyByteBuf buf)
	{
		super(buf);

		this.autocraftingMonitor = LevelBlockPos.decode(buf);
	}

	@Override
	public void encode(RegistryFriendlyByteBuf buf)
	{
		super.encode(buf);

		LevelBlockPos.encode(buf, this.autocraftingMonitor);
	}

	@Override
	public void handle(IPayloadContext ctx)
	{
		super.handle(ctx);

		if (ctx.player() instanceof ServerPlayer player)
		{
			if (this.getAutocraftingMonitor().blockEntity(player.server) instanceof AutocraftingMonitorBlockEntity autocraftingMonitor)
			{
		        Platform.INSTANCE.getMenuOpener().openMenu(player, autocraftingMonitor);
			}

		}

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

}
