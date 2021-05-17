package com.davigj.irregularchef.common.item;

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

public class DirtCup extends Item {
    public DirtCup(Item.Properties properties) {
        super(properties);
    }

    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity livingEntity) {
        super.onItemUseFinish(stack, worldIn, livingEntity);
        BlockPos blockpos = livingEntity.getPosition();
        if ((!worldIn.isRemote && blockpos.getY() <= 62 && !livingEntity.isInWaterRainOrBubbleColumn()) || (!worldIn.isRemote && worldIn.isRainingAt(blockpos) && !livingEntity.isAirBorne)) {
            livingEntity.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 1200, 0));
            // future plans: make night vision go away if the player "emerges"
        }
        return stack;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        IFormattableTextComponent tip = TextUtils.getTranslation("tooltip.dirt_cup.tip");
        tooltip.add(tip.mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
    }
}
