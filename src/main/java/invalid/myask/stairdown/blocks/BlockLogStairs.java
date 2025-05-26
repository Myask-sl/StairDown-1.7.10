package invalid.myask.stairdown.blocks;

import net.minecraft.block.Block;
import net.minecraft.util.IIcon;

public class BlockLogStairs extends BlockMadeStairs {

    public BlockLogStairs(Block parent, int meta) {
        super(parent, meta);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return parentBlock.getIcon(side, parentMeta);
    }
}
