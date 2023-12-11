package day11

import java.io.File


class PartA {
    data class Coord(var x: Int, var y: Int)

    private fun expandX(galaxies: List<Coord>) {
        var width = galaxies.maxBy { it.x }.x
        var currentX = 0
        while (currentX < width) {
            if (!galaxies.any { it.x == currentX }) {
                galaxies.forEach {
                    if (it.x > currentX) {
                        it.x++
                    }
                }
                currentX++
                width++
            }
            currentX++
        }
    }

    private fun expandY(galaxies: List<Coord>) {
        var height = galaxies.maxBy { it.y }.y
        var currentY = 0
        while (currentY < height) {
            if (!galaxies.any { it.y == currentY }) {
                galaxies.forEach {
                    if (it.y > currentY) {
                        it.y++
                    }
                }
                currentY++
                height++
            }
            currentY++
        }
    }

    private fun generatePairs(galaxies: List<Coord>) : List<Pair<Coord, Coord>> {
        val results = mutableListOf<Pair<Coord, Coord>>()
        for (gal1 in galaxies.indices) {
            for (gal2 in (gal1 + 1)..<(galaxies.size)) {
                results.add(Pair(galaxies[gal1], galaxies[gal2]))
            }
        }

        return results
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val galaxies = mutableListOf<Coord>()
        lines.forEachIndexed { indexY, line ->
            line.forEachIndexed { indexX, char ->
                if (char == '#') {
                    galaxies.add(Coord(indexX, indexY))
                }
            }
        }

        expandX(galaxies)
        expandY(galaxies)

        println("galaxies: ${ galaxies }")

        val pairs = generatePairs(galaxies)

        val distances = pairs.map {
            kotlin.math.abs(it.first.x - it.second.x) + kotlin.math.abs(it.first.y - it.second.y)
        }

        val total = distances.sum()
        println("total: ${ total }")
    }
}

class PartB {
    data class Coord(var x: Long, var y: Long)

    private val expandSize: Int = (1_000_000 - 1)

    private fun expandX(galaxies: List<Coord>) {
        var width = galaxies.maxBy { it.x }.x
        var currentX = 0L
        while (currentX < width) {
            if (!galaxies.any { it.x == currentX }) {
                galaxies.forEach {
                    if (it.x > currentX) {
                        it.x += expandSize
                    }
                }
                currentX += expandSize
                width += expandSize
            }
            currentX++
        }
    }

    private fun expandY(galaxies: List<Coord>) {
        var height = galaxies.maxBy { it.y }.y
        var currentY = 0L
        while (currentY < height) {
            if (!galaxies.any { it.y == currentY }) {
                galaxies.forEach {
                    if (it.y > currentY) {
                        it.y += expandSize
                    }
                }
                currentY += expandSize
                height += expandSize
            }
            currentY++
        }
    }

    private fun generatePairs(galaxies: List<Coord>) : List<Pair<Coord, Coord>> {
        val results = mutableListOf<Pair<Coord, Coord>>()
        for (gal1 in galaxies.indices) {
            for (gal2 in (gal1 + 1)..<(galaxies.size)) {
                results.add(Pair(galaxies[gal1], galaxies[gal2]))
            }
        }

        return results
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val galaxies = mutableListOf<Coord>()
        lines.forEachIndexed { indexY, line ->
            line.forEachIndexed { indexX, char ->
                if (char == '#') {
                    galaxies.add(Coord(indexX.toLong(), indexY.toLong()))
                }
            }
        }

        expandX(galaxies)
        expandY(galaxies)

        println("galaxies: ${ galaxies }")

        val pairs = generatePairs(galaxies)

        val distances = pairs.map {
            kotlin.math.abs(it.first.x - it.second.x) + kotlin.math.abs(it.first.y - it.second.y)
        }

        val total = distances.sum()
        println("total: ${ total }")
    }
}

fun main() {
//    PartA().run()
    PartB().run()
}