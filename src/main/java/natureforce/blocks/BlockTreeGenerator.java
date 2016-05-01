package natureforce.blocks;

import natureforce.lib.References;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockTreeGenerator extends Block {
    public BlockTreeGenerator() {
        super(Material.rock, MapColor.greenColor);
        setUnlocalizedName(References.NAME_PREFIX + "treeGenerator");
    }
}
