package invalid.myask.stairdown.blocks;

import invalid.myask.stairdown.Config;
import invalid.myask.stairdown.client.HollowLogRenderer;
import invalid.myask.stairdown.client.HollowTextures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockHollowLog extends BlockRotatedPillar {
    static final Material HOLLOW_WOOD = new Material(Material.wood.getMaterialMapColor()) {
        @Override
        public boolean isOpaque() {
            return false;
        }
    };

    final Block parentBlock;
    final int parentMeta;
    public BlockHollowLog(Block parent, int meta) {
        super(HOLLOW_WOOD);
        this.parentBlock = parent;
        this.parentMeta = meta;
        this.setBlockName(parent.getUnlocalizedName());
        while (getUnlocalizedName().startsWith("tile.tile."))
            this.setBlockName(getUnlocalizedName().substring(10));
        inSide = new ThreadLocal<>();
        inSide.set(-1);
    }

    @Override
    public void addCollisionBoxesToList(World worldIn, int x, int y, int z, AxisAlignedBB mask, List<AxisAlignedBB> outputList, Entity collider) {
        List<AxisAlignedBB> list = new ArrayList<>();
        int meta = worldIn.getBlockMetadata(x, y, z);
        if ((meta & 12) != 0) {
            list.add(AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + (Config.hollow_thickness / 16D), z + 1));
            list.add(AxisAlignedBB.getBoundingBox(x, y + ((16 - Config.hollow_thickness) / 16D), z, x + 1, y + 1, z + 1));
        }
        if ((meta & 12) != 4) {
            list.add(AxisAlignedBB.getBoundingBox(x, y, z, x + (Config.hollow_thickness / 16D), y + 1, z + 1));
            list.add(AxisAlignedBB.getBoundingBox(x + ((16 - Config.hollow_thickness) / 16D), y, z, x + 1, y + 1, z + 1));
        }
        if ((meta & 12) != 8) {
            list.add(AxisAlignedBB.getBoundingBox(x,y,z, x+1, y+1, z+(Config.hollow_thickness/16D)));
            list.add(AxisAlignedBB.getBoundingBox(x,y,z+((16-Config.hollow_thickness)/16D), x+1, y+1, z+1));
        }
        for (AxisAlignedBB aabb : list)
            if (aabb.intersectsWith(mask)) outputList.add(aabb);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, int x, int y, int z) {
        setBlockBounds(0, 0, 0, 1, 1, 1);
    }

    ThreadLocal<Integer> inSide;
    public void setVariablesForRenderSide(int phase, int meta) {
        //meta &12 == 8: n/s. Z hollow [0-1]. XY. phase 0-3 goes DEUW 0514 (1405 inside)
        //meta &12 == 0: u/d. Y hollow [0-1]. XZ. phase 0-3 goes SENW 3524 (2435 inside)
        //meta &12 == 4: e/w. X hollow [0-1]. ZY. phase 0-3 goes DSUN 0312 (1203 inside)
        int newSide = switch (meta & 12 + phase) {
            case  9, 1          -> 5;
            case 10, 6          -> 1;
            case 11, 3          -> 4;
            case  0, 5          -> 3;
            case  2, 7          -> 2;
          /*cas 8, 4,*/ default -> 0;
        };
        newSide ^= 1; //make it the inside rather than "which side is rendering"
        inSide.set(newSide);
        int coord1min, coord1max, coord2min, coord2max;
        coord2max = switch (phase) {
            case 0 -> {
                coord1min = 0;
                coord1max = 16 - Config.hollow_thickness;
                coord2min = 16 - Config.hollow_thickness;
                yield 16;
            }
            case 1 -> {
                coord1min = 16 - Config.hollow_thickness;
                coord1max = 16;
                coord2min = Config.hollow_thickness;
                yield 16;
            }
            case 2 -> {
                coord1min = Config.hollow_thickness;
                coord1max = 16;
                coord2min = 0;
                yield Config.hollow_thickness;
            }
            default -> {
                coord1min = 0;
                coord1max = Config.hollow_thickness;
                coord2min = 0;
                yield 16 - Config.hollow_thickness;
            }
        };
        int xMin, xMax, yMin, yMax, zMin, zMax;
        switch (meta & 12) {
            case 8 -> {
                zMin = 0; zMax = 16;
                xMin = coord1min; xMax = coord1max;
                yMin = coord2min; yMax = coord2max;
            }
            case 4 -> {
                yMin = 0; yMax = 16;
                xMin = coord1min; xMax = coord1max;
                zMin = coord2min; zMax = coord2max;
            }
            default -> {
                xMin = 0; xMax = 16;
                zMin = coord1min; zMax = coord1max;
                yMin = coord2min; yMax = coord2max;
            }
        };
        setBlockBounds(xMin / 16F, yMin / 16F, zMin / 16F,
            xMax / 16F, yMax / 16F, zMax / 16F);
    }

    @Override
    public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
        return (world.getBlockMetadata(x, y, z) & 12) == 0;
    }

    @Override
    protected IIcon getSideIcon(int meta) {
        return parentBlock.getIcon(2, meta);
    }

    @Override
    protected IIcon getTopIcon(int meta) {
        return parentBlock.getIcon(0, meta);
    }

    public IIcon getInsideIcon() {
        return HollowTextures.getInsideIcon(parentBlock.getIcon(0,parentMeta));
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        int i = inSide.get();
        if (i == -1 || i != side)
            return super.getIcon(side, meta);
        else
            return getInsideIcon();
    }

    @Override
    public int getRenderType() {
        return HollowLogRenderer.RENDER_ID;
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        parentBlock.getSubBlocks(itemIn, tab, list); //...actually, that's enough, since it doesn't check the itemIn is right
    }

    @Override
    public int onBlockPlaced(World worldIn, int x, int y, int z, int side, float subX, float subY, float subZ, int meta) {
        return super.onBlockPlaced(worldIn, x, y, z, side, subX, subY, subZ, meta) | parentMeta;
    }

    public final Block getParentBlock() {
        return parentBlock;
    }

    public final int getParentMeta() {
        return parentMeta;
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        HollowTextures.loadInsideIcon(parentBlock.getIcon(0,parentMeta));
    }
}
