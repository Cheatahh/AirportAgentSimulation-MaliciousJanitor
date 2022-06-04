@file:Suppress("deprecation")

package cheatahh.se.util

import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.simulation.entity.Entity
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.simulation.SimulationWorld
import sun.misc.Unsafe

// Provides access to the Unsafe API
internal val unsafe = Unsafe::class.java.getDeclaredField("theUnsafe").also { it.isAccessible = true }[null] as Unsafe

// Field offset of Entity::isSolid
internal val solidOffset = unsafe.objectFieldOffset(Entity::class.java.getDeclaredField("solid"))

// Field offset of SimulationWorld::messages
internal val messagesOffset = unsafe.objectFieldOffset(SimulationWorld::class.java.getDeclaredField("messages"))
