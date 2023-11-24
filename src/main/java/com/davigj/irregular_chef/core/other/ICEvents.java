package com.davigj.irregular_chef.core.other;

import com.davigj.irregular_chef.core.ICConfig;
import com.davigj.irregular_chef.core.IrregularChefMod;
import com.davigj.irregular_chef.core.other.tags.ICItemTags;
import com.davigj.irregular_chef.core.registry.ICParticleTypes;
import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.core.registry.EnvironmentalParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.block.CookingPotBlock;

@Mod.EventBusSubscriber(modid = IrregularChefMod.MOD_ID)
public class ICEvents {
    @SubscribeEvent
    public static void onChefSlabfish(PlayerInteractEvent.EntityInteract event) {
        if (ModList.get().isLoaded(ICConstants.ENVIRONMENTAL)) {
            if (event.getTarget() instanceof Slabfish slabfish && ICConfig.COMMON.chefSlabfish.get()) {
                BlockState blockStanding = slabfish.getCommandSenderWorld().getBlockState(slabfish.blockPosition());
                Block block = blockStanding.getBlock();
                if (block instanceof CookingPotBlock && !slabfish.getSlabfishType().equals(ICConstants.CHEF_SLABFISH)) {
                    ItemStack stack = event.getItemStack();
                    if (stack.is(ICItemTags.FANCY_KNIVES)) {
                        slabfish.setSlabfishType(ICConstants.CHEF_SLABFISH);
                        if (slabfish.level.isClientSide) {
                            slabfish.level.addParticle(ICParticleTypes.TOQUE.get(), slabfish.getX(), slabfish.getEyeY() + 0.3, slabfish.getZ(),
                                    10, 3, 10);
                            for (int i = 0; i < 4; i++) {
                                slabfish.level.addParticle(EnvironmentalParticleTypes.SLABFISH_FINDS_EFFIGY.get(), slabfish.getX() + slabfish.getRandom().nextDouble() - 0.5,
                                        slabfish.getY() + slabfish.getRandom().nextDouble(), slabfish.getZ() + slabfish.getRandom().nextDouble() - 0.5, 0, 0, 0);
                            }
                        }
                        event.getEntity().swing(event.getHand());
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void chefFingers(LivingAttackEvent event) {
        if (ModList.get().isLoaded(ICConstants.ENVIRONMENTAL)) {
            if (event.getEntity() instanceof Slabfish slabfish) {
                if (slabfish.getSlabfishType().equals(new ResourceLocation(IrregularChefMod.MOD_ID, ICConstants.CHEF))
                && event.getSource().isFire() && !(event.getSource().equals(DamageSource.LAVA))) {
                    slabfish.setSecondsOnFire(0);
                    event.setCanceled(true);
                }
            }
        }
    }

}