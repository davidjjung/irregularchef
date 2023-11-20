package com.davigj.irregular_chef.common.block;

import com.davigj.irregular_chef.core.other.tags.ICItemTags;
import com.davigj.irregular_chef.core.registry.ICItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class WaffleBlock extends Block {
    public static final IntegerProperty WAFFLES = IntegerProperty.create("waffles", 1, 8);
    public static final EnumProperty<Topping> TOPPING = EnumProperty.create("topping", Topping.class);

    private static final VoxelShape[] LAYERS = new VoxelShape[]{
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 2.0D, 13.0D),
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 4.0D, 13.0D),
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D),
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D),
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D),
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 12.0D, 13.0D),
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 14.0D, 13.0D),
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D)
    };

    public WaffleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WAFFLES, 1).setValue(TOPPING, Topping.NONE));
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!player.getItemInHand(hand).is(ICItems.WAFFLE_BLOCK.get())) {
            if (this.setTopping(worldIn, pos, state, player, player.getItemInHand(hand), hand)) {
                return InteractionResult.SUCCESS;
            }
            if (this.munch(worldIn, pos, state, player, player.getItemInHand(hand)) == InteractionResult.SUCCESS) {
                return InteractionResult.SUCCESS;
            }
            if (player.getItemInHand(hand).isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }

    private boolean setTopping(Level level, BlockPos pos, BlockState state, Player player, ItemStack itemstack, InteractionHand hand) {
        if (itemstack.is(ICItemTags.WAFFLE_TOPPINGS) && state.getValue(TOPPING) == Topping.NONE) {
            Topping topping = Topping.NONE;
            if (itemstack.is(ICItemTags.SYRUP_CONTAINERS)) {
                topping = Topping.SYRUP;
            } else if (itemstack.is(Items.SUGAR)) {
                topping = Topping.SUGAR;
            } else if (itemstack.is(ICItemTags.RED_BERRIES)) {
                topping = Topping.RED_BERRIES;
            } else if (itemstack.is(ICItemTags.OCHRE_BERRIES)) {
                topping = Topping.OCHRE_BERRIES;
            } else if (itemstack.is(ICItemTags.CHOCOLATE)) {
                topping = Topping.CHOCOLATE;
            }
            level.setBlock(pos, state.setValue(TOPPING, topping), 3);

            ItemStack container = itemstack.getCraftingRemainingItem();
            if (player != null) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                    if (itemstack.isEmpty()) {
                        player.setItemInHand(hand, container);
                    } else if (!player.getInventory().add(container)) {
                        player.drop(container, false);
                    }
                }
            }
            return true;
        }
        return false;
    }

    private InteractionResult munch(Level level, BlockPos pos, BlockState state, Player player, ItemStack itemstack) {
        int waffleCount = state.getValue(WAFFLES);
        if ((itemstack.is(ICItemTags.WAFFLE_TOPPINGS) && state.getValue(TOPPING) == Topping.NONE) ||
                itemstack.is(ICItems.WAFFLE_BLOCK.get())
                || !player.canEat(false) || level.getBlockState(pos.above()).getBlock() instanceof WaffleBlock) {
            return InteractionResult.PASS;
        } else {
            feedPlayer(player, state);

            level.gameEvent(player, GameEvent.EAT, pos);
            if (waffleCount > 1) {
                level.setBlock(pos, state.setValue(WAFFLES, waffleCount - 1), 3);
            } else {
                level.removeBlock(pos, false);
                level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            }

            return InteractionResult.SUCCESS;
        }
    }

    private void feedPlayer(Player player, BlockState state) {
        player.playSound(SoundEvents.GENERIC_EAT, 1.0F, (float) (1.0 + (player.getRandom().nextGaussian() * 0.2)));
        switch (state.getValue(TOPPING)) {
            case RED_BERRIES, OCHRE_BERRIES -> {
                player.getFoodData().eat(1, 0.8F);
            }
            case SUGAR, CHOCOLATE -> {
                player.getFoodData().eat(1, 0.6F);
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 10));
            }
            case SYRUP -> {
                player.getFoodData().eat(2, 0.6F);
            }
            default -> {
                player.getFoodData().eat(1, 0.5F);
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return LAYERS[state.getValue(WAFFLES) - 1];
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        if (blockstate.getBlock() instanceof WaffleBlock) {
            return blockstate.setValue(WAFFLES, Math.min(8, blockstate.getValue(WAFFLES) + 1));
        } else {
            return super.getStateForPlacement(context);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WAFFLES, TOPPING);
    }

    protected boolean isValidGround(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return !state.getCollisionShape(worldIn, pos).getFaceShape(Direction.UP).isEmpty() || (state.getBlock() == this && state.getValue(WAFFLES) >= 7);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.below();
        return this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
        return (int) (double) blockState.getValue(WAFFLES);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return useContext.getItemInHand().getItem() == this.asItem() && state.getValue(WAFFLES) < 8 || super.canBeReplaced(state, useContext);
    }

    public enum Topping implements StringRepresentable {
        RED_BERRIES("red_berries"),
        OCHRE_BERRIES("ochre_berries"),
        SYRUP("syrup"),
        SUGAR("sugar"),
        CHOCOLATE("chocolate"),
        NONE("none");

        private final String name;

        Topping(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
