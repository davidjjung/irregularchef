package com.davigj.irregularchef.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vectorwing.farmersdelight.blocks.FeastBlock;

import java.util.Random;
import java.util.function.Supplier;

public class TurtleGallimaufryBlock extends FeastBlock {

    protected static final VoxelShape SHAPE;

    public TurtleGallimaufryBlock(Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
        super(properties, servingItem, hasLeftovers);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    static {
        SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D);
    }
//    @OnlyIn(Dist.CLIENT)
//    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
//        Random random = worldIn.rand;
//        double baseX;
//        double baseY;
//        double baseZ;
//        if (random.nextFloat() < 0.3F) {
//            baseX = (double) pos.getX() + 0.5D + (random.nextDouble() * 0.6D - 0.3D);
//            baseY = (double) pos.getY() + 0.4D;
//            baseZ = (double) pos.getZ() + 0.5D + (random.nextDouble() * 0.6D - 0.3D);
//            worldIn.addParticle(ParticleTypes.BUBBLE, baseX, baseY, baseZ, 0.0D, 0.03D, 0.0D);
//        }
//    }
}
