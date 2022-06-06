package cheatahh.se.util

import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.geometry.Point
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Uses the pythagorean theorem to calculate the absolute distance between two points.
 *
 * @return The absolute distance between two points.
 * */
internal infix operator fun Point.rangeTo(other: Point) : Double {
    val dx = abs(x - other.x).toDouble()
    val dy = abs(y - other.y).toDouble()
    return sqrt(dx * dx + dy * dy)
}