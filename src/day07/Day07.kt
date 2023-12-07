package day07

import util.RegexUtil
import java.io.File
import kotlin.math.pow


data class Hand(val cards: List<String>, val bid: Int, val score: Long)

class PartA {
    private val cardValues: Map<String, Int> = mapOf(
        "A" to 14,
        "K" to 13,
        "Q" to 12,
        "J" to 11,
        "T" to 10,
        "9" to 9,
        "8" to 8,
        "7" to 7,
        "6" to 6,
        "5" to 5,
        "4" to 4,
        "3" to 3,
        "2" to 2
    )

    private fun score(cards: List<String>): Long {
        val groups = cards.groupBy { it }
            .values.map { it.size }
            .sortedDescending()

        var score: Long = 0
        cards.forEachIndexed { index, card ->
            score += (10.toDouble().pow((4 - index) * 2) * cardValues[card]!!).toLong()
        }

        if (groups[0] == 5) {
            score += 70000000000
        } else if (groups[0] == 4) {
            score += 60000000000
        } else if (groups[0] == 3 && groups[1] == 2) {
            score += 50000000000
        } else if (groups[0] == 3) {
            score += 40000000000
        } else if (groups[0] == 2 && groups[1] == 2) {
            score += 30000000000
        } else if (groups[0] == 2) {
            score += 20000000000
        } else {
            score += 10000000000
        }

        return score
    }

    private fun parseHand(line: String): Hand {
        val splits = line.split(" ")
        val cards = splits[0].split("")
            .filter { it.isNotEmpty() }

        return Hand(cards, Integer.valueOf(splits[1]), score(cards))
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val hands = lines.map { parseHand(it) }
            .sortedBy { it.score }
//        println("hands: ${ hands }")

        var total = 0
        hands.forEachIndexed { index, hand ->
            total += ((index + 1) * hand.bid)
        }

        println("total: ${ total }")

    }
}

class PartB {
    val cardValues: Map<String, Int> = mapOf(
        "A" to 14,
        "K" to 13,
        "Q" to 12,
        "J" to 1,
        "T" to 10,
        "9" to 9,
        "8" to 8,
        "7" to 7,
        "6" to 6,
        "5" to 5,
        "4" to 4,
        "3" to 3,
        "2" to 2
    )

    private fun score(cards: List<String>): Long {
        val rawGroups = cards.groupBy { it }
        val jokerCount = rawGroups["J"]?.size ?: 0

        val groups = rawGroups.filterKeys { it != "J" }
            .values.map { it.size }
            .sortedDescending()

        var score: Long = 0
        cards.forEachIndexed { index, card ->
            score += (10.toDouble().pow((4 - index) * 2) * cardValues[card]!!).toLong()
        }

        val firstCardCount = (groups.getOrElse(0) { 0 }) + jokerCount

        if (firstCardCount == 5) {
            score += 70000000000
        } else if (firstCardCount == 4) {
            score += 60000000000
        } else if (firstCardCount == 3 && groups[1] == 2) {
            score += 50000000000
        } else if (firstCardCount == 3) {
            score += 40000000000
        } else if (firstCardCount == 2 && groups[1] == 2) {
            score += 30000000000
        } else if (firstCardCount == 2) {
            score += 20000000000
        } else {
            score += 10000000000
        }

        return score
    }

    private fun parseHand(line: String): Hand {
        val splits = line.split(" ")
        val cards = splits[0].split("")
            .filter { it.isNotEmpty() }

        return Hand(cards, Integer.valueOf(splits[1]), score(cards))
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val hands = lines.map { parseHand(it) }
            .sortedBy { it.score }
        println("hands:\n${ hands.joinToString("\n") }")

        var total = 0
        hands.forEachIndexed { index, hand ->
            total += ((index + 1) * hand.bid)
        }

        println("total: ${ total }")
        // 252558854 NOT RIGHT

    }
}

fun main() {
//    PartA().run()
    PartB().run()
}