package natureforce.tileentities;

import natureforce.api.natureunits.IEnergyBeamConnection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityRelay extends TileEntity implements ITickable, IEnergyBeamConnection {
    public BlockPos connectedTo;

    @Override
    public boolean canBeamConnect() {
        return true;
    }

    @Override
    public float connectionPointX() {
        return .5f;
    }

    @Override
    public float connectionPointY() {
        return .70f;
    }

    @Override
    public float connectionPointZ() {
        return .5f;
    }


    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (connectedTo != null) {
            compound.setInteger("posX", connectedTo.getX());
            compound.setInteger("posY", connectedTo.getY());
            compound.setInteger("posZ", connectedTo.getZ());
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("posX")){
            connectedTo = new BlockPos(compound.getInteger("posX"), compound.getInteger("posY"), compound.getInteger("posZ"));
        }
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

    @Override
    public void update() {

    }
}
