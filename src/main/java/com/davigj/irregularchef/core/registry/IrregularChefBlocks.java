package com.davigj.irregularchef.core.registry;

import com.davigj.irregularchef.common.block.TurtleGallimaufryBlock;
import com.minecraftabnormals.abnormals_core.core.util.registry.BlockSubRegistryHelper;
import com.davigj.irregularchef.core.IrregularChef;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.blocks.PieBlock;

@Mod.EventBusSubscriber(modid = IrregularChef.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class IrregularChefBlocks {
	public static final BlockSubRegistryHelper HELPER = IrregularChef.REGISTRY_HELPER.getBlockSubHelper();

	public static final RegistryObject<Block> TURKEY_POT_PIE = HELPER.createBlockNoItem("turkey_pot_pie", () -> new PieBlock(
			Block.Properties.from(Blocks.CAKE), IrregularChefItems.TURKEY_POT_PIE_SLICE));

	public static final RegistryObject<Block> TURTLE_GALLIMAUFRY_BLOCK = HELPER.createBlockNoItem("turtle_gallimaufry_block", () -> new TurtleGallimaufryBlock(
			Block.Properties.from(Blocks.BONE_BLOCK).sound(SoundType.BONE), IrregularChefItems.TURTLE_GALLIMAUFRY, true));
}
