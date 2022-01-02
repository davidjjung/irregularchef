package com.davigj.irregularchef.common.item;

import com.davigj.irregularchef.core.IrregularChef;
import com.davigj.irregularchef.core.registry.IrregularChefItems;
import com.davigj.irregularchef.core.util.TextUtils;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vectorwing.farmersdelight.registry.ModParticleTypes;
import vectorwing.farmersdelight.utils.MathUtils;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class BoofWrap extends Item {
    Logger LOGGER = LogManager.getLogger(IrregularChef.MOD_ID);
    private static final List<EffectInstance> EFFECTS;
    public static final ResourceLocation BOOFLO = new ResourceLocation("endergetic", "booflo");


    public BoofWrap(Properties properties) {
        super(properties);
    }

    static {
        EFFECTS = Lists.newArrayList(
                new EffectInstance(Effects.JUMP, 6000, 1),
                new EffectInstance(Effects.SLOW_FALLING, 6000, 0));
    }

    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        IFormattableTextComponent textWhenFeeding = TextUtils.getTranslation("tooltip.boof_wrap.when_feeding", new Object[0]);
        tooltip.add(textWhenFeeding.withStyle(TextFormatting.GRAY));

        StringTextComponent effectDescription;
        Effect effect;
        for (Iterator var6 = EFFECTS.iterator(); var6.hasNext(); tooltip.add(effectDescription.withStyle(effect.getCategory().getTooltipFormatting()))) {
            EffectInstance effectInstance = (EffectInstance) var6.next();
            effectDescription = new StringTextComponent(" ");
            IFormattableTextComponent effectName = new TranslationTextComponent(effectInstance.getDescriptionId());
            effectDescription.append(effectName);
            effect = effectInstance.getEffect();
            if (effectInstance.getAmplifier() > 0) {
                effectDescription.append(" ").append(new TranslationTextComponent("potion.potency." + effectInstance.getAmplifier()));
            }

            if (effectInstance.getDuration() > 20) {
                effectDescription.append(" (").append(EffectUtils.formatDuration(effectInstance, 1.0F)).append(")");
            }
        }
    }

    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity livingEntity) {
        ItemStack itemstack = super.finishUsingItem(stack, worldIn, livingEntity);

        if (!worldIn.isClientSide) {
            double d0 = livingEntity.getX();
            double d2 = livingEntity.getZ();
            for(int i = 0; i < 16; ++i) {
                double d4 = livingEntity.getY() + (double)(livingEntity.getRandom().nextInt(8) + 12);
                if (livingEntity.isPassenger()) {
                    livingEntity.stopRiding();
                }

                net.minecraftforge.event.entity.living.EntityTeleportEvent.ChorusFruit event = net.minecraftforge.event.ForgeEventFactory.onChorusFruitTeleport(livingEntity, d0, d4, d2);
                if (event.isCanceled()) {
                    return itemstack;
                }
                if (this.attemptTeleUp(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true, livingEntity)) {
                    SoundEvent soundevent = livingEntity instanceof FoxEntity ? SoundEvents.FOX_TELEPORT : SoundEvents.CHORUS_FRUIT_TELEPORT;
                    worldIn.playSound((PlayerEntity)null, d0, d4, d2, soundevent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    livingEntity.playSound(soundevent, 1.0F, 1.0F);
                    break;
                }
            }

            if (livingEntity instanceof PlayerEntity) {
                ((PlayerEntity)livingEntity).getCooldowns().addCooldown(this, 20);
            }
        }
        return itemstack;
    }

    public boolean attemptTeleUp(double x, double y, double z, boolean mystery, LivingEntity victim) {
        double d0 = victim.getX();
        double d1 = victim.getY();
        double d2 = victim.getZ();
        double d3 = y;
        boolean flag = false;
        BlockPos blockpos = new BlockPos(x, y, z);
        World world = victim.level;
        boolean flag1 = false;
        if (world.isLoaded(blockpos)) {
            while(!flag1 && d3 > d1) {
                BlockState state = world.getBlockState(blockpos);
                BlockState state1 = world.getBlockState(blockpos.below());
                BlockState state2 = world.getBlockState(blockpos.above());
                if(state.getMaterial().blocksMotion() || state1.getMaterial().blocksMotion()
                 || state2.getMaterial().blocksMotion()) {
                    --d3;
                    blockpos = new BlockPos(x, d3, z);
                } else {
                    flag1 = true;
                    LOGGER.debug(blockpos.getY());
                }
            }
            victim.moveTo(x, d3, z);
            victim.setPosAndOldPos(x, d3, z);
                if (world.noCollision(victim) && !world.containsAnyLiquid(victim.getBoundingBox())) {
                    flag = true;
                }
        }

        if (!flag) {
            victim.setPosAndOldPos(d0, d1, d2);
            return false;
        }
        else {
            return true;
        }
    }

    @Mod.EventBusSubscriber(
            modid = IrregularChef.MOD_ID,
            bus = Mod.EventBusSubscriber.Bus.FORGE
    )
    public static class boofloFeedEvent {
        public boofloFeedEvent() {
        }

        @SubscribeEvent
        public static void onBoofWrapApplied(PlayerInteractEvent.EntityInteract event) {
            PlayerEntity player = event.getPlayer();
            Entity target = event.getTarget();
            ItemStack itemStack = event.getItemStack();
            if (target.getType().getRegistryName() == BOOFLO) {
                AgeableEntity entity = (AgeableEntity) target;
                if (entity.isAlive() && itemStack.getItem().equals(IrregularChefItems.BOOF_WRAP.get())) {
                    entity.setHealth(entity.getMaxHealth());

                    booFX(entity);
                    entity.level.playSound((PlayerEntity) null, target.blockPosition(), SoundEvents.GENERIC_EAT, SoundCategory.PLAYERS, 1.0F, 0.8F);

                    for (int i = 0; i < 5; ++i) {
                        double d0 = MathUtils.RAND.nextGaussian() * 0.02D;
                        double d1 = MathUtils.RAND.nextGaussian() * 0.02D;
                        double d2 = MathUtils.RAND.nextGaussian() * 0.02D;
                        entity.level.addParticle((IParticleData) ModParticleTypes.STAR.get(), entity.getRandomX(1.0D), entity.getRandomY() + 0.5D, entity.getRandomZ(1.0D), d0, d1, d2);
                    }

                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }

                    event.setCancellationResult(ActionResultType.SUCCESS);
                    event.setCanceled(true);
                }
            }
        }

        private static void booFX(LivingEntity entity) {
            List<EffectInstance> list = BoofWrap.EFFECTS;
            Iterator<EffectInstance> var7 = list.iterator();
            while (var7.hasNext()) {
                EffectInstance effect = (EffectInstance) var7.next();
                entity.addEffect(new EffectInstance(effect));
            }
        }

    }
    


}
