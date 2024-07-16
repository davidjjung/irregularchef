package com.davigj.irregular_chef.core.other.tags;

import com.davigj.irregular_chef.core.IrregularChefMod;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ICItemTags {
    public static final TagKey<Item> FANCY_KNIVES = itemTag("fancy_knives");

    private static TagKey<Item> itemTag(String name) {
        return TagUtil.itemTag(IrregularChefMod.MOD_ID, name);
    }
}
