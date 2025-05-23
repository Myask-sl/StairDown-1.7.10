package invalid.myask.stairdown.blocks;

import net.minecraft.block.Block;
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
    final Block parentBlock;
    final int parentMeta;
    public BlockHollowLog(Block parent, int meta) {
        super(parent.getMaterial());
        this.parentBlock = parent;
        this.parentMeta = meta;
        this.setBlockName(parent.getUnlocalizedName());
        while (getUnlocalizedName().startsWith("tile.tile."))
            this.setBlockName(getUnlocalizedName().substring(10));
    }

    @Override
    public void addCollisionBoxesToList(World worldIn, int x, int y, int z, AxisAlignedBB mask, List<AxisAlignedBB> list2, Entity collider) {
        List<AxisAlignedBB> list = new ArrayList<>();
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
        for (AxisAlignedBB aabb : list)
            if (aabb.intersectsWith(mask)) list.add(aabb);
    }

    @Override
    public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
        return false;//(world.getBlockMetadata(x, y, z) & 12) == 0;
    }

    @Override
    protected IIcon getSideIcon(int meta) {
        return parentBlock.getIcon(2, meta);
    }

    @Override
    protected IIcon getTopIcon(int meta) {
        return parentBlock.getIcon(0, meta);
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
}
