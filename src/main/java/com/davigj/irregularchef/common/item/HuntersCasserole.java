package com.davigj.irregularchef.common.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;

import net.minecraft.item.Item.Properties;

public class HuntersCasserole extends Item {
    public HuntersCasserole(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        super.finishUsingItem(stack, worldIn, entityLiving);
        if (entityLiving instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) entityLiving;
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);
            serverplayerentity.awardStat(Stats.ITEM_USED.get(this));
        }

        if (stack.isEmpty() && entityLiving instanceof PlayerEntity) {
            englow((PlayerEntity) entityLiving);
            return new ItemStack(Items.BOWL);
        } else {
            englow((PlayerEntity) entityLiving);
            return stack;
        }
    }

    private void englow (PlayerEntity entityLiving) {
        if (entityLiving != null) {
            for (AnimalEntity living : entityLiving.level.getEntitiesOfClass(AnimalEntity.class, entityLiving.getBoundingBox().inflate(45.0D, 10.0D, 45.0D))) {
                living.addEffect(new EffectInstance(Effects.GLOWING, 400));
            }
            for (MonsterEntity living : entityLiving.level.getEntitiesOfClass(MonsterEntity.class, entityLiving.getBoundingBox().inflate(25.0D, 2.0D, 25.0D))) {
                living.addEffect(new EffectInstance(Effects.GLOWING, 150));
            }
            for (PlayerEntity living : entityLiving.level.getEntitiesOfClass(PlayerEntity.class, entityLiving.getBoundingBox().inflate(15.0D, 4.0D, 15.0D))) {
                living.addEffect(new EffectInstance(Effects.GLOWING, 150));
            }
            for (AbstractFishEntity living : entityLiving.level.getEntitiesOfClass(AbstractFishEntity.class, entityLiving.getBoundingBox().inflate(25.0D, 4.0D, 25.0D))) {
                living.addEffect(new EffectInstance(Effects.GLOWING, 400));
            }
            entityLiving.removeEffect(Effects.GLOWING);
            ItemStack itemstack = new ItemStack(Items.BOWL);
            PlayerEntity playerentity = (PlayerEntity) entityLiving;
            if (!(playerentity.isCreative()) && !playerentity.inventory.add(itemstack)) {
                playerentity.drop(itemstack, false);
            }
        }
    }
}
