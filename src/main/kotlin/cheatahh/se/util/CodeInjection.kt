package cheatahh.se.util

import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.geometry.Point
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.simulation.World
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.simulation.entity.Entity
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.simulation.message.LocalMessage
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.simulation.message.StoredMessage

/**
 * Utility function to run injected code after the entity update loop.
 * This should be used to avoid an [ConcurrentModificationException]
 * */
fun World.runInjected(entity: Entity, action: () -> Unit) {
    @Suppress("unchecked_cast")
    (unsafe.getObject(this, messagesOffset) as MutableCollection<StoredMessage>) += InjectionMessage(entity, action)
}

private class InjectionMessage(origin: Entity, private val injectAction: () -> Unit) : StoredMessage(LocalDummyMessage(origin), Long.MAX_VALUE, ArrayList()) {

    override fun getTargets(): ArrayList<Entity> {
        // Run injected code
        injectAction()
        return super.getTargets()
    }

    private class LocalDummyMessage(private val origin: Entity) : LocalMessage {
        override fun fromString(string: String) = Unit
        override fun getMaxRange() = 1
        override fun getOrigin(): Entity = origin
        override fun getOriginPosition(): Point = origin.position
    }

}