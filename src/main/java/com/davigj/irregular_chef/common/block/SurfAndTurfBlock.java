package com.davigj.irregular_chef.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.FeastBlock;

import java.util.function.Supplier;

public class SurfAndTurfBlock extends FeastBlock {
    protected static final VoxelShape PLATE_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 2.0, 14.0);
    public static final DirectionProperty FACING;
    public static final IntegerProperty SERVINGS;
    protected static final VoxelShape[] SHAPES;

    public SurfAndTurfBlock(Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
        super(properties, servingItem, hasLeftovers);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(this.getServingsProperty(), this.getMaxServings()));
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        SERVINGS = IntegerProperty.create("servings", 0, 4);
        SHAPES = new VoxelShape[]{
                PLATE_SHAPE,
                PLATE_SHAPE,
                Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(3.0, 2.0, 3.0, 13.0, 4.0, 13.0), BooleanOp.OR),
                Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(3.0, 2.0, 3.0, 13.0, 5.0, 13.0), BooleanOp.OR),
                Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(3.0, 2.0, 3.0, 13.0, 6.0, 13.0), BooleanOp.OR)
        };
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES[(Integer)state.getValue(SERVINGS)];
    }
}
