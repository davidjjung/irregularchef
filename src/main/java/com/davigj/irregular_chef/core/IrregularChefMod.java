package com.davigj.irregular_chef.core;

import com.davigj.irregular_chef.core.other.ICConstants;
import com.davigj.irregular_chef.core.registry.ICItems;
import com.davigj.irregular_chef.core.registry.ICParticleTypes;
import com.teamabnormals.blueprint.common.world.storage.tracking.DataProcessors;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedData;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(IrregularChefMod.MOD_ID)
public class IrregularChefMod {
    public static final String MOD_ID = "irregular_chef";
    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

    public static final TrackedData<Integer> GLOW_WORMY = TrackedData.Builder.create(DataProcessors.INT, () -> 0).enableSaving().enablePersistence().build();
    public static final TrackedData<Integer> SWAMP_DOGGY = TrackedData.Builder.create(DataProcessors.INT, () -> 0).enableSaving().enablePersistence().build();

    public IrregularChefMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext context = ModLoadingContext.get();
        MinecraftForge.EVENT_BUS.register(this);

		REGISTRY_HELPER.register(bus);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ICItems::buildCreativeTabContents);

        ICParticleTypes.PARTICLE_TYPES.register(bus);

        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);
        bus.addListener(this::dataSetup);
        context.registerConfig(ModConfig.Type.COMMON, ICConfig.COMMON_SPEC);

        TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "glow_wormy"), GLOW_WORMY);
        TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "swamp_doggy"), SWAMP_DOGGY);
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