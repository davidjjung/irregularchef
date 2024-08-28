package com.davigj.irregular_chef.core.registry;

import com.davigj.irregular_chef.common.item.*;
import com.davigj.irregular_chef.core.IrregularChefMod;
import com.davigj.irregular_chef.core.other.ICConstants;
import com.teamabnormals.blueprint.core.util.item.CreativeModeTabContentsPopulator;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.function.Supplier;

import static net.minecraft.world.item.crafting.Ingredient.of;

@Mod.EventBusSubscriber(modid = IrregularChefMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ICItems {
    public static final ItemSubRegistryHelper HELPER = IrregularChefMod.REGISTRY_HELPER.getItemSubHelper();

    private static Supplier<Item> getCompatItem(String modid, ResourceLocation item) {
        return (ModList.get().isLoaded(modid) ? () -> ForgeRegistries.ITEMS.getValue(item) : () -> null);
    }
    private static boolean isModLoaded(String modid) {
        return (ModList.get().isLoaded(modid));
    }

    public static final RegistryObject<Item> BIRDS_NEST_SOUP = HELPER.createItem("birds_nest_soup", () -> new ConsumableItem(
            new Item.Properties().food(Foods.BIRDS_NEST_SOUP).craftRemainder(Items.BOWL).stacksTo(16), true, false));

    public static final RegistryObject<Item> THRASHER_SOUP = HELPER.createItem("thrasher_soup", () -> new ConsumableItem(
            new Item.Properties().food(Foods.THRASHER_SOUP).stacksTo(16), true, false));

    public static final RegistryObject<Item> PURPLE_COW_FLOAT = HELPER.createItem("purple_cow_float", () -> new ConsumableItem(
            new Item.Properties().food(Foods.PURPLE_COW_FLOAT).stacksTo(16), true, false));

    public static final RegistryObject<Item> DIRT_CUP = HELPER.createItem("dirt_cup", () -> new DirtCupItem(
            new Item.Properties().food(Foods.DIRT_CUP).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    public static final RegistryObject<Item> BELT_SPAGHETTI = HELPER.createItem("belt_spaghetti", () -> new ConsumableItem(
            new Item.Properties().food(Foods.BELT_SPAGHETTI).craftRemainder(Items.BOWL).stacksTo(16),true, false));

    public static final RegistryObject<Item> STEAMED_BEEF_WRAP = HELPER.createItem("steamed_beef_wrap", () -> new SteamedBeefWrapItem(
            new Item.Properties().food(Foods.STEAMED_BEEF_WRAP),
            ICConstants.NEAPOLITAN, ICConstants.AGILITY, 600, 0, false));

    public static final RegistryObject<Item> POOL_PARTY_STICK = HELPER.createItem("pool_party_stick", () -> new PoolPartyStickItem(
            new Item.Properties().food(Foods.POOL_PARTY_STICK), false, true));

    // Blocky foods
    public static final RegistryObject<Item> TURKEY_POT_PIE = HELPER.createItem("turkey_pot_pie", () -> new BlockItem(
            ICBlocks.TURKEY_POT_PIE.get(), new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> TURKEY_POT_PIE_SLICE = HELPER.createItem("turkey_pot_pie_slice", () -> new ConsumableItem(
            new Item.Properties().food(Foods.TURKEY_POT_PIE_SLICE), true, false));

    public static final RegistryObject<Item> HUMBLE_PIE = HELPER.createItem("humble_pie", () -> new BlockItem(
            ICBlocks.HUMBLE_PIE.get(), new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> HUMBLE_PIE_SLICE = HELPER.createItem("humble_pie_slice", () -> new HumblePieSlice(
            new Item.Properties().food(Foods.HUMBLE_PIE_SLICE), false, true));

    public static final RegistryObject<Item> TURTLE_GALLIMAUFRY = HELPER.createItem("turtle_gallimaufry", () -> new ConsumableItem(
            new Item.Properties().food(Foods.TURTLE_GALLIMAUFRY).craftRemainder(Items.BOWL).stacksTo(16), true));
    public static final RegistryObject<Item> TURTLE_GALLIMAUFRY_BLOCK = HELPER.createItem("turtle_gallimaufry_block", () -> new BlockItem(
            ICBlocks.TURTLE_GALLIMAUFRY_BLOCK.get(), new Item.Properties().stacksTo(1).craftRemainder(Items.TURTLE_HELMET)));
    public static final RegistryObject<Item> SURF_AND_TURF = HELPER.createItem("surf_and_turf", () -> new ConsumableItem(
            new Item.Properties().food(Foods.SURF_AND_TURF).craftRemainder(Items.BOWL).stacksTo(16), true));
    public static final RegistryObject<Item> SURF_AND_TURF_BLOCK = HELPER.createItem("surf_and_turf_block", () -> new BlockItem(
            ICBlocks.SURF_AND_TURF_BLOCK.get(), new Item.Properties().stacksTo(1).craftRemainder(getCompatItem(ICConstants.UPGRADE_AQUATIC, ICConstants.BEACHGRASS).get())));

    private static boolean isSurfAndTurf() {
        return (isModLoaded(ICConstants.ECOLOGICS) || isModLoaded(ICConstants.ALEXSMOBS) || isModLoaded(ICConstants.QUARK) || isModLoaded(ICConstants.CRABBERSDELIGHT))
                && isModLoaded(ICConstants.UPGRADE_AQUATIC);
    }

    static class Foods {
        public static final FoodProperties BIRDS_NEST_SOUP = (new FoodProperties.Builder()).nutrition(8).saturationMod(1.6F)
                .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), (int) ((60 * 6.5) * 20)), 1.0F).build();
        public static final FoodProperties THRASHER_SOUP = (new FoodProperties.Builder()).nutrition(7).saturationMod(0.7F)
                .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 20 * 15, 0, false, true), 1.0F)
                .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 18, 2, false, true), 1.0F).build();
        public static final FoodProperties DIRT_CUP = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.6F).build();
        public static final FoodProperties PRICKLY_PUDDING = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.6F).build();
        public static final FoodProperties BELT_SPAGHETTI = (new FoodProperties.Builder()).nutrition(13).saturationMod(0.8F)
                .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 90 * 20, 1, false, true), 1.0F)
                .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 180 * 20, 0, false, true), 1.0F).build();
        public static final FoodProperties SURF_AND_TURF = (new FoodProperties.Builder()).nutrition(14).saturationMod(0.8F)
                .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 300 * 20, 0, false, true), 1.0F).build();

        public static final FoodProperties HUNTERS_JERKY = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.6F)
                .effect(() -> new MobEffectInstance(MobEffects.INVISIBILITY, 20 * 10, 0, true, false), 1.0F).build();
        public static final FoodProperties STEAMED_BEEF_WRAP = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.8F)
                .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 2400), 1.0F).build();

        public static final FoodProperties TRAFFIC_JAM_ROLL = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.8F)
                .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100), 1.0F).build();
        public static final FoodProperties POOL_PARTY_STICK = (new FoodProperties.Builder()).nutrition(9).saturationMod(0.6F).build();
        public static final FoodProperties PURPLE_COW_FLOAT = (new FoodProperties.Builder()).nutrition(0).saturationMod(0.0F).alwaysEat()
                .effect(() -> new MobEffectInstance(MobEffects.LEVITATION, 20 * 15), 1.0F).build();

        public static final FoodProperties TURTLE_GALLIMAUFRY = (new FoodProperties.Builder()).nutrition(11).saturationMod(0.7F)
                .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 1800), 1.0F)
                .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 6000), 1.0F).build();
        public static final FoodProperties TURKEY_POT_PIE_SLICE = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.25F).fast().
                effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 300), 1.0F)
                .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 600), 1.0F).build();
        public static final FoodProperties HUMBLE_PIE_SLICE = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.5F).fast().build();

        //        public static final FoodProperties LAVENDER_MASHED_POTATOES = (new FoodProperties.Builder()).nutrition(11).saturationMod(0.7F).effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 1200), 1.0F).build();
    }

    public static void buildCreativeTabContents() {
        CreativeModeTabContentsPopulator.mod(IrregularChefMod.MOD_ID)
                .tab(CreativeModeTabs.FOOD_AND_DRINKS)
                .addItemsAfter(of(Items.RABBIT_STEW), BIRDS_NEST_SOUP, THRASHER_SOUP, BELT_SPAGHETTI, POOL_PARTY_STICK,
                        DIRT_CUP, PURPLE_COW_FLOAT, HUMBLE_PIE, HUMBLE_PIE_SLICE, TURKEY_POT_PIE, TURKEY_POT_PIE_SLICE,
                        TURTLE_GALLIMAUFRY, TURTLE_GALLIMAUFRY_BLOCK);
    }
}
