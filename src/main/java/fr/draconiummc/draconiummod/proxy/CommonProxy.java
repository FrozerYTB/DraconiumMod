package fr.draconiummc.draconiummod.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class CommonProxy {

    public void registerItemRenderer(Item item, int meta, String id) { }
    public void registerModels() { }
    public void registerEntityRenderers() { }

    public RenderItem getItemRenderer() {
        return Minecraft.getMinecraft().getRenderItem();
    }

    protected void onClientSetup(FMLInitializationEvent event) {
    }

    public void init(FMLInitializationEvent event) {
        // Rien ici côté serveur
    }
}