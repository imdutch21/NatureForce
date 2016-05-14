package natureforce.blocks;

import natureforce.lib.References;
import natureforce.tileentities.TileEntityRelay;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockUnitRelay extends Block implements ITileEntityProvider {
    public BlockUnitRelay() {
        super(Material.glass, MapColor.airColor);
        setRegistryName("relay");
        setUnlocalizedName(getRegistryName().toString());
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(.25d, 0d, .25d, .75d, .75d, .75d);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState worldIn, World pos, BlockPos state) {
        return new AxisAlignedBB(.25d, 0d, .25d, .75d, .75d, .75d);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityRelay();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote)
            return false;
        if (worldIn.getTileEntity(pos) instanceof TileEntityRelay)
            System.out.println(((TileEntityRelay) worldIn.getTileEntity(pos)).connectedTo);
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }
}
