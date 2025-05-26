package invalid.myask.stairdown.blocks;

import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import invalid.myask.stairdown.StairDownBlocks;

public class BlockBambooLog extends BlockLog {

    public static final String[] TEXTURE_SUB_NAMES = new String[] { "bamboo" };

    BlockBambooLog() {
        super();
        setCreativeTab(StairDownBlocks.stair_down_tab);
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        field_150167_a = new IIcon[TEXTURE_SUB_NAMES.length]; // side
        field_150166_b = new IIcon[TEXTURE_SUB_NAMES.length]; // top
        for (int i = 0; i < TEXTURE_SUB_NAMES.length; i++) {
            this.field_150167_a[i] = reg.registerIcon(this.getTextureName() + "_" + TEXTURE_SUB_NAMES[i]);
            this.field_150166_b[i] = reg.registerIcon(this.getTextureName() + "_" + TEXTURE_SUB_NAMES[i] + "_top");
        }
    }
}
