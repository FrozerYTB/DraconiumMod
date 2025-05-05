package fr.draconiummc.draconiummod.client;

import fr.draconiummc.draconiummod.client.gui.MainMenuGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Classe utilitaire client-only pour gérer l'ouverture de GUI personnalisées.
 */
@SideOnly(Side.CLIENT)
public class ClientGuiHandler {

    private static final Minecraft mc = Minecraft.getMinecraft();

    /**
     * Affiche une interface graphique personnalisée.
     * @param gui La GUI à afficher.
     */
    public static void openGui(GuiScreen gui) {
        if (mc != null && gui != null) {
            mc.addScheduledTask(() -> mc.displayGuiScreen(gui));
        }
    }

    /**
     * Ouvre le menu principal personnalisé du mod.
     */
    public static void openMainMenu() {
        openGui(new MainMenuGui());
    }

}
