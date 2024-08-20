package com.davigj.irregular_chef.core.mixin;

import com.davigj.irregular_chef.core.IrregularChefMod;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "isCurrentlyGlowing", at = @At("HEAD"), cancellable = true)
    private void glowWormy(CallbackInfoReturnable<Boolean> cir) {
        if (TrackedDataManager.INSTANCE.getValue((Entity)(Object)this, IrregularChefMod.GLOW_WORMY) > 0) {
            cir.setReturnValue(true);
        }
    }
}
