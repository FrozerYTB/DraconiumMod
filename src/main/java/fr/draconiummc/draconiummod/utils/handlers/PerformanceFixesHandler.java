package fr.draconiummc.draconiummod.utils.handlers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class PerformanceFixesHandler {

    // 🔧 1. Réduction du lag dû à l'eau et la redstone
    @SubscribeEvent
    public void onNeighborNotify(BlockEvent.NeighborNotifyEvent event) {
        IBlockState state = event.getState();
        if (state.getBlock() == Blocks.WATER || state.getBlock() == Blocks.FLOWING_WATER ||
                state.getBlock() == Blocks.REDSTONE_WIRE) {
            event.setCanceled(true);
        }
    }

    // 🧠 2. Suppression des entités hostiles avec IA cassée (surcharge de tick)
    @SubscribeEvent
    public void onEntityJoinWorld_AI(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (!event.getWorld().isRemote && entity instanceof IMob) {
            if (!entity.isNonBoss() && !entity.isEntityAlive()) {
                event.setCanceled(true);
            }
        }
    }

    // 🐔 3. Limitation des entités passives par zone
    @SubscribeEvent
    public void onEntityJoinWorld_LimitPassive(EntityJoinWorldEvent event) {
        if (!event.getWorld().isRemote) {
            Entity entity = event.getEntity();
            if (entity instanceof EntityAnimal) {
                World world = event.getWorld();
                List<Entity> nearby = world.getEntitiesWithinAABB(entity.getClass(), entity.getEntityBoundingBox().grow(8));
                if (nearby.size() > 20) {
                    event.setCanceled(true);
                }
            }
        }
    }

    // 💥 4. Annulation des explosions trop puissantes
    @SubscribeEvent
    public void onExplosionStart(ExplosionEvent.Start event) {
        Explosion explosion = event.getExplosion();

        // Si trop de blocs affectés, on annule l'explosion
        if (!event.getWorld().isRemote && explosion.getAffectedBlockPositions().size() > 100) {
            event.setCanceled(true);
        }
    }

    // 💨 5. Suppression des items au sol en excès
    @SubscribeEvent
    public void onItemDrop(EntityJoinWorldEvent event) {
        if (!event.getWorld().isRemote && event.getEntity() instanceof EntityItem) {
            World world = event.getWorld();
            int itemCount = world.getEntitiesWithinAABB(EntityItem.class, event.getEntity().getEntityBoundingBox().grow(4)).size();
            if (itemCount > 30) {
                event.setCanceled(true);
            }
        }
    }
}
