package cheatahh.se.agent

import cheatahh.se.util.ContextLogger
import cheatahh.se.util.runInjected
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.simulation.entity.MovingEntity
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.simulation.entity.StaticEntity
import kotlin.math.max
import kotlin.math.min

/**
 * The [SlowDownTile] Entity, described in [MaliciousJanitor]
 * */
context(ContextLogger)
internal class SlowDownTile(private val lifeTime: Long, private val slowDownTime: Long, private val coolDownTime: Long, private val slowDownFunction: SlowDownFunction, private val excludedTypes: List<Class<*>>) : StaticEntity() {

    // The ticks since this tile is alive. If this value is larger than [lifeTime] the tile will kill itself
    private var aliveTicks = 0L

    // All entities affected by this SlowDownTile
    private val slowDownEffectEntities = hashSetOf<SlowDownEffect>()

    // Unique speedAmplifierKey for this SlowDownTile
    private val identitySpeedAmplifier = "slowDownTileSpeedAmplifier${System.identityHashCode(this)}"

    override fun onBirth() {
        isSolid = false // This entity is non-solid. It will not take part in collision detections to allow entities to enter the tile
        info("Placed a new SlowDownTile at (${position.x}, $position.y)")
    }

    override fun onDeath() {
        info("SlowDownTile at (${position.x}, $position.y) died")
    }

    override fun pluginUpdate() {

        // Check whether the entity should be killed
        if(aliveTicks++ >= lifeTime && slowDownEffectEntities.isEmpty()) return world.runInjected(this) {
            kill()
        }

        // Acquire all entities on top of this tile
        if(aliveTicks < lifeTime) for(entity in world.entities) {

            if(entity.position != position || entity !is MovingEntity || entity.javaClass in excludedTypes || entity.isDead || !entity.isSolid) continue

            // Tries to add this entity to the affection set, silently fails if this entity is already present
            slowDownEffectEntities += SlowDownEffect(entity)
        }

        // Iterate over all claimed entities and apply the speedAmplifier attribute
        if(slowDownEffectEntities.isNotEmpty()) {
            val iterator = slowDownEffectEntities.iterator()
            for(effect in iterator) {
                // Evaluate the current effect state of this entity
                val effectTicks = effect.slowDownTicks++
                if(effectTicks == slowDownTime) {
                    // Entity is no longer slowed down, remove amplifier
                    effect.target.setSpeedAmplifier(identitySpeedAmplifier, 1.0)
                } else if(effectTicks > slowDownTime) {
                    // Entity is in coolDown phase
                    if(effectTicks >= slowDownTime + coolDownTime) iterator.remove()
                } else {
                    // Entity is slowed down
                    // A new speedAmplifier will be evaluated and applied
                    val newAmplifier = min(max(slowDownFunction(effect.slowDownTicks.toDouble() / slowDownTime), 0.0), 1.0)
                    val currentSpeed = effect.target.amplifiedSpeed
                    effect.target.setSpeedAmplifier(identitySpeedAmplifier, newAmplifier)
                    info("Changed speed of ${effect.target} ($currentSpeed -> ${effect.target.amplifiedSpeed})")
                }
            }
        }

    }

    // Wrapper class to keep track of the slowDown Effect
    class SlowDownEffect(val target: MovingEntity) {

        var slowDownTicks = 0L

        // This class takes the identity of ::target, in order to be matched by the HashSet
        override fun equals(other: Any?): Boolean {
            if (this === other) return true //Should never happen
            if (other !is SlowDownEffect || target !== other.target) return false
            return true
        }

        // This class takes the identity of ::target, in order to be matched by the HashSet
        override fun hashCode() = System.identityHashCode(target)

    }

}