package natureforce.registries;

import natureforce.lib.References;
import natureforce.tileentities.TileEntityDeathPlant;
import natureforce.tileentities.TileEntityPlantGenerator;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class NFTileEntityRegistry {

    public void preInit() {
        registerTileEntity(TileEntityDeathPlant.class, "deathPlant");
        registerTileEntity(TileEntityPlantGenerator.class, "plantGenerator");
    }

    private void registerTileEntity(Class<? extends TileEntity> cls, String baseName) {
        GameRegistry.registerTileEntity(cls, "tile." + References.NAME_PREFIX + baseName);
    }
}
