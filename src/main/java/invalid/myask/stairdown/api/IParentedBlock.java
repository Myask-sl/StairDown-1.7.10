package invalid.myask.stairdown.api;

import net.minecraft.block.Block;

public interface IParentedBlock {

    Block getParentBlock();

    int getParentMeta();
}
