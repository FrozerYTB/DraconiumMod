package fr.draconiummc.draconiummod.utils.handlers;

import fr.draconiummc.draconiummod.DraconiumMod;
import fr.draconiummc.draconiummod.entity.EntityGrenade;
import fr.draconiummc.draconiummod.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderHandler {

    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, new IRenderFactory<EntityGrenade>() {
            @Override
            public Render<? super EntityGrenade> createRenderFor(RenderManager manager) {
                return new RenderSnowball<>(manager, ItemInit.GRENADE, Minecraft.getMinecraft().getRenderItem());
            }
        });
    }
}


