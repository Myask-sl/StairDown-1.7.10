package invalid.myask.stairdown.items;

import invalid.myask.stairdown.blocks.BlockHollowLog;
import invalid.myask.stairdown.blocks.BlockMadeStairs;
import invalid.myask.stairdown.api.IParentBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockFromParent extends ItemBlockWithMetadata {
    public ItemBlockFromParent(Block b) {
        this(b, b);
    }

    public ItemBlockFromParent(Block b, Block b1) {
        super(b, b1);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advancedTooltips) {
        super.addInformation(stack, player, list, advancedTooltips);
        if (advancedTooltips) {
            if (field_150939_a instanceof BlockHollowLog)
                list.add("tooltips.hollow");
            else if (field_150939_a instanceof BlockMadeStairs)
                list.add("tooltips.stairs");
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getItemFromBlock(((IParentBlock) field_150939_a).getParentBlock()).getUnlocalizedName(stack);
    }
}
