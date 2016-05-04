package natureforce.blocks;

import natureforce.lib.References;
import natureforce.tileentities.TileEntityDeathPlant;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockDeathPlant extends Block implements ITileEntityProvider {
    public IBlockState block;

    public BlockDeathPlant() {
        super(Material.plants, MapColor.brownColor);
        setUnlocalizedName(References.NAME_PREFIX + "deathPlant");
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }


    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        if (block != null)
            return block.getBlock().getBoundingBox(block, source, pos);
        else
            return FULL_BLOCK_AABB;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Override
    public SoundType getStepSound() {
        if (block != null)
            return block.getBlock().getStepSound();
        else
            return super.getStepSound();
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityDeathPlant();
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return block != null && block.getBlock().isOpaqueCube(block);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World world, BlockPos pos) {
        if (block != null)
            return block.getBlock().getCollisionBoundingBox(block, world, pos);
        else
            return super.getCollisionBoundingBox(state, world, pos);
    }

    @Override
    public String getLocalizedName() {
        if (block != null)
            return block.getBlock().getLocalizedName();
        else
            return super.getLocalizedName();
    }


}
