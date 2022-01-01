/* Copyright (c) 2021 DeflatedPickle under the MIT license */

package com.deflatedpickle.heatfromfire

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.minecraft.client.render.entity.EmptyEntityRenderer

@Suppress("UNUSED")
object HeatFromFireClient : ClientModInitializer {
    private const val MOD_ID = "$[id]"
    private const val NAME = "$[name]"
    private const val GROUP = "$[group]"
    private const val AUTHOR = "$[author]"
    private const val VERSION = "$[version]"

    override fun onInitializeClient() {
        EntityRendererRegistry.register(HeatFromFire.HEAT_AREA_EFFECT, ::EmptyEntityRenderer)
    }
}
