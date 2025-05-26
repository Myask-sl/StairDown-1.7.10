package invalid.myask.stairdown;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import cpw.mods.fml.common.registry.GameRegistry;
import invalid.myask.stairdown.blocks.BlockHollowLog;
import invalid.myask.stairdown.blocks.BlockMadeStairs;

public class StairDownRecipes {

    public static void registerRecipes() {
        if (Config.enable_osha_noncompliant_stairs) addStairRecipe(StairDownBlocks.FURNACE_STAIR);
        for (BlockMadeStairs bs : StairDownBlocks.MADE_STAIRS) addStairRecipe(bs);
        for (BlockHollowLog hl : StairDownBlocks.HOLLOW_LOGS) {
            addBigHollowLogRecipe(hl);
            addSmallHollowLogRecipe(hl);
        }
        if (Config.enable_giant_bamboo) {
            GameRegistry.addRecipe(new ShapedOreRecipe(
                new ItemStack(StairDownBlocks.GIANT_BAMBOO),
                "BB",
                "BB",
                'B', "plankBamboo"
            ));
            Block bamboo = GameRegistry.findBlock("biomesoplenty", "planks");
            ItemStack bambooStack = null;
            boolean foundBambooPlank = false;
            if (bamboo == Blocks.air) {
                List<ItemStack> l = OreDictionary.getOres("plankBamboo");
                if (l != OreDictionary.EMPTY_LIST) {
                    foundBambooPlank = true;
                    bambooStack = l.get(0);
                    bambooStack.stackSize = 4;
                }
            } else {
                foundBambooPlank = true;
                bambooStack = new ItemStack(bamboo, 4, 10);
            }
            if (foundBambooPlank) {
                GameRegistry.addShapelessRecipe(bambooStack,
                    StairDownBlocks.GIANT_BAMBOO);
            }
        }
    }

    private static void addStairRecipe(BlockMadeStairs stair) {
        GameRegistry.addShapedRecipe(
            new ItemStack(stair, Config.stair_output_qty),
            "a  ",
            "aa ",
            "aaa",
            'a', new ItemStack(stair.getParentBlock(), 1, stair.getParentMeta()));
    }

    private static void addBigHollowLogRecipe(BlockHollowLog hl) {
        if (Config.log_output_qty <= 0) return;
        List<ItemStack> subblocks = new ArrayList<>();
        hl.getSubBlocks(Item.getItemFromBlock(hl), null, subblocks);
        for (ItemStack stack : subblocks) {
            GameRegistry.addShapedRecipe(
                new ItemStack(hl, Config.log_output_qty, stack.getItemDamage()),
                "LLL",
                "L L",
                "LLL",
                'L', new ItemStack(hl.getParentBlock(), 1, stack.getItemDamage()));
        }
    }

    private static void addSmallHollowLogRecipe(BlockHollowLog hl) {
        if (Config.small_log_output_qty <= 0) return;
        List<ItemStack> subblocks = new ArrayList<>();
        hl.getSubBlocks(Item.getItemFromBlock(hl), null, subblocks);
        for (ItemStack stack : subblocks) {
            GameRegistry.addShapedRecipe(
                new ItemStack(hl, Config.small_log_output_qty, stack.getItemDamage()),
                " L ",
                "L L",
                " L ",
                'L', new ItemStack(hl.getParentBlock(), 1, stack.getItemDamage()));
        }
    }
}
