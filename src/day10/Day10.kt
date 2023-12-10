package day10

import util.StringUtil
import java.io.File

data class Coord(val x: Int, val y: Int) {
    fun left(): Coord {
        return Coord(x -1 , y)
    }
    fun up(): Coord {
        return Coord(x, y - 1)
    }
    fun right(): Coord {
        return Coord(x + 1, y)
    }
    fun down(): Coord {
        return Coord(x, y + 1)
    }
}

data class Position(val coord: Coord, val flow: String)

data class Graph(val data: MutableList<MutableList<String>>) {
    fun get(coord: Coord): String {
        return data.getOrNull(coord.y)?.getOrNull(coord.x) ?: "."
    }

    fun update(coord: Coord, value: String) {
        data[coord.y][coord.x] = value
    }

    fun getStart(): Coord {
        data.forEachIndexed { indexY, lineY ->
            lineY.forEachIndexed { indexX, charX ->
                if (charX == "S") {
                    return Coord(indexX, indexY)
                }
            }
        }

        throw Exception("Unable to find start.")
    }

    fun getStartingPositions(start: Coord): List<Position> {
        val startingLoops = mutableListOf<Position>()

        val left = this.get(start.left())
        if (left == "-" || left == "L" || left == "F") {
            startingLoops.add(Position(start.left(), "L"))
        }

        val up = this.get(start.up())
        if (up == "|" || up == "7" || up == "F") {
            startingLoops.add(Position(start.up(), "U"))
        }

        val right = this.get(start.right())
        if (right == "-" || right == "J" || right == "7") {
            startingLoops.add(Position(start.right(), "R"))
        }

        val down = this.get(start.down())
        if (down == "|" || down == "J" || down == "L") {
            startingLoops.add(Position(start.down(), "D"))
        }

        return startingLoops
    }

    fun next(position: Position): Position {
        val char = this.get(position.coord)

        if (char == "|" && position.flow == "U") {
            return Position(position.coord.up(), "U")
        } else if (char == "|" && position.flow == "D") {
            return Position(position.coord.down(), "D")
        } else if (char == "-" && position.flow == "L") {
            return Position(position.coord.left(), "L")
        } else if (char == "-" && position.flow == "R") {
            return Position(position.coord.right(), "R")
        } else if (char == "L" && position.flow == "L") {
            return Position(position.coord.up(), "U")
        } else if (char == "L" && position.flow == "D") {
            return Position(position.coord.right(), "R")
        } else if (char == "J" && position.flow == "R") {
            return Position(position.coord.up(), "U")
        } else if (char == "J" && position.flow == "D") {
            return Position(position.coord.left(), "L")
        } else if (char == "7" && position.flow == "U") {
            return Position(position.coord.left(), "L")
        } else if (char == "7" && position.flow == "R") {
            return Position(position.coord.down(), "D")
        } else if (char == "F" && position.flow == "L") {
            return Position(position.coord.down(), "D")
        } else if (char == "F" && position.flow == "U") {
            return Position(position.coord.right(), "R")
        } else {
            throw Exception("Unable to get next. ${position}")
        }
    }
}

class PartA {
    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example1.txt").readLines()
//        val lines =  File("./src/${ this.javaClass.packageName }/example2.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val graph = Graph(StringUtil.parseGraph(lines))

        StringUtil.printGraph("graph", graph.data)

        val start = graph.getStart()

        println("start: ${ start }")

        var currentPositions = graph.getStartingPositions(start)

        var looking = true
        var count = 1
        while (looking) {
//            println("current: ${ currentPositions }")
            currentPositions = currentPositions.map {
                graph.next(it)
            }

            if (currentPositions[0].coord == currentPositions[1].coord) {
                looking = false
            }

            count++
        }
        println("count: ${ count }")

    }
}

class PartB {

    private fun doubleGraph(graph1: Graph): Graph {
        val data = mutableListOf(mutableListOf<String>())

        val start = graph1.getStart()
        val currentPositions = graph1.getStartingPositions(start)

        graph1.data.forEachIndexed { indexY, lineY ->
            data.add(mutableListOf())
            data.add(mutableListOf())
            lineY.forEachIndexed { indexX, char ->
                if (char == "|") {
                    data[(indexY * 2)].add("|")
                    data[(indexY * 2)].add(".")
                    data[(indexY * 2) + 1].add("|")
                    data[(indexY * 2) + 1].add(".")
                } else if (char == "-") {
                    data[(indexY * 2)].add("-")
                    data[(indexY * 2)].add("-")
                    data[(indexY * 2) + 1].add(".")
                    data[(indexY * 2) + 1].add(".")
                } else if (char == "L") {
                    data[(indexY * 2)].add("L")
                    data[(indexY * 2)].add("-")
                    data[(indexY * 2) + 1].add(".")
                    data[(indexY * 2) + 1].add(".")
                } else if (char == "J") {
                    data[(indexY * 2)].add("J")
                    data[(indexY * 2)].add(".")
                    data[(indexY * 2) + 1].add(".")
                    data[(indexY * 2) + 1].add(".")
                } else if (char == "7") {
                    data[(indexY * 2)].add("7")
                    data[(indexY * 2)].add(".")
                    data[(indexY * 2) + 1].add("|")
                    data[(indexY * 2) + 1].add(".")
                } else if (char == "F") {
                    data[(indexY * 2)].add("F")
                    data[(indexY * 2)].add("-")
                    data[(indexY * 2) + 1].add("|")
                    data[(indexY * 2) + 1].add(".")
                } else if (char == "S") {
                    data[(indexY * 2)].add("S")

                    if (currentPositions[0].flow == "R" || currentPositions[1].flow == "R") {
                        data[(indexY * 2)].add("-")
                    } else {
                        data[(indexY * 2)].add(".")
                    }

                    if (currentPositions[0].flow == "D" || currentPositions[1].flow == "D") {
                        data[(indexY * 2) + 1].add("|")
                    } else {
                        data[(indexY * 2) + 1].add(".")
                    }

                    data[(indexY * 2) + 1].add(".")
                } else {
                    data[(indexY * 2)].add(".")
                    data[(indexY * 2)].add(".")
                    data[(indexY * 2) + 1].add(".")
                    data[(indexY * 2) + 1].add(".")
                }
            }
        }

        data.removeLast() // dont know where this extra line came from

        data.add(0, MutableList(data[0].size) { "." })
        data.add(MutableList(data[0].size) { "." })
        data.forEach {
            it.add(0, ".")
            it.add(".")
        }

        return Graph(data)
    }

    private fun outsideLoop(graph: Graph, coord: Coord, stack: MutableSet<Coord>) {
        if (coord.y < 0
            || coord.y >= graph.data.size
            || coord.x < 0
            || coord.x >= graph.data[0].size
            ) {
            return // outside of graph
        }

        val current = graph.get(coord)
        if (current == "*") {
            return // this is the loop
        } else if (current == "O") {
            return // this is already flagged
        }

        graph.update(coord, "O")
        stack.add(coord.left())
        stack.add(coord.up())
        stack.add(coord.right())
        stack.add(coord.down())
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example1.txt").readLines()
//        val lines =  File("./src/${ this.javaClass.packageName }/example2.txt").readLines()
//        val lines =  File("./src/${ this.javaClass.packageName }/example3.txt").readLines()
//        val lines =  File("./src/${ this.javaClass.packageName }/example4.txt").readLines()
//        val lines =  File("./src/${ this.javaClass.packageName }/example5.txt").readLines()
//        val lines =  File("./src/${ this.javaClass.packageName }/example6.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val graph = Graph(StringUtil.parseGraph(lines))
        StringUtil.printGraph("graph", graph.data)

        val graph2 = doubleGraph(graph)

        println("\n\n")
        StringUtil.printGraph("graph2", graph2.data)

        val start = graph2.getStart()
        println("start: ${ start }")
        graph2.update(start, "*")
        var currentPositions = graph2.getStartingPositions(start)

        var looking = true
        while (looking) {
            currentPositions = currentPositions.map {
                val newCoord = graph2.next(it)
                graph2.update(it.coord, "*")
                newCoord
            }

            if (currentPositions[0].coord == currentPositions[1].coord) {
                looking = false
            }
        }
        graph2.update(currentPositions[0].coord, "*")
        val stack = mutableSetOf(Coord(0, 0))
        while (stack.isNotEmpty()) {
            val currentCoord = stack.first()
            stack.remove(currentCoord)
            outsideLoop(graph2, currentCoord, stack)
        }

        println("\n\n")
        StringUtil.printGraph("graph2", graph2.data)

        var count = 0
        val width = graph2.data[0].size
        for (indexY in (1..(graph2.data.size - 2)) step 2) {
            for (indexX in (1..(width - 2)) step 2) {
                val value = graph2.get(Coord(indexX, indexY))
                if (value != "*" && value != "O") {
                    count++
                }
            }
        }
        println("count: ${ count }")
    }
}

fun main() {
//    PartA().run()
    PartB().run()
}