package com.davigj.irregular_chef.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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

public class PoolPartyStickItem extends ConsumableItem {
    private final boolean hasCustomTooltip;

    public PoolPartyStickItem(Properties properties, boolean hasFoodEffectTooltip, boolean hasCustomTooltip) {
        super(properties, hasFoodEffectTooltip, hasCustomTooltip);
        this.hasCustomTooltip = hasCustomTooltip;
    }

    public void affectConsumer(ItemStack stack, Level worldIn, LivingEntity consumer) {
        if (consumer.getAirSupply() < consumer.getMaxAirSupply()) {
            consumer.setAirSupply(300);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if ((Boolean) Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            if (hasCustomTooltip) {
                MutableComponent textEmpty = com.davigj.irregular_chef.core.util.TextUtils.getTranslation("tooltip." + this, new Object[0]);
                tooltip.add(textEmpty.withStyle(ChatFormatting.BLUE));
            }
        }
    }
}
