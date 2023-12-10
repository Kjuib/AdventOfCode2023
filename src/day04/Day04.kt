package day04

import util.StringUtil
import java.io.File
import kotlin.math.pow

class PartA {
    data class Card(val index: Int, val winningNumbers: List<Int>, val currentNumbers: List<Int>)

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val cardRegex = Regex("Card\\s+(\\d+):([\\d\\s]+)\\|([\\d\\s]+)")

        val cards = lines.map { line ->
            val result = cardRegex.find(line) ?: throw Exception("Unable to parse: ${line}")
            val gameNumber = Integer.parseInt(result.groupValues[1])
            val winningNumbers = StringUtil.parseInt(result.groupValues[2])
            val currentNumbers = StringUtil.parseInt(result.groupValues[3])

            Card(gameNumber, winningNumbers, currentNumbers)
        }

        val scores = cards.map { card ->
            val winList = card.winningNumbers.intersect(card.currentNumbers)
            2.toDouble().pow(winList.size - 1).toLong()
        }

//        println("cards:\n${ cards.joinToString("\n") }")

        val total = scores.sum()

        println("total: ${ total }")
    }
}

class PartB {
    data class Card(val index: Int, val winningNumbers: List<Int>, val currentNumbers: List<Int>, var cardCount: Int)

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val cardRegex = Regex("Card\\s+(\\d+):([\\d\\s]+)\\|([\\d\\s]+)")

        val cards = lines.map { line ->
            val result = cardRegex.find(line) ?: throw Exception("Unable to parse: ${line}")
            val gameNumber = Integer.parseInt(result.groupValues[1])
            val winningNumbers = StringUtil.parseInt(result.groupValues[2])
            val currentNumbers = StringUtil.parseInt(result.groupValues[3])

            Card(gameNumber, winningNumbers, currentNumbers, 1)
        }

        cards.forEachIndexed { i, card ->
            val score = card.winningNumbers.intersect(card.currentNumbers).size

            for (i2 in (i + 1)..(i + score)) {
                cards[i2].cardCount += card.cardCount
            }
        }

//        println("cards:\n${ cards.joinToString("\n") }")

        val total = cards.sumOf { it.cardCount }

        println("total: ${ total }")
    }

}

fun main() {
//    PartA().run()
    PartB().run()
}