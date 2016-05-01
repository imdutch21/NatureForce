package natureforce.tileentities;

import natureforce.api.natureunits.IUnitStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityGenerator extends TileEntity implements IUnitStorage, ITickable {


    @Override
    public void update() {

    }

    @Override
    public int receiveUnits(int unitAmount, boolean simulate) {
        return 0;
    }

    @Override
    public int extractUnits(int unitAmount, boolean simulate) {
        return 0;
    }

    @Override
    public int getUnitsStored() {
        return 0;
    }

    @Override
    public int getMaxUnitsStored() {
        return 0;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return false;
    }

    public boolean matches(ItemStack itemStack1, ItemStack itemstack2) {
        return itemStack1 != null && itemstack2 != null && itemstack2.getItem() == itemStack1.getItem() && itemstack2.getItemDamage() == itemStack1.getItemDamage();
    }
}
