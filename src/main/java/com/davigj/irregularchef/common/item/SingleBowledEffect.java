package com.davigj.irregularchef.common.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import net.minecraft.item.Item.Properties;

public class SingleBowledEffect extends Item {
    // Granted, this class doesn't care about what vanilla/FD effects the player may acquire upon consumption
    // The "single effect" largely applies to a modded effect the food imparts.
    String modid;
    ResourceLocation effect;
    int duration;
    int amplifier;
    public SingleBowledEffect(Properties properties, String modid, ResourceLocation effect, int duration, int amplifier) {
        super(properties);
        this.modid = modid;
        this.effect = effect;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    private static Supplier<Effect> getCompatEffect(String modid, ResourceLocation effect) {
        return (ModList.get().isLoaded(modid) ? () -> ForgeRegistries.POTIONS.getValue(effect) : () -> null);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        super.finishUsingItem(stack, worldIn, entityLiving);
        if (entityLiving instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) entityLiving;
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);
            serverplayerentity.awardStat(Stats.ITEM_USED.get(this));
        }

        if (stack.isEmpty()) {
            return new ItemStack(Items.BOWL);
        } else {
            if (entityLiving instanceof PlayerEntity && !((PlayerEntity) entityLiving).abilities.instabuild) {
                if (ModList.get().isLoaded(modid)) {
                    entityLiving.addEffect(new EffectInstance(new EffectInstance(
                            getCompatEffect(modid, effect).get(), duration, amplifier)));
                }
                ItemStack itemstack = new ItemStack(Items.BOWL);
                PlayerEntity playerentity = (PlayerEntity) entityLiving;
                if (!playerentity.inventory.add(itemstack)) {
                    playerentity.drop(itemstack, false);
                }
            }
            return stack;
        }
    }
}
