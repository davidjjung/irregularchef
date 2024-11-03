package com.davigj.irregular_chef.common.item;

import com.davigj.irregular_chef.core.IrregularChefMod;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.ConsumableItem;

public class SwampDogItem extends ConsumableItem {
    public SwampDogItem(Properties properties, boolean hasFoodEffectTooltip, boolean hasCustomTooltip) {
        super(properties, hasFoodEffectTooltip, hasCustomTooltip);
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
            TrackedDataManager.INSTANCE.setValue(consumer, IrregularChefMod.SWAMP_DOGGY, 12 * 20);
    }
}
