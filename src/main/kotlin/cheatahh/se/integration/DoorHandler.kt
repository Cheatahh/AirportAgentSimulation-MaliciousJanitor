package cheatahh.se.integration

import cheatahh.se.util.ContextLogger
import cheatahh.se.util.rangeTo
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.geometry.Point
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.simulation.entity.Entity
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType

/**
 * Provides an integration for the [entities.Door] class. (Objects Group)
 * The [MaliciousJanitor][cheatahh.se.agent.MaliciousJanitor] is a staff member, so doors will open and close automatically for this agent.
 *
 * @return A handler instance. If the required module is not loaded, an empty function will be returned.
 * */
internal fun newDoorHandler(): DoorHandler = if(doorClass != null) DoorHandlerImplementation() else { _, _, _, _ -> }

internal typealias DoorHandler = (logger: ContextLogger, entities: Collection<Entity>, position: Point, range: Double) -> Unit

// Check if module is present
private val doorClass = try {
    Class.forName("entities.Door")
} catch (_: Exception) { null }

// Lazily loaded implementation
private class DoorHandlerImplementation : DoorHandler {

    private val openDoors = hashSetOf<Entity>()

    override operator fun invoke(logger: ContextLogger, entities: Collection<Entity>, position: Point, range: Double) {
        entities.forEach {
            if(doorClass!!.isInstance(it) && position .. it.position <= range && openDoors.add(it)) {
                openDoorHandle(it)
                logger.info("Opened door $it")
            }
        }
        val iterator = openDoors.iterator()
        for(door in iterator) {
            if(position .. door.position > range) {
                closeDoorHandle(door)
                logger.info("Closed door $door")
                iterator.remove()
            }
        }
    }

    // Dynamic method handles, because the entities.Door class is protected
    companion object Handles {
        private val openDoorHandle = MethodHandles.privateLookupIn(doorClass, MethodHandles.lookup()).findVirtual(doorClass, "openDoor", MethodType.methodType(Void.TYPE))
        private val closeDoorHandle = MethodHandles.privateLookupIn(doorClass, MethodHandles.lookup()).findVirtual(doorClass, "closeDoor", MethodType.methodType(Void.TYPE))
    }
}