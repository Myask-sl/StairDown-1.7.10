package invalid.myask.stairdown.blocks;

import invalid.myask.stairdown.StairDown;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockBambooLog extends BlockLog {

    public static final String[] TEXTURE_SUB_NAMES = new String[] { "giant" };

    public BlockBambooLog() {
        super();
        setBlockTextureName("bamboo");
        setBlockName("bamboo_giant");
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        field_150167_a = new IIcon[TEXTURE_SUB_NAMES.length]; // side
        field_150166_b = new IIcon[TEXTURE_SUB_NAMES.length]; // top
        for (int i = 0; i < TEXTURE_SUB_NAMES.length; i++) {
            this.field_150167_a[i] = reg.registerIcon(StairDown.MODID + ":" + this.getTextureName() + "_" + TEXTURE_SUB_NAMES[i]);
            this.field_150166_b[i] = reg.registerIcon(StairDown.MODID + ":" + this.getTextureName() + "_" + TEXTURE_SUB_NAMES[i] + "_top");
        }
    }
}
