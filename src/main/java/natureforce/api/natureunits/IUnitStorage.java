package natureforce.api.natureunits;

public interface IUnitStorage {

    /**
     * Adds Nature Units to the storage
     * @param unitAmount The amount of units that should be inserted
     * @param simulate If true the units will not be inserted
     * @return the amount of units that was/can be added
     */
    int receiveUnits(int unitAmount, boolean simulate);


    /**
     * Removes Nature Units from the storage
     * @param unitAmount he amount of units that should be inserted
     * @param simulate If true the units will not be extracted
     * @return the amount of units that was/can be removed
     */
    int extractUnits(int unitAmount, boolean simulate);

    /**
     * @return the amount of Nature Units stored
     */
    int getUnitsStored();

    /**
     * @return the maximum amount of Nature Units stored
     */
    int getMaxUnitsStored();

    /**
     * @return whether or not Nature Units can be extracted
     */
    boolean canExtract();

    /**
     * @return whether or not Nature Units can be received
     */
    boolean canReceive();
}
