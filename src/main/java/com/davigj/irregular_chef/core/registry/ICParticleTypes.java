package com.davigj.irregular_chef.core.registry;

import com.davigj.irregular_chef.client.particle.ToqueParticle;
import com.davigj.irregular_chef.core.IrregularChefMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = IrregularChefMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ICParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, IrregularChefMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> TOQUE = PARTICLE_TYPES.register("toque", () -> new SimpleParticleType(true));

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ICParticleTypes.TOQUE.get(), ToqueParticle.Factory::new);
    }
}
