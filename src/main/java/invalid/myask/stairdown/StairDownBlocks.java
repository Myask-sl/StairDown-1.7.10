package invalid.myask.stairdown;

import cpw.mods.fml.common.registry.GameRegistry;
import invalid.myask.stairdown.blocks.BlockLogStairs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

@GameRegistry.ObjectHolder(StairDown.MODID)
public class StairDownBlocks {
    public static final List<Block> stairs = new ArrayList<>();
    public static final Block furnaceStair = new BlockStairs(Blocks.furnace, 0);
    public static final CreativeTabs stairTab = new CreativeTabs("stairdown.tab") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(furnaceStair);
        }
    };
    public static void registerVanillaLogBlocks() {
        GameRegistry.registerBlock(furnaceStair, "furnace_stairs");
        furnaceStair.setCreativeTab(stairTab);
        for (int i = 0; i < 4; i++) {
            registerALogStair(Blocks.log, i);
        }
        for (int i = 0; i < 2; i++) {
            registerALogStair(Blocks.log2, i);
        }
    }

    public static void registerALogStair(Block b, int meta) {
        BlockStairs bs = new BlockLogStairs(b, meta);
        stairs.add(bs);
        String preformat = b.getUnlocalizedName();
        if (preformat.startsWith("tile."))
            preformat = preformat.substring(5);
        bs.setBlockName(preformat + ".stairs");
        GameRegistry.registerBlock(bs, bs.getUnlocalizedName());
        bs.setCreativeTab(stairTab);
    }

    public static Block registerAStair(Block b, int meta) {
        BlockStairs bs = new BlockStairs(b, meta);
        stairs.add(bs);
        String preformat = b.getUnlocalizedName();
        if (preformat.startsWith("tile."))
            preformat = preformat.substring(5);
        bs.setBlockName(preformat + ".stairs");
        GameRegistry.registerBlock(bs, bs.getUnlocalizedName());
        bs.setCreativeTab(stairTab);
    }
}
