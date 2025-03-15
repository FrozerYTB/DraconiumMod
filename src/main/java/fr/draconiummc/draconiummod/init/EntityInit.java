package fr.draconiummc.draconiummod.init;

import fr.draconiummc.draconiummod.entity.EntityGrenade;
import fr.draconiummc.draconiummod.utils.Reference;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraft.util.ResourceLocation;
import fr.draconiummc.draconiummod.DraconiumMod;

public class EntityInit {
    public static void registerEntities() {
        EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, "grenade"), EntityGrenade.class, "Grenade", 120, DraconiumMod.instance, 64, 1, true);
    }
}
