package natureforce.registries;

import natureforce.api.recipe.RecipePlantGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class NFRecipeRegistry {

    public void init(){
        registerCraftingRecipes();
        registerPlantGeneratorRecipes();
    }


    private void registerCraftingRecipes(){

    }

    private void registerPlantGeneratorRecipes(){
        for (ItemStack itemStack: OreDictionary.getOres("logWood"))
            RecipePlantGenerator.addRecipe(Block.getBlockFromItem(itemStack.getItem()).getDefaultState(), 2, 5);
        RecipePlantGenerator.addRecipe(Blocks.carrots.getStateFromMeta(7), 10, 3, false, false);
        RecipePlantGenerator.addRecipe(Blocks.wheat.getStateFromMeta(7), 10, 6, false, false);
        RecipePlantGenerator.addRecipe(Blocks.potatoes.getStateFromMeta(7), 10, 6, false, false);
        RecipePlantGenerator.addRecipe(Blocks.beetroots.getStateFromMeta(3), 10, 6, false, false);
    }
}
