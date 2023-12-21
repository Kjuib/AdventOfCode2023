package day21

import java.io.File
import kotlin.math.abs
import kotlin.text.StringBuilder

enum class Direction(val xMod: Int, val yMod: Int) {
    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0)
}

data class Coord(val x: Int, val y: Int) {
    fun move(direction: Direction): Coord {
        return Coord(x + direction.xMod, y + direction.yMod)
    }
}

class PartA {

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val map: MutableMap<Coord, Char> = mutableMapOf()
        var current: Set<Coord> = setOf()
        lines.forEachIndexed { indexY, line ->
            line.forEachIndexed { indexX, char ->
                val coord = Coord(indexX, indexY)
                if (char == 'S') {
                    map[coord] = '.'
                    current = setOf(coord)
                } else {
                    map[coord] = char
                }
            }
        }

        for (i in (1..64)) {
            current = current.flatMap { currentCoord ->
                Direction.entries.mapNotNull { direction ->
                    val nextCoord = currentCoord.move(direction)
                    if (map[nextCoord] == '.') {
                        nextCoord
                    } else {
                        null
                    }
                }
            }.toSet()
//            println("current: ${ current }")
        }

        println("Count: ${current.size}")
    }
}

class PartB {
    private var maxX: Int = 0
    private var maxY: Int = 0

    private fun getGarden(coord: Coord, map: Map<Coord, Char>): Char {
        return map[Coord(Math.floorMod(coord.x, maxX), Math.floorMod(coord.y, maxY))] ?: '?'
    }
    private fun isGarden(coord: Coord, map: Map<Coord, Char>): Boolean {
        return getGarden(coord, map) == '.'
    }

    private fun print(current: Set<Coord>, map: Map<Coord, Char>) {
        val minX = current.minOf { it.x }
        val maxX = current.maxOf { it.x }
        val minY = current.minOf { it.y }
        val maxY = current.maxOf { it.y }

        for (y in minY..maxY) {
            val sb: StringBuilder = StringBuilder()
            for (x in minX..maxX) {
                val coord = Coord(x, y)
                if (current.contains(coord)) {
                    sb.append('O')
                } else {
                    sb.append(getGarden(coord, map))
                }
                sb.append(" ")
            }
            println(sb)
        }
        println("\n\n")
    }

    fun run() {
        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
//        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        maxX = lines[0].length
        maxY = lines.size
        val map: MutableMap<Coord, Char> = mutableMapOf()
        var current: Set<Coord> = setOf()
        val history: MutableSet<Coord> = mutableSetOf()
        var countA: Long = 0L
        var countB: Long = 0L
        lines.forEachIndexed { indexY, line ->
            line.forEachIndexed { indexX, char ->
                val coord = Coord(indexX, indexY)
                if (char == 'S') {
                    map[coord] = '.'
                    current = setOf(coord)
                } else {
                    map[coord] = char
                }
            }
        }

        for (i in (1..2000)) {
            current = current.flatMap { currentCoord ->
                Direction.entries.mapNotNull { direction ->
                    val nextCoord = currentCoord.move(direction)
                    if (history.contains(nextCoord)) {
                        // ignore
                        null
                    } else if (isGarden(nextCoord, map)) {
                        nextCoord
                    } else {
                        null
                    }
                }
            }.toSet()
            history.addAll(current)
            if (i % 2 == 0) {
                countA += current.size
            } else {
                countB += current.size
            }
//            println("current: ${ current }")
        }
//        print(current, map)

        // 1000 = 668697
        // 2000 = 2677337

        if (countA > countB) {
            println("countA: ${ countA }")
        } else {
            println("countB: ${ countB }")
        }
    }
}

fun main() {
//    PartA().run()
    PartB().run()
}