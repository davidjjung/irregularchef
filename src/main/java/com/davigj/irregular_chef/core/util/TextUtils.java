package com.davigj.irregular_chef.core.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class TextUtils extends vectorwing.farmersdelight.common.utility.TextUtils{
    public TextUtils() {
    }

    public static MutableComponent getTranslation(String key, Object... args) {
        return Component.translatable("irregular_chef." + key, args);
    }
}
