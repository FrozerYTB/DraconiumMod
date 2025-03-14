package fr.draconiummc.draconiummod.objects.items;

import fr.draconiummc.draconiummod.DraconiumMod;
import fr.draconiummc.draconiummod.entity.EntityGrenade;
import fr.draconiummc.draconiummod.init.CreativeTabInit;
import fr.draconiummc.draconiummod.init.ItemInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraft.util.SoundCategory;
import net.minecraft.init.SoundEvents;
import net.minecraft.stats.StatList;
import net.minecraft.item.ItemStack;

public class ItemGrenade extends Item
{
    public ItemGrenade(String name)
    {
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(CreativeTabInit.DRACONIUM_MISC);

        ItemInit.ITEMS.add(this);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (!playerIn.capabilities.isCreativeMode)
        {
            itemstack.shrink(1);
        }

        // Joue le son de lancement de la grenade
        worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 0.5F, 1.0F);

        if (!worldIn.isRemote)
        {
            // Créer l'entité grenade et la lancer
            EntityGrenade entityGrenade = new EntityGrenade(worldIn, playerIn);
            entityGrenade.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F); // Appliquer la vitesse de lancement
            worldIn.spawnEntity(entityGrenade); // Ajouter l'entité au monde
        }

        playerIn.addStat(StatList.getObjectUseStats(this)); // Statistique de l'utilisation de l'objet
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack); // Retour succès
    }
}
