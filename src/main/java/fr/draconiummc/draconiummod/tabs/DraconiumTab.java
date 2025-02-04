package fr.draconiummc.draconiummod.tabs;

import fr.draconiummc.draconiummod.init.ItemInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DraconiumTab extends CreativeTabs {
    public DraconiumTab(String label) {
        super("draconiummodtab");
        this.setBackgroundImageName("draconiummod.png");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ItemInit.DRACONIUM_INGOT);
    }
}

