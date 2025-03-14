package fr.draconiummc.draconiummod.entity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
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
        EntityThrowable.registerFixesThrowable(fixer, "ThrownGrenade");
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        // Si la grenade touche une entité, infliger des dégâts
        if (result.entityHit != null)
        {
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 5.0F); // Dégâts de la grenade
        }

        // Créer une explosion de type TNT
        if (!this.world.isRemote)
        {
            // Explosion de type TNT (pas de feu et pas de dégâts à l'environnement, mais destruction des blocs)
            this.world.newExplosion(this, this.posX, this.posY, this.posZ, 4.0F, false, true); // Explosion TNT sans affecter le feu

            // Récupérer les blocs autour de l'explosion
            int radius = 5; // Rayon de l'explosion (ajuste selon la portée de l'effet)
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos pos = new BlockPos(this.posX + x, this.posY + y, this.posZ + z);

                        // Vérifier si le bloc est de l'obsidienne ou un autre bloc solide
                        IBlockState blockState = world.getBlockState(pos);
                        Block block = blockState.getBlock();

                        if (block == Blocks.OBSIDIAN) {

                            if (this.rand.nextFloat() < 0.6f) {
                                world.setBlockToAir(pos);
                            }
                        }
                    }
                }
            }

            this.setDead(); // Détruire l'entité après l'explosion
        }
    }
}

