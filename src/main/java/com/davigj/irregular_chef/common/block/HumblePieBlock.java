package com.davigj.irregular_chef.common.block;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.Iterator;
import java.util.function.Supplier;

public class HumblePieBlock extends PieBlock {
    public HumblePieBlock(Properties properties, Supplier<Item> pieSlice) {
        super(properties, pieSlice);
    }

    protected InteractionResult consumeBite(Level worldIn, BlockPos pos, BlockState state, Player playerIn) {
        if (!playerIn.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            ItemStack sliceStack = this.getPieSliceItem();
            FoodProperties sliceFood = sliceStack.getItem().getFoodProperties();
            playerIn.getFoodData().eat(sliceStack.getItem(), sliceStack);
            int humbleArmor = 20 - playerIn.getArmorValue();
            playerIn.addEffect(new MobEffectInstance(ModEffects.COMFORT.get(), 100 + (humbleArmor * 60), 0, false, true));
            playerIn.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60 + (humbleArmor * 30), 0, false, true));
            if (this.getPieSliceItem().getItem().isEdible() && sliceFood != null) {
                Iterator var7 = sliceFood.getEffects().iterator();

                while(var7.hasNext()) {
                    Pair<MobEffectInstance, Float> pair = (Pair)var7.next();
                    if (!worldIn.isClientSide && pair.getFirst() != null && worldIn.random.nextFloat() < (Float)pair.getSecond()) {

                    }
                }
            }

            int bites = (Integer)state.getValue(BITES);
            if (bites < this.getMaxBites() - 1) {
                worldIn.setBlock(pos, (BlockState)state.setValue(BITES, bites + 1), 3);
            } else {
                worldIn.removeBlock(pos, false);
            }

            worldIn.playSound((Player)null, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);
            return InteractionResult.SUCCESS;
        }
    }

}
