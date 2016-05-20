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
    public BlockPos sendPowerTo;
    public BlockPos getPowerFrom;

    public int rotation = 0;

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
        if (sendPowerTo != null) {
            compound.setInteger("posX", sendPowerTo.getX());
            compound.setInteger("posY", sendPowerTo.getY());
            compound.setInteger("posZ", sendPowerTo.getZ());
        }
        if (getPowerFrom != null) {
            compound.setInteger("posXFrom", getPowerFrom.getX());
            compound.setInteger("posYFrom", getPowerFrom.getY());
            compound.setInteger("posZFrom", getPowerFrom.getZ());
        }
        compound.setInteger("rotation", rotation);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("posX")){
            sendPowerTo = new BlockPos(compound.getInteger("posX"), compound.getInteger("posY"), compound.getInteger("posZ"));
        }
        if (compound.hasKey("posXFrom"))
            getPowerFrom = new BlockPos(compound.getInteger("posXFrom"), compound.getInteger("posYFrom"), compound.getInteger("posZFrom"));
        rotation = compound.getInteger("rotation");
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
        if (worldObj.isRemote) {
            if (rotation >= 90)
                rotation = 0;
            rotation++;
        }
    }
}
