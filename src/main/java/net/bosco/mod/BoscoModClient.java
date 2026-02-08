package net.bosco.mod;

import net.bosco.mod.entity.ModEntities;
import net.bosco.mod.entity.client.BoscoModel;
import net.bosco.mod.entity.client.BoscoRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class BoscoModClient implements ClientModInitializer {
    @Override


    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(BoscoModel.BOSCO, BoscoModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.BOSCO, BoscoRenderer::new);
    }
}
