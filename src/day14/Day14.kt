package day14

import util.StringUtil
import java.io.File

class PartA {

    private fun getLoad(graph: MutableList<MutableList<String>>): Long {
        val max = graph.size
        var load = 0L
        for (y in graph.indices) {
            for (x in graph[0].indices) {
                val char = graph[y][x]
                if (char == "O") {
                    load += max - y
                }
            }
        }
        return load
    }

    private fun tilt(graph: MutableList<MutableList<String>>) {
        for (y in graph.indices) {
            for (x in graph[0].indices) {
                if (y > 0 && graph[y][x] == "O" && graph[y-1][x] == ".") {
                    graph[y][x] = "."
                    graph[y-1][x] = "O"
                }
            }
        }
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val graph = StringUtil.parseGraph(lines)
        var keepGoing = true
        var currentLoad = 0L
        while (keepGoing) {
            tilt(graph)

            val load = getLoad(graph)
            if (load == currentLoad) {
                keepGoing = false
            } else {
                currentLoad = load
            }
        }
//        StringUtil.printGraph("Graph", graph)

        println("currentLoad: ${ currentLoad }")
    }
}

class PartB {

    private fun hash(graph: MutableList<MutableList<String>>): String {
        return graph.joinToString(":") { it.joinToString("") }
    }

    private fun clone(graph: MutableList<MutableList<String>>): MutableList<MutableList<String>> {
        return graph.map {
            it.toMutableList()
        }.toMutableList()
    }

    private fun isEqual(graph1: MutableList<MutableList<String>>, graph2: MutableList<MutableList<String>>): Boolean {
        graph1.forEachIndexed { index, line ->
            if (line != graph2[index]) {
                return false
            }
        }

        return true
    }

    private fun getLoad(graph: MutableList<MutableList<String>>): Long {
        val max = graph.size
        var load = 0L
        for (y in graph.indices) {
            for (x in graph[0].indices) {
                val char = graph[y][x]
                if (char == "O") {
                    load += max - y
                }
            }
        }
        return load
    }

    private fun tiltNorth(graph: MutableList<MutableList<String>>) {
        var oldGraph = clone(graph)
        var keepGoing = true
        while (keepGoing) {
            for (y in 1..<graph.size) {
                for (x in 0..<graph[0].size) {
                    if (y > 0 && graph[y][x] == "O" && graph[y-1][x] == ".") {
                        graph[y][x] = "."
                        graph[y-1][x] = "O"
                    }
                }
            }
            if (isEqual(oldGraph, graph)) {
                keepGoing = false
            } else {
                oldGraph = clone(graph)
            }
        }
    }

    private fun tiltWest(graph: MutableList<MutableList<String>>) {
        var oldGraph = clone(graph)
        var keepGoing = true
        while (keepGoing) {
            for (y in 0..<graph.size) {
                for (x in 1..<graph[0].size) {
                    if (graph[y][x] == "O" && graph[y][x-1] == ".") {
                        graph[y][x] = "."
                        graph[y][x-1] = "O"
                    }
                }
            }
            if (isEqual(oldGraph, graph)) {
                keepGoing = false
            } else {
                oldGraph = clone(graph)
            }
        }
    }

    private fun tiltSouth(graph: MutableList<MutableList<String>>) {
        var oldGraph = clone(graph)
        var keepGoing = true
        while (keepGoing) {
            for (y in 0..<(graph.size-1)) {
                for (x in 0..<graph[0].size) {
                    if (graph[y][x] == "O" && graph[y+1][x] == ".") {
                        graph[y][x] = "."
                        graph[y+1][x] = "O"
                    }
                }
            }
            if (isEqual(oldGraph, graph)) {
                keepGoing = false
            } else {
                oldGraph = clone(graph)
            }
        }
    }

    private fun tiltEast(graph: MutableList<MutableList<String>>) {
        var oldGraph = clone(graph)
        var keepGoing = true
        while (keepGoing) {
            for (y in 0..<graph.size) {
                for (x in 0..<(graph[0].size-1)) {
                    if (graph[y][x] == "O" && graph[y][x+1] == ".") {
                        graph[y][x] = "."
                        graph[y][x+1] = "O"
                    }
                }
            }
            if (isEqual(oldGraph, graph)) {
                keepGoing = false
            } else {
                oldGraph = clone(graph)
            }
        }
    }


    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val maxLoop = 1_000_000_000
        val graph = StringUtil.parseGraph(lines)
        val oldGraphs: MutableSet<String> = mutableSetOf()
        var repeatCount = 0
        var repeatIndex = 0
        for (i in 0..maxLoop) {
            if (i % 100 == 0) {
                println("CALCULATING: ${i}")
            }
            tiltNorth(graph)
            tiltWest(graph)
            tiltSouth(graph)
            tiltEast(graph)

            val graphHash = hash(graph)
            if (oldGraphs.contains(graphHash)) {
                repeatCount = i
                repeatIndex = oldGraphs.indexOf(graphHash)
                println("GRAPH IS REPEATING: ${repeatCount} - ${repeatIndex}")
                break
            } else {
                oldGraphs.add(graphHash)
            }
        }

        val repeatCycle = repeatCount - repeatIndex
        val repeatTimes = (maxLoop - repeatIndex) % repeatCycle

        for (i in 1..<repeatTimes) {
            tiltNorth(graph)
            tiltWest(graph)
            tiltSouth(graph)
            tiltEast(graph)
        }

        println("currentLoad: ${ getLoad(graph) }")
    }
}

fun main() {
//    PartA().run()
    PartB().run()
}