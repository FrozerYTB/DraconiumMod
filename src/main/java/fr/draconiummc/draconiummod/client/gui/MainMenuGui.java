package fr.draconiummc.draconiummod.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.GuiModList;

public class MainMenuGui extends GuiScreen {
    private static final String ADMIN_USERNAME = "FrozerYTB";
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("custommainmenu", "textures/gui/background.png");

    @Override
    public void initGui() {
        // Initialisation de l'interface utilisateur
        this.buttonList.clear();

        int centerX = this.width / 2;
        int startY = this.height / 3;

        // Ajout des boutons centrés
        this.buttonList.add(new GuiButton(0, centerX - 100, startY + 15 , 200, 20, "Jouer en solo (NE MARCHE PAS)"));
        this.buttonList.get(0).enabled = false;
        this.buttonList.add(new GuiButton(1, centerX - 100, startY + 40, 200, 20, "Jouer à DraconiumMC !"));
        this.buttonList.add(new GuiButton(2, centerX - 100, startY + 65, 200, 20, "Voir les mods de DraconiumMC"));
        this.buttonList.add(new GuiButton(3, centerX - 100, startY + 90, 98, 20, "Options"));
        this.buttonList.add(new GuiButton(4, centerX + 2, startY + 90, 98, 20, "Quitter le jeu"));

        // Vérifiez si l'utilisateur est l'administrateur
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (player != null && player.getName().equals(ADMIN_USERNAME)) {
            this.buttonList.add(new GuiButton(6, centerX - 100, startY + 144, 200, 20, "Edit Menu"));
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                // Le bouton est désactivé, donc cette action ne sera pas exécutée
                break;
            case 1:
                // Action pour jouer en multijoueur
                connectToServer("65.21.131.103", 25503);
                break;
            case 2:
                // Action pour voir les mods
                Minecraft.getMinecraft().displayGuiScreen(new GuiModList(this));
                break;
            case 3:
                // Action pour ouvrir les options
                Minecraft.getMinecraft().displayGuiScreen(new GuiOptions(this, Minecraft.getMinecraft().gameSettings));
                break;
            case 4:
                // Action pour quitter le jeu
                Minecraft.getMinecraft().shutdown();
                break;
            case 5:
                // Action pour ouvrir les langues
                Minecraft.getMinecraft().displayGuiScreen(new GuiLanguage(this, Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().getLanguageManager()));
                break;
            case 6:
                // Action pour éditer le menu
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Editing menu..."));
                break;
        }
    }

    private void connectToServer(String ip, int port) {
        try {
            ServerData serverData = new ServerData("DraconiumMC", ip + ":" + port, false);
            Minecraft.getMinecraft().displayGuiScreen(new GuiConnecting(this, Minecraft.getMinecraft(), serverData));
        } catch (Exception e) {
            // Log l'erreur dans la console
            System.err.println("[DraconiumMC] Erreur lors de la tentative de connexion : " + e.getMessage());
            e.printStackTrace();

            // Affiche un écran d'erreur personnalisé
            Minecraft.getMinecraft().displayGuiScreen(new GuiDisconnected(
                    this,
                    "Connexion échouée",
                    new TextComponentString("Impossible de se connecter au serveur DraconiumMC.\nErreur : " + e.getMessage())
            ));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Dessiner l'arrière-plan
        Minecraft.getMinecraft().getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        this.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);

        // Dessiner le texte en haut de l'écran
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        this.drawCenteredString(this.fontRenderer, "Bienvenue sur DraconiumMC !", this.width / 4, 10, 0xFFFFFF);
        GlStateManager.popMatrix();

        // Dessiner les boutons et autres éléments
        super.drawScreen(mouseX, mouseY, partialTicks);
        GuiButton soloButton = this.buttonList.get(0); // bouton id 0 = Jouer en solo

        if (!soloButton.enabled && isMouseOverButton(soloButton, mouseX, mouseY)) {
            this.drawHoveringText("🛈 Le mode solo est désactivé sur DraconiumMC.", mouseX, mouseY);
        }
    }

    private boolean isMouseOverButton(GuiButton button, int mouseX, int mouseY) {
        return mouseX >= button.x && mouseX <= button.x + button.width &&
                mouseY >= button.y && mouseY <= button.y + button.height;
    }
}
