package fr.draconiummc.draconiummod.objects.tools;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;


import com.google.common.collect.Multimap;
import com.ibm.icu.impl.BOCU;

import fr.draconiummc.draconiummod.DraconiumMod;
import fr.draconiummc.draconiummod.init.CreativeTabInit;
import fr.draconiummc.draconiummod.init.ItemInit;
import fr.draconiummc.draconiummod.utils.IHasModel;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemHammer extends ItemPickaxe implements IHasModel
{
    public ItemHammer(String name, ToolMaterial material)
    {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabInit.DRACONIUM_TOOLS);
        ItemInit.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
        DraconiumMod.proxy.registerItemRenderer(this, 0, "inventory");
    }

    public RayTraceResult rayTrace(double blockReachDistance, float partialTicks, World world, EntityLivingBase e)
    {
        Vec3d vec3d = e.getPositionEyes(partialTicks);
        Vec3d vec3d1 = e.getLook(partialTicks);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
        return world.rayTraceBlocks(vec3d, vec3d2, false, false, true);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack breaker, World w, IBlockState state, BlockPos pos, EntityLivingBase e)
    {
        if(e instanceof EntityPlayer && !w.isRemote)
        {
            EntityPlayer p = (EntityPlayer)e;
            RayTraceResult r = this.rayTrace(5.0D, 0.0f, w, e);

            if(r.typeOfHit == RayTraceResult.Type.BLOCK)
            {
                int x = pos.getX();
                int y = pos.getY();
                int z = pos.getZ();

                EnumFacing side = r.sideHit;

                if(side == EnumFacing.DOWN || side == EnumFacing.UP)
                {
                    this.destroyAndDropBlock(w, p, breaker, x + 1, y, z - 1);
                    this.destroyAndDropBlock(w, p, breaker, x + 1, y, z);
                    this.destroyAndDropBlock(w, p, breaker, x + 1, y, z + 1);
                    this.destroyAndDropBlock(w, p, breaker, x, y, z - 1);
                    // Middle block
                    this.destroyAndDropBlock(w, p, breaker, x, y, z + 1);
                    this.destroyAndDropBlock(w, p, breaker, x - 1, y, z - 1);
                    this.destroyAndDropBlock(w, p, breaker, x - 1, y, z);
                    this.destroyAndDropBlock(w, p, breaker, x - 1, y, z + 1);
                }
                // Z
                // NORTH - SOUTH
                else if(side == EnumFacing.NORTH || side == EnumFacing.SOUTH)
                {
                    this.destroyAndDropBlock(w, p, breaker, x + 1, y + 1, z);
                    this.destroyAndDropBlock(w, p, breaker, x, y + 1, z);
                    this.destroyAndDropBlock(w, p, breaker, x - 1, y + 1, z);
                    this.destroyAndDropBlock(w, p, breaker, x + 1, y, z);
                    // Middle block
                    this.destroyAndDropBlock(w, p, breaker, x - 1, y, z);
                    this.destroyAndDropBlock(w, p, breaker, x + 1, y - 1, z);
                    this.destroyAndDropBlock(w, p, breaker, x, y - 1, z);
                    this.destroyAndDropBlock(w, p, breaker, x - 1, y - 1, z);
                }
                // X
                // EAST - WEST
                else if(side == EnumFacing.EAST || side == EnumFacing.WEST)
                {
                    this.destroyAndDropBlock(w, p, breaker, x, y + 1, z + 1);
                    this.destroyAndDropBlock(w, p, breaker, x, y + 1, z);
                    this.destroyAndDropBlock(w, p, breaker, x, y + 1, z - 1);
                    this.destroyAndDropBlock(w, p, breaker, x, y, z + 1);
                    // Middle block
                    this.destroyAndDropBlock(w, p, breaker, x, y, z - 1);
                    this.destroyAndDropBlock(w, p, breaker, x, y - 1, z + 1);
                    this.destroyAndDropBlock(w, p, breaker, x, y - 1, z);
                    this.destroyAndDropBlock(w, p, breaker, x, y - 1, z - 1);
                }
                return true;
            }
        }

        breaker.damageItem(1, e);
        return super.onBlockDestroyed(breaker, w, state, pos, e);
    }

    private void destroyAndDropBlock(World w, EntityPlayer p, ItemStack breaker, int x, int y, int z)
    {
        BlockPos pos = new BlockPos(x, y, z);

        if (w.getBlockState(pos).getBlock() == net.minecraft.init.Blocks.STONE) {
            w.getBlockState(pos).getBlock().harvestBlock(w, p, pos, w.getBlockState(pos), w.getTileEntity(pos), breaker);
            w.setBlockToAir(pos);
        }
    }


    //ENCHANT

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment) {
        return enchantment != net.minecraft.init.Enchantments.MENDING && super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        if (net.minecraft.enchantment.EnchantmentHelper.getEnchantments(book).containsKey(net.minecraft.init.Enchantments.MENDING)) {
            return false;
        }
        return super.isBookEnchantable(stack, book);
    }

}