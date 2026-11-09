package com.davigj.irregular_chef.core.other;

import com.davigj.irregular_chef.core.registry.ICBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

public class ICClientCompat {
    public static void registerRenderLayers() {
        ItemBlockRenderTypes.setRenderLayer(ICBlocks.STEWED_CHIMP_HEAD_BLOCK.get(), RenderType.translucent());
    }
}
