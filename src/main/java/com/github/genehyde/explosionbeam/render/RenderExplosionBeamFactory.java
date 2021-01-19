package com.github.genehyde.explosionbeam.render;

import com.github.genehyde.explosionbeam.entity.ExplosionBeamEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RenderExplosionBeamFactory implements IRenderFactory<ExplosionBeamEntity> {
    public static final RenderExplosionBeamFactory INSTANCE = new RenderExplosionBeamFactory();

    @Override
    public EntityRenderer<? super ExplosionBeamEntity> createRenderFor(EntityRendererManager manager) {
        return new ExplosionBeamRender(manager);
    }
}
