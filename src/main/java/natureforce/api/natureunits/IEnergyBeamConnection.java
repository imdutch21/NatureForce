package natureforce.api.natureunits;

public interface IEnergyBeamConnection {

    /**
     * @return whether or not a beam can connect
     */
    boolean canBeamConnect();

    /**
     * @return the x coordinate inside the block that the beam should go to
     */
    float connectionPointX();

    /**
     * @return the y coordinate inside the block that the beam should go to
     */
    float connectionPointY();

    /**
     * @return the z coordinate inside the block that the beam should go to
     */
    float connectionPointZ();
}
