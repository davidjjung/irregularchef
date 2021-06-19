package com.davigj.irregularchef.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class SingleEffect extends Item {
    String modid;
    ResourceLocation effect;
    int duration;
    int amplifier;

    public SingleEffect(Properties properties, String modid, ResourceLocation effect, int duration, int amplifier) {
        super(properties);
        this.modid = modid;
        this.effect = effect;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        super.onItemUseFinish(stack, worldIn, entityLiving);
        if (!worldIn.isRemote && ModList.get().isLoaded(modid)) {
            entityLiving.addPotionEffect(new EffectInstance(new EffectInstance(
                    getCompatEffect(modid, effect).get(), duration, amplifier)));
        }
        return stack;
    }

    private static Supplier<Effect> getCompatEffect(String modid, ResourceLocation effect) {
        return (ModList.get().isLoaded(modid) ? () -> ForgeRegistries.POTIONS.getValue(effect) : () -> null);
    }

}
