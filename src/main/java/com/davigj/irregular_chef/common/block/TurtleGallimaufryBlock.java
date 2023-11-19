package com.davigj.irregular_chef.common.block;

import com.davigj.irregular_chef.core.IrregularChefMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vectorwing.farmersdelight.common.block.FeastBlock;
import vectorwing.farmersdelight.common.utility.TextUtils;

import java.util.function.Supplier;

public class TurtleGallimaufryBlock extends Block implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING;
    public static final IntegerProperty SERVINGS;
    public final Supplier<Item> servingItem;
    public final boolean hasLeftovers;
    protected static final VoxelShape SHAPE;
    public static final BooleanProperty WATERLOGGED;

    public TurtleGallimaufryBlock(BlockBehaviour.Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
        super(properties);
        this.servingItem = servingItem;
        this.hasLeftovers = hasLeftovers;
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(SERVINGS, 4).setValue(WATERLOGGED, Boolean.valueOf("false")));
    }

    public ItemStack getServingItem() {
        return new ItemStack((ItemLike)this.servingItem.get());
    }

    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        return worldIn.isClientSide && this.takeServing(worldIn, pos, state, player, handIn).consumesAction() ? InteractionResult.SUCCESS : this.takeServing(worldIn, pos, state, player, handIn);
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SERVINGS, WATERLOGGED);
    }

    public int getMaxServings() {
        return 4;
    }

    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
        return false;
    }

    public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
        return (Integer)blockState.getValue(SERVINGS);
    }

    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState p_153741_, LevelAccessor p_153742_, BlockPos p_153743_, BlockPos p_153744_) {
        if (state.getValue(WATERLOGGED)) {
            p_153742_.scheduleTick(p_153743_, Fluids.WATER, Fluids.WATER.getTickDelay(p_153742_));
        }

        return super.updateShape(state, direction, p_153741_, p_153742_, p_153743_, p_153744_);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        boolean flag = fluid.getType() == Fluids.WATER;
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, Boolean.valueOf(flag));
    }

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.below()).getMaterial().isSolid();
    }

    static {
        SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D);
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        SERVINGS = IntegerProperty.create("servings", 0, 4);
    }

    private InteractionResult takeServing(LevelAccessor worldIn, BlockPos pos, BlockState state, Player player, InteractionHand handIn) {
        int servings = (Integer)state.getValue(SERVINGS);
        if (servings == 0) {
            worldIn.playSound((Player)null, pos, SoundEvents.BONE_BLOCK_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
            worldIn.destroyBlock(pos, true);
            return InteractionResult.SUCCESS;
        } else {
            ItemStack serving = this.getServingItem();
            ItemStack heldStack = player.getItemInHand(handIn);
            if (servings > 0) {
                if (heldStack.sameItem(serving.getCraftingRemainingItem())) {
                    worldIn.setBlock(pos, (BlockState)state.setValue(SERVINGS, servings - 1), 3);
                    if (!player.getAbilities().instabuild) {
                        heldStack.shrink(1);
                    }

                    if (!player.getInventory().add(serving)) {
                        player.drop(serving, false);
                    }

                    if ((Integer)worldIn.getBlockState(pos).getValue(SERVINGS) == 0 && !this.hasLeftovers) {
                        worldIn.removeBlock(pos, false);
                    }

                    worldIn.playSound((Player)null, pos, SoundEvents.ARMOR_EQUIP_GENERIC, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                }
                else if ((heldStack.sameItem(Items.WATER_BUCKET.getDefaultInstance()) && !state.getValue(WATERLOGGED))
                        || (heldStack.sameItem(Items.BUCKET.getDefaultInstance()) && state.getValue(WATERLOGGED))) {
                    return InteractionResult.PASS;
                }

                player.displayClientMessage(TextUtils.getTranslation("block.feast.use_container", new Object[]{serving.getCraftingRemainingItem().getHoverName()}), true);
            }

            return InteractionResult.PASS;
        }
    }
}