package cheatahh.se.util

import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.config.ConfigurableAttribute
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.simulation.entity.Agent

/**
 * Serves as descriptor for registering an agent.
 * */
internal interface AgentCompanion<AgentType: Agent> {

    /**
     * The global id of the agent.
     * */
    val id: String

    /**
     * Parameters to be read from the config file. These should match the primary constructor of the given agent.
     * */
    val arguments: Array<ConfigurableAttribute>
}