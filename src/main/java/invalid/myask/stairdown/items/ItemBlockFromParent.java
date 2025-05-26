package invalid.myask.stairdown.items;

import invalid.myask.stairdown.blocks.BlockHollowLog;
import invalid.myask.stairdown.blocks.BlockMadeStairs;
import invalid.myask.stairdown.api.IParentBlock;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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
                list.add(I18n.format("tooltips.hollow"));
            else if (field_150939_a instanceof BlockMadeStairs)
                list.add(I18n.format("tooltips.stairs"));
        }
    }

    ItemStack cachedStack, fakeMetaStack;

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (cachedStack == null || stack != cachedStack) {
            fakeMetaStack = stack.copy();
            cachedStack = stack;
            fakeMetaStack.setItemDamage(((IParentBlock) field_150939_a).getParentMeta());
        }
        Item nullcheck = getItemFromBlock(((IParentBlock) field_150939_a).getParentBlock());
        return nullcheck != null ? nullcheck.getUnlocalizedName(fakeMetaStack) : "UNKNOWN ITEMPARENT";
    }
}
