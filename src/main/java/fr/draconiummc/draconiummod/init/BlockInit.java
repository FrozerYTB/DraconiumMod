package fr.draconiummc.draconiummod.init;

import fr.draconiummc.draconiummod.objects.blocks.BlockRandomOre;
import fr.draconiummc.draconiummod.objects.blocks.BlockRessources;
import fr.draconiummc.draconiummod.objects.blocks.CaveBlock;
import fr.draconiummc.draconiummod.objects.blocks.Elevator;
import fr.draconiummc.draconiummod.objects.tools.ItemPickaxeMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class BlockInit
{
    public static final List<Block> BLOCKS = new ArrayList<Block>();
    //BLOCS DE MINERAIS
    public static final Block PYRONITE_ORE = new BlockRessources("pyronite_ore", Material.ROCK, "pickaxe",3);

    public static final Block DRACONIUM_ORE = new BlockRessources("draconium_ore", Material.ROCK, "pickaxe",4);

    public static final Block NOXIUM_ORE = new BlockRessources("noxium_ore", Material.ROCK, "pickaxe",5);

    public static final Block RANDOM_ORE = new BlockRandomOre("random_ore", Material.ROCK);

    public static final Block DRACONIUM_BLOCK = new BlockRessources("draconium_block", Material.IRON, "pickaxe",2);

    public static final Block PYRONITE_BLOCK = new BlockRessources("pyronite_block", Material.IRON, "pickaxe",2);



    //BLOCS AUTRES
    public static final Block ELEVATOR = new Elevator("elevator", Material.IRON);
    public static final Block CAVE_BLOCK = new CaveBlock("cave_block", Material.GLASS);

}

