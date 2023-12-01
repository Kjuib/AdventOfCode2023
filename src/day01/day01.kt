package day01

import java.io.File

val example = """
    1abc2
    pqr3stu8vwx
    a1b2c3d4e5f
    treb7uchet
"""
val example2 = """
    two1nine
    eightwothree
    abcone2threexyz
    xtwone3four
    4nineeightseven2
    zoneight234
    7pqrstsixteen
"""

class PartA {

    private fun handleLine(line: String): Int {
        val digit1 = line.find { it.isDigit() }
        val digit2 = line.findLast { it.isDigit() }
        val num = "${ digit1 }${ digit2 }"

        return Integer.valueOf(num)
    }

    fun run() {
//        val lines = example.trimIndent().split('\n')
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val scores = lines.map { handleLine(it) }
        val total = scores.sum()

        println("total: ${ total }")

    }
}

class PartB {

    private val validDigits = mapOf(
        "0" to 0,
        "zero" to 0,
        "1" to 1,
        "one" to 1,
        "2" to 2,
        "two" to 2,
        "3" to 3,
        "three" to 3,
        "4" to 4,
        "four" to 4,
        "5" to 5,
        "five" to 5,
        "6" to 6,
        "six" to 6,
        "7" to 7,
        "seven" to 7,
        "8" to 8,
        "eight" to 8,
        "9" to 9,
        "nine" to 9,
    )

    private fun handleLine(line: String): Int {
        var first: Pair<Int, Int> = Pair(999999, 0)
        var last: Pair<Int, Int> = Pair(-1, 0)

        validDigits.forEach {
            val firstIndex = line.indexOf(it.key)
            if (firstIndex > -1 && firstIndex < first.first) {
                first = Pair(firstIndex, it.value)
            }

            val lastIndex = line.lastIndexOf(it.key)
            if (lastIndex > -1 && lastIndex > last.first) {
                last = Pair(lastIndex, it.value)
            }
        }
        val num = (first.second * 10) + last.second
        println("line: ${ num }\t\t${ line }")

        return (first.second * 10) + last.second
    }

    fun run() {
//        val lines = example2.trimIndent().split('\n')
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val scores = lines.map { handleLine(it) }
        val total = scores.sum()

        println("total: ${ total }")

    }
}

fun main() {
//    PartA().run()
    PartB().run()
}