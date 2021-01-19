package com.github.genehyde.explosionbeam;

import com.github.genehyde.explosionbeam.init.ModEntities;
import com.github.genehyde.explosionbeam.render.RenderExplosionBeamFactory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = ExplosionBeam.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSideEventBusSubscriber {
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void clientSetup(FMLCommonSetupEvent setupEvent) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.EXPLOSION_BEAM_ENTITY, RenderExplosionBeamFactory.INSTANCE);
    }
}
