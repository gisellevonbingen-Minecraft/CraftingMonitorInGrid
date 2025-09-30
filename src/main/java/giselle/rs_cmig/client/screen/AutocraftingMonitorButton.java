package giselle.rs_cmig.client.screen;

import java.util.List;
import java.util.function.Supplier;

import com.refinedmods.refinedstorage.common.content.Blocks;
import com.refinedmods.refinedstorage.common.support.widget.AbstractSideButtonWidget;
import com.refinedmods.refinedstorage.common.util.IdentifierUtil;

import giselle.rs_cmig.common.LevelBlockPos;
import giselle.rs_cmig.common.RS_CMIG;
import giselle.rs_cmig.common.network.SAutocraftingMonitorOpenRequestMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

public class AutocraftingMonitorButton extends AbstractSideButtonWidget
{
	private final Supplier<LevelBlockPos> autocraftingMonitor;

	private static final MutableComponent TITLE = Blocks.INSTANCE.getAutocraftingMonitor().get(Blocks.COLOR).getName();
	private static final List<MutableComponent> SUBTEXT_EQUAL = List.of(IdentifierUtil.createTranslation("gui", "detector.mode.equal").withStyle(ChatFormatting.GRAY));
	private static final ResourceLocation TEXTURE_OFF = RS_CMIG.rl("widget/side_button/grid/autocrafting_monitor/off");
	private static final ResourceLocation TEXTURE_ON = RS_CMIG.rl("widget/side_button/grid/autocrafting_monitor/on");

	public AutocraftingMonitorButton(Supplier<LevelBlockPos> autocraftingMonitorSupplier)
	{
		super(b ->
		{
			LevelBlockPos autocraftingMonitor = autocraftingMonitorSupplier.get();

			if (autocraftingMonitor != null)
			{
				PacketDistributor.sendToServer(new SAutocraftingMonitorOpenRequestMessage(autocraftingMonitor));
			}

		});
		this.autocraftingMonitor = autocraftingMonitorSupplier;
		this.active = autocraftingMonitorSupplier.get() != null;
	}

	@Override
	public void renderWidget(GuiGraphics arg0, int arg1, int arg2, float arg3)
	{
		this.active = autocraftingMonitor.get() != null;

		super.renderWidget(arg0, arg1, arg2, arg3);
	}

	@Override
	protected ResourceLocation getSprite()
	{
		return this.active ? TEXTURE_ON : TEXTURE_OFF;
	}

	@Override
	protected MutableComponent getTitle()
	{
		return TITLE;
	}

	@Override
	protected List<MutableComponent> getSubText()
	{
		return SUBTEXT_EQUAL;
	}

}
