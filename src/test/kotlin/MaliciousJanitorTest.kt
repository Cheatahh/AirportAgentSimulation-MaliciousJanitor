import cheatahh.se.agent.MaliciousJanitor
import cheatahh.se.agent.SlowDownTile
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.geometry.Point
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.simulation.entity.MovingEntity
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.simulation.SimulationWorld
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.random.Random
import kotlin.test.Test

private const val worldSize = 1024
private const val speed = 1.0
private const val placingChance = "1%"
private const val effectTime = 10000L
private const val slowDownFunction = "10%"
private const val excludedClassTypes = ""

private const val maxTick = 100000u

class MaliciousJanitorTest {

    private val localWorld = SimulationWorld(null, null, worldSize, worldSize)

    /**
     * Tests the walking pattern of a MaliciousJanitor
     * */
    @Test
    fun testWalking() {
        val janitor = MaliciousJanitor(speed, placingChance, 1, effectTime, effectTime, slowDownFunction, excludedClassTypes)
        janitor.spawn(localWorld, 0, 0, 1, 1)

        var previousPosition = Point(0, 0)
        val image = BufferedImage(worldSize, worldSize, BufferedImage.TYPE_INT_RGB)

        image.createGraphics().also {
            it.color = Color.RED
            for(tick in 0u ..maxTick) {
                localWorld.update()
                it.drawLine(previousPosition.x, previousPosition.y, janitor.position.x, janitor.position.y)
                previousPosition = janitor.position
            }
        }

        ImageIO.write(image, "png", File(File("${File("").absolutePath}/out/tests").also { it.mkdirs() }, "path.png"))
    }

    /**
     * Tests the SlowDownTile placing of a MaliciousJanitor
     * */
    @Test
    fun testPlacing() {
        val janitor = MaliciousJanitor(speed, placingChance, 1, effectTime, effectTime, slowDownFunction, excludedClassTypes)
        janitor.spawn(localWorld, 0, 0, 1, 1)

        val image = BufferedImage(worldSize, worldSize, BufferedImage.TYPE_INT_RGB)

        image.createGraphics().also { graphics ->
            graphics.color = Color.RED
            for(tick in 0u ..maxTick) {
                localWorld.update()
                localWorld.entities.forEach {
                    if(it is SlowDownTile) {
                        graphics.fillRect(it.position.x, it.position.y, 1, 1)
                    }
                }
            }
        }

        ImageIO.write(image, "png", File(File("${File("").absolutePath}/out/tests").also { it.mkdirs() }, "placing.png"))
    }

    /**
     * Tests the interaction between a MaliciousJanitor and a Civilian
     * */
    @Test
    fun testWithCivilian() {
        val janitor = MaliciousJanitor(speed, placingChance, effectTime, effectTime, effectTime, slowDownFunction, excludedClassTypes)
        janitor.spawn(localWorld, 0, 0, 1, 1)
        val civilian = TestCivilian(speed)
        civilian.spawn(localWorld, 1, 0, 1, 1)

        var distanceTraveledJanitor = 0.0
        var previousJanitorPosition = Point(0, 0)
        var distanceTraveledCivilian = 0.0
        var previousCivilianPosition = Point(0, 0)
        val image = BufferedImage(worldSize, worldSize, BufferedImage.TYPE_INT_RGB)

        val tiles = hashSetOf<SlowDownTile>()

        image.createGraphics().also {
            for(tick in 0u ..100000u) {
                localWorld.update()

                localWorld.entities.forEach { entity ->
                    if(entity is SlowDownTile) tiles += entity
                }

                it.color = Color.RED
                it.drawLine(previousJanitorPosition.x, previousJanitorPosition.y, janitor.position.x, janitor.position.y)

                it.color = if(civilian.speedAmplifier != 1.0) Color.GREEN else Color.BLUE
                it.drawLine(previousCivilianPosition.x, previousCivilianPosition.y, civilian.position.x, civilian.position.y)

                distanceTraveledJanitor += previousJanitorPosition.getDistance(janitor.position)
                previousJanitorPosition = janitor.position

                distanceTraveledCivilian += previousCivilianPosition.getDistance(civilian.position)
                previousCivilianPosition = civilian.position
            }

            it.color = Color(0, 0, 0, 100)
            it.fillRect(0, 0, worldSize, 70)

            it.color = Color.WHITE
            it.drawString("Distance traveled (Janitor): ${distanceTraveledJanitor.toLong()}", 0, 10)
            it.drawString("Distance traveled (Civilian): ${distanceTraveledCivilian.toLong()}", 0, 22)
            it.drawString("Tiles placed: ${tiles.size} (Chance: ${((tiles.size / maxTick.toFloat()) * 100)}%)", 0, 34)
            it.drawString("Janitor: Red", 0, 46)
            it.drawString("Civilian: Blue", 0, 58)
            it.drawString("Civilian slowed down: Green", 0, 70)
        }

        ImageIO.write(image, "png", File(File("${File("").absolutePath}/out/tests").also { it.mkdirs() }, "pathWithCivilian.png"))
    }

}

private class TestCivilian(initialSpeed: Double) : MovingEntity() {

    init {
        speed = initialSpeed
    }

    private var lastPosition = Point(-1, -1)

    override fun pluginUpdate() {

        // Copied walking pattern from MaliciousJanitor
        if(lastPosition == position) {
            do {
                try {
                    turn(Point(Random.nextInt(0, world.width), Random.nextInt(0, world.height)))
                    break
                } catch (_: Exception) {}
            } while(true)
        }
        lastPosition = position

    }

}