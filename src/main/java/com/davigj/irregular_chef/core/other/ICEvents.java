package com.davigj.irregular_chef.core.other;

import com.davigj.irregular_chef.client.particle.FluffParticle;
import com.davigj.irregular_chef.core.ICConfig;
import com.davigj.irregular_chef.core.IrregularChefMod;
import com.davigj.irregular_chef.core.other.tags.ICItemTags;
import com.davigj.irregular_chef.core.registry.ICParticleTypes;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.block.CookingPotBlock;

@Mod.EventBusSubscriber(modid = IrregularChefMod.MOD_ID)
public class ICEvents {
//    @SubscribeEvent
//    public static void onChefSlabfish(PlayerInteractEvent.EntityInteract event) {
//        if (ModList.get().isLoaded(ICConstants.ENVIRONMENTAL)) {
//            if (event.getTarget() instanceof Slabfish slabfish && ICConfig.COMMON.chefSlabfish.get()) {
//                BlockState blockStanding = slabfish.getCommandSenderWorld().getBlockState(slabfish.blockPosition());
//                Block block = blockStanding.getBlock();
//                if (block instanceof CookingPotBlock && !slabfish.getSlabfishType().equals(ICConstants.CHEF_SLABFISH)) {
//                    ItemStack stack = event.getItemStack();
//                    if (stack.is(ICItemTags.FANCY_KNIVES)) {
//                        slabfish.setSlabfishType(ICConstants.CHEF_SLABFISH);
//                        if (slabfish.level.isClientSide) {
//                            slabfish.level.addParticle(ICParticleTypes.TOQUE.get(), slabfish.getX(), slabfish.getEyeY() + 0.3, slabfish.getZ(),
//                                    10, 3, 10);
//                            for (int i = 0; i < 4; i++) {
//                                slabfish.level.addParticle(EnvironmentalParticleTypes.TAPIR_FINDS_FLORA.get(), slabfish.getX() + slabfish.getRandom().nextDouble() - 0.5,
//                                        slabfish.getY() + slabfish.getRandom().nextDouble(), slabfish.getZ() + slabfish.getRandom().nextDouble() - 0.5, 0, 0, 0);
//                            }
//                        }
//                        event.getEntity().swing(event.getHand());
//                        event.setCanceled(true);
//                    }
//                }
//            }
//        }
//    }

//    @SubscribeEvent
//    public static void chefFingers(LivingAttackEvent event) {
//        if (ModList.get().isLoaded(ICConstants.ENVIRONMENTAL)) {
//            if (event.getEntity() instanceof Slabfish slabfish) {
//                if (slabfish.getSlabfishType().equals(new ResourceLocation(IrregularChefMod.MOD_ID, ICConstants.CHEF))
//                && event.getSource().isFire() && !(event.getSource().equals(DamageSource.LAVA))) {
//                    slabfish.setSecondsOnFire(0);
//                    event.setCanceled(true);
//                }
//            }
//        }
//    }

    @SubscribeEvent
    public static void tickDown(TickEvent.PlayerTickEvent event) {
        TrackedDataManager manager = TrackedDataManager.INSTANCE;
        int glowy = manager.getValue(event.player, IrregularChefMod.GLOW_WORMY);
        if (glowy > 0) {
            manager.setValue(event.player, IrregularChefMod.GLOW_WORMY, glowy - 1);
        }

    }


    @SubscribeEvent
    public static void doggyDown(LivingEvent.LivingTickEvent event) {
        TrackedDataManager manager = TrackedDataManager.INSTANCE;
        LivingEntity living = event.getEntity();
        int doggy = manager.getValue(event.getEntity(), IrregularChefMod.SWAMP_DOGGY);
        if (doggy > 0) {
            manager.setValue(event.getEntity(), IrregularChefMod.SWAMP_DOGGY, doggy - 1);
            RandomSource random = living.getRandom();
            Vec3 vec3 = new Vec3(((double) random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
            vec3 = vec3.xRot(-living.getXRot() * ((float) Math.PI / 180F));
            vec3 = vec3.yRot(-living.getYRot() * ((float) Math.PI / 180F));
            double d0 = (double) (-random.nextFloat()) * 0.6D - 0.3D;
            Vec3 vec31 = new Vec3(((double) random.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
            vec31 = vec31.xRot(-living.getXRot() * ((float) Math.PI / 180F));
            vec31 = vec31.yRot(-living.getYRot() * ((float) Math.PI / 180F));
            vec31 = vec31.add(living.getX(), living.getEyeY(), living.getZ());
            if (living.level() instanceof ServerLevel)
                ((ServerLevel) living.level()).sendParticles(ICParticleTypes.FLUFF.get(), vec31.x, vec31.y, vec31.z, 1, vec3.x, vec3.y + 0.08D, vec3.z, 0.0D);
            else
                living.level().addParticle(ICParticleTypes.FLUFF.get(), vec31.x, vec31.y, vec31.z, vec3.x, vec3.y + 0.08D, vec3.z);
        }
    }

    @SubscribeEvent
    public static void resetGlow(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            TrackedDataManager manager = TrackedDataManager.INSTANCE;
            int glowy = manager.getValue(player, IrregularChefMod.GLOW_WORMY);
            if (glowy > 0) {
                manager.setValue(player, IrregularChefMod.GLOW_WORMY, 0);
            }
            int doggy = manager.getValue(player, IrregularChefMod.SWAMP_DOGGY);
            if (doggy > 0) {
                manager.setValue(player, IrregularChefMod.SWAMP_DOGGY, 0);
            }
        }
    }
}