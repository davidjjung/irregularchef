package com.davigj.irregular_chef.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ICConfig {
    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Boolean> hunterHuntBox;
        public final ForgeConfigSpec.ConfigValue<Integer> huntBoxRadius;
        public final ForgeConfigSpec.ConfigValue<Boolean> chefSlabfish;

        Common (ForgeConfigSpec.Builder builder) {
            builder.push("changes");
            hunterHuntBox = builder.comment("Consuming hunter's jerky gives living entities Glowing").define("Hunter's illumination", true);
            huntBoxRadius = builder.comment("Block radius in which hunter jerky gives Glowing").define("Hunter's hitbox", 16);
            chefSlabfish = builder.comment("Knighting a slabfish with a knife on a cooking pot turns it into a chef slabfish if Environmental is installed")
                    .define("Chef slabfish", true);
            builder.pop();
        }
    }

    static final ForgeConfigSpec COMMON_SPEC;
    public static final ICConfig.Common COMMON;


    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ICConfig.Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
}
