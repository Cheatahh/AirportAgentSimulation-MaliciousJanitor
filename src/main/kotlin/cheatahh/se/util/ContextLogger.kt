package cheatahh.se.util

import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.AirportAgentSimulation
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.logging.PluginLogger
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.plugin.Plugin

@JvmInline
value class ContextLogger private constructor(private val base: PluginLogger?) {

    constructor(plugin: Plugin?) : this(kotlin.runCatching { AirportAgentSimulation.getLogger(plugin) }.getOrNull())

    fun info(any: Any) = base?.info(any)
    fun warn(any: Any) = base?.warn(any)
}