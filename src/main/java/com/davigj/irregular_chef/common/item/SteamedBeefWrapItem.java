package com.davigj.irregular_chef.common.item;

import com.davigj.irregular_chef.core.other.ICConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.registry.ModEffects;

import javax.annotation.Nullable;
import java.util.List;

public class SteamedBeefWrapItem extends CompatEffectItem{
    String modid;
    ResourceLocation effect;
    int duration;
    int amplifier;
    boolean consumable;

    public SteamedBeefWrapItem(Properties properties, String modid, ResourceLocation effect, int duration, int amplifier, boolean consumable) {
        super(properties, modid, effect, duration, amplifier, consumable);
        this.modid = modid;
        this.effect = effect;
        this.duration = duration;
        this.amplifier = amplifier;
        this.consumable = consumable;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        if ((Boolean) Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            if (ModList.get().isLoaded(ICConstants.NEAPOLITAN)) {
                MobEffectInstance instance = new MobEffectInstance(new MobEffectInstance(getCompatEffect(modid, effect).get(), duration, amplifier));
                tooltip.add(Component.translatable(instance.getDescriptionId()).append(" (0:30)").withStyle(ChatFormatting.BLUE));
                tooltip.add(Component.translatable(ModEffects.NOURISHMENT.get().getDescriptionId()).append(" (2:00)").withStyle(ChatFormatting.BLUE));
            }
        }
    }
}
