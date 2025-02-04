package fr.draconiummc.draconiummod;

import fr.draconiummc.draconiummod.proxy.CommonProxy;
import fr.draconiummc.draconiummod.tabs.DraconiumTab;
import fr.draconiummc.draconiummod.utils.Reference;
import fr.draconiummc.draconiummod.utils.handlers.RegistryHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class DraconiumMod
{
    @Mod.Instance
    public static DraconiumMod instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    public static final CreativeTabs DRACONIUMMODTAB = new DraconiumTab("draconiumtab");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        RegistryHandler.preInitRegistries(event);
    }

    @Mod.EventHandler
    public void Init(FMLPreInitializationEvent event)
    {
        RegistryHandler.initRegistries();
    }

    @Mod.EventHandler
    public void postInit(FMLPreInitializationEvent event)
    {
        RegistryHandler.postInitRegistries();
    }


}
