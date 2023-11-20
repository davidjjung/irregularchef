package com.davigj.irregular_chef.core.other;

import com.davigj.irregular_chef.core.ICConfig;
import com.davigj.irregular_chef.core.IrregularChefMod;
import com.davigj.irregular_chef.core.other.tags.ICItemTags;
import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.block.CookingPotBlock;
import vectorwing.farmersdelight.common.tag.ForgeTags;

@Mod.EventBusSubscriber(modid = IrregularChefMod.MOD_ID)
public class ICEvents {
    @SubscribeEvent
    public static void onChefSlabfish(PlayerInteractEvent.EntityInteract event) {
        if (ModList.get().isLoaded(ICConstants.ENVIRONMENTAL)) {
            if (event.getTarget() instanceof Slabfish slabfish && ICConfig.COMMON.chefSlabfish.get()) {
                BlockState blockStanding = slabfish.getCommandSenderWorld().getBlockState(slabfish.blockPosition());
                Block block = blockStanding.getBlock();
                if (block instanceof CookingPotBlock) {
                    ItemStack stack = event.getItemStack();
                    if (stack.is(ICItemTags.FANCY_KNIVES)) {
                        slabfish.setSlabfishType(new ResourceLocation(IrregularChefMod.MOD_ID, ICConstants.CHEF_SLABFISH));
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
                if (slabfish.getSlabfishType().equals(new ResourceLocation(IrregularChefMod.MOD_ID, ICConstants.CHEF_SLABFISH))
                && event.getSource().isFire() && !(event.getSource().equals(DamageSource.LAVA))) {
                    slabfish.setSecondsOnFire(0);
                    event.setCanceled(true);
                }
            }
        }
    }

}