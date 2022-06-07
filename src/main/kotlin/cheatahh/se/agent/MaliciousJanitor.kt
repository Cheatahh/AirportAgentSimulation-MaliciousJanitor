package cheatahh.se.agent

import cheatahh.se.integration.newDoorHandler
import cheatahh.se.util.*
import cheatahh.se.util.EntityCompanion
import cheatahh.se.util.solidOffset
import cheatahh.se.util.unsafe
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.config.ConfigurableAttribute
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.geometry.Point
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.simulation.entity.Agent
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.simulation.SimulationWorld
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
 * @param tileWidth The width a SlowDownTile.
 * @param tileHeight The height a SlowDownTile.
 * @param tileSlowDownTime Total amount of ticks a SlowDownTile can affect an entity.
 * @param tileSlowDownCoolDown Total amount of ticks an entity cannot be affected by the same SlowDownTile, after it has been slowed down.
 * @param tileSlowDownFunction Function to determine the actual speed amplifier at a given point in time. Example values are "42%" or "linear".
 * @param excludedEntityTypes A list of entity types to be excluded by the SlowDownTiles, delimited by whitespaces and commas.
 *
 * */
class MaliciousJanitor(initialSpeed: DoubleValue, tilePlacingChance: String, private val tileLifeTime: LongValue, private val tileWidth: IntValue, private val tileHeight: IntValue, private val tileSlowDownTime: LongValue, private val tileSlowDownCoolDown: LongValue, tileSlowDownFunction: String, excludedEntityTypes: String) : Agent() {

    // Null-Safe logger
    private val logger = ContextLogger(plugin)

    // The actual chance to place down a SlowDownTime, Ranges from 0.0 - 1.0
    private val placingChance = min(max(tilePlacingChance.substringBefore('%').toDoubleOrNull() ?: 10.0, 0.0), 100.0) / 100.0

    // The parsed SlowDownFunction, cached for faster invocation
    private val slowDownFunction = with(logger) { SlowDownFunctions[tileSlowDownFunction] }

    // A list of excluded class types, cached for faster invocation
    private val excludedTypes = excludedEntityTypes.split(Regex("[\\s,]+")).mapNotNull { try {
        Class.forName(it)
    } catch (_: Exception) { null } } + MaliciousJanitor::class.java

    // The last position of this agent. Used to determine, whether the agent is stuck
    private var lastPosition = Point(-1, -1)

    // Auto open & closed doors, as this is a staff member
    private val doorHandler = newDoorHandler()

    init {
        speed = initialSpeed.toDouble()
    }

    override fun onBirth() {
        // Ensure the underlying world is of the expected type -> Unsafe Api, temporary workaround
        require(world is SimulationWorld)
        logger.info("Hello World!")
    }

    // Move and place SlowDownTiles
    override fun pluginUpdate() {

        val currentPosition = position

        // Open & close doors
        doorHandler(logger, world.entities, currentPosition, amplifiedSpeed)

        // Move
        if(lastPosition == currentPosition) {
            do {
                try {
                    // Try to turn to a random point
                    turn(Point(Random.nextInt(0, world.width), Random.nextInt(0, world.height)))
                    break
                } catch (_: Exception) {} // Rare condition, where the randomly generated point is the exact point we are currently standing on.
            } while(true)
        }
        lastPosition = currentPosition

        // Place a SlowDownTile
        if(Random.nextDouble() <= placingChance) {
            if(currentPosition.x < world.width - 1 && currentPosition.y < world.height - 1) {
                val tile = SlowDownTile(logger, tileLifeTime.toLong(), tileSlowDownTime.toLong(), tileSlowDownCoolDown.toLong(), slowDownFunction, excludedTypes)
                unsafe.putBoolean(tile, solidOffset, false)
                world.runInjected(tile) {
                    tile.spawn(world, currentPosition.x, currentPosition.y, tileWidth.toInt(), tileHeight.toInt())
                }
            }
        }

    }

    /**
     * Companion Object to class [MaliciousJanitor].
     * Serves as descriptor for registering this agent.
     * */
    companion object : EntityCompanion<MaliciousJanitor> {

        override val id: String
            get() = "MaliciousJanitor"

        override val arguments: Array<ConfigurableAttribute>
            get() = arrayOf(
                ConfigurableAttribute("initialSpeed", DoubleValue::class.java, 1.0 as DoubleValue),
                ConfigurableAttribute("tilePlacingChance", String::class.java, "10%"),
                ConfigurableAttribute("tileLifeTime", LongValue::class.java, 100L as LongValue),
                ConfigurableAttribute("tileWidth", IntValue::class.java, 1 as IntValue),
                ConfigurableAttribute("tileHeight", IntValue::class.java, 1 as IntValue),
                ConfigurableAttribute("tileSlowDownTime", LongValue::class.java, 50L as LongValue),
                ConfigurableAttribute("tileSlowDownCoolDown", LongValue::class.java, 20L as LongValue),
                ConfigurableAttribute("tileSlowDownFunction", String::class.java, "linear"),
                ConfigurableAttribute("excludedEntityTypes", String::class.java, "")
            )

    }

}