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

public class TrafficJamRoll extends Item {

    public TrafficJamRoll(Properties properties) {
        super(properties);
    }

    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity livingEntity) {
        super.onItemUseFinish(stack, worldIn, livingEntity);
        double rand = (double) Math.random();
        if (!worldIn.isRemote && rand <= 0.8) {
                for (LivingEntity living : livingEntity.world.getEntitiesWithinAABB(LivingEntity.class, livingEntity.getBoundingBox().grow(7.0D, 3.0D, 7.0D))) {
                    living.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 100, 1, false, true));
                }
                livingEntity.removePotionEffect(Effects.SLOWNESS);
                livingEntity.addPotionEffect(new EffectInstance(Effects.SPEED, 100, 0));

        } else if (!worldIn.isRemote && rand > 0.8) {
            for (LivingEntity living : livingEntity.world.getEntitiesWithinAABB(LivingEntity.class, livingEntity.getBoundingBox().grow(7.0D, 3.0D, 7.0D))) {
                living.addPotionEffect(new EffectInstance(Effects.SPEED, 100, 1, false, true));
            }
            livingEntity.removePotionEffect(Effects.SPEED);
            livingEntity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 100, 0));
        }
        return stack;
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        IFormattableTextComponent tip = TextUtils.getTranslation("tooltip.traffic_jam_roll.tip");
        tooltip.add(tip.mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
    }
}
