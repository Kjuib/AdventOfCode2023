package day19

import java.io.File
import java.math.BigDecimal

class PartA {

    data class Rule(val variable: String?, val sign: String, val value: Int?, val destination: String)

    private fun testPart(current: String, part: Map<String, Int>, rules: MutableMap<String, List<Rule>>): Boolean {
        if (current == "A") {
            return true
        } else if (current == "R") {
            return false
        }

        val ruleList = rules[current] ?: throw Exception("Unknown rule: ${current}")
        ruleList.forEach { rule ->
            if (rule.sign == "=") {
                return testPart(rule.destination, part, rules)
            } else if (rule.sign == "<") {
                if (part[rule.variable]!! < rule.value!!) {
                    return testPart(rule.destination, part, rules)
                }
            } else {
                if (part[rule.variable]!! > rule.value!!) {
                    return testPart(rule.destination, part, rules)
                }
            }
        }

        throw Exception("No valid rule: ${part}")
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val parseRule = Regex("^(\\D)([<>])(\\d+):(.*)\$")
        val rules: MutableMap<String, List<Rule>> = mutableMapOf()
        val parts: MutableList<Map<String, Int>> = mutableListOf()
        var part1 = true
        lines.forEach { line ->
            if (line.isEmpty()) {
                part1 = false
            } else if (part1) {
                val splits = line.split("{")
                val ruleList = splits[1].substring(0, splits[1].lastIndex).split(",").map { ruleLine ->
                    val match = parseRule.find(ruleLine)
                    if (match == null) {
                        Rule(null, "=", null, ruleLine)
                    } else {
                        Rule(match.groups[1]!!.value, match.groups[2]!!.value, match.groups[3]!!.value.toInt(), match.groups[4]!!.value)
                    }
                }
                rules[splits[0]] = ruleList
            } else {
                val partValues = line.substring(1, line.lastIndex).split(",")
                val partMap: Map<String, Int> = partValues.associate {
                    val splits = it.split("=")
                    splits[0] to splits[1].toInt()
                }
                parts.add(partMap)
            }
        }

        var total = 0L

        parts.forEach {
            if (testPart("in", it, rules)) {
                total += it.values.sum()
            }
        }

        println("total: ${ total }")

    }
}

class PartB {

    data class Rule(val variable: String?, val sign: String, val value: Int?, val destination: String)

    private fun testPart(current: String, part: Map<String, Int>, rules: MutableMap<String, List<Rule>>): Boolean {
        if (current == "A") {
            return true
        } else if (current == "R") {
            return false
        }

        val ruleList = rules[current] ?: throw Exception("Unknown rule: ${current}")
        ruleList.forEach { rule ->
            if (rule.sign == "=") {
                return testPart(rule.destination, part, rules)
            } else if (rule.sign == "<") {
                if (part[rule.variable]!! < rule.value!!) {
                    return testPart(rule.destination, part, rules)
                }
            } else {
                if (part[rule.variable]!! > rule.value!!) {
                    return testPart(rule.destination, part, rules)
                }
            }
        }

        throw Exception("No valid rule: ${part}")
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val parseRule = Regex("^(\\D)([<>])(\\d+):(.*)\$")
        val rules: MutableMap<String, List<Rule>> = mutableMapOf()
        var part1 = true
        lines.forEach { line ->
            if (line.isEmpty()) {
                part1 = false
            } else if (part1) {
                val splits = line.split("{")
                val ruleList = splits[1].substring(0, splits[1].lastIndex).split(",").map { ruleLine ->
                    val match = parseRule.find(ruleLine)
                    if (match == null) {
                        Rule(null, "=", null, ruleLine)
                    } else {
                        Rule(match.groups[1]!!.value, match.groups[2]!!.value, match.groups[3]!!.value.toInt(), match.groups[4]!!.value)
                    }
                }
                rules[splits[0]] = ruleList
            } else {
                // we can ignore
            }
        }

        val keyValues: Map<String, MutableList<Int>> = mapOf("x" to mutableListOf(1), "m" to mutableListOf(1), "a" to mutableListOf(1), "s" to mutableListOf(1))
        rules.values.forEach { ruleList ->
            ruleList.forEach { rule ->
                if (rule.value != null) {
                    keyValues[rule.variable]!!.add(rule.value)
                    if (rule.sign == "<") {
                        keyValues[rule.variable]!!.add(rule.value - 1)
                    } else {
                        keyValues[rule.variable]!!.add(rule.value + 1)
                    }
                }
            }
        }

        keyValues.values.forEach {
            it.sort()
        }

//        println("keyValues: ${ keyValues }")

        var total: BigDecimal = BigDecimal(0)
        for (indexX in 0..keyValues["x"]!!.lastIndex) {
            println("STARTING X: ${indexX}")
            val startX = keyValues["x"]!![indexX]
            val endX = keyValues["x"]!!.getOrElse(indexX + 1) { 4001 }

            for (indexM in 0..keyValues["m"]!!.lastIndex) {
                val startM = keyValues["m"]!![indexM]
                val endM = keyValues["m"]!!.getOrElse(indexM + 1) { 4001 }

                for (indexA in 0..keyValues["a"]!!.lastIndex) {
                    val startA = keyValues["a"]!![indexA]
                    val endA = keyValues["a"]!!.getOrElse(indexA + 1) { 4001 }

                    for (indexS in 0..keyValues["s"]!!.lastIndex) {
                        val startS = keyValues["s"]!![indexS]
                        val endS = keyValues["s"]!!.getOrElse(indexS + 1) { 4001 }

                        val part: Map<String, Int> = mapOf("x" to startX, "m" to startM, "a" to startA, "s" to startS)
                        if (testPart("in", part, rules)) {
                            var subtotal: BigDecimal = (endX - startX).toBigDecimal()
                            subtotal *= (endM - startM).toBigDecimal()
                            subtotal *= (endA - startA).toBigDecimal()
                            subtotal *= (endS - startS).toBigDecimal()

                            total += subtotal
                        }
                    }
                }
            }
        }

        println("total: ${ total }")
    }
}

fun main() {
//    PartA().run()
    PartB().run()
}