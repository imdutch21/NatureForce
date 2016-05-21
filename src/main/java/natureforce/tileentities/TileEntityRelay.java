package natureforce.tileentities;

import com.sun.javafx.geom.Vec3f;
import natureforce.api.natureunits.IEnergyBeamConnection;
import natureforce.entities.EntityEnergyBall;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class TileEntityRelay extends TileEntity implements ITickable, IEnergyBeamConnection {
    public BlockPos sendPowerTo;
    public BlockPos getPowerFrom;

    public int rotation = 0;

    //TODO change on state
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
        if (compound.hasKey("posX")) {
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
        } else {
            if (worldObj.rand.nextInt(100) == 0) {
                if (sendPowerTo != null && !(worldObj.getTileEntity(sendPowerTo) instanceof TileEntityRelay))
                    sendPowerTo = null;
                else if (sendPowerTo != null){
                    TileEntityRelay tileEntity = (TileEntityRelay) worldObj.getTileEntity(sendPowerTo);
                    EntityEnergyBall entityEnergyBall = new EntityEnergyBall(worldObj, getPos().getX() + connectionPointX(), getPos().getY() + connectionPointY(), getPos().getZ() + connectionPointZ());
                    entityEnergyBall.setOrigin(pos);
                    entityEnergyBall.setHeading(sendPowerTo);
                    entityEnergyBall.setEnergyStored(20);
                    Vec3f beamStart = new Vec3f(getPos().getX() + connectionPointX(), getPos().getY() + connectionPointY(), getPos().getZ() + connectionPointZ());
                    Vec3f beamEnd = new Vec3f(tileEntity.getPos().getX() + ((IEnergyBeamConnection) tileEntity).connectionPointX(), tileEntity.getPos().getY() + ((IEnergyBeamConnection) tileEntity).connectionPointY(), tileEntity.getPos().getZ() + ((IEnergyBeamConnection) tileEntity).connectionPointZ());
                    beamEnd.sub(beamStart);
                    BlockPos offset = sendPowerTo.subtract(getPos());
                    int dx = offset.getX();
                    int dy = offset.getY();
                    int dz = offset.getZ();
                    double subDistance = MathHelper.sqrt_double(dx * dx + dz * dz);
                    float rotYaw = -((float) (Math.atan2(dz, dx) * 180.0D / Math.PI));
                    float rotPitch = ((float) (Math.atan2(dy, subDistance) * 180.0D / Math.PI));
                    float mx = MathHelper.sin(rotYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotPitch / 180.0F * (float) Math.PI) * .4f / 2f;
                    float mz = -(MathHelper.cos(rotYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotPitch / 180.0F * (float) Math.PI) * .4f) / 2f;
                    float my = MathHelper.sin((rotPitch) / 180.0F * (float) Math.PI) * .4f / 2f;
                    entityEnergyBall.setVelocity(mx, my, mz);
                    worldObj.spawnEntityInWorld(entityEnergyBall);
                }

                if (getPowerFrom != null && !(worldObj.getTileEntity(getPowerFrom) instanceof TileEntityRelay))
                    getPowerFrom = null;

            }
        }
    }
}
