package day05

import util.StringUtil
import java.io.File

class PartA {
    data class ElfMap(val destination: Long, val source: Long, val length: Long)

    private fun getDestination(source: Long, elfMap: List<ElfMap>): Long {
        elfMap.forEach {
            if (source >= it.source && source <= (it.source + it.length)) {
                return it.destination + (source - it.source)
            }
        }

        return source
    }

    private fun calcLocation(seed: Long, maps: Map<String, List<ElfMap>>): Long {
//        println("\n\nseed: ${ seed }")
        var currentKey = seed
        maps.forEach {
            currentKey = getDestination(currentKey, it.value)
//            println("currentKey: ${ currentKey }")
        }

        return currentKey
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        var seeds: List<Long> = listOf()
        val maps: MutableMap<String, MutableList<ElfMap>> = mutableMapOf()
        var currentMap: String = ""

        lines.forEach { line ->
            if (line.isEmpty()) {
                // do nothing
            } else if (line.startsWith("seeds:")) {
                seeds = StringUtil.parseLong(line.split(":")[1])
            } else if (line.contains(" map:")) {
                currentMap = line.split(" ")[0]
                maps[currentMap] = mutableListOf()
            } else {
                val values = StringUtil.parseLong(line)
                maps[currentMap]?.add(ElfMap(values[0], values[1], values[2]))
            }
        }

        val locations = seeds.map {
            calcLocation(it, maps)
        }

        val min = locations.min()

        println("min: ${ min }")
    }
}

class PartB {
    data class ElfMap(val destination: Long, val source: Long, val length: Long)

    private fun getDestination(source: Long, elfMap: List<ElfMap>): Long {
        elfMap.forEach {
            if (source >= it.source && source < (it.source + it.length)) {
                return it.destination + (source - it.source)
            }
        }

        return source
    }

    private fun calcLocation(seed: Long, maps: Map<String, List<ElfMap>>): Long {
//        println("\n\nseed: ${ seed }")
        var currentKey = seed
        maps.forEach {
            currentKey = getDestination(currentKey, it.value)
//            println("currentKey: ${ currentKey }")
        }

        return currentKey
    }

    fun run() {
//        val lines =  File("./src/${ this.javaClass.packageName }/example.txt").readLines()
        val lines =  File("./src/${ this.javaClass.packageName }/input.txt").readLines()

        var seeds: List<Long> = listOf()
        val maps: MutableMap<String, MutableList<ElfMap>> = mutableMapOf()
        var currentMap: String = ""

        lines.forEach { line ->
            if (line.isEmpty()) {
                // do nothing
            } else if (line.startsWith("seeds:")) {
                seeds = StringUtil.parseLong(line.split(":")[1])
            } else if (line.contains(" map:")) {
                currentMap = line.split(" ")[0]
                maps[currentMap] = mutableListOf()
            } else {
                val values = StringUtil.parseLong(line)
                maps[currentMap]?.add(ElfMap(values[0], values[1], values[2]))
            }
        }

        var currentMin = Long.MAX_VALUE

        for (i in seeds.indices step 2) {
            val seedStart = seeds[i]
            val seedLength = seeds[i + 1]
            val seedEnd = seedStart + seedLength - 1

            println("STARTING SEED:\t${i}\t${seedStart}\t\t${seedLength}\t\t${seedEnd}\t\t::\t${currentMin}")

            for (s in seedStart..seedEnd) {
                val location = calcLocation(s, maps)
                if (location < currentMin) {
                    currentMin = location
                }
            }
        }

        println("currentMin: ${ currentMin }")
        // TOO HIGH: 7873085
    }
}

fun main() {
//    PartA().run()
    PartB().run()
}