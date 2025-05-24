package fr.draconiummc.draconiummod.init;

import net.minecraft.advancements.Advancement;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class AdvancementsInit {
    public static final ResourceLocation BUG_HUNTER = new ResourceLocation("draconiummod", "special/bughunter");

    public static void grantBugHunter(EntityPlayerMP player) {
        Advancement advancement = player.getServer().getAdvancementManager().getAdvancement(BUG_HUNTER);
        if (advancement != null) {
            player.getAdvancements().grantCriterion(advancement, "report");
        }
    }
}

