package natureforce.registries;

import natureforce.NatureForce;
import natureforce.items.ItemRelayLinker;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class NFItemRegistry {
    public static List<Item> ITEMS = new ArrayList<>();

    public final Item relayLinker = new ItemRelayLinker();

    public void preInit(){
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                if (field.getType().isAssignableFrom(Item.class)) {
                    Item item = (Item) field.get(this);
                    registerItem(item);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void init() {
        for (Item item : ITEMS) {
            NatureForce.proxy.registerDefaultItemRenderer(item);
        }
    }


    private void registerItem(Item item) {
        GameRegistry.register(item);
        ITEMS.add(item);
    }
}
