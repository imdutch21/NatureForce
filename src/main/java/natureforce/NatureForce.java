package natureforce;

import natureforce.lib.References;
import natureforce.proxy.CommonProxy;
import natureforce.registries.Registries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = References.MOD_ID, name = References.MOD_NAME, version = References.VERSION)
public class NatureForce {

    @SidedProxy(modId = References.MOD_ID, clientSide = References.CLIENTPROXY_LOCATION, serverSide = References.COMMONPROXY_LOCATION)
    public static CommonProxy proxy;
    @Mod.Instance(References.MOD_ID)
    public static NatureForce instance;

    public static File sourceFile;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        sourceFile = event.getSourceFile();
        Registries.INSTANCE.preInit();

        proxy.preInit();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        Registries.INSTANCE.init();

        proxy.init();
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {

        proxy.postInit();
    }
}
