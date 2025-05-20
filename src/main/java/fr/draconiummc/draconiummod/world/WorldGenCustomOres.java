package fr.draconiummc.draconiummod.world;

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

import java.util.Random;

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
        if (!ModConfig.enableOreGeneration) {
            return;
        }
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
        if (world == null) {
            System.err.println("[CRASH] Le monde est null, impossible de générer !");
            return;
        }
        try {
            if (minHeight > maxHeight || minHeight < 0 || maxHeight > 256) {
                System.err.println("[ERREUR] Minerai généré hors des limites ! minHeight: " + minHeight + ", maxHeight: " + maxHeight);
                return;
            }

            int heightDiff = maxHeight - minHeight + 1;
            int maxAttempts = chance * 5; // Empêcher une boucle infinie

            int generated = 0;
            int attempts = 0;

            while (generated < chance && attempts < maxAttempts) {
                int x = chunkX * 16 + rand.nextInt(16);
                int y = minHeight + rand.nextInt(heightDiff);
                int z = chunkZ * 16 + rand.nextInt(16);
                BlockPos pos = new BlockPos(x, y, z);

                // Vérifie que le chunk est chargé
                if (!world.isBlockLoaded(pos)) {
                    System.out.println("[DEBUG] Chunk non chargé, tentative ignorée.");
                    attempts++;
                    continue;
                }

                // Si on veut générer près d'un lac de lave
                if (isNearLava) {
                    if(isLiquid(world.getBlockState(pos).getBlock())) {
                        if (isNearLava(world, pos)) {
//                        System.out.println("[INFO] Génération de minerai près de la lave en " + pos);
                        generateNearLava(world, rand, pos, gen);
                        generated++;
                    }} else {
//                        System.out.println("[DEBUG] Pas de lave trouvée près de " + pos);
                    }
                } else {
                    // Vérifie que le bloc à la position est valide avant de générer
                    if (world.getBlockState(pos).getBlock() == Blocks.STONE || world.getBlockState(pos).getBlock() == Blocks.AIR) {
                        gen.generate(world, rand, pos);
                        generated++;
                    } else {
                        System.out.println("[DEBUG] Position invalide pour génération à " + pos);
                    }
                }
                attempts++;
            }

            System.out.println("[INFO] Génération terminée : " + generated + " minerais générés sur " + chance);
        } catch (Exception e) {
            System.err.println("[CRASH] Erreur dans runGenerator: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isNearLava(World world, BlockPos pos) {
        // Vérifie si la position est proche d'un liquide (lave ou eau) dans un rayon de 3 blocs.
        for (int dx = -3; dx <= 3; dx++) {
            for (int dy = -3; dy <= 3; dy++) {
                for (int dz = -3; dz <= 3; dz++) {
                    BlockPos checkPos = pos.add(dx, dy, dz);
                    if (isLiquid(world.getBlockState(checkPos).getBlock())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isLiquid(Block block) {
        return block == Blocks.LAVA || block == Blocks.FLOWING_LAVA || block == Blocks.WATER || block == Blocks.FLOWING_WATER;
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
            // Assurez-vous que le minerai ne se génère pas sur de la lave
            if (world.getBlockState(generatePos).getBlock() == Blocks.STONE || world.getBlockState(generatePos).getBlock() == Blocks.AIR) {
                gen.generate(world, rand, generatePos);
                System.out.println("[INFO] Minerai généré à " + generatePos);
                break;
            } else {
                System.out.println("[DEBUG] Lave détectée, tentative ignorée à " + generatePos);
            }
        }
    }
}
