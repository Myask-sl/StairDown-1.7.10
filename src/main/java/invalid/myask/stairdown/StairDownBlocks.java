package invalid.myask.stairdown;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

import invalid.myask.stairdown.blocks.*;
import invalid.myask.stairdown.items.ItemBlockFromParent;
import invalid.myask.stairdown.items.ItemSlabFromParent;

public class StairDownBlocks {

    public static final List<BlockMadeStairs> MADE_STAIRS = new ArrayList<>();
    public static final List<BlockHollowLog> HOLLOW_LOGS = new ArrayList<>();
    public static final List<BlockMadeSlab> MADE_SLABS = new ArrayList<>();
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
            GameRegistry.registerBlock(FURNACE_STAIR, ItemBlockFromParent.class, "furnace.stairs.0");
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
        if (Config.enable_vanilla_logslabs) {
            registerASlab("log", 0, 3);
            registerASlab("log2", 0, 1);
        }
    }

    public static void registerGiantBamboo() {
        if (Config.enable_giant_bamboo) {
            GameRegistry.registerBlock(GIANT_BAMBOO, "bamboo_giant");
            GIANT_BAMBOO.setCreativeTab(StairDownBlocks.STAIR_DOWN_TAB);
            OreDictionary
                .registerOre("plankBamboo", new ItemStack(GameRegistry.findBlock("BiomesOPlenty", "planks"), 1, 10));
            if (Config.enable_hollow_bamboo) {
                registerAHollowLog("stairdown:bamboo_giant", 0);
            }
            if (Config.enable_slab_bamboo) {
                registerASlab("stairdown:bamboo_giant", 0, 0);
            }
        }
    }

    public static void registerModdedBonusBlocks() {
        registerAStair("BiomesOPlenty:planks", 10);
        registerASlab("BiomesOPlenty:planks", 10, 10);
    }

    public static void registerABlockAlter(Block b, String oldname, String postfix,
        Class<? extends ItemBlock> itemBlockClass, Object... itemBlockParams) {
        String preformat = (oldname == null) ? b.getUnlocalizedName() : oldname;
        while (preformat.startsWith("tile.")) preformat = preformat.substring(5);
        preformat = preformat.replace(':', '.'); // let's make the othermod
        b.setBlockName(preformat + postfix);
        GameRegistry.registerBlock(b, itemBlockClass, preformat + postfix, itemBlockParams);
        b.setCreativeTab(STAIR_DOWN_TAB);
    }

    public static void registerALogStair(String s, int meta) {
        Block b = (Block) Block.blockRegistry.getObject(s);
        if (b == null || b == Blocks.air) {
            StairDown.LOG.error("Block {} not found in registry to make log stair of, skipping...", s);
            return;
        }
        BlockMadeStairs bs = new BlockLogStairs(b, meta);
        registerABlockAlter(bs, s, ".stairs." + meta, ItemBlockFromParent.class);
        MADE_STAIRS.add(bs);
    }

    public static void registerAStair(String s, int meta) {
        Block b = (Block) Block.blockRegistry.getObject(s);
        if (b == null || b == Blocks.air) {
            StairDown.LOG.error("Block {} not found in registry to make stair of, skipping...", s);
            return;
        }
        BlockMadeStairs bs = new BlockMadeStairs(b, meta);
        registerABlockAlter(bs, s, ".stairs." + meta, ItemBlockFromParent.class);
        MADE_STAIRS.add(bs);
    }

    public static void registerAHollowLog(String s, int meta) {
        Block b = (Block) Block.blockRegistry.getObject(s);
        if (b == null || b == Blocks.air) {
            StairDown.LOG.error("Block {} not found in registry to make hollow log of, skipping...", s);
            return;
        }
        BlockHollowLog bhl = new BlockHollowLog(b, meta);
        registerABlockAlter(bhl, s, ".hollow." + meta, ItemBlockFromParent.class);
        HOLLOW_LOGS.add(bhl);
    }

    public static void registerASlab(String s, int metaMin, int metaMax) {
        Block b = (Block) Block.blockRegistry.getObject(s);
        if (b == null || b == Blocks.air) {
            StairDown.LOG.error("Block {} not found in registry to make slab of, skipping...", s);
            return;
        }
        BlockMadeSlab bs = new BlockMadeSlab(false, b, metaMin, metaMax);
        BlockMadeSlab bds = new BlockMadeSlab(true, b, metaMin, metaMax);
        registerABlockAlter(bs, s, ".slab." + metaMin + ".." + metaMax, ItemSlabFromParent.class, bs, bds, false);
        registerABlockAlter(bds, s, ".doubleSlab." + metaMin + ".." + metaMax, ItemSlabFromParent.class, bs, bds, true);
        MADE_SLABS.add(bs);
        MADE_SLABS.add(bds);
    }
}
