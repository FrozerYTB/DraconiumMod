package fr.draconiummc.draconiummod.objects.armor;

import fr.draconiummc.draconiummod.DraconiumMod;
import fr.draconiummc.draconiummod.init.CreativeTabInit;
import fr.draconiummc.draconiummod.init.ItemInit;
import fr.draconiummc.draconiummod.utils.IHasModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ArmorBase extends ItemArmor implements IHasModel {

    public ArmorBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, renderIndexIn, equipmentSlotIn);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabInit.DRACONIUM_ARMOR);
        ItemInit.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        DraconiumMod.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (!world.isRemote && player.ticksExisted % 20 == 0) { // Appliquer toutes les 20 ticks (~1s)
            boolean hasHelmet = isWearing(player, ItemInit.DRACONIUM_HELMET);
            boolean hasChestplate = isWearing(player, ItemInit.DRACONIUM_CHESTPLATE);
            boolean hasLeggings = isWearing(player, ItemInit.DRACONIUM_LEGGINGS);
            boolean hasBoots = isWearing(player, ItemInit.DRACONIUM_BOOTS);

            applyOrRemoveEffect(player, MobEffects.NIGHT_VISION, hasHelmet, 450); 
            applyOrRemoveEffect(player, MobEffects.RESISTANCE, hasChestplate, 40); // 2s
            applyOrRemoveEffect(player, MobEffects.HASTE, hasLeggings, 40); // 2s
            applyOrRemoveEffect(player, MobEffects.SPEED, hasBoots, 40); // 2s
        }
    }

    private void applyOrRemoveEffect(EntityPlayer player, Potion effect, boolean shouldHaveEffect, int duration) {
        PotionEffect activeEffect = player.getActivePotionEffect(effect);

        if (shouldHaveEffect) {
            if (activeEffect == null || activeEffect.getDuration() <= duration / 2) {
                player.addPotionEffect(new PotionEffect(effect, duration, 0, false, false));
            }
        } else if (activeEffect != null) {
            player.removePotionEffect(effect);
        }
    }

    private boolean isWearing(EntityPlayer player, Item item) {
        return player.inventory.armorInventory.stream()
                .anyMatch(stack -> !stack.isEmpty() && stack.getItem() == item);
    }
}
