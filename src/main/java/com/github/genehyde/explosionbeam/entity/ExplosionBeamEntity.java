package com.github.genehyde.explosionbeam.entity;


import com.github.genehyde.explosionbeam.init.ModEntities;
import net.minecraft.entity.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExplosionBeamEntity extends Entity {
    private static final DataParameter<Integer> FUSE;
    private static final DataParameter<Float> RADIUS;
    private int fuse;
    private float radius;

    public ExplosionBeamEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        this.fuse = 40;
        this.radius = 2F;
        this.preventEntitySpawning = true;
    }

    public ExplosionBeamEntity(World worldIn, double x, double y, double z, float radius) {
        this(ModEntities.EXPLOSION_BEAM_ENTITY, worldIn);
        this.setPosition(x, y, z);
        this.setFuse(40);
        this.setRadius(radius);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public boolean canBeCollidedWith() {
        return false;
    }

    public void tick() {
        --this.fuse;
        if (this.fuse <= 0) {
            this.remove();
            if (!this.world.isRemote) {
                this.explode();
            }
        }

        if (this.world.isRemote) {
            int evenFuse = fuse % 2 == 0 ? 1 : -1;
            double d1 = evenFuse * ((10 * this.world.rand.nextDouble()) % 2) / 10;
            double d2 = evenFuse * ((10 * this.world.rand.nextDouble()) % 2) / 10;
            double d3 = evenFuse * ((10 * this.world.rand.nextDouble()) % 2) / 10;
            double d4 = evenFuse * ((10 * this.world.rand.nextDouble()) % 2) / 10;
            double d5 = this.world.rand.nextDouble() / 10;
            double d6 = this.world.rand.nextDouble() / 10;
            double d7 = this.world.rand.nextDouble() / 10;
            double d8 = this.world.rand.nextDouble() / 10;
            double d9 = this.world.rand.nextDouble() / 10;
            double d10 = this.world.rand.nextDouble() / 10;
            this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), d1, d5, d2);
            this.world.addParticle(ParticleTypes.FLAME, this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), d3, d6, d4);
            this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), d7, 0.3D, d8);
            this.world.addParticle(ParticleTypes.FLAME, this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), d9, 0.3D, d10);
        }
    }

    protected void explode() {
        this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), this.radius, Explosion.Mode.DESTROY);
    }

    @Override
    protected float getEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.0F;
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        if (FUSE.equals(key)) {
            this.fuse = this.getFuseDataManager();
        } else if (RADIUS.equals(key)) {
            this.radius = this.getRadiusDataManager();
        }
    }

    public int getFuseDataManager() {
        return this.dataManager.get(FUSE);
    }

    public float getRadiusDataManager() {
        return this.dataManager.get(RADIUS);
    }

    @Override
    protected void registerData() {
        this.dataManager.register(FUSE, 40);
        this.dataManager.register(RADIUS, 2F);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        compound.putShort("Fuse", (short)this.getFuse());
        compound.putShort("Radius", (short)this.getRadius());
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        this.setFuse(compound.getShort("Fuse"));
        this.setRadius(compound.getShort("Radius"));
    }

    public int getFuse() {
        return this.fuse;
    }

    public void setFuse(int fuseIn) {
        this.dataManager.set(FUSE, fuseIn);
        this.fuse = fuseIn;
    }

    public float getRadius() {
        return this.radius;
    }

    public void setRadius(float radius) {
        this.dataManager.set(RADIUS, radius);
        this.radius = radius;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    static {
        FUSE = EntityDataManager.createKey(ExplosionBeamEntity.class, DataSerializers.VARINT);
        RADIUS = EntityDataManager.createKey(ExplosionBeamEntity.class, DataSerializers.FLOAT);
    }
}
