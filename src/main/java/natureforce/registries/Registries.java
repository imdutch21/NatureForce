package natureforce.registries;

public class Registries {
    public static final Registries INSTANCE = new Registries();

    public final NFBlockRegistry blockRegistry = new NFBlockRegistry();
    public final NFRecipeRegistry recipeRegistry = new NFRecipeRegistry();
    public final NFItemRegistry itemRegistry = new NFItemRegistry();
    public final NFEntityRegistry entityRegistry = new NFEntityRegistry();
    public final NFTileEntityRegistry tileEntityRegistry = new NFTileEntityRegistry();

    public void preInit() {
        blockRegistry.preInit();
        itemRegistry.preInit();
        entityRegistry.preInit();
        tileEntityRegistry.preInit();
    }

    public void init() {
        blockRegistry.init();
        recipeRegistry.init();
    }
}
