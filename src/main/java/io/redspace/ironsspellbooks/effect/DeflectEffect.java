package io.redspace.ironsspellbooks.effect;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.capabilities.magic.SyncedSpellData;
import io.redspace.ironsspellbooks.datagen.DamageTypeTagGenerator;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class DeflectEffect extends CustomDescriptionMobEffect {
    public DeflectEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public Component getDescriptionLine(MobEffectInstance instance) {
        int amp = instance.getAmplifier() + 1;
        return Component.translatable("tooltip.irons_spellbooks.deflect.description", amp).withStyle(ChatFormatting.BLUE);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
        MagicData.getPlayerMagicData(pLivingEntity).getSyncedData().removeEffects(SyncedSpellData.DEFLECT);
    }

    @Override
    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
        MagicData.getPlayerMagicData(pLivingEntity).getSyncedData().addEffects(SyncedSpellData.DEFLECT);
        MagicData.getPlayerMagicData(pLivingEntity).getSyncedData().setDeflectHitsRemaining(pAmplifier);
    }

    public static boolean doEffect(LivingEntity livingEntity, DamageSource damageSource) {
        if (livingEntity.level.isClientSide
                || damageSource.is(DamageTypeTags.IS_FALL)
                || damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)
                || damageSource.is(DamageTypeTagGenerator.BYPASS_DEFLECT)
                || !(damageSource.getDirectEntity() instanceof Projectile)
                || (damageSource.getDirectEntity() instanceof AbstractMagicProjectile)) {
            return false;
        }

        var data = MagicData.getPlayerMagicData(livingEntity).getSyncedData();
        data.subtractDeflectHit();
        if (data.getDeflectHitsRemaining() < 0) {
            livingEntity.removeEffect(MobEffectRegistry.DEFLECT.get());
        }

        // Deflect the projectile
        Projectile projectile = (Projectile) damageSource.getDirectEntity();
        Vec3 currentVelocity = projectile.getDeltaMovement();
        Vec3 reverseVelocity = currentVelocity.scale(-1); // Reverse the direction
        projectile.setDeltaMovement(reverseVelocity);
        projectile.hasImpulse = true;

        // Play deflect sound
        livingEntity.level.playSound((Player) null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0F, 1.0F);
        return true;
    }
}
