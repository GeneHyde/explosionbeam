package com.github.genehyde.explosionbeam;

import com.github.genehyde.explosionbeam.init.ModEntities;
import com.github.genehyde.explosionbeam.init.ModItemGroups;
import com.github.genehyde.explosionbeam.init.ModSounds;
import com.github.genehyde.explosionbeam.item.ExplosionBeamItem;
import net.minecraft.client.audio.Sound;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistryEntry;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(ExplosionBeam.MODID)
public class ExplosionBeam {
    public static final String MODID = "explosionbeam";

    public ExplosionBeam() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class
    // (this is subscribing to the MOD Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(
                    setup(new ExplosionBeamItem(new Item.Properties()
                            .group(ModItemGroups.MOD_ITEM_GROUP)
                            .setNoRepair()
                            .maxDamage(10)), "explosion_beam")
            );
        }

        @SubscribeEvent
        public static void onRegisterEntity(final RegistryEvent.Register<EntityType<?>> event) {
            ModEntities.EXPLOSION_BEAM_ENTITY.setRegistryName("element", "explosionbeam");

            event.getRegistry().registerAll(ModEntities.EXPLOSION_BEAM_ENTITY);
        }

        @SubscribeEvent
        public static void onRegisterSound(final RegistryEvent.Register<SoundEvent> event) {
            event.getRegistry().registerAll(ModSounds.MEGUMIN_CHANT, ModSounds.MEGUMIN_EXPLOSION);
        }

        public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
            return setup(entry, new ResourceLocation(MODID, name));
        }

        public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
            entry.setRegistryName(registryName);
            return entry;
        }
    }
}
