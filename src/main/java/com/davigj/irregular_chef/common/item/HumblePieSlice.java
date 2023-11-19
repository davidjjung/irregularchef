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
import vectorwing.farmersdelight.common.registry.ModEffects;

import javax.annotation.Nullable;
import java.util.List;

public class HumblePieSlice extends ConsumableItem {

    public HumblePieSlice(Properties properties, boolean hasFoodEffectTooltip, boolean hasCustomTooltip) {
        super(properties, hasFoodEffectTooltip, hasCustomTooltip);
    }

    public void affectConsumer(ItemStack stack, Level worldIn, LivingEntity consumer) {
        int humbleArmor = 20 - consumer.getArmorValue();
        consumer.addEffect(new MobEffectInstance(ModEffects.COMFORT.get(), 100 + (humbleArmor * 60), 0, false, true));
        consumer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60 + (humbleArmor * 30), 1, false, true));
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if ((Boolean) Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            tooltip.add(Component.translatable("irregular_chef.tooltip.eat_humble_pie_slice").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.empty().append(" ").append(Component.translatable(ModEffects.COMFORT.get().getDescriptionId()).append(" (1:00)").withStyle(ChatFormatting.BLUE)));
            tooltip.add(Component.empty().append(" ").append(Component.translatable(MobEffects.DAMAGE_RESISTANCE.getDescriptionId()).append(" II (0:30)").withStyle(ChatFormatting.BLUE)));
        }
    }
}
