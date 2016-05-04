package natureforce.items;

import natureforce.blocks.BlockUnitRelay;
import natureforce.creativetab.NFCreativeTabs;
import natureforce.lib.References;
import natureforce.tileentities.TileEntityRelay;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemRelayLinker extends Item {

    public ItemRelayLinker() {
        setCreativeTab(NFCreativeTabs.tabMain);
        setUnlocalizedName(References.NAME_PREFIX + "relayLinker");
    }


    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote && worldIn.getBlockState(pos) != null && worldIn.getBlockState(pos).getBlock() instanceof BlockUnitRelay) {
            NBTTagCompound tagCompound = stack.getTagCompound();
            if (tagCompound.hasKey("posX")) {
                TileEntity tileEntity = worldIn.getTileEntity(pos);
                if (tileEntity instanceof TileEntityRelay) {
                    ((TileEntityRelay) tileEntity).connectedTo = new BlockPos(tagCompound.getInteger("posX"), tagCompound.getInteger("posY"), tagCompound.getInteger("posZ"));
                    stack.setTagCompound(new NBTTagCompound());
                    playerIn.addChatMessage(new TextComponentString("relays linked"));
                    return EnumActionResult.SUCCESS;
                }
            } else {
                tagCompound.setInteger("posX", pos.getX());
                tagCompound.setInteger("posY", pos.getY());
                tagCompound.setInteger("posZ", pos.getZ());
                stack.setTagCompound(tagCompound);
                return EnumActionResult.SUCCESS;
            }
        }

        return EnumActionResult.FAIL;
    }
}
