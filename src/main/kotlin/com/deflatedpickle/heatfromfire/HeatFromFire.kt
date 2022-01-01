/* Copyright (c) 2021 DeflatedPickle under the MIT license */

package com.deflatedpickle.heatfromfire

import net.fabricmc.api.ModInitializer
import net.minecraft.block.AbstractFireBlock
import net.minecraft.block.Block
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.world.World

@Suppress("UNUSED")
object HeatFromFire : ModInitializer {
    private const val MOD_ID = "$[id]"
    private const val NAME = "$[name]"
    private const val GROUP = "$[group]"
    private const val AUTHOR = "$[author]"
    private const val VERSION = "$[version]"

    val HEAT_AREA_EFFECT: EntityType<HeatAreaAffectEntity> = Registry.register(
        Registry.ENTITY_TYPE,
        "heat_area_effect",
        EntityType.Builder.create(
            { t: EntityType<HeatAreaAffectEntity>, w -> HeatAreaAffectEntity(t, w) }, SpawnGroup.MISC
        ).makeFireImmune().setDimensions(1.0f, 0.5f).maxTrackingRange(10).trackingTickInterval(
            Int.MAX_VALUE
        ).build("heat_area_effect")
    )

    override fun onInitialize() {
        println(listOf(MOD_ID, NAME, GROUP, AUTHOR, VERSION))

        Registry.register(Registry.STATUS_EFFECT, Identifier(MOD_ID, "heat"), HeatStatusEffect)
    }

    fun neighborUpdate(world: World, block: Block, pos: BlockPos) {
        if (!world.isClient) {
            if (block is AbstractFireBlock) {
                world.spawnEntity(
                    HeatAreaAffectEntity(
                        world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble()
                    ).apply {
                        radius = 0.5f
                        waitTime = 0
                        duration = 150
                        particleType = ParticleTypes.SMOKE
                        addEffect(StatusEffectInstance(HeatStatusEffect))
                    }
                )
            }
        }
    }
}
