package natureforce.api.natureunits;

import net.minecraft.util.EnumFacing;

public interface IUnitProvider {
    /**
     * removes Nature Units from the receiver
     * @param facing the facing the units are coming from
     * @param unitAmount The amount of units that should be extracted
     * @param simulate If true the units will not be inserted
     * @return the amount of units that was/can be removed
     */
    int extractUnits(EnumFacing facing, int unitAmount, boolean simulate);

    /**
     * @return the Nature Units stored
     */
    int getUnitsStored();

    /**
     * @return the maximum Nature Units stored
     */
    int getMaxUnitsStored();
}
