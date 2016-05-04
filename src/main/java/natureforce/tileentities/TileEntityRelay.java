package natureforce.tileentities;

import natureforce.api.natureunits.IEnergyBeamConnection;
import net.minecraft.nbt.NBTTagCompound;
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
        return .70f;
    }

    @Override
    public float connectionPointY() {
        return .70f;
    }

    @Override
    public float connectionPointZ() {
        return .70f;
    }


    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (connectedTo != null) {
            compound.setInteger("posX", connectedTo.getX());
            compound.setInteger("posY", connectedTo.getX());
            compound.setInteger("posZ", connectedTo.getX());
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
    public void update() {

    }
}
