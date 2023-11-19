package com.davigj.irregular_chef.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.item.ConsumableItem;

import javax.annotation.Nullable;
import java.util.List;

public class TrafficJamRollItem extends ConsumableItem {

    public TrafficJamRollItem(Properties properties) {
        super(properties);
    }

    public void affectConsumer(ItemStack stack, Level worldIn, LivingEntity consumer) {
        for (LivingEntity living : consumer.level.getEntitiesOfClass(LivingEntity.class, consumer.getBoundingBox().inflate(7.0D, 3.0D, 7.0D))) {
            if (!living.is(consumer)) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1, false, true));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if ((Boolean) Configuration.FOOD_EFFECT_TOOLTIP.get()) {
//            com.davigj.irregular_chef.core.util.TextUtils.addFoodEffectTooltip(stack, tooltip, 1.0F);
            tooltip.add(Component.translatable(MobEffects.MOVEMENT_SPEED.getDescriptionId()).append(" (0:05)").withStyle(ChatFormatting.BLUE));
            tooltip.add(Component.translatable("irregular_chef.tooltip.eat_traffic_jam_roll").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.empty().append(" ").append(Component.translatable(MobEffects.MOVEMENT_SLOWDOWN.getDescriptionId())
                    .append(" (0:05)").withStyle(ChatFormatting.BLUE)));
        }
    }

}
