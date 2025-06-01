package invalid.myask.stairdown.items;

import invalid.myask.stairdown.blocks.BlockMadeSlab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;
import net.minecraft.realms.RealmsMth;

public class ItemSlabFromParent extends ItemSlab {
    BlockMadeSlab parent;
    public ItemSlabFromParent(Block thisBlock, Block parent, BlockMadeSlab slab, BlockMadeSlab doubleSlab, Boolean isDouble) {
        super(thisBlock, slab, doubleSlab, isDouble);
        parent = (BlockMadeSlab) parent;
    }

    @Override
    public int getMetadata(int damage) {
        return RealmsMth.clamp(damage, parent.getParentMeta(), parent.getMetaMax());
    }
}
