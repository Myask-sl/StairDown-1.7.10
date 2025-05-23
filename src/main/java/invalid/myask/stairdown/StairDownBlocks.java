package invalid.myask.stairdown;

import cpw.mods.fml.common.registry.GameRegistry;
import invalid.myask.stairdown.blocks.BlockHollowLog;
import invalid.myask.stairdown.blocks.BlockLogStairs;
import invalid.myask.stairdown.blocks.BlockMadeStairs;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class StairDownBlocks {
    public static final List<BlockMadeStairs> stairs = new ArrayList<>();
    public static final List<BlockHollowLog> logs = new ArrayList<>();
    public static final BlockMadeStairs furnace_stair = new BlockMadeStairs(Blocks.furnace, 0);
    public static final CreativeTabs stair_down_tab = new CreativeTabs("stairdown.tab") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(furnace_stair);
        }
    };
    public static void registerVanillaLogBlocks() {
        GameRegistry.registerBlock(furnace_stair, "furnace_stairs");
        furnace_stair.setCreativeTab(stair_down_tab);
        for (int i = 0; i < 4; i++) {
            registerALogStair(Blocks.log, i);
        }
        for (int i = 0; i < 2; i++) {
            registerALogStair(Blocks.log2, i);
        }
        registerAHollowLog(Blocks.log, 0);
        registerAHollowLog(Blocks.log2, 0);
    }

    public static void registerABlockAlter(Block b, String postfix) {
        String preformat = b.getUnlocalizedName();
        if (preformat.startsWith("tile."))
            preformat = preformat.substring(5);
        b.setBlockName(preformat + postfix);
        GameRegistry.registerBlock(b, b.getUnlocalizedName().substring(5));
        b.setCreativeTab(stair_down_tab);
    }

    public static void registerALogStair(Block b, int meta) {
        BlockMadeStairs bs = new BlockLogStairs(b, meta);
        registerABlockAlter(bs, ".stairs");
        stairs.add(bs);
    }

    public static void registerAStair(Block b, int meta) {
        BlockMadeStairs bs = new BlockMadeStairs(b, meta);
        registerABlockAlter(bs, ".stairs");
        stairs.add(bs);
    }

    public static void registerAHollowLog(Block b, int meta) {
        BlockHollowLog bhl = new BlockHollowLog(b, meta);
        registerABlockAlter(bhl, ".hollow");
        logs.add(bhl);
    }
}
