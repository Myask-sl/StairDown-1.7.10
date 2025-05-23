package invalid.myask.stairdown.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockMadeStairs extends BlockStairs {
    protected final Block parentBlock;
    protected final int parentMeta;
    /**
     * Ctor
     * @param parentBlock block from which to draw textures and behavior
     * @param parentMeta meta from which to draw textures and behavior--note that vanilla logs use upper two bits for orientation
     */
    public BlockMadeStairs(Block parentBlock, int parentMeta) {
        super(parentBlock, parentMeta);
        this.parentBlock = parentBlock;
        this.parentMeta = parentMeta;
        this.setBlockName(parentBlock.getUnlocalizedName());
        while (getUnlocalizedName().startsWith("tile.tile."))
            this.setBlockName(getUnlocalizedName().substring(10));
        useNeighborBrightness = true;
    }

    public final Block getParentBlock() {
        return parentBlock;
    }

    public final int getParentMeta() {
        return parentMeta;
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        super.registerBlockIcons(reg);
    }
}
