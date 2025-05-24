package invalid.myask.stairdown.client;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import invalid.myask.stairdown.blocks.BlockHollowLog;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class HollowLogRenderer implements ISimpleBlockRenderingHandler {
    public static final HollowLogRenderer instance = new HollowLogRenderer();
    public static final int RENDER_ID = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        int rotTop = renderer.uvRotateTop, rotBottom = renderer.uvRotateBottom,
            rotNorth = renderer.uvRotateNorth, rotSouth = renderer.uvRotateSouth,
            rotEast = renderer.uvRotateEast, rotWest = renderer.uvRotateWest;
        renderer.uvRotateNorth =
        renderer.uvRotateSouth =
        renderer.uvRotateEast =
        renderer.uvRotateWest =
        renderer.uvRotateTop =
        renderer.uvRotateBottom = 0;
        renderer.uvRotateNorth = rotNorth; //kindly restore
        renderer.uvRotateSouth = rotSouth;
        renderer.uvRotateEast = rotEast;
        renderer.uvRotateWest = rotWest;
        renderer.uvRotateTop = rotTop;
        renderer.uvRotateBottom = rotBottom;
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        if (block instanceof BlockHollowLog hollow) {
            int rotTop = renderer.uvRotateTop, rotBottom = renderer.uvRotateBottom,
                rotNorth = renderer.uvRotateNorth, rotSouth = renderer.uvRotateSouth,
                rotEast = renderer.uvRotateEast, rotWest = renderer.uvRotateWest;
            int meta = world.getBlockMetadata(x, y, z);
            boolean result = true;
            renderer.uvRotateEast =
                renderer.uvRotateWest = ((meta & 12) == 8) ? 1 : 0;

            renderer.uvRotateNorth =
                renderer.uvRotateSouth =
                    renderer.uvRotateTop =
                        renderer.uvRotateBottom = ((meta & 12) == 4) ? 1 : 0;
            Tessellator tessellator = Tessellator.instance;

            //meta &12 == 0: up/down. phase 0-3 goes
            //meta &12 == 8: n/s. phase 0-3 goes
            //meta &12 == 4: e/w. phase 0-3 goes
            for (int phase = 0; phase < 4; phase++) {
                hollow.setVariablesForRenderSide(phase, meta);
                renderer.setRenderBoundsFromBlock(hollow);
                result = renderer.renderStandardBlock(hollow, x, y, z) && result;
            }

            renderer.uvRotateNorth = rotNorth; //kindly restore
            renderer.uvRotateSouth = rotSouth;
            renderer.uvRotateEast = rotEast;
            renderer.uvRotateWest = rotWest;
            renderer.uvRotateTop = rotTop;
            renderer.uvRotateBottom = rotBottom;
            return result;
        }
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return RENDER_ID;
    }
}
