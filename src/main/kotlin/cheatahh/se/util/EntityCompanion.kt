package cheatahh.se.util

import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.config.ConfigurableAttribute
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.simulation.entity.Entity

/**
 * Serves as descriptor for registering an entity.
 * */
internal interface EntityCompanion<EntityType: Entity> {

    /**
     * The global id of the entity.
     * */
    val id: String

    /**
     * Parameters to be read from the config file. These should match the primary constructor of the given entity.
     * */
    val arguments: Array<ConfigurableAttribute>
}