package net.bosco.mod.entity.client;

import net.bosco.mod.BoscoMod;
import net.bosco.mod.entity.custom.BoscoEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BoscoRenderer extends MobEntityRenderer<BoscoEntity, BoscoModel<BoscoEntity>> {
    public BoscoRenderer(EntityRendererFactory.Context context) {
        super(context, new BoscoModel<>(context.getPart(BoscoModel.BOSCO)), 0.5f);
    }

    @Override
    public Identifier getTexture(BoscoEntity entity) {
        return Identifier.of(BoscoMod.MOD_ID, "textures/entity/bosco/bosco_texture.png");
    }

    @Override
    public void render(BoscoEntity entity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.scale(1f, 1f, 1f);

        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
    }

}
