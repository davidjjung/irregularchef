package com.davigj.irregular_chef.common.item;

import com.davigj.irregular_chef.core.util.TextUtils;
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

public class DirtCupItem extends ConsumableItem {

    public DirtCupItem(Properties properties) {
        super(properties);
    }

    public void affectConsumer(ItemStack stack, Level worldIn, LivingEntity consumer) {
        if (consumer.getCommandSenderWorld().dimensionType().effectsLocation().toString().equals("minecraft:overworld") &&
                ((consumer.blockPosition().getY() <= 62 && !consumer.isInFluidType()) ||
                        (worldIn.isRainingAt(consumer.blockPosition()) && !consumer.hasImpulse))) {
            int duration = 128 - consumer.blockPosition().getY();
            consumer.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, duration * 10, 0));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if ((Boolean) Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            tooltip.add(Component.translatable("irregular_chef.tooltip.eat_dirt_cup").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.empty().append(" ").append(Component.translatable(MobEffects.NIGHT_VISION.getDescriptionId()).append(" (0:30+)").withStyle(ChatFormatting.BLUE)));
        }
    }
}
