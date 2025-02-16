package fr.draconiummc.draconiummod.init;

import fr.draconiummc.draconiummod.objects.armor.ArmorBase;
import fr.draconiummc.draconiummod.objects.items.ItemRessources;
import fr.draconiummc.draconiummod.objects.tools.*;
import fr.draconiummc.draconiummod.utils.Reference;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemInit {
    public static final List<Item> ITEMS = new ArrayList<>();

    // 📌 Déclaration des matériaux d'outils et des armures
    public static final ToolMaterial DRACONIUM_HAMMER_TOOLS = EnumHelper.addToolMaterial("draconium_hammer", 5, 2750, 0.8f, 1.0f, 5);
    public static final ToolMaterial PYRONITE_HAMMER_TOOLS = EnumHelper.addToolMaterial("pyroniye_hammer", 4, 1980, 0.5f, 1.0f, 5);
    public static final ToolMaterial PYRONITE_TOOLS = EnumHelper.addToolMaterial("pyronite_tools", 4, 1750, 6.7f, 6.0f, 10);
    public static final ToolMaterial DRACONIUM_TOOLS = EnumHelper.addToolMaterial("draconium_tools", 5, 2500, 9.0f, 6.0f, 14);
    public static final ArmorMaterial PYRONITE_ARMOR = EnumHelper.addArmorMaterial("pyronite_armor", Reference.MOD_ID + ":pyronite_armor", 75, new int[] {4, 7, 9, 4}, 18, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.0f);
    public static final ArmorMaterial DRACONIUM_ARMOR = EnumHelper.addArmorMaterial("draconium_armor", Reference.MOD_ID + ":draconium_armor", 90, new int[] {5, 9, 11, 5}, 18, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0f);

    // 🔹 Items de base
    public static final Item PYRONITE_INGOT = new ItemRessources("pyronite_ingot");
    public static final Item DRACONIUM_INGOT = new ItemRessources("draconium_ingot");
    public static final Item NOXIUM_GEM = new ItemRessources("noxium_gem");

    // ⚒️ Outils
    public static final ItemSword PYRONITE_SWORD = new ItemSwordMod("pyronite_sword", DRACONIUM_TOOLS);
    public static final ItemPickaxe PYRONITE_PICKAXE = new ItemPickaxeMod("pyronite_pickaxe", DRACONIUM_TOOLS);
    public static final ItemSpade PYRONITE_SHOVEL = new ItemShovelMod("pyronite_shovel", DRACONIUM_TOOLS);
    public static final ItemAxe PYRONITE_AXE = new ItemAxeMod("pyronite_axe", DRACONIUM_TOOLS);

    public static final ItemSword DRACONIUM_SWORD = new ItemSwordMod("draconium_sword", DRACONIUM_TOOLS);
    public static final ItemPickaxe DRACONIUM_PICKAXE = new ItemPickaxeMod("draconium_pickaxe", DRACONIUM_TOOLS);
    public static final ItemSpade DRACONIUM_SHOVEL = new ItemShovelMod("draconium_shovel", DRACONIUM_TOOLS);
    public static final ItemAxe DRACONIUM_AXE = new ItemAxeMod("draconium_axe", DRACONIUM_TOOLS);

    //ARMURES
    public static final Item PYRONITE_HELMET = new ArmorBase("pyronite_helmet", PYRONITE_ARMOR, 1, EntityEquipmentSlot.HEAD);
    public static final Item PYRONITE_CHESTPLATE = new ArmorBase("pyronite_chestplate", PYRONITE_ARMOR, 1, EntityEquipmentSlot.CHEST);
    public static final Item PYRONITE_LEGGINGS = new ArmorBase("pyronite_leggings", PYRONITE_ARMOR, 2, EntityEquipmentSlot.LEGS);
    public static final Item PYRONITE_BOOTS = new ArmorBase("pyronite_boots", PYRONITE_ARMOR, 1, EntityEquipmentSlot.FEET);

    public static final Item DRACONIUM_HELMET = new ArmorBase("draconium_helmet", DRACONIUM_ARMOR, 1, EntityEquipmentSlot.HEAD);
    public static final Item DRACONIUM_CHESTPLATE = new ArmorBase("draconium_chestplate", DRACONIUM_ARMOR , 1, EntityEquipmentSlot.CHEST);
    public static final Item DRACONIUM_LEGGINGS = new ArmorBase("draconium_leggings", DRACONIUM_ARMOR, 2, EntityEquipmentSlot.LEGS);
    public static final Item DRACONIUM_BOOTS = new ArmorBase("draconium_boots", DRACONIUM_ARMOR, 1, EntityEquipmentSlot.FEET);


    // 🛠️ Marteau spécial
    public static final Item DRACONIUM_HAMMER = new ItemHammer("draconium_hammer", DRACONIUM_HAMMER_TOOLS);
    public static final Item PYRONITE_HAMMER = new ItemHammer("pyronite_hammer", PYRONITE_HAMMER_TOOLS);
}
