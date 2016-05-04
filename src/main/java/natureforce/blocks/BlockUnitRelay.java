package natureforce.blocks;

import natureforce.lib.References;
import natureforce.tileentities.TileEntityRelay;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockUnitRelay extends Block implements ITileEntityProvider {
    public BlockUnitRelay() {
        super(Material.glass, MapColor.airColor);
        setUnlocalizedName(References.NAME_PREFIX + "relay");
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(.25d, 0d, .25d, .75d, .75d, .75d);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityRelay();
    }
}
