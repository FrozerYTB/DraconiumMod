package fr.draconiummc.draconiummod.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabInit {

    // 🛡️ Armures
    public static final CreativeTabs DRACONIUM_ARMOR = new CreativeTabs("draconiummod_armor") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ItemInit.DRACONIUM_INGOT); // Remplace par une vraie armure
        }
    };

    // 📦 Blocs normaux (ex : blocs décoratifs, structures)
    public static final CreativeTabs DRACONIUM_BLOCKS = new CreativeTabs("draconiummod_blocks") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(Item.getItemFromBlock(BlockInit.DRACONIUM_BLOCK));
        }
    };

    // 🍖 Nourriture et potions
    public static final CreativeTabs DRACONIUM_FOOD = new CreativeTabs("draconiummod_food") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ItemInit.DRACONIUM_INGOT); // Remplace par une vraie nourriture
        }
    };

    // 🔄 Divers / objets sans catégorie précise
    public static final CreativeTabs DRACONIUM_MISC = new CreativeTabs("draconiummod_misc") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ItemInit.DRACONIUM_INGOT); // Remplace par un item au choix
        }
    };
    // 🔩 Ressources brutes (lingots, cristaux, fragments...)
    public static final CreativeTabs DRACONIUM_RESOURCES = new CreativeTabs("draconiummod_resources") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ItemInit.DRACONIUM_INGOT);
        }
    };

    // 🔨 Outils (pelle, pioche, hache, épée)
    public static final CreativeTabs DRACONIUM_TOOLS = new CreativeTabs("draconiummod_tools") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ItemInit.DRACONIUM_PICKAXE); // Remplace par un vrai outil
        }
    };

    // ⚔️ Armes spéciales
    public static final CreativeTabs DRACONIUM_WEAPONS = new CreativeTabs("draconiummod_weapons") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ItemInit.DRACONIUM_SWORD); // Remplace par une vraie arme
        }
    };
}
