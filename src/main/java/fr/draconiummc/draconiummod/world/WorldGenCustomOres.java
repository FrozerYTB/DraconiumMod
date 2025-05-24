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
        overworldOres.add(new OreGenParams("Draconium", 3, 10, 1, 4, 5, BlockInit.DRACONIUM_ORE.getDefaultState(), Blocks.STONE));
        overworldOres.add(new OreGenParams("Noxium", 3, 7, 1, 2, 4, BlockInit.NOXIUM_ORE.getDefaultState(), Blocks.STONE));
        overworldOres.add(new OreGenParams("RandomOre", 3, 30, 1, 1, 6, BlockInit.RANDOM_ORE.getDefaultState(), Blocks.STONE));
    }

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGen, IChunkProvider chunkProvider) {
        if (!ModConfig.enableOreGeneration) {
            return;
        }
        // Ne générez que dans l'Overworld pour éviter les problèmes
        if (world.provider.getDimension() != 0) return;

        // Ajoutez un délai aléatoire pour étaler la génération
        if (rand.nextInt(3) != 0) return;

        for (OreGenParams ore : overworldOres) {
            runGenerator(ore, world, rand, chunkX, chunkZ);
        }
    }

    private void runGenerator(OreGenParams ore, World world, Random rand, int chunkX, int chunkZ) {
        // Ajoutez cette vérification au début
        if (world.isRemote) return; // Ne pas exécuter côté client

        int heightDiff = ore.maxHeight - ore.minHeight + 1;

        for (int v = 0; v < ore.veinCount; v++) {
            int x = chunkX * 16 + rand.nextInt(16);
            int y = ore.minHeight + rand.nextInt(heightDiff);
            int z = chunkZ * 16 + rand.nextInt(16);

            BlockPos pos = new BlockPos(x, y, z);

            // Vérifiez que la position est dans le bon biome si nécessaire
            Biome biome = world.getBiome(pos);
            if (!isBiomeValid(biome)) continue;

            // Utilisez setBlockState directement au lieu de WorldGenMinable
            if (world.getBlockState(pos).getBlock() == ore.targetBlock) {
                world.setBlockState(pos, ore.oreBlock, 2); // Flag 2 pour éviter les updates inutiles
            }
        }
    }
    private boolean isBiomeValid(Biome biome) {
        return true;
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