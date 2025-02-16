package fr.draconiummc.draconiummod.objects.tools;


import fr.draconiummc.draconiummod.DraconiumMod;
import fr.draconiummc.draconiummod.init.CreativeTabInit;
import fr.draconiummc.draconiummod.init.ItemInit;
import fr.draconiummc.draconiummod.utils.IHasModel;
import net.minecraft.item.ItemSword;

public class ItemSwordMod extends ItemSword implements IHasModel
{

    public ItemSwordMod(String name, ToolMaterial material)
    {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabInit.DRACONIUM_WEAPONS);
        ItemInit.ITEMS.add(this);
    }

    @Override
    public void registerModels()
    {

        DraconiumMod.proxy.registerItemRenderer(this, 0, "inventory");
    }

}