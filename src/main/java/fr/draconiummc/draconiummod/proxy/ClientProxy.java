package fr.draconiummc.draconiummod.proxy;

import fr.draconiummc.draconiummod.init.ItemInit;
import fr.draconiummc.draconiummod.objects.items.ItemGrenade;
import fr.draconiummc.draconiummod.entity.EntityGrenade;
import fr.draconiummc.draconiummod.entity.render.RenderGrenade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }

    @Override
    public void registerModels() {
        for (Item item : ItemInit.ITEMS) {
            if (item instanceof ItemGrenade) {
                ((ItemGrenade) item).registerModels();
            }
        }
    }

    @Override
    public void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, RenderGrenade::new);
    }

    @Override
    public RenderItem getItemRenderer() {
        return Minecraft.getMinecraft().getRenderItem();
    }
}
