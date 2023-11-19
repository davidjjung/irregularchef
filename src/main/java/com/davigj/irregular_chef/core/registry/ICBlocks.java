package com.davigj.irregular_chef.core.registry;

import com.davigj.irregular_chef.common.block.HumblePieBlock;
import com.davigj.irregular_chef.common.block.TurtleGallimaufryBlock;
import com.davigj.irregular_chef.common.block.WaffleBlock;
import com.davigj.irregular_chef.core.IrregularChefMod;
import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.block.PieBlock;

@Mod.EventBusSubscriber(modid = IrregularChefMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ICBlocks {
    public static final BlockSubRegistryHelper HELPER = IrregularChefMod.REGISTRY_HELPER.getBlockSubHelper();
    public static final RegistryObject<Block> TURKEY_POT_PIE = HELPER.createBlockNoItem("turkey_pot_pie", () -> new PieBlock(
            Block.Properties.copy(Blocks.CAKE), ICItems.TURKEY_POT_PIE_SLICE));
    public static final RegistryObject<Block> HUMBLE_PIE = HELPER.createBlockNoItem("humble_pie", () -> new HumblePieBlock(
            Block.Properties.copy(Blocks.CAKE), ICItems.HUMBLE_PIE_SLICE));

    public static final RegistryObject<Block> TURTLE_GALLIMAUFRY_BLOCK = HELPER.createBlockNoItem("turtle_gallimaufry_block", () -> new TurtleGallimaufryBlock(
            Block.Properties.copy(Blocks.BONE_BLOCK).sound(SoundType.BONE_BLOCK), ICItems.TURTLE_GALLIMAUFRY, true));
    public static final RegistryObject<Block> WAFFLE_BLOCK = HELPER.createBlockNoItem("waffle", () -> new WaffleBlock(
            Block.Properties.copy(Blocks.CAKE).sound(SoundType.WOOL)));
}