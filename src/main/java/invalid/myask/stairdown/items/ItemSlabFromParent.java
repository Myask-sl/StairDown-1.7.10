package invalid.myask.stairdown.items;

import invalid.myask.stairdown.api.IParentedBlock;
import invalid.myask.stairdown.blocks.BlockHollowLog;
import invalid.myask.stairdown.blocks.BlockMadeSlab;
import invalid.myask.stairdown.blocks.BlockMadeStairs;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.realms.RealmsMth;

import java.util.List;

public class ItemSlabFromParent extends ItemSlab {
    BlockMadeSlab theSlab;
    public ItemSlabFromParent(Block thisBlock, BlockMadeSlab slab, BlockMadeSlab doubleSlab, Boolean isDouble) {
        super(thisBlock, slab, doubleSlab, isDouble);
        theSlab = (BlockMadeSlab) thisBlock;
    }

    @Override
    public int getMetadata(int damage) {
        return RealmsMth.clamp(damage, theSlab.getParentMeta(), theSlab.getMetaMax());
    }


    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean advancedTooltips) {
        super.addInformation(stack, player, list, advancedTooltips);
        if (advancedTooltips) {
            if (field_150939_a instanceof BlockMadeSlab) list.add(I18n.format("tooltips.slab"));
        }
    }

    ItemStack cachedStack, fakeMetaStack;

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (cachedStack == null || stack != cachedStack) {
            fakeMetaStack = stack.copy();
            cachedStack = stack;
            fakeMetaStack.setItemDamage(RealmsMth.clamp(fakeMetaStack.getItemDamage(),
                theSlab.getParentMeta(), theSlab.getMetaMax())); // TODO: fix to allow current
            // meta through somewise
        }
        Item nullcheck = getItemFromBlock(((IParentedBlock) field_150939_a).getParentBlock());
        return nullcheck != null ? nullcheck.getUnlocalizedName(fakeMetaStack) : "UNKNOWN ITEMPARENT";
    }
}
