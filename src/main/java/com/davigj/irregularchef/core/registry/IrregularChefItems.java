package com.davigj.irregularchef.core.registry;

import com.davigj.irregularchef.common.item.*;
import com.davigj.irregularchef.core.IrregularChef;
import com.davigj.irregularchef.core.other.IrregularChefCompat;
import com.minecraftabnormals.abnormals_core.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.items.ConsumableItem;
import vectorwing.farmersdelight.registry.ModEffects;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = IrregularChef.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class IrregularChefItems {
    //TODO: Beef bone broth.
    public static final ItemSubRegistryHelper HELPER = IrregularChef.REGISTRY_HELPER.getItemSubHelper();

    private static Supplier<Item> getCompatItem(String modid, ResourceLocation item) {
        return (ModList.get().isLoaded(modid) ? () -> ForgeRegistries.ITEMS.getValue(item) : () -> null);
    }

    private static boolean isModLoaded(String modid) {
        return (ModList.get().isLoaded(modid));
    }

    public static final RegistryObject<Item> STEAMED_BEEF_WRAP = HELPER.createItem("steamed_beef_wrap", () -> new SingleEffect(
            new Item.Properties().food(Foods.STEAMED_BEEF_WRAP).tab(isModLoaded(IrregularChefCompat.CompatMods.NEAPOLITAN) ? ItemGroup.TAB_FOOD : null),
            IrregularChefCompat.CompatMods.NEAPOLITAN, IrregularChefCompat.CompatEffects.AGILITY, 300, 0));

    public static final RegistryObject<Item> TURKEY_POT_PIE = HELPER.createItem("turkey_pot_pie", () -> new BlockItem(
            IrregularChefBlocks.TURKEY_POT_PIE.get(), new Item.Properties().stacksTo(16).tab(isModLoaded(IrregularChefCompat.CompatMods.AUTUMNITY) ? ItemGroup.TAB_FOOD : null)));
    public static final RegistryObject<Item> TURKEY_POT_PIE_SLICE = HELPER.createItem("turkey_pot_pie_slice", () -> new Item(
            new Item.Properties().food(Foods.TURKEY_POT_PIE_SLICE).tab(isModLoaded(IrregularChefCompat.CompatMods.AUTUMNITY) ? ItemGroup.TAB_FOOD : null)));

    public static final RegistryObject<Item> LAVENDER_MASHED_POTATOES = HELPER.createItem("lavender_mashed_potatoes", () -> new SingleBowledEffect(
            new Item.Properties().food(Foods.LAVENDER_MASHED_POTATOES).craftRemainder(Items.BOWL).stacksTo(16).tab(isModLoaded(IrregularChefCompat.CompatMods.ABUNDANCE) ? ItemGroup.TAB_FOOD : null),
            IrregularChefCompat.CompatMods.ABUNDANCE, IrregularChefCompat.CompatEffects.SUPPORTIVE, 300, 0));

    public static final RegistryObject<Item> BIRDS_NEST_SOUP = HELPER.createItem("birds_nest_soup", () -> new ConsumableItem(
            new Item.Properties().food(Foods.BIRDS_NEST_SOUP).craftRemainder(Items.BOWL).stacksTo(16).tab(isModLoaded(IrregularChefCompat.CompatMods.ENVIRONMENTAL) ? ItemGroup.TAB_FOOD : null)));

    public static final RegistryObject<Item> TRAFFIC_JAM_ROLL = HELPER.createItem("traffic_jam_roll", () -> new TrafficJamRoll(
            new Item.Properties().food(Foods.TRAFFIC_JAM_ROLL).tab((isModLoaded(IrregularChefCompat.CompatMods.UPGRADE_AQUATIC) && isModLoaded(IrregularChefCompat.CompatMods.ATMOSPHERIC)) ? ItemGroup.TAB_FOOD : null)));

    public static final RegistryObject<Item> PURPLE_COW_FLOAT = HELPER.createItem("purple_cow_float", () -> new PurpleCowFloat(
            new Item.Properties().food(Foods.PURPLE_COW_FLOAT).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab((isModLoaded(IrregularChefCompat.CompatMods.ENDERGETIC) && isModLoaded(IrregularChefCompat.CompatMods.ATMOSPHERIC)) ? ItemGroup.TAB_FOOD : null)));

//    public static final RegistryObject<Item> BOOF_WRAP = HELPER.createItem("boof_wrap", () -> new BoofWrap(
//            new Item.Properties().food(Foods.BOOF_WRAP).tab((isModLoaded(IrregularChefCompat.CompatMods.ENDERGETIC)) ? ItemGroup.TAB_FOOD : null)));

    public static final RegistryObject<Item> DIRT_CUP = HELPER.createItem("dirt_cup", () -> new DirtCup(
            new Item.Properties().food(Foods.DIRT_CUP).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab((isModLoaded(IrregularChefCompat.CompatMods.ENVIRONMENTAL) && isModLoaded(IrregularChefCompat.CompatMods.NEAPOLITAN) && isModLoaded(IrregularChefCompat.CompatMods.UPGRADE_AQUATIC)) ? ItemGroup.TAB_FOOD : null)));

    public static final RegistryObject<Item> POOL_PARTY_STICK = HELPER.createItem("pool_party_stick", () -> new PoolPartyStick(
            new Item.Properties().food(Foods.POOL_PARTY_STICK).craftRemainder(getCompatItem(IrregularChefCompat.CompatMods.UPGRADE_AQUATIC,
                    IrregularChefCompat.CompatItems.PRISMARINE_ROD).get()).tab(isModLoaded(IrregularChefCompat.CompatMods.UPGRADE_AQUATIC) ? ItemGroup.TAB_FOOD : null)));

    public static final RegistryObject<Item> HUNTERS_CASSEROLE = HELPER.createItem("hunters_casserole", () -> new HuntersCasserole(
            new Item.Properties().food(Foods.HUNTERS_CASSEROLE).stacksTo(16).tab(ItemGroup.TAB_FOOD)));

    public static final RegistryObject<Item> CHARCOAL_WAFFLES = HELPER.createItem("charcoal_waffles", () -> new ConsumableItem(
            new Item.Properties().food(Foods.CHARCOAL_WAFFLES).tab(ItemGroup.TAB_FOOD)));

    public static final RegistryObject<Item> THRASHER_SOUP = HELPER.createItem("thrasher_soup", () -> new ConsumableItem(
            new Item.Properties().food(Foods.THRASHER_SOUP).stacksTo(16).tab(isModLoaded(IrregularChefCompat.CompatMods.UPGRADE_AQUATIC) ? ItemGroup.TAB_FOOD : null)));

    public static final RegistryObject<Item> TURTLE_GALLIMAUFRY = HELPER.createItem("turtle_gallimaufry", () -> new ConsumableItem(
            new Item.Properties().food(Foods.TURTLE_GALLIMAUFRY).craftRemainder(Items.BOWL).stacksTo(16).tab(ItemGroup.TAB_FOOD)));
    public static final RegistryObject<Item> TURTLE_GALLIMAUFRY_BLOCK = HELPER.createItem("turtle_gallimaufry_block", () -> new BlockItem(
            IrregularChefBlocks.TURTLE_GALLIMAUFRY_BLOCK.get(), new Item.Properties().stacksTo(1).craftRemainder(Items.TURTLE_HELMET).tab(ItemGroup.TAB_FOOD)));

    static class Foods {
        public static final Food STEAMED_BEEF_WRAP = (new Food.Builder()).nutrition(8).saturationMod(0.8F).effect(() -> new EffectInstance(ModEffects.NOURISHED.get(), 2400), 1.0F).build();
        public static final Food TURKEY_POT_PIE_SLICE = (new Food.Builder()).nutrition(4).saturationMod(0.4F).fast().effect(() -> new EffectInstance(ModEffects.NOURISHED.get(), 600), 1.0F).effect(() -> new EffectInstance(ModEffects.COMFORT.get(), 200), 1.0F).build();
        public static final Food LAVENDER_MASHED_POTATOES = (new Food.Builder()).nutrition(11).saturationMod(0.7F).build();
        public static final Food BIRDS_NEST_SOUP = (new Food.Builder()).nutrition(7).saturationMod(2.0F).effect(() -> new EffectInstance(ModEffects.COMFORT.get(), 2000), 1.0F).build();
        public static final Food TRAFFIC_JAM_ROLL = (new Food.Builder()).nutrition(5).saturationMod(0.8F).build();
        public static final Food DIRT_CUP = (new Food.Builder()).nutrition(4).saturationMod(0.6F).build();
        public static final Food POOL_PARTY_STICK = (new Food.Builder()).nutrition(9).saturationMod(0.6F).build();
        public static final Food PURPLE_COW_FLOAT = (new Food.Builder()).nutrition(0).saturationMod(0.0F).alwaysEat().effect(() -> new EffectInstance(Effects.LEVITATION, 120), 1.0F).build();
        public static final Food TURTLE_GALLIMAUFRY = (new Food.Builder()).nutrition(11).saturationMod(0.7F).effect(() -> new EffectInstance(Effects.WATER_BREATHING, 1800), 1.0F).effect(() -> new EffectInstance(ModEffects.NOURISHED.get(), 6000), 1.0F).build();
        public static final Food HUNTERS_CASSEROLE = (new Food.Builder()).nutrition(8).saturationMod(0.6F).effect(() -> new EffectInstance(Effects.INVISIBILITY, 300, 0, true, false), 1.0F).build();
        public static final Food CHARCOAL_WAFFLES = (new Food.Builder()).nutrition(4).saturationMod(0.8F).effect(() -> new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 480, 0, false, true), 0.7F).effect(() -> new EffectInstance(Effects.FIRE_RESISTANCE, 500, 0, false, true), 1.0F).build();
        public static final Food THRASHER_SOUP = (new Food.Builder()).nutrition(7).saturationMod(0.8F).effect(() -> new EffectInstance(Effects.BLINDNESS, 260, 0, false, true), 1.0F).effect(() -> new EffectInstance(Effects.DAMAGE_BOOST, 300, 2, false, true), 1.0F).build();
        public static final Food BOOF_WRAP = (new Food.Builder()).nutrition(4).saturationMod(0.6F).effect(() -> new EffectInstance(Effects.SLOW_FALLING, 60, 0, false, true), 1.0F).build();

    }
}