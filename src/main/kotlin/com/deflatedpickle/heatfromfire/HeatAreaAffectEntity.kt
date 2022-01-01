/* Copyright (c) 2021 DeflatedPickle under the MIT license */

package com.deflatedpickle.heatfromfire

import com.google.common.collect.Lists
import net.minecraft.entity.AreaEffectCloudEntity
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.particle.ParticleTypes
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.world.World

class HeatAreaAffectEntity(
    entityType: EntityType<HeatAreaAffectEntity>,
    world: World,
) : AreaEffectCloudEntity(entityType, world) {
    constructor(
        world: World,
        x: Double,
        y: Double,
        z: Double,
    ) : this(HeatFromFire.HEAT_AREA_EFFECT, world) {
        this.setPosition(x, y, z)
    }

    override fun tick() {
        if (age >= waitTime + duration) {
            discard()
            return
        }

        affectedEntities.entries.removeIf { (_, value): Map.Entry<Entity?, Int> -> age >= value as Int }
        val i = Lists.newArrayList<StatusEffectInstance>()
        for (j in potion.effects) {
            i.add(StatusEffectInstance(j.effectType, j.duration / 4, j.amplifier, j.isAmbient, j.shouldShowParticles()))
        }
        i.addAll(effects)
        affectedEntities.clear()

        val g = world.getNonSpectatingEntities(LivingEntity::class.java, this.boundingBox)

        for (h in g) {
            var f = this.radius

            var q: Double
            var k: Double
            var r: Double
            if (affectedEntities.containsKey(h) || !h.isAffectedBySplashPotions || (
                h.x - this.x.also { k = it }
                ) * k + (h.z - this.z.also { q = it }) * q.also { r = it } > (f * f).toDouble()
            ) continue
            affectedEntities[h] = age + reapplicationDelay
            for (n in i) {
                if (n.effectType.isInstant) {
                    n.effectType.applyInstantEffect(this, owner, h, n.amplifier, 0.5)
                    continue
                }
                h.addStatusEffect(StatusEffectInstance(n), this)
            }
            if (radiusOnUse != 0.0f) {
                if (radiusOnUse.let { f += it; f } < 0.5f) {
                    discard()
                    return
                }
                this.radius = f
            }
            if (durationOnUse == 0) continue
            duration += durationOnUse
            if (duration > 0) continue
            discard()
            return
        }

        if (random.nextInt(4) == 0) {
            val d = pos.getX() + random.nextDouble(0.0, 1.0)
            val e = pos.getY() - 1.0
            val f = pos.getZ() + random.nextDouble(0.0, 1.0)
            world.addImportantParticle(ParticleTypes.SMOKE, d, e, f, 0.0, 0.0, 0.0)
            world.playSound(
                d,
                e,
                f,
                SoundEvents.ENTITY_BLAZE_BURN,
                SoundCategory.BLOCKS,
                0.2f + random.nextFloat() * 0.2f,
                0.9f + random.nextFloat() * 0.15f,
                false
            )
        }
    }
}
