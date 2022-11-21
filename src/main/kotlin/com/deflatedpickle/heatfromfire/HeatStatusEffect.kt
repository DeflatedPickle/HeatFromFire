/* Copyright (c) 2021-2022 DeflatedPickle under the MIT license */

package com.deflatedpickle.heatfromfire

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectType.HARMFUL

object HeatStatusEffect : StatusEffect(
    HARMFUL,
    0,
) {
    override fun isInstant() = true

    override fun canApplyUpdateEffect(duration: Int, amplifier: Int) = true

    override fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) {
        if (!entity.world.isClient) {
            entity.damage(
                DamageSource.ON_FIRE,
                0.5f
            )
        }
    }
}
