package natureforce.entities;

import natureforce.tileentities.TileEntityRelay;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityEnergyBall extends Entity {
    public BlockPos heading;
    public BlockPos origin;
    public float energyStored;
    public float velocityX;
    public float velocityY;
    public float velocityZ;

    public EntityEnergyBall(World worldIn) {
        super(worldIn);
    }

    public void setHeading(BlockPos heading) {
        this.heading = heading;
    }
    public void setOrigin(BlockPos origin) {
        this.origin = origin;
    }

    public void setVelocity(float velocityX, float velocityZ, float velocityY) {
        this.velocityX = velocityX;
        this.velocityZ = velocityZ;
        this.velocityY = velocityY;
    }

    public void setEnergyStored(float energyStored) {
        this.energyStored = energyStored;
        setSize(energyStored * .05f, energyStored * .05f);
    }

    @Override
    protected void entityInit() {
        noClip = true;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tagCompund) {
        if (tagCompund.hasKey("velocityX"))
            velocityX = tagCompund.getFloat("velocityX");
        if (tagCompund.hasKey("velocityY"))
            velocityY = tagCompund.getFloat("velocityY");
        if (tagCompund.hasKey("velocityZ"))
            velocityZ = tagCompund.getFloat("velocityZ");
        if (tagCompund.hasKey("energyStored"))
            energyStored = tagCompund.getFloat("energyStored");
        if (tagCompund.hasKey("headingX"))
            heading = new BlockPos(tagCompund.getInteger("headingX"), tagCompund.getInteger("headingY"), tagCompund.getInteger("headingZ"));
        if (tagCompund.hasKey("originX"))
            origin = new BlockPos(tagCompund.getInteger("originX"), tagCompund.getInteger("originY"), tagCompund.getInteger("originZ"));
    }
    @Override
    protected void writeEntityToNBT(NBTTagCompound tagCompound) {
        tagCompound.setInteger("headingX", heading.getX());
        tagCompound.setInteger("headingY", heading.getY());
        tagCompound.setInteger("headingZ", heading.getZ());
        tagCompound.setInteger("originX", origin.getX());
        tagCompound.setInteger("originY", origin.getY());
        tagCompound.setInteger("originZ", origin.getZ());
        tagCompound.setFloat("velocityX", velocityX);
        tagCompound.setFloat("velocityY", velocityZ);
        tagCompound.setFloat("velocityY", velocityZ);
        tagCompound.setFloat("energyStored", energyStored);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        motionX = velocityX;
        motionY = velocityY;
        motionZ = velocityZ;
        if (!(worldObj.getTileEntity(origin) instanceof TileEntityRelay) || !(worldObj.getTileEntity(heading) instanceof TileEntityRelay))
            destroy();
        else if (worldObj.getBlockState(heading).getBoundingBox(worldObj, heading).intersectsWith(this.getEntityBoundingBox())){
            TileEntityRelay tile = (TileEntityRelay)worldObj.getTileEntity(heading);
            //tile recieve energy
            this.setDead();
        }
    }


    public void destroy(){
        worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 1 + 0.05f*energyStored, false);
        this.setDead();
    }


    @Override
    protected void setOnFireFromLava() {
    }

    @Override
    public void setFire(int seconds) {
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }


}
