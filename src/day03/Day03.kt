package day03

import java.io.File
import java.lang.Exception

data class FoundNumber(val number: Int, val rowIndex: Int, val startIndex: Int, val endIndex: Int)

class PartA {
    private val findNumbersRegex = Regex("\\D*(\\d+)\\D*")
    private val findSymbol = Regex("[^.\\d]")

    private fun validateNumber(number: FoundNumber, lines: List<String>): Boolean {
        for (row in (number.rowIndex - 1)..(number.rowIndex + 1)) {
            for (col in (number.startIndex - 1)..(number.endIndex + 1)) {
                val char = lines.getOrNull(row)?.getOrNull(col)
//                println("char: ${ char }  AND  ${ findSymbol.matches(char.toString()) }")
                if (char != null && findSymbol.matches(char.toString())) {
                    return true
                }
            }
        }

        return false
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val numbers = ArrayList<FoundNumber>()
        val validNumbers = ArrayList<FoundNumber>()

        lines.forEachIndexed { rowIndex, line ->
            val matchList = findNumbersRegex.findAll(line)
            val names = matchList.forEach { match ->
                val group = match.groups[1] ?: throw Exception("Unknown Group: ${line}")

                numbers.add(FoundNumber(
                    Integer.valueOf(group.value),
                    rowIndex,
                    group.range.first,
                    group.range.last
                ))
            }
        }

        numbers.forEach { number ->
            if (validateNumber(number, lines)) {
                validNumbers.add(number)
            }
        }

//        println("numbers:\n${ validNumbers.joinToString("\n") } }")

        val total = validNumbers.sumOf { it.number }

        println("total: ${ total }")
    }
}

class PartB {
    data class Gear(val row: Int, val col: Int)

    private val findNumbersRegex = Regex("\\D*(\\d+)\\D*")

    private val foundGears = mutableMapOf<Gear, List<FoundNumber>>()

    private fun scanForGears(number: FoundNumber, lines: List<String>): Unit {
        for (row in (number.rowIndex - 1)..(number.rowIndex + 1)) {
            for (col in (number.startIndex - 1)..(number.endIndex + 1)) {
                val char = lines.getOrNull(row)?.getOrNull(col)
//                println("char: ${ char }  AND  ${ findSymbol.matches(char.toString()) }")
                if (char == '*') {
                    val foundGear = Gear(row, col)
                    val oldList = foundGears[foundGear] ?: listOf()

                    foundGears[foundGear] = oldList + number
                }
            }
        }
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val numbers = ArrayList<FoundNumber>()
        val validNumbers = ArrayList<FoundNumber>()

        lines.forEachIndexed { rowIndex, line ->
            val matchList = findNumbersRegex.findAll(line)
            val names = matchList.forEach { match ->
                val group = match.groups[1] ?: throw Exception("Unknown Group: ${line}")

                numbers.add(FoundNumber(
                    Integer.valueOf(group.value),
                    rowIndex,
                    group.range.first,
                    group.range.last
                ))
            }
        }

        numbers.forEach { number ->
            scanForGears(number, lines)
        }

        val validGears = foundGears.filter {
            it.value.size == 2
        }

//        println("validGears: ${ validGears }")

        val total = validGears.values.sumOf { it[0].number * it[1].number }

        println("total: ${ total }")
    }
}

fun main() {
//    PartA().run()
    PartB().run()
}