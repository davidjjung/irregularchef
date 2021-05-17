package com.davigj.irregularchef.core.util;

import com.davigj.irregularchef.core.IrregularChef;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TextUtils {
        public static IFormattableTextComponent getTranslation(String key, Object... args) {
            return new TranslationTextComponent(IrregularChef.MOD_ID + "." + key, args);
        }
    }

