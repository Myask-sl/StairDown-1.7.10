package invalid.myask.stairdown;

import cpw.mods.fml.common.registry.GameRegistry;
import invalid.myask.stairdown.blocks.BlockHollowLog;
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
    public static final List<BlockHollowLog> logs = new ArrayList<>();
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
        registerAHollowLog(Blocks.log, 0);
        registerAHollowLog(Blocks.log2, 0);
    }

    public static void registerABlockAlter(Block b, String postfix) {
        String preformat = b.getUnlocalizedName();
        if (preformat.startsWith("tile."))
            preformat = preformat.substring(5);
        b.setBlockName(preformat + postfix);
        GameRegistry.registerBlock(b, b.getUnlocalizedName().substring(5));
        b.setCreativeTab(stairTab);
    }

    public static void registerALogStair(Block b, int meta) {
        BlockStairs bs = new BlockLogStairs(b, meta);
        registerABlockAlter(b, ".stairs");
        stairs.add(bs);
    }

    public static void registerAStair(Block b, int meta) {
        BlockStairs bs = new BlockStairs(b, meta);
        registerABlockAlter(b, ".stairs");
        stairs.add(bs);
    }

    public static void registerAHollowLog(Block b, int meta) {
        BlockHollowLog bs = new BlockHollowLog(b, meta);
        registerABlockAlter(b, ".hollow");
        logs.add(bs);
    }
}
