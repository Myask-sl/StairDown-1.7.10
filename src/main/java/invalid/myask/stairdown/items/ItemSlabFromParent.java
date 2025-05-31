package invalid.myask.stairdown.items;

import invalid.myask.stairdown.blocks.BlockMadeSlab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;
import net.minecraft.realms.RealmsMth;

public class ItemSlabFromParent extends ItemSlab {
    BlockMadeSlab parent;
    public ItemSlabFromParent(BlockMadeSlab block, BlockMadeSlab slab, BlockSlab doubleSlab, boolean isDouble) {
        super(block, slab, doubleSlab, isDouble);
        parent = block;
    }

    @Override
    public int getMetadata(int damage) {
        return RealmsMth.clamp(damage, parent.getParentMeta(), parent.getMetaMax());
    }
}
