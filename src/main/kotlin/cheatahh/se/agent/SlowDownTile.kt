package cheatahh.se.agent

import cheatahh.se.util.speedAmplifierOffset
import cheatahh.se.util.unsafe
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.simulation.entity.MovingEntity
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.simulation.entity.StaticEntity
import kotlin.math.max
import kotlin.math.min

// All entities claimed by SlowDownTiles
private val claimedEntities = mutableMapOf<MovingEntity, SlowDownTile.SlowDownEffect>()

/**
 * The [SlowDownTile] Entity, described in [MaliciousJanitor]
 * */
internal class SlowDownTile(private val lifeTime: Long, private val slowDownTime: Long, private val coolDownTime: Long, private val slowDownFunction: SlowDownFunction, private val excludedTypes: List<Class<*>>) : StaticEntity() {

    // The ticks since this tile is alive. If this value is larger than [lifeTime] the tile will kill itself
    private var aliveTicks = 0L

    override fun onBirth() {
        isSolid = false // This entity is non-solid. It will not take part in collision detections to allow entities to enter the tile
    }

    override fun pluginUpdate() {

        // Check whether the entity should be killed
        if(aliveTicks++ >= lifeTime) return kill()

        // Acquire all entities on top of this tile
        for(entity in world.entities) {
            if(entity.position != position || entity !is MovingEntity || entity.javaClass in excludedTypes || entity.isDead || !entity.isSolid || claimedEntities[entity]?.owner === this) continue
            // Claim this entity, any claims by other SlowDownTiles will be dropped
            claimedEntities[entity] = SlowDownEffect()
        }

        // Iterate over all claimed entities and apply the speedAmplifier attribute
        val iterator = claimedEntities.iterator()
        for((entity, effect) in iterator) {
            // Discard entities owned by other tiles
            if(effect.owner !== this) continue

            // Evaluate the current effect state of this entity
            val effectTicks = effect.slowDownTicks++
            if(effectTicks == slowDownTime) {
                // Entity is no longer slowed down
                unsafe.putDouble(entity, speedAmplifierOffset, 1.0)
            } else if(effectTicks > slowDownTime) {
                // Entity is in coolDown phase
                if(effectTicks >= slowDownTime + coolDownTime) iterator.remove()
            } else {
                // Entity is slowed down
                // A new speedAmplifier will be evaluated and applied
                unsafe.putDouble(entity,
                    speedAmplifierOffset,
                    min(max(slowDownFunction(effect.slowDownTicks.toDouble() / slowDownTime), 0.0), 1.0)
                )
            }
        }

    }

    // Inner class to keep track of the slowDown Effect
    inner class SlowDownEffect {
        var slowDownTicks = 0L
        val owner
            get() = this@SlowDownTile
    }

}