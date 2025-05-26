package invalid.myask.stairdown.client;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import invalid.myask.stairdown.blocks.BlockHollowLog;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class HollowLogRenderer implements ISimpleBlockRenderingHandler {
    public static final HollowLogRenderer instance = new HollowLogRenderer();
    public static final int RENDER_ID = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        if (modelId != RENDER_ID) return;
        if (block instanceof BlockHollowLog hollow) {
            int rotTop = renderer.uvRotateTop, rotBottom = renderer.uvRotateBottom,
                rotNorth = renderer.uvRotateNorth, rotSouth = renderer.uvRotateSouth,
                rotEast = renderer.uvRotateEast, rotWest = renderer.uvRotateWest;
            renderer.uvRotateNorth =
                renderer.uvRotateSouth =
                    renderer.uvRotateEast =
                        renderer.uvRotateWest =
                            renderer.uvRotateTop =
                                renderer.uvRotateBottom = 0;
            int facing = (metadata & 12) == 0 ? 0 : (metadata & 12) == 8 ? 2 : 4;
            if (facing == 4)
            {
                renderer.uvRotateBottom = 1;
                renderer.uvRotateTop = 1;
                renderer.uvRotateEast = 1; //rotate the side
                renderer.uvRotateWest = 1;
            } else if (facing == 2) {
                renderer.uvRotateNorth = 1;
                renderer.uvRotateSouth = 1;
            }
            GL11.glPushMatrix();

            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            Tessellator.instance.startDrawingQuads();

            for (int phase = 0; phase < 4; phase++) {
                hollow.setVariablesForRenderSide(phase, metadata);
                renderer.setRenderBoundsFromBlock(hollow);
                renderAStandardCuboid(hollow, metadata, renderer);
            }
            Tessellator.instance.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            GL11.glPopMatrix();
            renderer.uvRotateNorth = rotNorth; //kindly restore
            renderer.uvRotateSouth = rotSouth;
            renderer.uvRotateEast = rotEast;
            renderer.uvRotateWest = rotWest;
            renderer.uvRotateTop = rotTop;
            renderer.uvRotateBottom = rotBottom;
        }
    }

    public static void renderAStandardCuboid(Block block, int metadata, RenderBlocks renderer) {
        Tessellator.instance.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
        Tessellator.instance.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
        Tessellator.instance.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
        Tessellator.instance.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
        Tessellator.instance.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
        Tessellator.instance.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        if (block instanceof BlockHollowLog hollow) {
            int rotTop = renderer.uvRotateTop, rotBottom = renderer.uvRotateBottom,
                rotNorth = renderer.uvRotateNorth, rotSouth = renderer.uvRotateSouth,
                rotEast = renderer.uvRotateEast, rotWest = renderer.uvRotateWest;
            int meta = world.getBlockMetadata(x, y, z);
            boolean result = true;
            renderer.uvRotateNorth =
                renderer.uvRotateSouth = ((meta & 12) == 8) ? 1 : 0;

            renderer.uvRotateEast =
                renderer.uvRotateWest =
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
