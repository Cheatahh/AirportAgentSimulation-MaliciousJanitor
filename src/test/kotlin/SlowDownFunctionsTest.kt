import cheatahh.se.util.SlowDownFunctions
import kotlin.math.pow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

private const val maxTick = 1000

class SlowDownFunctionsTest {

    @Test
    fun testLinearFunction() {
        val function = SlowDownFunctions.getOrNull("linear")
        assertNotNull(function, "Could not parse input 'linear'")

        repeat(maxTick) { tick ->
            val tickProgress = tick.toDouble() / maxTick.toDouble()
            val slowDownFactor = function(tickProgress)
            assertEquals(tickProgress, slowDownFactor)
        }
    }

    @Test
    fun testCubicFunction() {
        val function = SlowDownFunctions.getOrNull("cubic")
        assertNotNull(function, "Could not parse input 'cubic'")

        repeat(maxTick) { tick ->
            val tickProgress = tick.toDouble() / maxTick.toDouble()
            val slowDownFactor = function(tickProgress)
            assertEquals(tickProgress * tickProgress, slowDownFactor)
        }
    }

    @Test
    fun testCubicInvertedFunction() {
        val function = SlowDownFunctions.getOrNull("cubicInverted")
        assertNotNull(function, "Could not parse input 'cubicInverted'")

        repeat(maxTick) { tick ->
            val tickProgress = tick.toDouble() / maxTick.toDouble()
            val slowDownFactor = function(tickProgress)
            val oneMinusTickProgress = 1.0 - tickProgress
            assertEquals(1.0 - oneMinusTickProgress * oneMinusTickProgress, slowDownFactor)
        }
    }

    @Test
    fun testExponentialFunction() {
        val function = SlowDownFunctions.getOrNull("exponential")
        assertNotNull(function, "Could not parse input 'exponential'")

        repeat(maxTick) { tick ->
            val tickProgress = tick.toDouble() / maxTick.toDouble()
            val slowDownFactor = function(tickProgress)

            if(tickProgress == 0.0) assertEquals(0.0, slowDownFactor)
            else assertEquals(2.0.pow(10 * tickProgress - 10), slowDownFactor)
        }
    }

    @Test
    fun testExponentialInvertedFunction() {
        val function = SlowDownFunctions.getOrNull("exponentialInverted")
        assertNotNull(function, "Could not parse input 'exponentialInverted'")

        repeat(maxTick) { tick ->
            val tickProgress = tick.toDouble() / maxTick.toDouble()
            val slowDownFactor = function(tickProgress)

            if(tickProgress == 1.0) assertEquals(1.0, slowDownFactor)
            else assertEquals(1.0 - 2.0.pow(-10 * tickProgress), slowDownFactor)
        }
    }

    @Test
    fun testPercentFunction() {
        val percentage = 0.42

        val function = SlowDownFunctions.getOrNull("${percentage * 100}")
        assertNotNull(function, "Could not parse input '$percentage'")

        repeat(maxTick) { tick ->
            val tickProgress = tick.toDouble() / maxTick.toDouble()
            val slowDownFactor = function(tickProgress)
            assertEquals(slowDownFactor, percentage)
        }
    }

    @Test
    fun testPercentSymbolFunction() {
        val percentage = 0.42

        val function = SlowDownFunctions.getOrNull("${percentage * 100}%")
        assertNotNull(function, "Could not parse input '$percentage'")

        repeat(maxTick) { tick ->
            val tickProgress = tick.toDouble() / maxTick.toDouble()
            val slowDownFactor = function(tickProgress)
            assertEquals(slowDownFactor, percentage)
        }
    }

}