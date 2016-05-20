package natureforce.blocks;

import natureforce.tileentities.TileEntityRelay;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLever;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockUnitRelay extends Block implements ITileEntityProvider {
    private static final PropertyInteger FACING = PropertyInteger.create("facing", 0, 5);
    private static final AxisAlignedBB RELAY_NORTH_AABB = new AxisAlignedBB(0.25D, 0.25D, 0.25D, 0.75D, 0.75D, 1.0D);
    private static final AxisAlignedBB RELAY_SOUTH_AABB = new AxisAlignedBB(0.25D, 0.25D, 0.0D, 0.75D, 0.75D, 0.75D);
    private static final AxisAlignedBB RELAY_WEST_AABB = new AxisAlignedBB(0.25D, 0.25D, 0.25D, 1.0D, 0.75D, 0.75D);
    private static final AxisAlignedBB RELAY_EAST_AABB = new AxisAlignedBB(0.0D, 0.25D, 0.25D, 0.75D, 0.75D, 0.57D);
    private static final AxisAlignedBB RELAY_UP_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.75D, 0.75D);
    private static final AxisAlignedBB RELAY_DOWN_AABB = new AxisAlignedBB(0.25d, 0.25d, 0.25d, 0.75d, 1.0d, 0.75d);

    public BlockUnitRelay() {
        super(Material.glass, MapColor.airColor);
        setRegistryName("relay");
        setUnlocalizedName(getRegistryName().toString());
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, 0));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch (state.getValue(FACING)) {
            case 5:
            default:
                return RELAY_EAST_AABB;
            case 4:
                return RELAY_WEST_AABB;
            case 3:
                return RELAY_SOUTH_AABB;
            case 2:
                return RELAY_NORTH_AABB;
            case 1:
                return RELAY_UP_AABB;
            case 0:
                return RELAY_DOWN_AABB;
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState worldIn, World pos, BlockPos state) {
        switch (worldIn.getValue(FACING)) {
            case 5:
            default:
                return RELAY_EAST_AABB;
            case 4:
                return RELAY_WEST_AABB;
            case 3:
                return RELAY_SOUTH_AABB;
            case 2:
                return RELAY_NORTH_AABB;
            case 1:
                return RELAY_UP_AABB;
            case 0:
                return RELAY_DOWN_AABB;
        }
    }


    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, meta);
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
            System.out.println(((TileEntityRelay) worldIn.getTileEntity(pos)).sendPowerTo);
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, facing.ordinal());
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING);
    }
}
