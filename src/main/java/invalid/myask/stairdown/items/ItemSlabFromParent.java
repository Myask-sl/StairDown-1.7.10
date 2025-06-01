package invalid.myask.stairdown.items;

import invalid.myask.stairdown.blocks.BlockMadeSlab;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSlab;
import net.minecraft.realms.RealmsMth;

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
}
