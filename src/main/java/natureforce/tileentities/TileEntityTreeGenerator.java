package natureforce.tileentities;

import natureforce.api.natureunits.UnitStorage;

public class TileEntityTreeGenerator extends TileEntityGenerator {
    public UnitStorage unitStorage = new UnitStorage(1000, 1000, 10);

    public int progress = 0;

    @Override
    public void update() {
        if (worldObj.isRemote) return;

    }

    @Override
    public int receiveUnits(int unitAmount, boolean simulate) {
        worldObj.notifyBlockUpdate(getPos(), worldObj.getBlockState(getPos()), worldObj.getBlockState(getPos()), 3);
        return unitStorage.receiveUnits(unitAmount, simulate);
    }

    @Override
    public int extractUnits(int unitAmount, boolean simulate) {
        worldObj.notifyBlockUpdate(getPos(), worldObj.getBlockState(getPos()), worldObj.getBlockState(getPos()), 3);
        return unitStorage.extractUnits(unitAmount, simulate);
    }

    @Override
    public int getUnitsStored() {
        return unitStorage.getUnitsStored();
    }

    @Override
    public int getMaxUnitsStored() {
        return unitStorage.getMaxUnitsStored();
    }
}
