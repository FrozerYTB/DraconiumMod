package fr.draconiummc.draconiummod.objects.items;

import fr.draconiummc.draconiummod.DraconiumMod;
import fr.draconiummc.draconiummod.init.CreativeTabInit;
import fr.draconiummc.draconiummod.init.ItemInit;
import fr.draconiummc.draconiummod.utils.IHasModel;
import net.minecraft.item.Item;

public class ItemRessources extends Item implements IHasModel
{
    public ItemRessources(String name)
    {
        this.setUnlocalizedName(name);
        this.setRegistryName(name);
        this.setCreativeTab(CreativeTabInit.DRACONIUM_RESOURCES);

        ItemInit.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {
        DraconiumMod.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
