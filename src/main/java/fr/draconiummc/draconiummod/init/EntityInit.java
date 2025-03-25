package fr.draconiummc.draconiummod.init;

import fr.draconiummc.draconiummod.entity.EntityGrenade;
import fr.draconiummc.draconiummod.utils.Reference;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraft.util.ResourceLocation;
import fr.draconiummc.draconiummod.DraconiumMod;

public class EntityInit {
    public static void registerEntities() {
        EntityRegistry.registerModEntity(new ResourceLocation("draconiummod", "grenade"), EntityGrenade.class, "Grenade", 1, DraconiumMod.instance, 64, 10, true);
    }
}
