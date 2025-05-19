package fr.draconiummc.draconiummod.client.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientEventHandler {
    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution res = new ScaledResolution(mc);

        mc.getTextureManager().bindTexture(new ResourceLocation("draconiummod:textures/gui/energy_bar.png"));
        // Dessine la barre d’énergie par ex.
        Gui.drawModalRectWithCustomSizedTexture(res.getScaledWidth() - 110, res.getScaledHeight() - 50, 0, 0, 100, 10, 100, 10);
    }

}
