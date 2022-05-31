package cheatahh.se.util

import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.simulation.entity.MovingEntity
import sun.misc.Unsafe

// Provides access to the Unsafe API
internal val unsafe = Unsafe::class.java.getDeclaredField("theUnsafe").also { it.isAccessible = true }[null] as Unsafe

// Field offset of MovingEntity::speedAmplifier
@Suppress("deprecation")
internal val speedAmplifierOffset = unsafe.objectFieldOffset(MovingEntity::class.java.getDeclaredField("speedAmplifier"))

// Field offset of MovingEntity::xFraction
@Suppress("deprecation")
internal val xFractionOffset = unsafe.objectFieldOffset(MovingEntity::class.java.getDeclaredField("xFraction"))

// Field offset of MovingEntity::yFraction
@Suppress("deprecation")
internal val yFractionOffset = unsafe.objectFieldOffset(MovingEntity::class.java.getDeclaredField("yFraction"))
