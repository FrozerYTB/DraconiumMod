package fr.draconiummc.draconiummod.entity.render;

import fr.draconiummc.draconiummod.entity.EntityGrenade;
import fr.draconiummc.draconiummod.init.ItemInit;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.init.Items;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import fr.draconiummc.draconiummod.entity.EntityGrenade;
import fr.draconiummc.draconiummod.init.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGrenade extends RenderSnowball<EntityGrenade> {

    public RenderGrenade(RenderManager renderManager) {
        super(renderManager, ItemInit.GRENADE, Minecraft.getMinecraft().getRenderItem());

        System.out.println("[DEBUG] RenderGrenade chargé !");
    }

    public static void register() {
        RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, manager -> {
            System.out.println("[DEBUG] Rendu enregistré pour EntityGrenade !");
            return new RenderGrenade(manager);
        });
    }

    public static final IRenderFactory<EntityGrenade> FACTORY = new IRenderFactory<EntityGrenade>() {
        @Override
        public RenderGrenade createRenderFor(RenderManager manager) {
            return new RenderGrenade(manager);
        }
    };
}