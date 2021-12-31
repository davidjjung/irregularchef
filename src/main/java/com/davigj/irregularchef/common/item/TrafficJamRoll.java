package com.davigj.irregularchef.common.item;

import com.davigj.irregularchef.core.other.IrregularChefCompat;
import com.davigj.irregularchef.core.util.TextUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.item.Item.Properties;

public class TrafficJamRoll extends Item {

    public TrafficJamRoll(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity livingEntity) {
        super.finishUsingItem(stack, worldIn, livingEntity);
        double rand = (double) Math.random();
        if (!worldIn.isClientSide && rand <= 0.8) {
                for (LivingEntity living : livingEntity.level.getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(7.0D, 3.0D, 7.0D))) {
                    living.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 1, false, true));
                }
                livingEntity.removeEffect(Effects.MOVEMENT_SLOWDOWN);
                livingEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 100, 0));

        } else if (!worldIn.isClientSide && rand > 0.8) {
            for (LivingEntity living : livingEntity.level.getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(7.0D, 3.0D, 7.0D))) {
                living.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 100, 1, false, true));
            }
            livingEntity.removeEffect(Effects.MOVEMENT_SPEED);
            livingEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 0));
        }
        return stack;
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        IFormattableTextComponent tip = TextUtils.getTranslation("tooltip.traffic_jam_roll.tip");
        tooltip.add(tip.withStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
    }
}
