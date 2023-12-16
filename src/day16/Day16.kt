package day16

import java.io.File

class PartA {
    data class Flow(val direction: String, val x: Int, val y: Int) {
        fun next(direction: String): Flow {
            when (direction) {
                "north" -> return Flow("north", x, y - 1)
                "east" -> return Flow("east", x + 1, y)
                "south" -> return Flow("south", x, y + 1)
                "west" -> return Flow("west", x - 1, y)
                else -> throw Exception("Unknown direction: ${direction}")
            }
        }
    }

    data class Point(val type: String, val x: Int, val y: Int, val history: MutableList<String> = mutableListOf()) {
        fun pos(): String {
            return "${x},${y}"
        }
    }

    private fun handleFlow(flow: Flow, points: Map<String, Point>): List<Flow> {
        val point = points["${flow.x},${flow.y}"]

        if (point == null) {
            return listOf()
        } else if (point.history.contains(flow.direction)) {
            return listOf()
        } else {
            point.history.add(flow.direction)

            if (point.type == ".") {
                return listOf(flow.next(flow.direction))
            } else if (point.type == "\\") {
                when (flow.direction) {
                    "north" -> return listOf(flow.next("west"))
                    "east" -> return listOf(flow.next("south"))
                    "south" -> return listOf(flow.next("east"))
                    "west" -> return listOf(flow.next("north"))
                }
            } else if (point.type == "/") {
                when (flow.direction) {
                    "north" -> return listOf(flow.next("east"))
                    "east" -> return listOf(flow.next("north"))
                    "south" -> return listOf(flow.next("west"))
                    "west" -> return listOf(flow.next("south"))
                }
            } else if (point.type == "-") {
                when (flow.direction) {
                    "north" -> return listOf(flow.next("east"), flow.next("west"))
                    "east" -> return listOf(flow.next("east"))
                    "south" -> return listOf(flow.next("east"), flow.next("west"))
                    "west" -> return listOf(flow.next("west"))
                }
            } else if (point.type == "|") {
                when (flow.direction) {
                    "north" -> return listOf(flow.next("north"))
                    "east" -> return listOf(flow.next("north"), flow.next("south"))
                    "south" -> return listOf(flow.next("south"))
                    "west" -> return listOf(flow.next("north"), flow.next("south"))
                }
            }
        }

        throw Exception("Unknown point: ${flow}")
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val points: MutableMap<String, Point> = mutableMapOf()
        lines.forEachIndexed { indexY: Int, line: String ->
            line.forEachIndexed { indexX, char ->
                val point = Point(char.toString(), indexX, indexY)
                points[point.pos()] = point
            }
        }

        val stack: MutableList<Flow> = mutableListOf(Flow("east", 0, 0))
        while (stack.size > 0) {
            val flow = stack.removeFirst()
            stack.addAll(handleFlow(flow, points))
        }
//        println("points:\n${ points.values.joinToString("\n") }")

        val energizedPoints = points.values.filter { it.history.size > 0 }

        println("Energized: ${energizedPoints.size}")
    }
}

class PartB {
    data class Flow(val direction: String, val x: Int, val y: Int) {
        fun next(direction: String): Flow {
            when (direction) {
                "north" -> return Flow("north", x, y - 1)
                "east" -> return Flow("east", x + 1, y)
                "south" -> return Flow("south", x, y + 1)
                "west" -> return Flow("west", x - 1, y)
                else -> throw Exception("Unknown direction: ${direction}")
            }
        }
    }

    data class Point(val type: String, val x: Int, val y: Int, val history: MutableList<String> = mutableListOf()) {
        fun pos(): String {
            return "${x},${y}"
        }
    }

    private fun handleFlow(flow: Flow, points: Map<String, Point>): List<Flow> {
        val point = points["${flow.x},${flow.y}"]

        if (point == null) {
            return listOf()
        } else if (point.history.contains(flow.direction)) {
            return listOf()
        } else {
            point.history.add(flow.direction)

            if (point.type == ".") {
                return listOf(flow.next(flow.direction))
            } else if (point.type == "\\") {
                when (flow.direction) {
                    "north" -> return listOf(flow.next("west"))
                    "east" -> return listOf(flow.next("south"))
                    "south" -> return listOf(flow.next("east"))
                    "west" -> return listOf(flow.next("north"))
                }
            } else if (point.type == "/") {
                when (flow.direction) {
                    "north" -> return listOf(flow.next("east"))
                    "east" -> return listOf(flow.next("north"))
                    "south" -> return listOf(flow.next("west"))
                    "west" -> return listOf(flow.next("south"))
                }
            } else if (point.type == "-") {
                when (flow.direction) {
                    "north" -> return listOf(flow.next("east"), flow.next("west"))
                    "east" -> return listOf(flow.next("east"))
                    "south" -> return listOf(flow.next("east"), flow.next("west"))
                    "west" -> return listOf(flow.next("west"))
                }
            } else if (point.type == "|") {
                when (flow.direction) {
                    "north" -> return listOf(flow.next("north"))
                    "east" -> return listOf(flow.next("north"), flow.next("south"))
                    "south" -> return listOf(flow.next("south"))
                    "west" -> return listOf(flow.next("north"), flow.next("south"))
                }
            }
        }

        throw Exception("Unknown point: ${flow}")
    }

    private fun findEnergized(startingFlow: Flow, points: Map<String, Point>): Int {
        val stack: MutableList<Flow> = mutableListOf(startingFlow)
        val privatePoints = points.map {
            it.key to Point(it.value.type, it.value.x, it.value.y)
        }.toMap()
        while (stack.size > 0) {
            val flow = stack.removeFirst()
            stack.addAll(handleFlow(flow, privatePoints))
        }

        val energizedPoints = privatePoints.values.filter { it.history.size > 0 }
        return energizedPoints.size
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val points: MutableMap<String, Point> = mutableMapOf()
        lines.forEachIndexed { indexY: Int, line: String ->
            line.forEachIndexed { indexX, char ->
                val point = Point(char.toString(), indexX, indexY)
                points[point.pos()] = point
            }
        }

        val lastX = lines[0].length - 1
        val lastY = lines.size - 1
        val startingFlows: MutableList<Flow> = mutableListOf()
        startingFlows.add(Flow("east", 0, 0))
        startingFlows.add(Flow("south", 0, 0))
        startingFlows.add(Flow("west", lastX, 0))
        startingFlows.add(Flow("south", lastX, 0))
        startingFlows.add(Flow("west", lastX, lastY))
        startingFlows.add(Flow("north", lastX, lastY))
        startingFlows.add(Flow("east", 0, lastY))
        startingFlows.add(Flow("north", 0, lastY))
        for (x in 1..<lastX) {
            startingFlows.add(Flow("south", x, 0))
            startingFlows.add(Flow("north", x, lastY))
        }
        for (y in 1..<lastX) {
            startingFlows.add(Flow("east", 0, y))
            startingFlows.add(Flow("west", lastX, y))
        }

        val results = startingFlows.map {
            val count = findEnergized(it, points)
//            println("StartingFlow: ${it}\t${count}")
            count
        }

        println("Max: ${results.max()}")
    }
}

fun main() {
//    PartA().run()
    PartB().run()
}