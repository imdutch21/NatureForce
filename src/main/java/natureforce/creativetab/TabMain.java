package natureforce.creativetab;

import natureforce.lib.References;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class TabMain extends NFCreativeTab {

    public TabMain() {
        super(References.NAME_PREFIX + "creativetab.main");
    }

    @Override
    public Item getTabIconItem() {
        return Items.potato;
    }
}
