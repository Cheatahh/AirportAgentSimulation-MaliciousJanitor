@file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")

package cheatahh.se.util

import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.AirportAgentSimulation
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.config.ConfigurationFormatException
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.plugin.Plugin
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.simulation.entity.Entity

/**
 * Helper function to register an entity.
 *
 * @param entityDescriptor The Companion Descriptor of the entity class
 * */
internal inline fun <reified EntityType : Entity> Plugin.registerEntity(entityDescriptor: EntityCompanion<EntityType>) {

    try {
        AirportAgentSimulation.registerEntity(this, entityDescriptor.id, EntityType::class.java, entityDescriptor.arguments)
    } catch (exception: ConfigurationFormatException) {
        exception.printStackTrace()
    }

}

internal typealias DoubleValue = java.lang.Double
internal typealias LongValue = java.lang.Long
internal typealias IntValue = Integer