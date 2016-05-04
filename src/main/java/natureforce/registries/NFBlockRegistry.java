package natureforce.registries;

import natureforce.NatureForce;
import natureforce.blocks.BlockDeathPlant;
import natureforce.blocks.BlockPlantGenerator;
import natureforce.blocks.BlockUnitRelay;
import natureforce.creativetab.NFCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class NFBlockRegistry {
    public final List<Block> BLOCKS = new ArrayList<Block>();
    public final Block treeGenerator = new BlockPlantGenerator();
    public final Block deathPlant = new BlockDeathPlant();
    public final Block unitRelay = new BlockUnitRelay();

    public void preInit(){
        try {
            for(Field field : this.getClass().getDeclaredFields()) {
                if(field.getType().isAssignableFrom(Block.class)) {
                    Block block = (Block) field.get(this);
                    registerBlock(block);

                    if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
                        if(block.getCreativeTabToDisplayOn() == null)
                            block.setCreativeTab(NFCreativeTabs.tabMain);
                    }
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void init(){
        for (Block block:BLOCKS)
            NatureForce.proxy.registerDefaultBlockItemRenderer(block);
    }

    private void registerBlock(Block block) {
        String name = block.getUnlocalizedName();
        String blockName = name.substring(name.lastIndexOf(".") + 1, name.length());
        GameRegistry.register(block.setRegistryName(blockName));
        ItemBlock itemBlock = new ItemBlock(block);
        GameRegistry.register(itemBlock.setRegistryName(blockName));
        this.BLOCKS.add(block);
    }
}
