package natureforce.blocks;

import natureforce.lib.References;
import natureforce.tileentities.TileEntityDeathPlant;
import natureforce.tileentities.TileEntityPlantGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class BlockPlantGenerator extends Block implements ITileEntityProvider {
    public BlockPlantGenerator() {
        super(Material.rock, MapColor.greenColor);
        setRegistryName("plantGenerator");
        setUnlocalizedName(getRegistryName().toString());
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityPlantGenerator();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (playerIn.getHeldItem(hand) == null){
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TileEntityPlantGenerator) {
                playerIn.addChatMessage(new TextComponentString("" + ((TileEntityPlantGenerator) tileEntity).getUnitsStored()));
                return true;
            }
        }
        return false;
    }
}
