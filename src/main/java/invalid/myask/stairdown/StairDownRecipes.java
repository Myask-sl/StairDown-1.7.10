package invalid.myask.stairdown;

import cpw.mods.fml.common.registry.GameRegistry;
import invalid.myask.stairdown.blocks.BlockHollowLog;
import invalid.myask.stairdown.blocks.BlockMadeStairs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class StairDownRecipes {
    public static void registerRecipes() {
        addStairRecipe(StairDownBlocks.furnace_stair);
        for (BlockMadeStairs bs : StairDownBlocks.stairs)
            addStairRecipe(bs);
        for (BlockHollowLog hl : StairDownBlocks.logs) {
            addBigHollowLogRecipe(hl);
            addSmallHollowLogRecipe(hl);
        }
    }

    private static void addStairRecipe(BlockMadeStairs stair) {
        GameRegistry.addShapedRecipe(new ItemStack(stair, Config.stair_output_qty),
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
            GameRegistry.addShapedRecipe(new ItemStack(hl, Config.log_output_qty, stack.getItemDamage()),
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
            GameRegistry.addShapedRecipe(new ItemStack(hl, Config.small_log_output_qty, stack.getItemDamage()),
                " L ",
                "L L",
                " L ",
                'L', new ItemStack(hl.getParentBlock(), 1, stack.getItemDamage()));
        }
    }
}
