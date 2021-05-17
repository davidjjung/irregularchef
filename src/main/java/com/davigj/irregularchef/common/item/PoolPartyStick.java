package com.davigj.irregularchef.common.item;

import com.davigj.irregularchef.core.util.TextUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PoolPartyStick extends Item {
    public PoolPartyStick (Properties builder) {
        super(builder);
    }

    @Override
    public ItemStack onItemUseFinish (ItemStack stack, World worldIn, LivingEntity entityLiving) {
        PlayerEntity playerentity = entityLiving instanceof PlayerEntity ? (PlayerEntity)entityLiving : null;
        ItemStack container = stack.getContainerItem();
        ItemStack itemstack = super.onItemUseFinish(stack, worldIn, entityLiving);
        if (entityLiving instanceof PlayerEntity && ((PlayerEntity)entityLiving).abilities.isCreativeMode) {
            return itemstack;
        } else {
            if (playerentity == null || !playerentity.abilities.isCreativeMode) {
                if (itemstack.isEmpty()) {
                    return container;
                }
                if (playerentity != null) {
                    // don't know why removing the bottom line fixes it giving two sticks
                    playerentity.inventory.addItemStackToInventory(container);
                }
            }
            entityLiving.setAir(300);
            return stack;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        IFormattableTextComponent tip = TextUtils.getTranslation("tooltip.pool_party_stick.tip");
        tooltip.add(tip.mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
    }
}

