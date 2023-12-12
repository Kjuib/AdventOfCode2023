package day12

import util.StringUtil
import java.io.File
import kotlin.math.pow
import kotlin.time.measureTime

class PartA {

    data class Condition(val code: String, val springs: List<Int>) {
        private val regex = Regex("^\\.*?${springs.joinToString("\\.+?") { "#{${it}}" }}\\.*\$")

        fun match(str: String): Boolean {
            return regex.matches(str)
        }
    }

    private fun getCodeOptions(code: String): List<String> {
        if (code.contains("?")) {
            return getCodeOptions(code.replaceFirst("?", "#")) + getCodeOptions(code.replaceFirst("?", "."))
        } else {
            return listOf(code)
        }
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val conditions = lines.map {
            val splits = it.split(" ")

            Condition(splits[0], StringUtil.parseInt(splits[1], ","))
        }

        val results = conditions.map {condition ->
            val options = getCodeOptions(condition.code)
            val matches = options.map {option ->
                condition.match(option)
            }
            val groups = matches.groupBy { it }
            groups[true]?.size ?: 0
        }

        val total = results.sum()
        println("total: ${ total }")
    }
}

class PartB {

    private val memoize = mutableMapOf<String, Long>()

    private fun countOptionsMemoized(code: String, nums: List<Int>): Long {
        val key = "${code}::${nums.joinToString(",")}"
        return memoize.getOrElse(key) {
            val result = countOptions(code, nums)
            memoize.put(key, result)
            result
        }
    }

    private fun countOptions(code: String, nums: List<Int>): Long {
        if (code.isEmpty()) {
            // we ran out of code
            if (nums.isEmpty()) {
                // but we ran out of numbers too
                return 1 // found match
            } else {
                return 0 // no match
            }
        }

        if (nums.isEmpty()) {
            // we ran out of number but we have code left
            if (code.contains("#")) {
                return 0 // no match
            } else {
                return 1 // found match
            }
        }

        if (code.length < nums.sum() + nums.size - 1) {
            // we don't have enough code for the rest of the nums
            return 0
        }

        if (code[0] == '#') {
            // we need to place a num here
            val nextNum = nums[0]
            if (code.substring(0, nextNum).contains(".")) {
                // not enough room for our next num
                return 0
            } else if ((code.getOrNull(nextNum) ?: '0') == '#') {
                // too many # for our num
                return 0
            }

            if (code.length == nextNum) {
                return countOptionsMemoized("", nums.subList(1, nums.size))
            } else {
                return countOptionsMemoized(code.substring(nextNum + 1), nums.subList(1, nums.size))
            }
        } else if (code[0] == '.') {
            // we need an empty space... just keep trying
            return countOptionsMemoized(code.substring(1), nums)
        } else {
            // who knows what this is? branching
            return countOptionsMemoized("#" + code.substring(1), nums) + countOptionsMemoized("." + code.substring(1), nums)
        }
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val results = lines.mapIndexed { index, line ->
            val splits = line.split(" ")
            val code = splits[0]
            val nums = StringUtil.parseInt(splits[1], ",")

//            val options1 = countOptions(code, nums)
//            val options2 = countOptions("${code}?${code}", nums + nums)
            val options5 = countOptions("${code}?${code}?${code}?${code}?${code}", nums + nums + nums + nums + nums)

            println("RUNNING: ${index}\t${options5}")
//            (((options2 / options1).toDouble().pow(3).toLong()) * options2)
            options5
        }

        val total = results.sum()
        println("total: ${ total }")
        // 16500201918932 - too low
    }
}

fun main() {
//    PartA().run()
    PartB().run()
}