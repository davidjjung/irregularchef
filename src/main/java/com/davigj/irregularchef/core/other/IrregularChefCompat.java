package com.davigj.irregularchef.core.other;

import net.minecraft.util.ResourceLocation;

public class IrregularChefCompat {
    public static class CompatMods {
        public static final String ATMOSPHERIC = "atmospheric";
        public static final String ABUNDANCE = "abundance";
        public static final String AUTUMNITY = "autumnity";
        public static final String BUZZIER_BEES = "buzzier_bees";
        public static final String ENVIRONMENTAL = "environmental";
        public static final String ENDERGETIC = "endergetic";
        public static final String NEAPOLITAN = "neapolitan";
        public static final String UPGRADE_AQUATIC = "upgrade_aquatic";
    }

    public static class CompatEffects {
        public static final ResourceLocation PERSISTENCE = new ResourceLocation(CompatMods.ATMOSPHERIC, "persistence");
        public static final ResourceLocation VANILLA_SCENT = new ResourceLocation(CompatMods.NEAPOLITAN, "vanilla_scent");
        public static final ResourceLocation SUGAR_RUSH = new ResourceLocation(CompatMods.NEAPOLITAN, "sugar_rush");
        public static final ResourceLocation AGILITY = new ResourceLocation(CompatMods.NEAPOLITAN, "agility");
        public static final ResourceLocation REPELLENCE = new ResourceLocation(CompatMods.UPGRADE_AQUATIC, "repellence");
        public static final ResourceLocation SUNNY = new ResourceLocation(CompatMods.BUZZIER_BEES, "sunny");
        public static final ResourceLocation SUPPORTIVE = new ResourceLocation(CompatMods.ABUNDANCE, "supportive");

    }

    public static class CompatItems {
        public static final ResourceLocation SNAIL_SHELL_PIECE = new ResourceLocation(CompatMods.AUTUMNITY, "snail_shell_piece");
        public static final ResourceLocation SMALL_BANANA_FROND = new ResourceLocation(CompatMods.NEAPOLITAN, "small_banana_frond");
        public static final ResourceLocation PRISMARINE_ROD = new ResourceLocation(CompatMods.UPGRADE_AQUATIC, "prismarine_rod");
    }

}
