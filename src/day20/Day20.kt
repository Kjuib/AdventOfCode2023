package day20

import java.io.File

class PartA {

    data class Module(val label: String, val type: String, val destination: List<String>) {
        var flipflopStatus = "off"
        val lastPulse: MutableMap<String, String> = mutableMapOf()
    }

    data class Command(val fromModule: String, val toModule: String, val pulse: String)

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example1.txt").readLines()
//        val lines =  File("./src/${ this.javaClass.packageName }/example2.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val modules: Map<String, Module> = lines.associate {
            val splits = it.split(" -> ")
            if (splits[0].startsWith("broadcaster")) {
                splits[0] to Module(splits[0], "broadcaster", splits[1].split(", "))
            } else if (splits[0].startsWith("%")) {
                val label = splits[0].substring(1)
                label to Module(label, "flipflop", splits[1].split(", "))
            } else if (splits[0].startsWith("&")) {
                val label = splits[0].substring(1)
                label to Module(label, "conjunction", splits[1].split(", "))
            } else {
                throw Exception("Unknown parse: ${it}")
            }
        }

        modules.values.forEach { parentModule ->
            parentModule.destination.forEach { childModuleName ->
                val childModule = modules[childModuleName]
                if (childModule != null && childModule.type == "conjunction") {
                    childModule.lastPulse[parentModule.label] = "low"
                }
            }
        }

        val counts: MutableMap<String, Int> = mutableMapOf("low" to 0, "high" to 0)

        for (i in 0..<1000) {
            val stack: MutableList<Command> = mutableListOf(Command("button", "broadcaster", "low"))
            while (stack.isNotEmpty()) {
                val command = stack.removeFirst()
                counts[command.pulse] = counts[command.pulse]!! + 1
                val module = modules[command.toModule]

                if (module == null) {
                    // ignore if module is missing
                } else if (module.type == "broadcaster") {
                    module.destination.forEach {
                        stack.add(Command(module.label, it, command.pulse))
                    }
                } else if (module.type == "flipflop") {
                    if (command.pulse == "low") {
                        if (module.flipflopStatus == "on") {
                            module.flipflopStatus = "off"
                            module.destination.forEach {
                                stack.add(Command(module.label, it, "low"))
                            }
                        } else {
                            module.flipflopStatus = "on"
                            module.destination.forEach {
                                stack.add(Command(module.label, it, "high"))
                            }
                        }
                    }
                } else if (module.type == "conjunction") {
                    module.lastPulse[command.fromModule] = command.pulse
                    if (module.lastPulse.values.all { it == "high" }) {
                        module.destination.forEach {
                            stack.add(Command(module.label, it, "low"))
                        }
                    } else {
                        module.destination.forEach {
                            stack.add(Command(module.label, it, "high"))
                        }
                    }
                }
            }
        }

        println("counts: ${ counts }")
        println("Total: ${counts["low"]!! * counts["high"]!!}")
    }
}

class PartB {

    data class Module(val label: String, val type: String, var destination: List<String>) {
        var flipflopStatus = "off"
        var lastPulse: MutableMap<String, String> = mutableMapOf()

        override fun toString(): String {
            if (this.type == "broadcaster") {
                return "broadcaster"
            } else if (this.type == "flipflop") {
                return "${this.label}\t:${this.flipflopStatus}\t"
            } else if (this.type == "conjunction") {
                return "${this.label}\t:${this.lastPulse.toSortedMap().map { "${it.key}:${it.value}\t" }}"
            } else {
                return ""
            }
        }
    }

    data class Command(val fromModule: String, val toModule: String, val pulse: String)

    private fun run2(modules: Map<String, Module>): Int {
        val counts: MutableMap<String, Int> = mutableMapOf("low" to 0, "high" to 0)

        for (i in 1..100000) {
            val stack: MutableList<Command> = mutableListOf(Command("button", "broadcaster", "low"))
            val rxCounts: MutableMap<String, Int> = mutableMapOf("low" to 0, "high" to 0)
            while (stack.isNotEmpty()) {
                val command = stack.removeFirst()
                counts[command.pulse] = counts[command.pulse]!! + 1
                if (command.toModule == "rx") {
                    rxCounts[command.pulse] = counts[command.pulse]!! + 1
                }
                val module = modules[command.toModule]

                if (module == null) {
                    // ignore if module is missing
                } else if (module.type == "broadcaster") {
                    module.destination.forEach {
                        stack.add(Command(module.label, it, command.pulse))
                    }
                } else if (module.type == "flipflop") {
                    if (command.pulse == "low") {
                        if (module.flipflopStatus == "on") {
                            module.flipflopStatus = "off"
                            module.destination.forEach {
                                stack.add(Command(module.label, it, "low"))
                            }
                        } else {
                            module.flipflopStatus = "on"
                            module.destination.forEach {
                                stack.add(Command(module.label, it, "high"))
                            }
                        }
                    }
                } else if (module.type == "conjunction") {
                    module.lastPulse[command.fromModule] = command.pulse
                    if (module.lastPulse.values.all { it == "high" }) {
                        module.destination.forEach {
                            stack.add(Command(module.label, it, "low"))
                        }
                    } else {
                        module.destination.forEach {
                            stack.add(Command(module.label, it, "high"))
                        }
                    }
                }
            }
            if (rxCounts["low"]!! > 0) {
                println("rxCounts: ${ rxCounts }  with ${i} button clicks")
                return i
            }

//            println("modules: ${ modules.values }")
        }

//        println("counts: ${ counts }")
        println("NOT FOUND")
        return -1
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example1.txt").readLines()
//        val lines =  File("./src/${ this.javaClass.packageName }/example2.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        val modules: Map<String, Module> = lines.associate {
            val splits = it.split(" -> ")
            if (splits[0].startsWith("broadcaster")) {
                splits[0] to Module(splits[0], "broadcaster", splits[1].split(", "))
            } else if (splits[0].startsWith("%")) {
                val label = splits[0].substring(1)
                label to Module(label, "flipflop", splits[1].split(", "))
            } else if (splits[0].startsWith("&")) {
                val label = splits[0].substring(1)
                label to Module(label, "conjunction", splits[1].split(", "))
            } else {
                throw Exception("Unknown parse: ${it}")
            }
        }

        modules.values.forEach { parentModule ->
            parentModule.destination.forEach { childModuleName ->
                val childModule = modules[childModuleName]
                if (childModule != null && childModule.type == "conjunction") {
                    if (childModule.label == "hb") {
                        childModule.lastPulse[parentModule.label] = "high"
                    } else {
                        childModule.lastPulse[parentModule.label] = "low"
                    }
                }
            }
        }

        var total: Long = 1L
        (0..<4).map {
            val newModules = modules.map { entry ->
                val newModule = entry.value.copy(destination = entry.value.destination.toList())
                newModule.lastPulse = entry.value.lastPulse.toMutableMap()
                if (newModule.label == "broadcaster") {
                    newModule.destination = newModule.destination.subList(it, it+1)
                }
                newModule.label to newModule
            }.toMap()
            val result = run2(newModules)
            total *= result
        }
        println("total: ${ total }")
    }
}

fun main() {
//    PartA().run()
    PartB().run()
}