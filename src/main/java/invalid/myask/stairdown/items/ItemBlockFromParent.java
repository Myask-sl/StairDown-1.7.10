package invalid.myask.stairdown.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

import invalid.myask.stairdown.api.IParentedBlock;
import invalid.myask.stairdown.blocks.BlockHollowLog;
import invalid.myask.stairdown.blocks.BlockMadeStairs;

public class ItemBlockFromParent extends ItemBlockWithMetadata {

    public ItemBlockFromParent(Block b) {
        this(b, b);
    }

    public ItemBlockFromParent(Block b, Block b1) {
        this(b, b1, "");
    }

    public ItemBlockFromParent(Block b, Block b1, String s) { //TODO: remember what s was for
        super(b, b1);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advancedTooltips) {
        super.addInformation(stack, player, list, advancedTooltips);
        if (advancedTooltips) {
            if (field_150939_a instanceof BlockHollowLog) list.add(I18n.format("tooltips.hollow"));
            else if (field_150939_a instanceof BlockMadeStairs) list.add(I18n.format("tooltips.stairs"));
        }
    }

    ItemStack cachedStack, fakeMetaStack;

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (cachedStack == null || stack != cachedStack) {
            fakeMetaStack = stack.copy();
            cachedStack = stack;
//            fakeMetaStack.setItemDamage(((IParentedBlock) field_150939_a).getParentMeta()); // TODO: fix to allow current
                                                                                          // meta through somewise
        }
        Item nullcheck = getItemFromBlock(((IParentedBlock) field_150939_a).getParentBlock());
        return nullcheck != null ? nullcheck.getUnlocalizedName(fakeMetaStack) : "UNKNOWN ITEMPARENT";
    }
}
