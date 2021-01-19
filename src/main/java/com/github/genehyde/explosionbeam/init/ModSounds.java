package com.github.genehyde.explosionbeam.init;

import com.github.genehyde.explosionbeam.ExplosionBeam;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class ModSounds {
    public static SoundEvent MEGUMIN_CHANT =
            new SoundEvent(new ResourceLocation(ExplosionBeam.MODID, "megumin_chant"))
                    .setRegistryName("megumin_chant");

    public static SoundEvent MEGUMIN_EXPLOSION =
            new SoundEvent(new ResourceLocation(ExplosionBeam.MODID, "megumin_explosion"))
                    .setRegistryName("megumin_explosion");
}
