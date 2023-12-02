package day02

import util.RegexUtil
import java.io.File

val example = """
    Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
    Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
    Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
    Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
"""

data class Cubes(var redCount: Int, var blueCount: Int, var greenCount: Int)
data class Game(val id: Int, val sessionList: List<Cubes> )

private val gameIndexRegex = Regex("^Game (\\d*):")
private val redCountRegex = Regex("(\\d*) red")
private val blueCountRegex = Regex("(\\d*) blue")
private val greenCountRegex = Regex("(\\d*) green")

class PartA {

    private fun parseLine(line: String): Game {
        val gameIndex = RegexUtil.parseInt(gameIndexRegex, line)

        val sessionStringList = line.split(":")[1].split(";")
        val sessionList = sessionStringList.map {
            val redCount = RegexUtil.parseInt(redCountRegex, it, 0)
            val blueCount = RegexUtil.parseInt(blueCountRegex, it, 0)
            val greenCount = RegexUtil.parseInt(greenCountRegex, it, 0)

            Cubes(redCount, blueCount, greenCount)
        }

        return Game(gameIndex, sessionList)
    }

    private fun validGame(game: Game): Boolean {
        game.sessionList.forEach {
            if (it.redCount > 12) {
                return false
            }
            if (it.blueCount > 14) {
                return false
            }
            if (it.greenCount > 13) {
                return false
            }
        }

        return true;
    }

    fun run() {
//        val lines = example.trimIndent().split('\n')
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val games = lines.map { parseLine(it) }
        val validGames = games.filter { validGame(it) }

        val total = validGames.sumOf { it.id }

        println("total: ${ total }")
    }
}

class PartB {

    private fun parseLine(line: String): Game {
        val gameIndex = RegexUtil.parseInt(gameIndexRegex, line)

        val sessionStringList = line.split(":")[1].split(";")
        val sessionList = sessionStringList.map {
            val redCount = Integer.valueOf(redCountRegex.find(it)?.groups?.get(1)?.value ?: "0")
            val blueCount = Integer.valueOf(blueCountRegex.find(it)?.groups?.get(1)?.value ?: "0")
            val greenCount = Integer.valueOf(greenCountRegex.find(it)?.groups?.get(1)?.value ?: "0")

            Cubes(redCount, blueCount, greenCount)
        }

        return Game(gameIndex, sessionList)
    }

    private fun minCubes(game: Game): Cubes {
        val current = Cubes(0, 0, 0)

        game.sessionList.forEach {
            if (it.redCount > current.redCount) {
                current.redCount = it.redCount
            }
            if (it.blueCount > current.blueCount) {
                current.blueCount = it.blueCount
            }
            if (it.greenCount > current.greenCount) {
                current.greenCount = it.greenCount
            }
        }

        return current;
    }

    fun run() {
//        val lines = example.trimIndent().split('\n')
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val games = lines.map { parseLine(it) }
        val minCubes = games.map {
            minCubes(it)
        }

        val total = minCubes.sumOf {
            it.redCount * it.blueCount * it.greenCount
        }

        println("total: ${ total }")
    }}

fun main() {
//    PartA().run()
    PartB().run()
}