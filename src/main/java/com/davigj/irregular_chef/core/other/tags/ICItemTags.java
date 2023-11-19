package com.davigj.irregular_chef.core.other.tags;

import com.davigj.irregular_chef.core.IrregularChefMod;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ICItemTags {
    public static final TagKey<Item> FANCY_KNIVES = itemTag("fancy_knives");
    public static final TagKey<Item> WAFFLE_TOPPINGS = itemTag("waffle_toppings");
    public static final TagKey<Item> SYRUP_CONTAINERS = itemTag("syrup_containers");
    public static final TagKey<Item> RED_BERRIES = itemTag("red_berries");
    public static final TagKey<Item> OCHRE_BERRIES = itemTag("ochre_berries");
    public static final TagKey<Item> CHOCOLATE = itemTag("chocolate");

    private static TagKey<Item> itemTag(String name) {
        return TagUtil.itemTag(IrregularChefMod.MOD_ID, name);
    }
}
