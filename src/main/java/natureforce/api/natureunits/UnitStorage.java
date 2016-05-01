package natureforce.api.natureunits;

import net.minecraft.nbt.NBTTagCompound;

public class UnitStorage implements IUnitStorage {
    private int unitsStored;
    private int maxUnitsStored;
    private int maxUnitsReceived;
    private int maxUnitsExtracted;

    public UnitStorage(int maxUnitsStored, int maxUnitsReceived, int maxUnitsExtracted) {
        this.maxUnitsStored = maxUnitsStored;
        this.maxUnitsReceived = maxUnitsReceived;
        this.maxUnitsExtracted = maxUnitsExtracted;
    }


    public UnitStorage readFromNBT(NBTTagCompound nbt) {
        this.unitsStored = nbt.getInteger("Units");

        if (unitsStored > maxUnitsStored)
            unitsStored = maxUnitsStored;
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        if (unitsStored < 0)
            unitsStored = 0;
        nbt.setInteger("Units", unitsStored);
        return nbt;
    }

    /**
     * Sets the units stored.
     *
     * @param unitAmount the amount of units that should be stored
     */
    public void setUnitsStored(int unitAmount) {
        if (unitAmount > maxUnitsStored)
            unitsStored = maxUnitsStored;
        else
            unitsStored = unitAmount;
    }


    @Override
    public int receiveUnits(int unitAmount, boolean simulate) {
        int unitsReceived = Math.min(maxUnitsStored - unitsStored, Math.min(unitAmount, maxUnitsReceived));
        if (!simulate)
            unitsStored += unitsReceived;
        return unitsReceived;
    }

    @Override
    public int extractUnits(int unitAmount, boolean simulate) {
        int unitsExtracted = Math.min(unitsStored, Math.min(unitAmount, maxUnitsExtracted));
        if (!simulate)
            unitsStored -= unitsExtracted;
        return unitsExtracted;
    }

    @Override
    public int getUnitsStored() {
        return unitsStored;
    }

    @Override
    public int getMaxUnitsStored() {
        return maxUnitsStored;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return true;
    }


    public void setMaxUnitsExtracted(int maxUnitsExtracted) {
        this.maxUnitsExtracted = maxUnitsExtracted;
    }


    public void setMaxUnitsReceived(int maxUnitsReceived) {
        this.maxUnitsReceived = maxUnitsReceived;
    }

    public void setMaxUnitsStored(int maxUnitsStored) {
        this.maxUnitsStored = maxUnitsStored;
    }
}
