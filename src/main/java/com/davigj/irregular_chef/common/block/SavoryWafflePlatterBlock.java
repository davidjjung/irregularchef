package com.davigj.irregular_chef.common.block;

import com.davigj.irregular_chef.core.registry.ICItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.FeastBlock;

import java.util.function.Supplier;

public class SavoryWafflePlatterBlock extends FeastBlock {
    public static final IntegerProperty SERVINGS = IntegerProperty.create("servings", 0, 3);
    protected static final VoxelShape PLATE_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 2.0, 15.0);
    protected static final VoxelShape FOOD_SHAPE;
    public SavoryWafflePlatterBlock(Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
        super(properties, servingItem, hasLeftovers);
    }

    public ItemStack getServingItem(BlockState state) {
        return new ItemStack((ItemLike)(ICItems.SAVORY_WAFFLES.get()));
    }

    public int getMaxServings() {
        return 3;
    }

    public IntegerProperty getServingsProperty() {
        return SERVINGS;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, SERVINGS});
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return (Integer)state.getValue(this.getServingsProperty()) == 0 ? PLATE_SHAPE : FOOD_SHAPE;
    }

    static {
        FOOD_SHAPE = Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(2.0, 2.0, 2.0, 14.0, 4.0, 14.0), BooleanOp.OR);
    }
}
