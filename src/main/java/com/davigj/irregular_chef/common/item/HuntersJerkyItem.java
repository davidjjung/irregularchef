package com.davigj.irregular_chef.common.item;

import com.davigj.irregular_chef.core.ICConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.utility.TextUtils;

import javax.annotation.Nullable;
import java.util.List;

public class HuntersJerkyItem extends ConsumableItem {

    public HuntersJerkyItem(Properties p_41383_) {
        super(p_41383_);
    }

    public void affectConsumer(ItemStack stack, Level worldIn, LivingEntity consumer) {
        if (consumer instanceof Player player && ICConfig.COMMON.hunterHuntBox.get()) {
            AABB huntBox = new AABB(player.blockPosition()).inflate(ICConfig.COMMON.huntBoxRadius.get());
            List<LivingEntity> entities = player.level.getEntities(EntityTypeTest.forClass(LivingEntity.class), huntBox, LivingEntity::isAlive);
            for (LivingEntity living : entities) {
                if (living != player && !(living.getMobType() == MobType.UNDEAD)) {
                    living.addEffect(new MobEffectInstance(MobEffects.GLOWING, 150));
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        if ((Boolean) Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            if (!ICConfig.COMMON.hunterHuntBox.get()) {
                TextUtils.addFoodEffectTooltip(stack, tooltip, 1.0F);
            } else {
//                tooltip.add(Component.translatable("irregular_chef.tooltip.eat").withStyle(ChatFormatting.GRAY));
                TextUtils.addFoodEffectTooltip(stack, tooltip, 1.0F);
                tooltip.add(Component.translatable("irregular_chef.tooltip.hunters_jerky").withStyle(ChatFormatting.BLUE));
            }
        }
    }
}
