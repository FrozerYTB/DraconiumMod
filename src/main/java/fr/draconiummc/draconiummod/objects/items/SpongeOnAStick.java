package fr.draconiummc.draconiummod.objects.items;

import fr.draconiummc.draconiummod.init.CreativeTabInit;
import fr.draconiummc.draconiummod.init.ItemInit;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpongeOnAStick extends ItemBase {

    public SpongeOnAStick(String name)
    {
        super(name);
        this.setMaxStackSize(1);
        this.setMaxDamage(16);

    }

    public static final int spongeStickRange = 3;

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        return soakUp(world, pos, player, stack) ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        boolean result = soakUp(world, player.getPosition(), player, stack);
        return new ActionResult<>(result ? EnumActionResult.SUCCESS : EnumActionResult.FAIL, stack);
    }

    private static boolean soakUp(World world, BlockPos pos, EntityPlayer player, ItemStack stack) {
        boolean absorbed = false;
        boolean hitLava = false;
        int damage = stack.getItemDamage();

        int range = spongeStickRange;
        boolean isUnderwater = isPlayerUnderwater(world, player);

        int blockUpdateFlag = isUnderwater ? 3 : 2; // Mise à jour des blocs SEULEMENT si sous l'eau

        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos targetPos = pos.add(x, y, z);
                    Material material = world.getBlockState(targetPos).getMaterial();

                    if (material.isLiquid()) {
                        absorbed = true;
                        hitLava |= (material == Material.LAVA);
                        world.setBlockState(targetPos, Blocks.AIR.getDefaultState(), blockUpdateFlag);
                    }
                }
            }
        }

        if (hitLava) {
            stack.setCount(0);
            player.setFire(6);
        }

        if (absorbed) {
            stack.damageItem(1, player);
            return true;
        }
        return false;
    }

    private static boolean isPlayerUnderwater(World world, EntityPlayer player) {
        BlockPos headPos = new BlockPos(player.posX, player.posY + 1, player.posZ);
        return world.getBlockState(headPos).getMaterial() == Material.WATER;
    }
}
