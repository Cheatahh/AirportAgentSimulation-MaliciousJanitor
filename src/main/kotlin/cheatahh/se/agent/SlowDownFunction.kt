package cheatahh.se.agent

import kotlin.math.max
import kotlin.math.min

/**
 * Function to determine the actual speed reduction at a given point in time.
 * */
@FunctionalInterface
internal fun interface SlowDownFunction {

    /**
     * Functional component, meant to be bound by an invoke-dynamic instruction.
     *
     * @param time The stage of this effect. Ranges from 0.0 (start) to 1.0 (end)
     * @return The speed amplifier at this point in time.
     * */
    operator fun invoke(time: Double) : Double

    companion object {

        /**
         * Constant holding a linear progress function.
         * */
        private val linear = SlowDownFunction { delta ->
            delta
        }

        /**
         * Get or create a function from a given input name.
         *
         * @param name The name of the function. Example values are "42%" or "linear".
         * */
        operator fun get(name: String) = when(name) {
            "linear" -> linear
            else -> {
                val checkedValue = min(max(name.substringBefore('%').toDoubleOrNull() ?: 0.0, 0.0), 100.0)
                val percentage = checkedValue / 100
                SlowDownFunction {
                    percentage
                }
            }
        }
    }
}