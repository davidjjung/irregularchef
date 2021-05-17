package com.davigj.irregularchef.core;

import com.minecraftabnormals.abnormals_core.core.util.registry.RegistryHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(IrregularChef.MOD_ID)
public class IrregularChef {
	public static final String MOD_ID = "irregularchef";
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

	public IrregularChef() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		REGISTRY_HELPER.register(bus);
		MinecraftForge.EVENT_BUS.register(this);

		bus.addListener(this::commonSetup);
		bus.addListener(this::clientSetup);
		bus.addListener(this::dataSetup);
	}
	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
		});
	}
	private void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
		});
	}
	private void dataSetup(GatherDataEvent event) {
	}
}