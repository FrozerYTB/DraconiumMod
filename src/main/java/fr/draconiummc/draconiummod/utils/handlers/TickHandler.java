package fr.draconiummc.draconiummod.utils.handlers;

import fr.draconiummc.draconiummod.client.gui.MainMenuGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickHandler {

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        // Vérifiez que le jeu est dans le menu principal
        if (event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu) {
            // Remplacez le menu principal par votre menu personnalisé
            Minecraft.getMinecraft().displayGuiScreen(new MainMenuGui());
        }
    }
}
