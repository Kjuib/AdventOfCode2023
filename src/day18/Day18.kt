package day18

import util.StringUtil
import java.io.File
import kotlin.math.abs

enum class Direction(val xMod: Int, val yMod: Int) {
    U(0, -1),
    R(1, 0),
    D(0, 1),
    L(-1, 0)
}

data class Command(val direction: Direction, val distance: Int, val color: String)

data class Coord(val x: Int, val y: Int) {
    fun move(direction: Direction): Coord {
        return Coord(x + direction.xMod, y + direction.yMod)
    }
    fun moveOpposite(direction: Direction): Coord {
        return Coord(x - direction.xMod, y - direction.yMod)
    }
    fun move(direction: Direction, distance: Int): Coord {
        return Coord(x + (direction.xMod * distance), y + (direction.yMod * distance))
    }
}

class PartA {

    private fun printMap(map: Map<Coord, String>) {
        val startX = map.keys.minOf { it.x }
        val endX = map.keys.maxOf { it.x }
        val startY = map.keys.minOf { it.y }
        val endY = map.keys.maxOf { it.y }

        for (y in startY..endY) {
            val line = StringBuilder()
            for (x in startX..endX) {
                val coord = Coord(x, y)
                val value = map[coord] ?: "."
                line.append(value).append(" ")
            }
            println(line)
        }
    }

    private fun isInside(coord: Coord, map: Map<Coord, String>): Boolean {
        val positionLine = map.filter { it.key.y == coord.y }
        val totalSegments = positionLine.size
        val leftSegments = positionLine.filter { it.key.x < coord.x }.size

        return totalSegments > 0 && leftSegments % 2 == 1
    }

    private fun fillMap(start: Coord, map: MutableMap<Coord, String>) {
        val stack: MutableList<Coord> = mutableListOf(start)
        while (stack.isNotEmpty()) {
            val coord = stack.removeFirst()
            if (!map.containsKey(coord)) {
                map[coord] = "#"
                stack.add(coord.move(Direction.U))
                stack.add(coord.move(Direction.R))
                stack.add(coord.move(Direction.D))
                stack.add(coord.move(Direction.L))
            }
        }
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val commands = lines.map {
            val splits = it.split(" ")
            Command(Direction.valueOf(splits[0]), splits[1].toInt(), splits[2].substring(1, splits[2].length - 1))
        }

        val map: MutableMap<Coord, String> = mutableMapOf()
        var currentCoord = Coord(0, 0)
        map[currentCoord] = "#"

        commands.forEach {
            for (i in 0..<it.distance) {
                currentCoord = currentCoord.move(it.direction)
                map[currentCoord] = "#"
            }
        }

        var fillStart = Coord(0, 0)
            .move(commands.first().direction)
            .moveOpposite(commands.last().direction)
        if (!isInside(fillStart, map)) {
            fillStart = Coord(fillStart.x * -1, fillStart.y * -1)
        }

        fillMap(fillStart, map)

//        printMap(map)

        println("SIZE: ${map.size}")

    }
}

class PartB {

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val commands = lines.map {
            val splits = it.split(" ")
            Command(Direction.valueOf(splits[0]), splits[1].toInt(), splits[2].substring(1, splits[2].length - 1))
        }.map { badCommand ->
            val distanceHex = badCommand.color.substring(1, 6)
            val direction = when(badCommand.color.substring(6, 7)) {
                "0" -> Direction.R
                "1" -> Direction.D
                "2" -> Direction.L
                "3" -> Direction.U
                else -> throw Exception("Unknown Direction: ${badCommand.color}\t\t${distanceHex}")
            }
            Command(direction, distanceHex.toInt(16), badCommand.color)
        }

        var currentCoord = Coord(0, 0)
        var sumA = 0L
        var sumB = 0L
        var sumPath = 0L

        commands.forEach { command ->
            val nextCoord = currentCoord.move(command.direction, command.distance)
            sumA += currentCoord.x.toLong() * nextCoord.y.toLong()
            sumB += currentCoord.y.toLong() * nextCoord.x.toLong()
            sumPath += command.distance
            currentCoord = nextCoord
        }

        val area = ((abs(sumA - sumB) + sumPath) / 2) + 1

        println("area: ${ area }")
    }
}

fun main() {
//    PartA().run()
    PartB().run()
}