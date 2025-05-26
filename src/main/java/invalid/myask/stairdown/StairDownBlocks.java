package invalid.myask.stairdown;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;
import invalid.myask.stairdown.blocks.BlockBambooLog;
import invalid.myask.stairdown.blocks.BlockHollowLog;
import invalid.myask.stairdown.blocks.BlockLogStairs;
import invalid.myask.stairdown.blocks.BlockMadeStairs;
import invalid.myask.stairdown.items.ItemBlockFromParent;

public class StairDownBlocks {

    public static final List<BlockMadeStairs> MADE_STAIRS = new ArrayList<>();
    public static final List<BlockHollowLog> HOLLOW_LOGS = new ArrayList<>();
    public static final BlockMadeStairs FURNACE_STAIR = new BlockMadeStairs(Blocks.furnace, 0);
    public static final Block GIANT_BAMBOO = new BlockBambooLog();
    public static final CreativeTabs STAIR_DOWN_TAB = new CreativeTabs("stairdown") {

        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(FURNACE_STAIR);
        }
    };

    public static void registerVanillaLogBlocks() {
        if (Config.enable_osha_noncompliant_stairs) {
            GameRegistry.registerBlock(FURNACE_STAIR, ItemBlockFromParent.class, "furnace.stairs0");
            FURNACE_STAIR.setCreativeTab(STAIR_DOWN_TAB);
            registerAStair("crafting_table", 0);
        }
        if (Config.enable_vanilla_logstairs) {
            for (int i = 0; i < 4; i++) registerALogStair("log", i);
            for (int i = 0; i < 2; i++) registerALogStair("log2", i);
        }
        if (Config.enable_vanilla_hollow_logs) {
            registerAHollowLog("log", 0);
            registerAHollowLog("log2", 0);
        }
    }

    public static void registerGiantBamboo() {
        if (Config.enable_giant_bamboo) {
            GameRegistry.registerBlock(GIANT_BAMBOO, "bamboo_giant");
            OreDictionary.registerOre("plankBamboo", new ItemStack(GameRegistry.findBlock("BiomesOPlenty","planks"), 1, 10));
            if (Config.enable_hollow_bamboo) {
                registerAHollowLog("stairdown:bamboo_giant", 0);
            }
        }
    }

    public static void registerModdedBonusBlocks() {
        registerAStair("BiomesOPlenty:planks", 10);
    }

    public static void registerABlockAlter(Block b, String oldname, String postfix) {
        String preformat = (oldname == null) ? b.getUnlocalizedName() : oldname;
        while (preformat.startsWith("tile.")) preformat = preformat.substring(5);
        preformat = preformat.replace(':', '.'); // let's make the othermod
        b.setBlockName(preformat + postfix);
        GameRegistry.registerBlock(b, ItemBlockFromParent.class, preformat + postfix);
        b.setCreativeTab(STAIR_DOWN_TAB);
    }

    public static void registerALogStair(String s, int meta) {
        Block b = (Block) Block.blockRegistry.getObject(s);
        if (b == null || b == Blocks.air) {
            StairDown.LOG.error("Block {} not found in registry to make log stair of, skipping...", s);
            return;
        }
        BlockMadeStairs bs = new BlockLogStairs(b, meta);
        registerABlockAlter(bs, s, ".stairs" + meta);
        MADE_STAIRS.add(bs);
    }

    public static void registerAStair(String s, int meta) {
        Block b = (Block) Block.blockRegistry.getObject(s);
        if (b == null || b == Blocks.air) {
            StairDown.LOG.error("Block {} not found in registry to make stair of, skipping...", s);
            return;
        }
        BlockMadeStairs bs = new BlockMadeStairs(b, meta);
        registerABlockAlter(bs, s, ".stairs" + meta);
        MADE_STAIRS.add(bs);
    }

    public static void registerAHollowLog(String s, int meta) {
        Block b = (Block) Block.blockRegistry.getObject(s);
        if (b == null || b == Blocks.air) {
            StairDown.LOG.error("Block {} not found in registry to make hollow log of, skipping...", s);
            return;
        }
        BlockHollowLog bhl = new BlockHollowLog(b, meta);
        registerABlockAlter(bhl, s, ".hollow" + meta);
        HOLLOW_LOGS.add(bhl);
    }
}
