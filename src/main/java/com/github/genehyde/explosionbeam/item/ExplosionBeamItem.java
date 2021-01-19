package com.github.genehyde.explosionbeam.item;

import com.github.genehyde.explosionbeam.entity.ExplosionBeamEntity;
import com.github.genehyde.explosionbeam.init.ModSounds;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExplosionBeamItem extends Item {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public Minecraft mc = Minecraft.getInstance();

    public ExplosionBeamItem(Properties builder) {
        super(builder);
        this.addPropertyOverride(new ResourceLocation("throwing"), (p_210315_0_, p_210315_1_, p_210315_2_) -> {
            return p_210315_2_ != null && p_210315_2_.isHandActive() && p_210315_2_.getActiveItemStack() == p_210315_0_ ? 1.0F : 0.0F;
        });
    }

    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return player.isCreative();
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public void onPlayerUsing() {

    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            int i = this.getUseDuration(stack) - timeLeft;
            if (i >= 10) {
                if (!worldIn.isRemote) {
                    final RayTraceResult rayResult = rayTrace(playerentity, 20, worldIn);
                    Vec3d vec3d = rayResult.getHitVec();
                    float explosionRadius = (float) (i / 20) + 1;
                    explosionRadius = explosionRadius > 10 ? 10 : explosionRadius;
                    ExplosionBeamEntity explosionBeamEntity = new ExplosionBeamEntity(worldIn,
                            vec3d.getX(), vec3d.getY(), vec3d.getZ(), explosionRadius);
                    worldIn.addEntity(explosionBeamEntity);
                    worldIn.playMovingSound(null, playerentity, ModSounds.MEGUMIN_EXPLOSION,
                            SoundCategory.PLAYERS, 1.0F, 1.0F);
                }

                playerentity.addStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    public static BlockRayTraceResult rayTrace(Entity player, double length, World worldIn){
        Vec3d vec = player.getPositionVector();
        Vec3d vec3 = new Vec3d(vec.x,vec.y+player.getEyeHeight(),vec.z);
        Vec3d vecLook = player.getLook(1.0F);
        Vec3d vecEnd = vec3.add(vecLook.getX() * length, vecLook.getY()*  length,
                vecLook.getZ()*  length);

        return worldIn.rayTraceBlocks(new RayTraceContext(vec3,
                vecEnd,RayTraceContext.BlockMode.OUTLINE,  RayTraceContext.FluidMode.NONE, player));
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (itemstack.getDamage() >= itemstack.getMaxDamage() - 1) {
            return ActionResult.resultFail(itemstack);
        } else if (EnchantmentHelper.getRiptideModifier(itemstack) > 0 && !playerIn.isWet()) {
            return ActionResult.resultFail(itemstack);
        } else {
            playerIn.setActiveHand(handIn);
            return ActionResult.resultConsume(itemstack);
        }
    }

    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (p_220048_0_) -> {
            p_220048_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if ((double)state.getBlockHardness(worldIn, pos) != 0.0D) {
            stack.damageItem(2, entityLiving, (p_220046_0_) -> {
                p_220046_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }

        return true;
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EquipmentSlotType.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", 8.0D, AttributeModifier.Operation.ADDITION));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -2.9000000953674316D, AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    public int getItemEnchantability() {
        return 1;
    }
}
