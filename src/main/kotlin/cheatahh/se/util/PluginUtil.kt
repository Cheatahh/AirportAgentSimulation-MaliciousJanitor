package cheatahh.se.util

import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.AirportAgentSimulation
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.config.ConfigurationFormatException
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.plugin.Plugin
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.simulation.entity.Agent

/**
 * Helper function to register an agent.
 *
 * @param agentDescriptor The Companion Descriptor of the agent class
 * */
internal inline fun <reified AgentType : Agent> Plugin.registerAgent(agentDescriptor: AgentCompanion<AgentType>) {

    try {
        AirportAgentSimulation.registerEntity(this, agentDescriptor.id, AgentType::class.java, agentDescriptor.arguments)
    } catch (exception: ConfigurationFormatException) {
        exception.printStackTrace()
    }

}