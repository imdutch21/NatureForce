package natureforce.tileentities;

import natureforce.blocks.BlockDeathPlant;
import natureforce.registries.Registries;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;

public class TileEntityDeathPlant extends TileEntity {
    private IBlockState deathPlantBlock;
    public int decayPercentage = 0;
    public int tillRefresh = 0;

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if (deathPlantBlock != null) {
            ResourceLocation resourcelocation = Block.blockRegistry.getNameForObject(deathPlantBlock.getBlock());
            nbt.setString("block", resourcelocation == null ? "" : resourcelocation.toString());
            nbt.setInteger("meta", deathPlantBlock.getBlock().getMetaFromState(deathPlantBlock));
        }
        nbt.setInteger("decay", decayPercentage);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("block") && nbt.hasKey("meta")) {
            Block block = Block.getBlockFromName(nbt.getString("block"));
            int meta = nbt.getInteger("meta");
            deathPlantBlock = block.getStateFromMeta(meta);
        }
        decayPercentage = nbt.getInteger("decay");
    }

    @Override
    public Packet<?> getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        return new SPacketUpdateTileEntity(getPos(), 0, tagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        NBTTagCompound tagCompound = pkt.getNbtCompound();
        readFromNBT(tagCompound);
    }

    public IBlockState getBlock() {
        return deathPlantBlock;
    }

    public void setBlock(IBlockState block) {
        deathPlantBlock = block;
        if (worldObj != null && worldObj.getBlockState(pos) != null && worldObj.getBlockState(pos).getBlock() == Registries.INSTANCE.blockRegistry.deathPlant)
            ((BlockDeathPlant) worldObj.getBlockState(pos).getBlock()).block = deathPlantBlock;
    }
}
