package com.davigj.irregularchef.common.item;

import com.davigj.irregularchef.core.other.IrregularChefCompat;
import com.davigj.irregularchef.core.util.TextUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class DirtCup extends Item {
    public DirtCup(Item.Properties properties) {
        super(properties);
    }

    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity livingEntity) {
        super.onItemUseFinish(stack, worldIn, livingEntity);
        if (stack.isEmpty()) {
            if ((!worldIn.isRemote && livingEntity.getPosition().getY() <= 62 && !livingEntity.isInWaterRainOrBubbleColumn()) ||
                    (!worldIn.isRemote && worldIn.isRainingAt(livingEntity.getPosition()) && !livingEntity.isAirBorne)) {
                livingEntity.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 1200, 0));
            }
            return new ItemStack(Items.GLASS_BOTTLE);
        } else {
            if (livingEntity instanceof PlayerEntity && !((PlayerEntity) livingEntity).abilities.isCreativeMode) {
                if (ModList.get().isLoaded("neapolitan")) {
                    livingEntity.addPotionEffect(new EffectInstance(new EffectInstance(
                            getCompatEffect("neapolitan", IrregularChefCompat.CompatEffects.SUGAR_RUSH).get(), 400, 0)));
                }
                ItemStack itemstack = new ItemStack(Items.GLASS_BOTTLE);
                PlayerEntity playerentity = (PlayerEntity) livingEntity;
                if (!playerentity.inventory.addItemStackToInventory(itemstack)) {
                    playerentity.dropItem(itemstack, false);
                }
            }
            BlockPos blockpos = livingEntity.getPosition();
            if ((!worldIn.isRemote && blockpos.getY() <= 62 && !livingEntity.isInWaterRainOrBubbleColumn()) || (!worldIn.isRemote && worldIn.isRainingAt(blockpos) && !livingEntity.isAirBorne)) {
                livingEntity.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 1200, 0));
            }
            return stack;
        }
    }

    private static Supplier<Effect> getCompatEffect(String modid, ResourceLocation effect) {
        return (ModList.get().isLoaded(modid) ? () -> ForgeRegistries.POTIONS.getValue(effect) : () -> null);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        IFormattableTextComponent tip = TextUtils.getTranslation("tooltip.dirt_cup.tip");
        tooltip.add(tip.mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
    }
}
