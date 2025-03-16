package fr.draconiummc.draconiummod.entity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBow;
import net.minecraft.util.DamageSource;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;


public class EntityGrenade extends EntityThrowable
{

    public EntityGrenade(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    public EntityGrenade(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }
    public static void registerFixesGrenade(DataFixer fixer)
    {
        EntityThrowable.registerFixesThrowable(fixer, "grenade");
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        if (result.entityHit != null)
        {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 5.0F);
        }


        if (!this.world.isRemote)
        {

            this.world.newExplosion(this, this.posX, this.posY, this.posZ, 4.0F, false, true);


            int radius = 4;
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos pos = new BlockPos(this.posX + x, this.posY + y, this.posZ + z);

                        IBlockState blockState = world.getBlockState(pos);
                        Block block = blockState.getBlock();

                        if (block == Blocks.OBSIDIAN) {

                            if (this.rand.nextFloat() < 0.5f) {
                                world.setBlockToAir(pos);
                            }
                        }
                    }
                }
            }

            this.setDead();
        }
    }
}

