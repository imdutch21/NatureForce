package natureforce.util;

import natureforce.NatureForce;
import natureforce.blocks.ICustomJsonGenerationBlock;
import natureforce.items.ICustomJsonGenerationItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonRenderGenerator {
    private static File assetsFolder = new File(NatureForce.sourceFile, "assets/natureforce");

    public static final String BLOCK_STATE_NORMAL = "{ \n" +
            "  \"variants\": { \n" +
            "    \"normal\": {\n" +
            "      \"model\": \"natureforce:%s\"\n" +
            "    } \n" +
            "  } \n" +
            "}";//<model name>
    public static final String BLOCK_CUBE_ALL_FORMAT = "{ \n" +
            "  \"parent\": \"block/cube_all\",\n" +
            "  \"textures\": {\n" +
            "    \"all\": \"natureforce:blocks/%s\"\n" +
            "  } \n" +
            "}";//<block texture name>

    public static final String BLOCK_CUBE_FORMAT = "{ \n" +
            "  \"parent\": \"block/cube\",\n" +
            "  \"textures\": {\n" +
            "    \"particle\": \"natureforce:blocks/%s\", \n" +
            "    \"up\": \"natureforce:blocks/%s\", \n" +
            "    \"down\": \"natureforce:blocks/%s\", \n" +
            "    \"north\": \"natureforce:blocks/%s\", \n" +
            "    \"east\": \"natureforce:blocks/%s\", \n" +
            "    \"south\": \"natureforce:blocks/%s\", \n" +
            "    \"west\": \"natureforce:blocks/%s\" \n" +
            "  } \n" +
            "}";//<particle texture>/<top texture>/<bottom texture>/<north texture>/<east texture>/<south texture>/<west texture>

    public static final String BLOCK_ORIENTABLE = "{ \n" +
            "  \"parent\": \"block/orientable\", \n" +
            "  \"textures\": {\n" +
            "    \"top\": \"natureforce:blocks/%s\", \n" +
            "    \"bottom\": \"natureforce:blocks/%s\", \n" +
            "    \"front\": \"natureforce:blocks/%s\", \n" +
            "    \"side\": \"natureforce:blocks/%s\" \n" +
            "  }\n" +
            "}";//<top texture>/<bottom texture>/<front texture>/<side texture>

    public static final String BLOCK_CUBE_COLUMN_FORMAT = "{ \n" +
            "  \"parent\": \"block/cube_column\", \n" +
            "  \"textures\": { \n" +
            "    \"side\": \"natureforce:blocks/%s\", \n" +
            "    \"end\": \"natureforce:blocks/%s\" \n" +
            "  }\n" +
            "}"; //<block side texture>/<block end texture name>

    public static final String BLOCK_CROSS_FORMAT = "{ \n" +
            "  \"parent\": \"block/cross\",\n" +
            "  \"textures\": {\n" +
            "    \"cross\": \"natureforce:blocks/%s\"\n" +
            "  } \n" +
            "}";

    public static final String ITEM_BLOCK_DEFAULT_FORMAT = "{ \n" +
            "  \"parent\":\"natureforce:block/%s\" \n" +
            "}"; //<block name>

    public static final String ITEM_DEFAULT_FORMAT = "{ \n" +
            "  \"parent\": \"item/generated\", \n" +
            "  \"textures\": { \n" +
            "    \"layer0\": \"natureforce:items/%s\" \n" +
            "  }\n" +
            "}"; //<texture name>

    public static final String ITEM_TOOL_FORMAT = "{ \n" +
            "  \"parent\": \"item/handheld\", \n" +
            "  \"textures\": { \n" +
            "    \"layer0\": \"natureforce:items/%s\" \n" +
            "  }\n" +
            "}"; //<texture name>


    /**
     * makes the json text for a block with block states
     *
     * @param blockstateName the name of the blockstate
     * @param values         a map that contains a value and the corresponding model(with natureforce and quotes)
     * @return the json text for a block with block states
     */
    public static String complexBlockStates(String blockstateName, Map<String, String> values) {
        String string = "{ \n" +
                "  \"variants\": { \n";

        int i = 0;
        int size = values.entrySet().size();
        for (Object o : values.entrySet()) {
            i++;
            Map.Entry pair = (Map.Entry) o;
            string += String.format("    \"%s=%s\":{ \"model\":%s }" + (i < size ? "," : "") + " \n", blockstateName, pair.getKey(), pair.getValue());
        }

        string += "  } \n" +
                "}";

        return string;
    }


    public static void createJSONForItem(Item item, int meta, String itemName) {
        (new File(assetsFolder, "models")).mkdir();
        File dir = new File(assetsFolder, "models/item");
        dir.mkdir();
        File file = new File(dir, itemName + ".json");
        String jsonText;
        if (item instanceof ICustomJsonGenerationItem)
            jsonText = ((ICustomJsonGenerationItem) item).getJsonText(meta);
        else if (item instanceof ItemTool)
            jsonText = String.format(ITEM_TOOL_FORMAT, itemName);
        else
            jsonText = String.format(ITEM_DEFAULT_FORMAT, itemName);

        try {
            if (!file.exists() && file.createNewFile()) {
                PrintWriter writer = new PrintWriter(file);
                writer.println(jsonText);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void createJSONForBlock(Block block, String blockName) {
        (new File(assetsFolder, "models")).mkdir();
        createBlockStateJSON(block, blockName);
        createBlockModelJSON(block, blockName);
        createBlockItemJSON(block, blockName);
    }


    private static void createBlockStateJSON(Block block, String blockName) {
        File dir = new File(assetsFolder, "blockstates");
        dir.mkdir();
        File file = new File(dir, blockName + ".json");

        String jsonText;
        if (block instanceof ICustomJsonGenerationBlock && ((ICustomJsonGenerationBlock) block).getBlockStateText() != null)
            jsonText = ((ICustomJsonGenerationBlock) block).getBlockStateText();
        else
            jsonText = String.format(BLOCK_STATE_NORMAL, blockName);

        try {
            if (!file.exists() && file.createNewFile()) {
                PrintWriter writer = new PrintWriter(file);
                writer.println(jsonText);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createBlockModelJSON(Block block, String blockName) {
        File dir = new File(assetsFolder, "models/block");
        dir.mkdir();

        List<Integer> list = new ArrayList<>();
        if (block instanceof ICustomJsonGenerationBlock) {
            ((ICustomJsonGenerationBlock) block).getMetas(list);
        }
        if (list.size() == 0)
            list.add(0);
        for (int i : list) {
            File file;
            if (block instanceof ICustomJsonGenerationBlock)
                file = new File(dir, ((ICustomJsonGenerationBlock) block).getFileNameFromMeta(i) + ".json");
            else
                file = new File(dir, blockName + ".json");
            String jsonText;
            if (block instanceof ICustomJsonGenerationBlock && ((ICustomJsonGenerationBlock) block).getBlockModelText(i) != null)
                jsonText = ((ICustomJsonGenerationBlock) block).getBlockModelText(i);
            else
                jsonText = String.format(BLOCK_CUBE_ALL_FORMAT, blockName);

            try {
                if (!file.exists() && file.createNewFile()) {
                    PrintWriter writer = new PrintWriter(file);
                    writer.println(jsonText);
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createBlockItemJSON(Block block, String blockName) {
        File dir = new File(assetsFolder, "models/item");
        dir.mkdir();

        File file = new File(dir, blockName + ".json");
        if (block instanceof ICustomJsonGenerationBlock)
            blockName = ((ICustomJsonGenerationBlock) block).getBlockModelForItem();
        String jsonText = String.format(ITEM_BLOCK_DEFAULT_FORMAT, blockName);

        try {
            if (!file.exists() && file.createNewFile()) {
                PrintWriter writer = new PrintWriter(file);
                writer.println(jsonText);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
