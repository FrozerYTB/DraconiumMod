package fr.draconiummc.draconiummod.world;

import java.util.Random;


import fr.draconiummc.draconiummod.init.BlockInit;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenCustomOres implements IWorldGenerator
{
    private WorldGenerator pyronite_ore, draconium_ore, noxium_ore;

    public WorldGenCustomOres()
    {
        pyronite_ore = new WorldGenMinable(BlockInit.PYRONITE_ORE.getDefaultState(), 6, BlockMatcher.forBlock(Blocks.STONE));
        draconium_ore = new WorldGenMinable(BlockInit.DRACONIUM_ORE.getDefaultState(), 4, BlockMatcher.forBlock(Blocks.STONE));
        noxium_ore = new WorldGenMinable(BlockInit.NOXIUM_ORE.getDefaultState(), 15, BlockMatcher.forBlock(Blocks.STONE));
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        switch(world.provider.getDimension())
        {
            case 0:
                runGenerator(pyronite_ore, world, random, chunkX, chunkZ, 17, 0, 20);
                runGenerator(draconium_ore, world, random, chunkX, chunkZ, 12, 0, 10);
                runGenerator(noxium_ore, world, random, chunkX, chunkZ, 5, 0, 5);
                break;

        }
    }

    private void runGenerator(WorldGenerator gen, World world, Random rand, int chunkX, int chunkZ, int chance, int minHeight, int maxHeight)
    {
        if(minHeight > maxHeight || minHeight < 0 || maxHeight > 256) throw new IllegalArgumentException("Ore generated out of bounds");

        int heightDiff = maxHeight - minHeight + 1;

        for(int i = 0; i < chance; i++)
        {
            int x = chunkX * 16 + rand.nextInt(16);
            int y = minHeight + rand.nextInt(heightDiff);
            int z = chunkZ * 16 + rand.nextInt(16);

            gen.generate(world, rand, new BlockPos(x, y, z));
        }
    }
}