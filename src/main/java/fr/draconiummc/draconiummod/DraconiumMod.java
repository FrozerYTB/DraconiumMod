package fr.draconiummc.draconiummod;

import fr.draconiummc.draconiummod.init.CreativeTabInit;
import fr.draconiummc.draconiummod.init.ItemInit;
import fr.draconiummc.draconiummod.proxy.CommonProxy;
import fr.draconiummc.draconiummod.utils.Reference;
import fr.draconiummc.draconiummod.utils.handlers.RegistryHandler;
import fr.draconiummc.draconiummod.utils.handlers.RenderHandler;
import fr.draconiummc.draconiummod.world.ModConfig;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class DraconiumMod
{

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for(Item item : ItemInit.ITEMS) {
            DraconiumMod.proxy.registerItemRenderer(item, 0, "inventory");
        }
    }
    @Mod.Instance
    public static DraconiumMod instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;

    public static void registerCreativeTabs() {
        CreativeTabInit.DRACONIUM_ARMOR.getTabIconItem();
        CreativeTabInit.DRACONIUM_BLOCKS.getTabIconItem();
        CreativeTabInit.DRACONIUM_FOOD.getTabIconItem();
        CreativeTabInit.DRACONIUM_MISC.getTabIconItem();
        CreativeTabInit.DRACONIUM_RESOURCES.getTabIconItem();
        CreativeTabInit.DRACONIUM_TOOLS.getTabIconItem();
        CreativeTabInit.DRACONIUM_WEAPONS.getTabIconItem();

    }

    public static org.apache.logging.log4j.Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        RegistryHandler.preInitRegistries(event);

        proxy.registerEntityRenderers();  // Ceci appelle ton ClientProxy côté client.

        ModConfig.loadConfig(new File(event.getModConfigurationDirectory(), "draconiummod.cfg"));
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
