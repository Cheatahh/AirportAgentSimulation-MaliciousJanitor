package cheatahh.se

import cheatahh.se.agent.MaliciousJanitor
import cheatahh.se.agent.SlowDownTile
import cheatahh.se.util.registerEntity
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.AirportAgentSimulation
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.plugin.Plugin

/**
 * Plugin for the [AirportAgentSimulation] of my software engineering class.
 *
 * This plugin provides an Attacker-Agent, the [MaliciousJanitor]
 * */
class MaliciousJanitorPlugin : Plugin {

    override fun activate() {
        registerEntity(MaliciousJanitor)
        registerEntity(SlowDownTile)
    }

}
