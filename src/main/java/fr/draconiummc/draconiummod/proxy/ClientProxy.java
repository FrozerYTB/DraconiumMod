package fr.draconiummc.draconiummod.proxy;

import fr.draconiummc.draconiummod.client.gui.MainMenuGui;
import fr.draconiummc.draconiummod.init.ItemInit;
import fr.draconiummc.draconiummod.entity.EntityGrenade;
import fr.draconiummc.draconiummod.entity.render.RenderGrenade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }

    @Override
    public void registerModels() {
        for (Item item : ItemInit.ITEMS) {
            registerItemRenderer(item, 0, "inventory");
        }
    }

    @Override
    public void registerEntityRenderers() {
        RenderGrenade.register();
        RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, RenderGrenade::new);
    }

    @Override
    public RenderItem getItemRenderer() {
        return Minecraft.getMinecraft().getRenderItem();
    }


    public class TickHandler {
        private boolean shown = false;

        @SubscribeEvent
        public void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.END && !shown && Minecraft.getMinecraft().player != null) {
                shown = true;
            }
        }
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onOpenGui(GuiOpenEvent event)
    {
        if(event.getGui() != null && event.getGui().getClass() == GuiMainMenu.class)
        {
            event.setGui(new MainMenuGui());
        }
    }

}