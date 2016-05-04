package natureforce.api.recipe;

import natureforce.api.NatureForceAPI;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RecipePlantGenerator {
    public IBlockState block;
    public int unitsPerDecayPercentage;
    public int ticks;
    public boolean hasCustomHandling;
    public boolean ignoreMeta;

    /**
     * Makes a plant work with the plant generator
     *
     * @param block                   the plant that can be consumed
     * @param unitsPerDecayPercentage the amount of Nature Force that can be generated from the plant
     * @param ticks                   the amount of ticks until the block increases it's decay percentage
     * @param hasCustomHandling       if the machine needs to do any special handling to decay the plant (for example trees)
     * @param ignoreMeta              whether or not the generator needs to check for meta
     */
    public RecipePlantGenerator(IBlockState block, int unitsPerDecayPercentage, int ticks, boolean hasCustomHandling, boolean ignoreMeta) {
        this.block = block;
        this.unitsPerDecayPercentage = unitsPerDecayPercentage;
        this.ticks = ticks;
        this.hasCustomHandling = hasCustomHandling;
        this.ignoreMeta = ignoreMeta;
    }

    /**
     * put the special handling code here
     *
     * @param world the world the te is in
     * @param pos   the position of the PLANT (2 above the generator)
     */
    public void customHandling(World world, BlockPos pos) {

    }

    public static void addRecipe(IBlockState block, int unitsPerDecayPercentage, int ticks) {
        NatureForceAPI.plantGeneratorRecipes.add(new RecipePlantGenerator(block, unitsPerDecayPercentage, ticks, false, true));
    }

    /**
     * Makes a plant work with the plant generator
     *
     * @param block                   the plant that can be consumed
     * @param unitsPerDecayPercentage the amount of Nature Force that can be generated from the plant
     * @param ticks                   the amount of ticks until the block increases it's decay percentage
     * @param hasCustomHandling       if the machine needs to do any special handling to decay the plant (for example trees)
     * @param ignoreMeta              whether or not the generator needs to check for meta
     */
    public static void addRecipe(IBlockState block, int unitsPerDecayPercentage, int ticks, boolean hasCustomHandling, boolean ignoreMeta) {
        NatureForceAPI.plantGeneratorRecipes.add(new RecipePlantGenerator(block, unitsPerDecayPercentage, ticks, hasCustomHandling, ignoreMeta));
    }

    /**
     * returns the recipe for a block
     *
     * @param block most probably a plant
     * @return the recipe for the plant
     */
    public static RecipePlantGenerator getRecipeFromBlockState(IBlockState block) {
        for (RecipePlantGenerator recipe : NatureForceAPI.plantGeneratorRecipes) {
            if (recipe.ignoreMeta) {
                if (recipe.block.getBlock() == block.getBlock())
                    return recipe;
            } else if (recipe.block == block)
                return recipe;
        }
        return null;
    }
}
