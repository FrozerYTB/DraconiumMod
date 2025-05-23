package fr.draconiummc.draconiummod.utils.handlers;

import fr.draconiummc.draconiummod.init.BlockInit;
import fr.draconiummc.draconiummod.init.ItemInit;
import fr.draconiummc.draconiummod.utils.IHasModel;
import fr.draconiummc.draconiummod.world.WorldGenCustomOres;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class RegistryHandler {

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        for (Item item : ItemInit.ITEMS) {
            if (item instanceof IHasModel) {
                ((IHasModel) item).registerModels();
            }
        }

        for(Block block :BlockInit.BLOCKS) {
            if (block instanceof IHasModel) {
                ((IHasModel) block).registerModels();
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent e)
    {
        EntityPlayer player = e.player;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(MathHelper.floor(player.posX), MathHelper.floor(player.posY - 1), MathHelper.floor(player.posZ));
        if(e.player.world.getBlockState(pos).getBlock() == BlockInit.ELEVATOR)
        {
            if(player.isSneaking())
            {
                for(int i = (int)(player.posY - 2); i > 0; i--)
                {
                    pos.setPos(player.posX, i, player.posZ);
                    if(e.player.world.getBlockState(pos).getBlock() == BlockInit.ELEVATOR)
                        player.setPosition(player.posX, i + 1, player.posZ);
                }
            }
        }
    }

    public static double getElevatorJump(EntityPlayer player)
    {
        int u = 0;

        for(int i = (int)player.posY + 1; i < 256; i++)
        {

            BlockPos pos = new BlockPos(player.posX, i, player.posZ);

            if(player.world.getBlockState(pos).getBlock() == BlockInit.ELEVATOR)
            {
                u = i;
                return i;
            }
        }
        return u;
    }

    @SubscribeEvent
    public static void playerJumpingEvent(LivingEvent.LivingJumpEvent e)
    {
        if(e.getEntityLiving() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer)e.getEntityLiving();

            if(player.world.getBlockState(new BlockPos(player.posX, player.posY - 1, player.posZ)).getBlock() == BlockInit.ELEVATOR)
            {
                player.setPosition(player.posX, getElevatorJump(player) + 1, player.posZ);
            }
        }
    }
}
