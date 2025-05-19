package fr.draconiummc.draconiummod.utils.handlers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class GameplayHandler {
    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            float armor = player.getTotalArmorValue();

            // Nouvelle formule de réduction : logarithmique
            float reduction = (float)(1.0 - Math.log(armor + 1) / 5.0);
            event.setAmount(event.getAmount() * reduction);
        }
    }
    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (!event.getEntity().world.isRemote && event.getEntity() instanceof EntityMob) {
            int xpToDrop = MathHelper.clamp((int)(event.getEntityLiving().getMaxHealth() / 2), 3, 30);
            ((WorldServer)event.getEntity().world).spawnEntity(new EntityXPOrb(event.getEntity().world,
                    event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, xpToDrop));
        }
    }

    @SubscribeEvent
    public void onLivingAttacked(LivingAttackEvent event) {
        if (event.getEntityLiving() instanceof EntityMob) {
            List<EntityMob> allies = event.getEntity().world.getEntitiesWithinAABB(EntityMob.class, event.getEntity().getEntityBoundingBox().grow(10));
            for (EntityMob ally : allies) {
                ally.setAttackTarget(event.getSource().getTrueSource() instanceof EntityLivingBase ? (EntityLivingBase)event.getSource().getTrueSource() : null);
            }
        }
    }

}
