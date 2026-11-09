package com.davigj.irregular_chef.common.entity.ai.goal;

import com.davigj.irregular_chef.common.block.StewedChimpHeadBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.ForgeEventFactory;

import static com.davigj.irregular_chef.common.block.StewedChimpHeadBlock.SERVINGS;

public class MmmBrainsGoal extends MoveToBlockGoal {
    protected int ticksWaited;

    public MmmBrainsGoal(LivingEntity living, double speedIn) {
        super((PathfinderMob) living, speedIn, 16);
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        BlockState blockstate = level.getBlockState(pos.above());
        Block block = blockstate.getBlock();
        return block instanceof StewedChimpHeadBlock && blockstate.getValue(SERVINGS) > 0;
    }

    public void tick() {
        if (this.isReachedTarget()) {
            if (this.ticksWaited >= 40) {
                this.onReachedTarget();
            } else {
                if (ticksWaited > 20) {
                    this.mob.getLookControl().setLookAt(this.blockPos.getX() + 0.5,
                            this.blockPos.getY() + 1.2, this.blockPos.getZ() + 0.5);
                }
                ++this.ticksWaited;
            }
        }
        super.tick();
    }

    protected void onReachedTarget() {
        Level level = this.mob.level();
        this.mob.getLookControl().setLookAt(this.blockPos.getX() + 0.5,
                this.blockPos.getY() + 1.2, this.blockPos.getZ() + 0.5);
        if (this.mob.tickCount % 30 == 0) {
            if (ForgeEventFactory.getMobGriefingEvent(level, this.mob) && !level.isClientSide) {
                BlockState state = level.getBlockState(this.blockPos.above());
                if (state.getBlock() instanceof StewedChimpHeadBlock head) {
                    level.setBlock(this.blockPos.above(), (BlockState) state.setValue(SERVINGS, state.getValue(SERVINGS) - 1), 3);
                    this.mob.swing(InteractionHand.MAIN_HAND);
                    this.mob.playSound(SoundEvents.GENERIC_EAT);
                }
                this.stop();
            }
        }
    }

    public boolean canUse() {
        return super.canUse();
    }

    public boolean canContinueToUse() {
        return super.canContinueToUse();
    }

    public void start() {
        super.start();
        this.ticksWaited = 0;
    }

    public double acceptedDistance() {
        return 1.4D;
    }
}
