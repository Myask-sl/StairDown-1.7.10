package invalid.myask.stairdown.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockLogStairs extends BlockStairs {
    /**
     * Ctor
     * @param parentBlock block from which to draw textures and behavior
     * @param parentMeta meta from which to draw textures and behavior--note that vanilla logs use upper two bits for orientation
     */
    public BlockLogStairs(Block parentBlock, int parentMeta) {
        super(parentBlock, parentMeta);
    }
}
