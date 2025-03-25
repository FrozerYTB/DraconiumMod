package fr.draconiummc.draconiummod.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;


public class EntityGrenade extends EntityThrowable {

    public EntityGrenade(World worldIn) {
        super(worldIn);
    }

    public EntityGrenade(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    public EntityGrenade(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.entityHit != null) {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 5.0F);
        }

        if (!this.world.isRemote) {
            this.world.newExplosion(this, this.posX, this.posY, this.posZ, 4.0F, false, true);
            this.setDead();
        }
    }
    public static EntityEntry createEntry() {
        return EntityEntryBuilder.create()
                .entity(EntityGrenade.class)
                .id("grenade", 1)
                .name("grenade")
                .tracker(64, 1, true)
                .build();
    }
}