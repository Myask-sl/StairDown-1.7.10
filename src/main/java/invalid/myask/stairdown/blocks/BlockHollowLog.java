package invalid.myask.stairdown.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockRotatedPillar;
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
    Block parent;
    int parentMeta;
    public BlockHollowLog(Block parent, int meta) {
        super(parent.getMaterial());
        this.parent = parent;
        this.parentMeta = meta;
    }

    @Override
    public void addCollisionBoxesToList(World worldIn, int x, int y, int z, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collider) {
        int meta = worldIn.getBlockMetadata(x, y, z);
        if ((meta & 12) != 0) {
            list.add(AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + (2 / 16D), z + 1));
            list.add(AxisAlignedBB.getBoundingBox(x, y + (14 / 16D), z, x + 1, y + 1, z + 1));
        }
        if ((meta & 12) != 4) {
            list.add(AxisAlignedBB.getBoundingBox(x, y, z, x + (2 / 16D), y + 1, z + 1));
            list.add(AxisAlignedBB.getBoundingBox(x + (14 / 16D), y, z, x + 1, y + 1, z + 1));
        }
        if ((meta & 12) != 8) {
            list.add(AxisAlignedBB.getBoundingBox(x,y,z, x+1, y+1, z+(2/16D)));
            list.add(AxisAlignedBB.getBoundingBox(x,y,z+(14/16D), x+1, y+1, z+1));
        }
    }

    @Override
    public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
        return (world.getBlockMetadata(x, y, z) & 12) == 0;
    }

    @Override
    protected IIcon getSideIcon(int meta) {
        return parent.getIcon(2, meta);
    }

    @Override
    protected IIcon getTopIcon(int meta) {
        return parent.getIcon(0, meta);
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        parent.getSubBlocks(itemIn, tab, list); //...actually, that's enough, since it doesn't check the itemIn is right
    }

    @Override
    public int onBlockPlaced(World worldIn, int x, int y, int z, int side, float subX, float subY, float subZ, int meta) {
        return super.onBlockPlaced(worldIn, x, y, z, side, subX, subY, subZ, meta) | parentMeta;
    }
}
