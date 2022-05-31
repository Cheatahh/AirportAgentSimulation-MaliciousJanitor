package cheatahh.se.agent

import cheatahh.se.util.unsafe
import cheatahh.se.util.AgentCompanion
import cheatahh.se.util.xFractionOffset
import cheatahh.se.util.yFractionOffset
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.config.ConfigurableAttribute
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.geometry.Point
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.simulation.entity.Agent
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

/**
 * The [MaliciousJanitor] Agent is classified as attacker.
 * It randomly wanders around the simulation world and has a specific chance to place down a [SlowDownTile]. For example, this could be seen as waxing the floor.
 *
 * If an entity walks over a [SlowDownTile] it receives a temporary slowdown.
 *
 * @param initialSpeed The initial speed of this agent.
 * @param tilePlacingChance Chance to lay down a SlowDownTile in percent. Example values are "69" or "42%".
 * @param tileLifeTime Total amount of ticks a SlowDownTile is considered alive. After this threshold is reached, the tile will get cleared.
 * @param tileSlowDownTime Total amount of ticks a SlowDownTile can affect an entity.
 * @param tileSlowDownCoolDown Total amount of ticks an entity cannot be affected by the same SlowDownTile, after it has been slowed down.
 * @param tileSlowDownFunction Function to determine the actual speed amplifier at a given point in time. Example values are "42%" or "linear".
 * @param excludedEntityTypes A list of entity types to be excluded by the SlowDownTiles, delimited by whitespaces and commas.
 *
 * */
class MaliciousJanitor(initialSpeed: Double, tilePlacingChance: String, private val tileLifeTime: Long, private val tileSlowDownTime: Long, private val tileSlowDownCoolDown: Long, tileSlowDownFunction: String, excludedEntityTypes: String) : Agent() {

    // The actual chance to place down a SlowDownTime, Ranges from 0.0 - 1.0
    private val placingChance = min(max(tilePlacingChance.substringBefore('%').toDoubleOrNull() ?: 10.0, 0.0), 100.0) / 100.0

    // The parsed SlowDownFunction, cached for faster invocation
    private val slowDownFunction = SlowDownFunction[tileSlowDownFunction]

    // A list of excluded class types, cached for faster invocation
    private val excludedTypes = excludedEntityTypes.split(Regex("[\\s,]+")).mapNotNull { try {
        Class.forName(it)
    } catch (_: Exception) { null } } + MaliciousJanitor::class.java

    // The last position of this agent. Used to determine, whether the agent is stuck
    private var lastXFraction = 0.0
    private var lastYFraction = 0.0

    init {
        speed = initialSpeed
    }

    // Move and place SlowDownTiles
    override fun pluginUpdate() {

        // Get the current position as exact doubles
        val xFraction = unsafe.getDouble(this, xFractionOffset)
        val yFraction = unsafe.getDouble(this, yFractionOffset)

        if(lastXFraction == xFraction && lastYFraction == yFraction) {
            // We have not moved since the last tick -> We are stuck
            do {
                try {
                    // Try to turn to a random point
                    turn(Point(Random.nextInt(0, world.width), Random.nextInt(0, world.height)))
                    break
                } catch (_: Exception) {} // Rare condition, where the randomly generated point is the exact point we are currently standing on.
            } while(true)
        }

        lastXFraction = xFraction
        lastYFraction = yFraction

        // Place a SlowDownTile
        if(Random.nextDouble() <= placingChance) {
            // Check whether this point already contains a SlowDownTile, if not, spawn a new one
            if(world.entities.none { it.position == position && it is SlowDownTile }) {
                val tile = SlowDownTile(tileLifeTime, tileSlowDownTime, tileSlowDownCoolDown, slowDownFunction, excludedTypes)
                val pos = position
                tile.spawn(world, pos.x, pos.y, width, height)
            }
        }

    }

    /**
     * Companion Object to class [MaliciousJanitor].
     * Serves as descriptor for registering this agent.
     * */
    companion object : AgentCompanion<MaliciousJanitor> {

        override val id: String
            get() = "MaliciousJanitor"

        override val arguments: Array<ConfigurableAttribute>
            get() = arrayOf(
                ConfigurableAttribute("initialSpeed", Double::class.java, 1.0),
                ConfigurableAttribute("tilePlacingChance", String::class.java, "10%"),
                ConfigurableAttribute("tileLifeTime", Long::class.java, 100),
                ConfigurableAttribute("tileSlowDownTime", Long::class.java, 50),
                ConfigurableAttribute("tileSlowDownCoolDown", Long::class.java, 20),
                ConfigurableAttribute("tileSlowDownFunction", String::class.java, "linear"),
                ConfigurableAttribute("excludedEntityTypes", String::class.java, "")
            )

    }

}