package natureforce.tileentities;

import natureforce.api.natureunits.UnitStorage;
import natureforce.api.recipe.RecipePlantGenerator;
import natureforce.registries.Registries;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityPlantGenerator extends TileEntityGenerator {
    public UnitStorage unitStorage = new UnitStorage(1000, 1000, 10);

    public BlockPos currentSelectedBlock = null;

    public int progress = 0;

    @Override
    public void update() {
        if (worldObj.isRemote) return;
        if (currentSelectedBlock == null) {
            if (worldObj.getBlockState(pos.up(2)) != null) {
                RecipePlantGenerator recipe = RecipePlantGenerator.getRecipeFromBlockState(worldObj.getBlockState(pos.up(2)));
                if (recipe != null) {
                    if (recipe.hasCustomHandling)
                        recipe.customHandling(getWorld(), getPos().up(2));
                    else if (recipe.block.getBlock() instanceof BlockLog)
                        handleBasicTree(worldObj, pos.up(2));
                    else
                        handleSimplePlant(worldObj, pos.up(2));
                }
            }
        }
        if (currentSelectedBlock != null && worldObj.getBlockState(currentSelectedBlock) != null && worldObj.getBlockState(currentSelectedBlock).getBlock() == Registries.INSTANCE.blockRegistry.deathPlant) {
            TileEntity tile = worldObj.getTileEntity(currentSelectedBlock);
            if (tile instanceof TileEntityDeathPlant) {
                RecipePlantGenerator recipe = RecipePlantGenerator.getRecipeFromBlockState(((TileEntityDeathPlant) tile).getBlock());
                if (recipe != null && ((TileEntityDeathPlant) tile).decayPercentage < 100) {
                    if (progress >= recipe.ticks) {
                        if (receiveUnits(recipe.unitsPerDecayPercentage, true) == recipe.unitsPerDecayPercentage) {
                            receiveUnits(recipe.unitsPerDecayPercentage, false);
                            progress = 0;
                            worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 3);
                            ((TileEntityDeathPlant) tile).decayPercentage++;
                            worldObj.notifyBlockUpdate(currentSelectedBlock, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 3);
                        }
                    } else
                        progress++;
                } else
                    currentSelectedBlock = null;

            }
        } else
            currentSelectedBlock = null;
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


    public void handleBasicTree(World world, BlockPos pos) {

    }

    public void handleSimplePlant(World world, BlockPos pos) {
        IBlockState blockState = worldObj.getBlockState(pos);
        world.setBlockState(pos, Registries.INSTANCE.blockRegistry.deathPlant.getDefaultState(), 2);
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof  TileEntityDeathPlant)
            ((TileEntityDeathPlant) tileEntity).setBlock(blockState);
        currentSelectedBlock = pos;
    }


    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        unitStorage.writeToNBT(compound);
        compound.setInteger("progress", progress);
        if (currentSelectedBlock != null) {
            compound.setInteger("posX", currentSelectedBlock.getX());
            compound.setInteger("posY", currentSelectedBlock.getX());
            compound.setInteger("posZ", currentSelectedBlock.getX());
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        unitStorage.readFromNBT(compound);
        progress = compound.getInteger("progress");
        if (compound.hasKey("posX")){
            currentSelectedBlock = new BlockPos(compound.getInteger("posX"), compound.getInteger("posY"), compound.getInteger("posZ"));
        }
    }
}
