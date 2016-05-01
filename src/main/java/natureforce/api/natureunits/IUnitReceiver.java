package natureforce.api.natureunits;

import net.minecraft.util.EnumFacing;

public interface IUnitReceiver {

    /**
     * Adds Nature Units to the receiver
     * @param facing the facing the units are coming from
     * @param unitAmount The amount of units that should be inserted
     * @param simulate If true the units will not be inserted
     * @return the amount of units that was/can be added
     */
    int receiveUnits(EnumFacing facing, int unitAmount, boolean simulate);

    /**
     * @return the Nature Units stored
     */
    int getUnitsStored();

    /**
     * @return the maximum Nature Units stored
     */
    int getMaxUnitsStored();
}
