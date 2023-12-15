package day15

import util.StringUtil
import java.io.File

class PartA {

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val commands = lines[0].split(",")

        val results = commands.map { command ->
            var currentValue = 0
            command.forEach { char ->
                currentValue += char.code
                currentValue *= 17
                currentValue %= 256
            }
            currentValue
        }

        println("results: ${ results }")

        val total = results.sum()
        println("total: ${ total }")
    }
}

class PartB {

    private data class Command(val label: String, val operation: String, val focalLength: Int = 0) {
        companion object {
            fun parse(command: String): Command {
                if (command.contains("=")) {
                    val splits = command.split("=")
                    return Command(splits[0], "=", splits[1].toInt())
                } else if (command.contains("-")) {
                    val splits = command.split("-")
                    return Command(splits[0], "-")
                } else {
                    throw Exception("Unable to parse: ${command}")
                }
            }
        }

        override fun toString(): String {
            return "${label}${operation}${focalLength}"
        }
    }

    private fun hash(label: String): Int {
        var currentValue = 0
        label.forEach { char ->
            currentValue += char.code
            currentValue *= 17
            currentValue %= 256
        }
        return currentValue
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val commands = lines[0].split(",").map {
            Command.parse(it)
        }

        val stack: MutableMap<Int, MutableList<Command>> = mutableMapOf()
        commands.forEach { command ->
            val hash = hash(command.label)
            when (command.operation) {
                "=" -> {
                    if (!stack.containsKey(hash)) {
                        stack[hash] = mutableListOf()
                    }
                    val indexOfCurrent = stack[hash]?.indexOfFirst { it.label == command.label } ?: -1
                    if (indexOfCurrent < 0) {
                        stack[hash]?.add(command) ?: throw Exception("Missing key: ${hash}")
                    } else {
                        stack[hash]?.set(indexOfCurrent, command)
                    }
                }
                "-" -> {
                    stack[hash]?.removeIf { it.label == command.label } // ignore if not there
                }
                else -> {
                    throw Exception("Unknown command: ${command}")
                }
            }

//            println("stack: ${ stack }")
        }

        val results = stack.flatMap { box ->
            box.value.mapIndexed { index, command ->
                (1 + box.key) * (1 + index) * (command.focalLength)
            }
        }
//        println("results: ${ results }")

        val total = results.sum()
        println("total: ${ total }")
    }

}

fun main() {
//    PartA().run()
    PartB().run()
}