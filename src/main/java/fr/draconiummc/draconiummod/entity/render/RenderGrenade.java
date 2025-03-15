package fr.draconiummc.draconiummod.entity.render;

import fr.draconiummc.draconiummod.entity.EntityGrenade;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Items;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderGrenade extends RenderSnowball<EntityGrenade> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("draconiummod:textures/entity/grenade.png");

    public RenderGrenade(RenderManager renderManager) {
        super(renderManager, Items.SNOWBALL, Minecraft.getMinecraft().getRenderItem());
    }

    public static void register() {
        RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, RenderGrenade::new);
    }
}
