package com.github.genehyde.explosionbeam.render;

import com.github.genehyde.explosionbeam.ExplosionBeam;
import com.github.genehyde.explosionbeam.entity.ExplosionBeamEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//@OnlyIn(Dist.CLIENT)
public class ExplosionBeamRender extends EntityRenderer<ExplosionBeamEntity> {
    private static final Logger LOGGER = LogManager.getLogger();
    public ExplosionBeamRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        LOGGER.info("NEW RENDERER");
    }

    public void render(ExplosionBeamEntity entityIn, float entityYaw, float partialTicks, MatrixStack
            matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(ExplosionBeamEntity entity) {
        return new ResourceLocation("textures/entity/bee/bee.png");
    }

}
