package invalid.myask.stairdown.api;

import net.minecraft.block.Block;

public interface IParentBlock {
    Block getParentBlock();
    int getParentMeta();
}
