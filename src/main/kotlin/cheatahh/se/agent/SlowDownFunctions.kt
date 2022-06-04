@file:Suppress("SpellCheckingInspection", "KDocUnresolvedReference")

package cheatahh.se.agent

import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

/**
 * Function to determine the actual speed reduction at a given point in time.
 *
 * @param time The stage of this effect. Ranges from 0.0 (start) to 1.0 (end)
 * @return The speed amplifier at this point in time.
 * */
internal typealias SlowDownFunction = (time: Double) -> Double

internal object SlowDownFunctions {

    /**
     * Constant holding an exponential progress function.
     *
     * **Source** [easings.net](https://easings.net/de#easeInExpo)
     * */
    private val exponential = { time: Double ->
        if(time == 0.0) 0.0 else 2.0.pow(10 * time - 10)
    }

    /**
     * Constant holding an inverted exponential progress function.
     *
     * **Source** [easings.net](https://easings.net/de#easeOutExpo)
     * */
    private val exponentialInverted = { time: Double ->
        if(time == 1.0) 1.0 else 1.0 - 2.0.pow(-10 * time)
    }

    /**
     * Constant holding a linear progress function.
     * */
    private val linear = { time: Double ->
        time
    }

    /**
     * Constant holding a cubic progress function.
     * */
    private val cubic = { time: Double ->
        time * time
    }

    /**
     * Constant holding an inverted cubic progress function.
     *
     * **Source** [easings.net](https://easings.net/de#easeOutCubic)
     * */
    private val cubicInverted = { time: Double ->
        val oneMinusTime = 1.0 - time
        1.0 - oneMinusTime * oneMinusTime
    }

    /**
     * Get or create a function from a given input name.
     *
     * @param name The name of the function. Possible values are "exponential", "exponentialInverted", "linear", "cubic", "cubicInverted" or any percentages like for example "42%".
     * */
    operator fun get(name: String): SlowDownFunction = getOrNull(name) ?: { 0.0 }

    internal fun getOrNull(name: String): SlowDownFunction? {
        return when(name) {
            "exponential" -> exponential
            "exponentialInverted" -> exponentialInverted
            "linear" -> linear
            "cubic" -> cubic
            "cubicInverted" -> cubicInverted
            else -> {
                val checkedValue = min(max(name.substringBefore('%').toDoubleOrNull() ?: return null, 0.0), 100.0)
                val percentage = checkedValue / 100
                { percentage }
            }
        }
    }
}