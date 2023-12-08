package day08

import java.io.File
import java.math.BigDecimal
import kotlin.math.pow

class PartA {
    data class Instruction(val current: String, val left: String, val right: String)

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example2.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val parseRegex = Regex("([A-Z]{3}).*([A-Z]{3}),\\s([A-Z]{3})")

        val path: String = lines[0]
        val instructions = lines.subList(2, lines.size).map {
            val matchResult = parseRegex.find(it) ?: throw Exception("Unable to parse line: ${ it }")
            matchResult.groupValues[1] to Instruction(matchResult.groupValues[1], matchResult.groupValues[2], matchResult.groupValues[3])
        }.toMap()

        var current = "AAA"
        var currentIndex = 0
        var count = 0
        while (current != "ZZZ") {
            val move = path[currentIndex]
            val currentInstruction = instructions[current] ?: throw Exception("Unable to find instruction: ${ current }")

            if (move == 'R') {
                current = currentInstruction.right
            } else {
                current = currentInstruction.left
            }

            count++
            currentIndex++
            if (currentIndex >= path.length) {
                currentIndex = 0
            }
        }

        println("count: ${ count }")
    }
}

class PartB {
    data class Instruction(val current: String, val left: String, val right: String)

    private fun findLeastCommon(num1: Long, num2: Long): Long {
        val larger = if (num1 > num2) num1 else num2
        val maxLcm = num1 * num2
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % num1 == 0.toLong() && lcm % num2 == 0.toLong()) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    private fun findLeastCommon(nums: List<Long>): Long {
        var result = nums[0]
        for (i in 1 until nums.size) {
            result = findLeastCommon(result, nums[i])
        }
        return result
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example3.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val parseRegex = Regex("(.{3}).*(.{3}),\\s(.{3})")

        val path: String = lines[0]
        val instructions = lines.subList(2, lines.size).map {
            val matchResult = parseRegex.find(it) ?: throw Exception("Unable to parse line: ${ it }")
            matchResult.groupValues[1] to Instruction(matchResult.groupValues[1], matchResult.groupValues[2], matchResult.groupValues[3])
        }.toMap()

        val starting = instructions.keys.mapNotNull { if (it.endsWith("A")) it else null }

        println("starting: ${ starting }")

        val counts = starting.map {
            var current = it
            var currentIndex = 0
            var count: Long = 0
            while (!current.endsWith("Z")) {
                val move = path[currentIndex]
//                println("CURRENT: ${ current } ${ move }")
                val currentInstruction = instructions[current] ?: throw Exception("Unable to find instruction: ${ it }")
                if (move == 'R') {
                    current = currentInstruction.right
                } else {
                    current = currentInstruction.left
                }

                count++
                currentIndex++
                if (currentIndex >= path.length) {
                    currentIndex = 0
                }
            }

            count
        }

        println("counts: ${ counts }")

        val leastCommon = findLeastCommon(counts)

        println("leastCommon: ${ leastCommon }")
    }
}

fun main() {
//    PartA().run()
    PartB().run()
}