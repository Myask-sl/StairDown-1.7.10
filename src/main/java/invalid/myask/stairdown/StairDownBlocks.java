package invalid.myask.stairdown;

import cpw.mods.fml.common.registry.GameRegistry;
import invalid.myask.stairdown.blocks.BlockHollowLog;
import invalid.myask.stairdown.blocks.BlockLogStairs;
import invalid.myask.stairdown.blocks.BlockMadeStairs;
import invalid.myask.stairdown.items.ItemBlockFromParent;
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
        GameRegistry.registerBlock(furnace_stair, ItemBlockFromParent.class, "furnace.stairs0");
        furnace_stair.setCreativeTab(stair_down_tab);
        registerAStair("crafting_table", 0);
        for (int i = 0; i < 4; i++)
            registerALogStair("log", i);
        for (int i = 0; i < 2; i++)
            registerALogStair("log2", i);
        registerAHollowLog("log", 0);
        registerAHollowLog("log2", 0);
    }

    public static void registerModdedBonusBlocks() {
        registerAStair("BiomesOPlenty:planks", 10);
    }

    public static void registerABlockAlter(Block b, String oldname, String postfix) {
        String preformat = (oldname == null) ? b.getUnlocalizedName() : oldname;
        while (preformat.startsWith("tile."))
            preformat = preformat.substring(5);
        preformat = preformat.replace(':', '.'); //let's make the othermod
        b.setBlockName(preformat + postfix);
        GameRegistry.registerBlock(b, ItemBlockFromParent.class, preformat + postfix);
        b.setCreativeTab(stair_down_tab);
    }

    public static void registerALogStair(String s, int meta) {
        Block b = (Block) Block.blockRegistry.getObject(s);
        if (b == null) {
            StairDown.LOG.error("Block {} not found in registry to make log stair of, skipping...", s);
            return;
        }
        BlockMadeStairs bs = new BlockLogStairs(b, meta);
        registerABlockAlter(bs, s,".stairs" + meta);
        stairs.add(bs);
    }

    public static void registerAStair(String s, int meta) {
        Block b = (Block) Block.blockRegistry.getObject(s);
        if (b == null) {
            StairDown.LOG.error("Block {} not found in registry to make stair of, skipping...", s);
            return;
        }
        BlockMadeStairs bs = new BlockMadeStairs(b, meta);
        registerABlockAlter(bs, s,".stairs" + meta);
        stairs.add(bs);
    }

    public static void registerAHollowLog(String s, int meta) {
        Block b = (Block) Block.blockRegistry.getObject(s);
        if (b == null) {
            StairDown.LOG.error("Block {} not found in registry to make hollow log of, skipping...", s);
            return;
        }
        BlockHollowLog bhl = new BlockHollowLog(b, meta);
        registerABlockAlter(bhl, s,".hollow");
        logs.add(bhl);
    }
}
