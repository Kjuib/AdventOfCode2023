package day17

import util.StringUtil
import java.io.File
import java.util.PriorityQueue

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

data class Flow(val coord: Coord, val direction: Direction?, val lineCount: Int)

data class FlowScore(val flow: Flow, val score: Int): Comparable<FlowScore> {
    override fun compareTo(other: FlowScore): Int {
        return score.compareTo(other.score)
    }
}

data class Graph(val data: List<List<Int>>) {
    fun get(coord: Coord): Int {
        return data.getOrNull(coord.y)?.getOrNull(coord.x) ?: -1
    }
}

class PartA {

    private fun getOptions(current: Flow): List<Flow> {
        return buildList {
            if (current.lineCount < 3 && current.direction != null) {
                add(Flow(current.coord.move(current.direction), current.direction, current.lineCount + 1))
            }
            if (current.direction == null || current.direction in listOf(Direction.NORTH, Direction.SOUTH)) {
                add(Flow(current.coord.move(Direction.EAST), Direction.EAST, 1))
                add(Flow(current.coord.move(Direction.WEST), Direction.WEST, 1))
            }
            if (current.direction == null || current.direction in listOf(Direction.EAST, Direction.WEST)) {
                add(Flow(current.coord.move(Direction.NORTH), Direction.NORTH, 1))
                add(Flow(current.coord.move(Direction.SOUTH), Direction.SOUTH, 1))
            }
        }
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val graph = Graph(StringUtil.parseGraphInt(lines))

        val start = Flow(Coord(0, 0), Direction.WEST, 0)
        val target = Coord(lines[0].lastIndex, lines.lastIndex)

        val cache: MutableMap<Flow, Int> = mutableMapOf(start to 0)
        val stack = PriorityQueue(listOf(FlowScore(start, 0)))
        var found: Flow? = null

        while (found == null) {
            val current = stack.remove()
            if (current.flow.coord == target) {
                found = current.flow
            }

            val options = getOptions(current.flow)
                .filter { it !in cache }
                .filter { graph.get(it.coord) != -1}
                .map {
                    FlowScore(it, current.score + graph.get(it.coord))
                }

            stack.addAll(options)
            cache.putAll(options.associate { it.flow to it.score })
        }

        println("found: ${ found }")
        println("SCORE: ${cache[found]}")
    }
}

class PartB {

    private fun getOptions(current: Flow): List<Flow> {
        return buildList {
            if (current.lineCount < 10 && current.direction != null) {
                add(Flow(current.coord.move(current.direction), current.direction, current.lineCount + 1))
            }
            if (current.lineCount >= 4 || current.lineCount == 0) {
                if (current.direction == null || current.direction in listOf(Direction.NORTH, Direction.SOUTH)) {
                    add(Flow(current.coord.move(Direction.EAST), Direction.EAST, 1))
                    add(Flow(current.coord.move(Direction.WEST), Direction.WEST, 1))
                }
                if (current.direction == null || current.direction in listOf(Direction.EAST, Direction.WEST)) {
                    add(Flow(current.coord.move(Direction.NORTH), Direction.NORTH, 1))
                    add(Flow(current.coord.move(Direction.SOUTH), Direction.SOUTH, 1))
                }
            }
        }
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val graph = Graph(StringUtil.parseGraphInt(lines))

        val start = Flow(Coord(0, 0), null, 0)
        val target = Coord(lines[0].lastIndex, lines.lastIndex)

        val cache: MutableMap<Flow, Int> = mutableMapOf(start to 0)
        val stack = PriorityQueue(listOf(FlowScore(start, 0)))
        var found: Flow? = null

        while (found == null) {
            val current = stack.remove()
            if (current.flow.coord == target && current.flow.lineCount >= 4) {
                found = current.flow
            }

            val options = getOptions(current.flow)
                .filter { graph.get(it.coord) != -1}
                .filter { it !in cache }
                .map {
                    FlowScore(it, current.score + graph.get(it.coord))
                }

            stack.addAll(options)
            cache.putAll(options.associate { it.flow to it.score })
        }

        println("found: ${ found }")
        println("SCORE: ${cache[found]}")
    }
}

fun main() {
//    PartA().run()
    PartB().run()
}