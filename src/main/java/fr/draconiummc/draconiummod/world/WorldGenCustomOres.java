package fr.draconiummc.draconiummod.world;

import java.util.Random;

import fr.draconiummc.draconiummod.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenCustomOres implements IWorldGenerator {
    private WorldGenerator pyronite_ore, draconium_ore, noxium_ore, random_ore;

    public WorldGenCustomOres() {
        pyronite_ore = new WorldGenMinable(BlockInit.PYRONITE_ORE.getDefaultState(), 5, BlockMatcher.forBlock(Blocks.STONE));
        draconium_ore = new WorldGenMinable(BlockInit.DRACONIUM_ORE.getDefaultState(), 4, BlockMatcher.forBlock(Blocks.STONE));
        noxium_ore = new WorldGenMinable(BlockInit.NOXIUM_ORE.getDefaultState(), 2, BlockMatcher.forBlock(Blocks.STONE));
        random_ore = new WorldGenMinable(BlockInit.RANDOM_ORE.getDefaultState(), 1, BlockMatcher.forBlock(Blocks.STONE));
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()) {
            case 0:
                // Générer pyronite près des lacs de lave (dans, au-dessus ou autour)
                runGenerator(pyronite_ore, world, random, chunkX, chunkZ, 13, 0, 15, true); // Pyronite proche des lacs de lave
                runGenerator(draconium_ore, world, random, chunkX, chunkZ, 8, 0, 10, false);
                runGenerator(noxium_ore, world, random, chunkX, chunkZ, 2, 0, 5, false);
                runGenerator(random_ore, world, random, chunkX, chunkZ, 3, 0, 5, false);
                break;
        }
    }

    private void runGenerator(WorldGenerator gen, World world, Random rand, int chunkX, int chunkZ, int chance, int minHeight, int maxHeight, boolean isNearLava) {
        if (minHeight > maxHeight || minHeight < 0 || maxHeight > 256)
            throw new IllegalArgumentException("Minerai généré hors limites");

        int heightDiff = maxHeight - minHeight + 1;

        for (int i = 0; i < chance; i++) {
            int x = chunkX * 16 + rand.nextInt(16);
            int y = minHeight + rand.nextInt(heightDiff);
            int z = chunkZ * 16 + rand.nextInt(16);

            // Si on veut générer près d'un lac de lave
            if (isNearLava) {
                BlockPos pos = new BlockPos(x, y, z);
                if (isNearLava(world, pos)) {
                    // Générer le minerai dans, au-dessus ou autour de la lave
                    generateNearLava(world, rand, pos, gen);
                }
            } else {
                gen.generate(world, rand, new BlockPos(x, y, z));
            }
        }
    }

    private boolean isNearLava(World world, BlockPos pos) {
        // Vérifie si la position est près d'un lac de lave (environ 5 blocs de distance)
        for (int dx = -5; dx <= 5; dx++) {
            for (int dy = -5; dy <= 5; dy++) {
                for (int dz = -5; dz <= 5; dz++) {
                    BlockPos checkPos = pos.add(dx, dy, dz);
                    if (world.getBlockState(checkPos).getBlock() == Blocks.LAVA) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void generateNearLava(World world, Random rand, BlockPos pos, WorldGenerator gen) {
        // Vérifie plusieurs niveaux autour du lac de lave pour placer le minerai
        BlockPos[] possiblePositions = {
                pos, // Générer directement dans la lave
                pos.up(), // Générer juste au-dessus de la lave
                pos.down(), // Générer juste en dessous de la lave
                pos.add(rand.nextInt(3) - 1, 0, rand.nextInt(3) - 1) // Générer autour, dans un rayon de 1 bloc
        };

        // Choisit une position valide parmi les options
        for (BlockPos generatePos : possiblePositions) {
            if (world.getBlockState(generatePos).getBlock() == Blocks.STONE || world.getBlockState(generatePos).getBlock() == Blocks.LAVA) {
                gen.generate(world, rand, generatePos);
                break;
            }
        }
    }
}
