package fr.draconiummc.draconiummod.world;

import fr.draconiummc.draconiummod.init.BlockInit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.*;

public class WorldGenCustomOres implements IWorldGenerator {

    private final List<OreGenParams> overworldOres = new ArrayList<>();
    private final List<OreGenParams> netherOres    = new ArrayList<>();
    private final List<OreGenParams> endOres       = new ArrayList<>();

    public WorldGenCustomOres() {
        // Overworld ores
        overworldOres.add(new OreGenParams("Pyronite", 3, 15, 1, 6, 8, BlockInit.PYRONITE_ORE.getDefaultState(), Blocks.STONE));
        overworldOres.add(new OreGenParams("Draconium", 3, 10, 1, 5, 5, BlockInit.DRACONIUM_ORE.getDefaultState(), Blocks.STONE));
        overworldOres.add(new OreGenParams("Noxium", 3, 8, 1, 2, 4, BlockInit.NOXIUM_ORE.getDefaultState(), Blocks.STONE));
        overworldOres.add(new OreGenParams("RandomOre", 3, 30, 1, 3, 6, BlockInit.RANDOM_ORE.getDefaultState(), Blocks.STONE));

        // Example Nether ore (non activé pour l’instant)
        // netherOres.add(new OreGenParams("Infernium", 10, 120, 3, 8, 10, BlockInit.INFERNIUM_ORE.getDefaultState(), Blocks.NETHERRACK));

        // Example End ore (non activé)
        // endOres.add(new OreGenParams("Enderite", 0, 80, 2, 5, 6, BlockInit.ENDERITE_ORE.getDefaultState(), Blocks.END_STONE));
    }

    @Override
    public void generate(Random rand, int chunkX, int chunkZ,
                         World world, IChunkGenerator chunkGen, IChunkProvider chunkProvider) {

        int dim = world.provider.getDimension();

        switch (dim) {
            case 0: // Overworld
                overworldOres.forEach(ore -> runGenerator(ore, world, rand, chunkX, chunkZ));
                break;
            case -1: // Nether
                netherOres.forEach(ore -> runGenerator(ore, world, rand, chunkX, chunkZ));
                break;
            case 1: // End
                endOres.forEach(ore -> runGenerator(ore, world, rand, chunkX, chunkZ));
                break;
            default:
                break;
        }
    }

    private void runGenerator(OreGenParams ore, World world, Random rand, int chunkX, int chunkZ) {

        if (ore.oreBlock == null) {
            System.err.println("[ERREUR] Bloc null pour " + ore.name);
            return;
        }
        if (ore.minHeight > ore.maxHeight || ore.minHeight < 0 || ore.maxHeight > 256) {
            System.err.println("[ERREUR] Hauteurs invalides pour " + ore.name);
            return;
        }

        int heightDiff = ore.maxHeight - ore.minHeight + 1;

        for (int v = 0; v < ore.veinCount; v++) {
            int x = chunkX * 16 + rand.nextInt(16);
            int y = ore.minHeight + rand.nextInt(heightDiff);
            int z = chunkZ * 16 + rand.nextInt(16);
            BlockPos pos = new BlockPos(x, y, z);

            // Optionnel : filtrer par biome
            // Biome biome = world.getBiome(pos);
            // if (!ore.allowedBiomes.isEmpty() && !ore.allowedBiomes.contains(biome.getRegistryName().toString())) {
            //     continue;
            // }

            int veinSize = ore.minVeinSize + rand.nextInt(ore.maxVeinSize - ore.minVeinSize + 1);
            WorldGenerator gen = new WorldGenMinable(ore.oreBlock, veinSize, BlockMatcher.forBlock(ore.targetBlock));

            gen.generate(world, rand, pos);

            // Comptage rapide autour du point d’origine
            int placed = 0;
            for (int dx = -4; dx <= 4; dx++)
                for (int dy = -4; dy <= 4; dy++)
                    for (int dz = -4; dz <= 4; dz++)
                        if (world.getBlockState(pos.add(dx, dy, dz)).getBlock() == ore.oreBlock.getBlock())
                            placed++;

            double ratio = placed / (double) veinSize;
            System.out.println("[GEN] " + ore.name +
                    " | Veine demandée : " + veinSize +
                    " | Blocs placés : " + placed +
                    " | Ratio : " + String.format("%.2f", ratio) +
                    " | Pos : " + pos + " (chunk " + chunkX + "," + chunkZ + ")");
        }
    }

    /** Contient tous les paramètres d’un minerai. */
    private static class OreGenParams {
        String name;
        int minHeight, maxHeight;
        int minVeinSize, maxVeinSize;
        int veinCount;
        IBlockState oreBlock;
        net.minecraft.block.Block targetBlock;
        List<String> allowedBiomes = new ArrayList<>(); // Pour les filtres biome si besoin

        OreGenParams(String name, int minH, int maxH,
                     int minSize, int maxSize, int count,
                     IBlockState state, net.minecraft.block.Block targetBlock) {

            this.name = name;
            this.minHeight = minH;
            this.maxHeight = maxH;
            this.minVeinSize = minSize;
            this.maxVeinSize = maxSize;
            this.veinCount = count;
            this.oreBlock = state;
            this.targetBlock = targetBlock;
        }
    }
}
