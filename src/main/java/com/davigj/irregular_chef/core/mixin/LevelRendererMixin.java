package com.davigj.irregular_chef.core.mixin;

import com.davigj.irregular_chef.core.IrregularChefMod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @WrapOperation(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getTeamColor()I"))
    private int glowWorm(Entity instance, Operation<Integer> original) {
        TrackedDataManager manager = TrackedDataManager.INSTANCE;
        return manager.getValue(instance, IrregularChefMod.GLOW_WORMY) > 0 ? 8244198 : original.call(instance);
    }
}
