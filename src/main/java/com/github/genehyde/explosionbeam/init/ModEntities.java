package com.github.genehyde.explosionbeam.init;

import com.github.genehyde.explosionbeam.entity.ExplosionBeamEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

public class ModEntities {
    public static EntityType<ExplosionBeamEntity> EXPLOSION_BEAM_ENTITY = EntityType
            .Builder.<ExplosionBeamEntity>create(ExplosionBeamEntity::new, EntityClassification.MISC)
            .setShouldReceiveVelocityUpdates(false)
            .setUpdateInterval(1).setTrackingRange(128).size(1F, 1F)
            .build("element:explosionbeam");
}
