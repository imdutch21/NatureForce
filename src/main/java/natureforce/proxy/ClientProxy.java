package natureforce.proxy;

import natureforce.client.render.RenderDeathPlant;
import natureforce.client.render.RenderRelay;
import natureforce.lib.References;
import natureforce.tileentities.TileEntityDeathPlant;
import natureforce.tileentities.TileEntityRelay;
import natureforce.util.JsonRenderGenerator;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.ArrayList;
import java.util.List;

public class ClientProxy extends CommonProxy {
    private static final boolean createJSONFile = true;


    @Override
    public void preInit() {
        super.preInit();

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDeathPlant.class, new RenderDeathPlant());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRelay.class, new RenderRelay());
    }

    @Override
    public void registerDefaultBlockItemRenderer(Block block) {
        String blockName = block.getRegistryName().toString().replace(References.ASSETS_PREFIX, "");
        if (createJSONFile)
            JsonRenderGenerator.createJSONForBlock(block, blockName);
        ModelLoader.registerItemVariants(Item.getItemFromBlock(block), new ModelResourceLocation(References.ASSETS_PREFIX + blockName, "inventory"));
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(References.ASSETS_PREFIX + blockName, "inventory"));
    }

    @Override
    public void registerDefaultItemRenderer(Item item) {
        List<ItemStack> list = new ArrayList<>();
        item.getSubItems(item, null, list);
        if (list.size() > 0) {
            for (ItemStack itemStack : list) {
                String itemName = item.getRegistryName().toString().replace(References.ASSETS_PREFIX, "");
                if (createJSONFile)
                    JsonRenderGenerator.createJSONForItem(item, itemStack.getItemDamage(), itemName);
                Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, itemStack.getItemDamage(), new ModelResourceLocation(References.ASSETS_PREFIX + itemName, "inventory"));
                ModelLoader.setCustomModelResourceLocation(item, itemStack.getItemDamage(), new ModelResourceLocation(References.ASSETS_PREFIX + itemName, "inventory"));
            }
        } else {
            String itemName = item.getRegistryName().toString().replace(References.ASSETS_PREFIX, "");
            if (createJSONFile)
                JsonRenderGenerator.createJSONForItem(item, 0, itemName);
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(References.ASSETS_PREFIX + itemName, "inventory"));
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(References.ASSETS_PREFIX + itemName, "inventory"));
        }
    }
}
